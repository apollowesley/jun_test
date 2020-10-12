package com.dtdream.rdic.saas.prj;

import java.util.HashSet;
import java.util.Iterator;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by Ozz8 on 2015/07/18.
 */
public class SecureHashSet<T> extends ReentrantLock {
    HashSet<T> set;

    public SecureHashSet() {
        set = new HashSet<>(10);
    }

    public SecureHashSet(int size) {
        set = new HashSet<>(size);
    }

    public void put (T object) {
        this.lock();
        if (! set.contains(object))
            set.add(object);
        this.unlock();
    }

    public T get () {
        T object = null;
        Iterator<T> it = set.iterator();
        this.lock();
        if (it.hasNext()) {
            object = it.next();
            it.remove();
        }
        this.unlock();
        return object;
    }
}
