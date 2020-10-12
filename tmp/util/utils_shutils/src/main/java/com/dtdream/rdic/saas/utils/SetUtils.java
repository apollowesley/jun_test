package com.dtdream.rdic.saas.utils;

import com.dtdream.rdic.saas.def.Checker;
import com.dtdream.rdic.saas.def.Constant;
import com.dtdream.rdic.saas.intf.*;
import com.dtdream.rdic.saas.intf.Comparator;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;

/**
 * Created by Ozz8 on 2015/06/25.
 */
public class SetUtils {
    public static String join (List list, Character sep, Character prefix, Character suffix) {
        StringBuilder sb = new StringBuilder();
        if (null == list || list.size() <= 0)
            return sb.toString();
        for (int i = 0; i < list.size(); ++ i) {
            if (0 != i)
                sb.append(sep);
            if (null != prefix)
                sb.append(prefix);
            sb.append(list.get(i));
            if (null != suffix)
                sb.append(suffix);
        }
        return sb.toString();
    }

    public static String join (Object[] objects, Character sep, Character prefix, Character suffix) {
        StringBuilder sb = new StringBuilder();
        if (null == objects || objects.length <= 0)
            return sb.toString();
        for (int i = 0; i < objects.length; ++ i) {
            if (0 != i)
                sb.append(sep);
            if (null != prefix)
                sb.append(prefix);
            sb.append(objects[i]);
            if (null != suffix)
                sb.append(suffix);
        }
        return sb.toString();
    }

    public static<F,T> List<T> convert (Collection<F> list, Class<T> clazz) {
        if (null == list || null == clazz)
            return null;
        List<T> results = new ArrayList<>();
        Iterator<F> it = list.iterator();
        T o;
        while (it.hasNext()) {
            o = BeanUtils.convert(it.next(), clazz);
            if (null != o)
                results.add(o);
        }
        return results;
    }

    public static<F,T> List<T> convert (Collection<F> set, Class<T> clazz, Converter<F,T> converter) {
        if (null == set || null == clazz || null == converter)
            return null;
        List<T> results = new ArrayList<>();
        T o;
        Iterator<F> it = set.iterator();
        while (it.hasNext()) {
            o = converter.convert(it.next(), clazz);
            if (null != o)
                results.add(o);
        }
        return results;
    }

    public static<F,T> T convert (F src, Class<T> clazz, Converter<F,T> converter) {
        if (null == src || null == clazz || null == converter)
            return null;
        return converter.convert(src, clazz);
    }

    public static<S,V> boolean contains (Collection<S> collection, V value, Comparator<S,V> comparator) {
        if (null == collection)
            return false;
        Iterator<S> it = collection.iterator();
        while (it.hasNext()) {
            if (comparator.eq(it.next(), value))
                return true;
        }
        return false;
    }

    public static<S,V> boolean contains (Collection<S> collection, Collection<V> values, Comparator<S,V> comparator) {
        if (null == collection)
            return false;
        int count = 0;
        Iterator<V> it = values.iterator();
        while (it.hasNext()) {
            if (contains(collection, it.next(), comparator))
                ++ count;
        }
        return count == values.size();
    }

    public static<K,T> Map<K,T> mkmap (Collection<T> collection, Getter<K,T> getter) {
        if (null == collection || null == getter)
            return null;
        Map<K,T> results = new HashMap<>();
        Iterator<T> it = collection.iterator();
        T item;
        while(it.hasNext()) {
            item = it.next();
            results.put(getter.get(item), item);
        }
        return results;
    }

    /**
     * 遍历集合执行特定操作，如果指定了adder，则会尝试在返回结果0位置返回一个adder.add结果的总和
     * 换句话，如果指定了adder，那么实际上累加操作的个数比返回结果集个数少1，因为要排除元素0
     * @param collection
     * @param worker
     * @param checker
     * @param adder
     * @param initial
     * @param <R>
     * @param <T>
     * @return
     */
    public static<R,T> List<R> foreach
        (
            Collection<T> collection,
            Worker<R,T> worker,
            Checker<T> checker,
            Calculator<R,R> adder,
            R initial
        )
    {
        if (null == collection || null == worker)
            return null;
        Iterator<T> it = collection.iterator();
        List<R> results = new ArrayList<>();
        T item;
        if (null != adder) {
            results.add(0, initial);
        }
        R sum = initial;
        R result;
        int index = results.size();
        while (it.hasNext()) {
            item = it.next();
            if (null == checker || checker.check(item)) {
                result = worker.work(item);
                if (null != result) {
                    results.add(index, result);
                    if (null != adder)
                        sum = adder.add(sum, result);
                    ++index;
                }
            }
        }
        if (null != adder)
            results.set(0, sum);
        return results;
    }

    /**
     *
     * @param objects
     * @param worker
     * @param checker
     * @param adder
     * @param initial
     * @param <R>
     * @param <T>
     * @return
     */
    public static<R,T> List<R> foreach
        (
            T[] objects,
            Worker<R,T> worker,
            Checker<T> checker,
            Calculator<R,R> adder,
            R initial
        )
    {
        if (null == objects || null == worker)
            return null;
        List<R> results = new ArrayList<>();
        T item;
        if (null != adder) {
            results.add(0, initial);
        }
        R sum = initial;
        R result;
        int index = results.size();
        for (int i = 0; i < objects.length; ++ i) {
            item = objects[i];
            if (null == checker || checker.check(item)) {
                result = worker.work(item);
                if (null != result) {
                    results.add(index, result);
                    if (null != adder)
                        sum = adder.add(sum, result);
                    ++index;
                }
            }
        }
        if (null != adder)
            results.set(0, sum);
        return results;
    }

    public static Collection reset (Collection src, Collection target) {
        if (null != target) {
            src.clear();
            src.addAll(target);
        }
        return src;
    }

    public static<T> Collection<T> clear (Collection<T> src, String method, Object expected, Class<?>... parametertypes) {
        if (null == src || src.size() <= 0)
            return src;
        Class clazz = expected.getClass();
        Iterator<T> it = src.iterator();
        T item = null;
        Method getIsdel;
        while (it.hasNext()) {
            item = it.next();
            try {
                getIsdel = item.getClass().getMethod(method, parametertypes);
                if (getIsdel.getReturnType() == clazz)
                    if (getIsdel.invoke(item) != expected)
                        it.remove();
            } catch (NoSuchMethodException e) {
                continue;
            } catch (InvocationTargetException e) {
                continue;
            } catch (IllegalAccessException e) {
                continue;
            }
        }
        return src;
    }

    public static<T> Collection<T> clear (Collection<T> src, Checker<T> checker) {
        Iterator<T> it = src.iterator();
        while (it.hasNext()) {
            if (! checker.check(it.next()))
                it.remove();
        }
        return src;
    }

    public static<T> List<T> clear (Collection<T> src, Checker<T> checker, java.util.Comparator comparator) {
        List<T> results = null;
        if (null != comparator)
            results = new ArrayList<>(src.size());
        T object;
        Iterator<T> it = src.iterator();
        while (it.hasNext()) {
            object = it.next();
            if (! checker.check(object)) {
                it.remove();
                continue;
            }
            if (null != comparator)
                results.add(object);
        }
        if (null != comparator)
            Collections.sort(results, comparator);
        return results;
    }
}
