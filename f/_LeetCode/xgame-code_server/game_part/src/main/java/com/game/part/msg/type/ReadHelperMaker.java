package com.game.part.msg.type;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.nio.ByteBuffer;
import java.text.MessageFormat;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtConstructor;
import javassist.CtMethod;
import javassist.CtNewMethod;

import com.game.part.msg.MsgError;
import com.game.part.msg.MsgLog;
import com.game.part.msg.MsgServ;
import com.game.part.util.Assert;
import com.game.part.util.ClazzUtil;
import com.game.part.util.FieldUtil;

/**
 * 读取帮助器构建者
 * 
 * @author hjj2017
 * @since 2015/3/15
 * 
 */
public final class ReadHelperMaker {
    /** 帮助者字典 */
    private static final Map<Class<?>, IReadHelper> _helperMap = new ConcurrentHashMap<>();
    /** 计数器 */
    private static AtomicInteger _counter = new AtomicInteger(0);

    /**
     * 类默认构造器
     *
     */
    private ReadHelperMaker() {
    }

    /**
     * 构建帮助器
     * 
     * @param byClazz
     * @return
     * 
     */
    public static IReadHelper make(Class<?> byClazz) {
        // 断言参数不为空
        Assert.notNull(byClazz, "byClazz");
        // 获取帮助者
        IReadHelper helper = _helperMap.get(byClazz);

        if (helper == null) {
            try {
                // 构建帮助者类并创建对象
                Class<IReadHelper> clazz = buildHelperClazz(byClazz);
                helper = clazz.newInstance();
                // 缓存到字典
                _helperMap.put(byClazz, helper);
            } catch (MsgError err) {
                // 直接抛出异常
                throw err;
            } catch (Exception ex) {
                // 抛出异常
                MsgLog.LOG.error(ex.getMessage(), ex);
                throw new MsgError(ex);
            }
        }

        return helper;
    }

    /**
     * 构建帮助者类
     * 
     * @param byClazz
     * @return
     * 
     */
    private static Class<IReadHelper> buildHelperClazz(Class<?> byClazz) {
        // 断言参数不为空
        Assert.notNull(byClazz, "byClazz");
        //
        // 设置解析器名称
        // 注意 : 在这里使用了 1 个计数器,
        // 目的是为了避免 byClazz 为匿名类!
        // 匿名类的 simpleName 为空
        final String helperClazzName = MessageFormat.format(
            "{0}.ReadHelper_{1}_{2}",
            byClazz.getPackage().getName(),
            byClazz.getSimpleName(),
            String.valueOf(_counter.incrementAndGet())
        );

        try {
            // 获取类池
            ClassPool pool = ClassPool.getDefault();
            // 获取接口类
            CtClass helperInterface = pool.getCtClass(IReadHelper.class.getName());
            // 
            // 创建解析器 JAVA 类
            // 会生成如下代码 :
            // public class ReadHelper_CGUpgradeBuilding implements IReadHelper 
            CtClass cc = pool.makeClass(helperClazzName);
            cc.addInterface(helperInterface);
            // 
            // 设置默认构造器
            // 会生成如下代码 :
            // ReadHelper_CGUpgradeBuilding() {}
            putDefaultConstructor(cc);

            // 创建代码上下文
            CodeContext codeCtx = new CodeContext();            
            // 
            // 将所有必须的类都导入进来, 
            // 会生成如下代码 : 
            // import org.apache.mina.core.buffer.IoBuffer;
            // import com.game.part.msg.type.AbstractMsgObj;
            // import byClazz;
            codeCtx._importClazzSet.add(ByteBuffer.class);
            codeCtx._importClazzSet.add(AbstractMsgObj.class);
            codeCtx._importClazzSet.add(byClazz);
            // 构建函数体
            buildFuncText(byClazz, codeCtx);

            // 生成方法之前先导入类
            importPackage(pool, codeCtx._importClazzSet);

            // 创建解析方法
            CtMethod cm = CtNewMethod.make(
                codeCtx._codeText.toString(), cc
            );

            // 添加方法
            cc.addMethod(cm);

            if (MsgServ.OBJ._outputClazzToDir != null &&
                MsgServ.OBJ._outputClazzToDir.isEmpty() == false) {
                // 如果输出目录不为空, 
                // 则写出类文件用作调试
                cc.writeFile(MsgServ.OBJ._outputClazzToDir);
            }

            // 获取 JAVA 类
            @SuppressWarnings("unchecked")
            Class<IReadHelper> javaClazz = (Class<IReadHelper>)cc.toClass();
            // 返回 JAVA 类
            return javaClazz;
        } catch (Exception ex) {
            // 抛出异常
            throw new MsgError(ex);
        }
    }

    /**
     * 构建函数文本
     * 
     * @param byClazz
     * @param codeCtx
     * @return 
     * 
     */
    private static void buildFuncText(Class<?> byClazz, CodeContext codeCtx) {
        // 断言参数不为空
        Assert.notNull(byClazz, "byClazz");
        Assert.notNull(codeCtx, "codeCtx");

        // 函数头
        codeCtx._codeText.append("public void readBuff(AbstractMsgObj msgObj, ByteBuffer buff) {\n");
        // 增加空值判断
        codeCtx._codeText.append("if (msgObj == null || buff == null) { return; }\n");
        // 定义大 O 参数避免转型问题
        codeCtx._codeText.append(byClazz.getSimpleName())
            .append(" O = (")
            .append(byClazz.getSimpleName())
            .append(")msgObj;\n");

        // 构建字段赋值文本
        buildFieldAssignText(byClazz, codeCtx);
        // 函数脚
        codeCtx._codeText.append("}");
    }

    /**
     * 构建字段赋值文本
     * 
     * @param byClazz
     * @param codeCtx
     * 
     */
    private static void buildFieldAssignText(Class<?> byClazz, CodeContext codeCtx) {
        // 断言参数不为空
        Assert.notNull(byClazz, "byClazz");
        Assert.notNull(codeCtx, "codeCtx");

        // 
        // 获取类型为 AbstractMsgField 字段, 
        // 子类字段也算上
        List<Field> fl = ClazzUtil.listField(
            byClazz, f -> AbstractMsgField.class.isAssignableFrom(f.getType())
        );

        if (fl == null || 
            fl.isEmpty()) {
            return;
        }

        fl.forEach(f -> {
            if (ClazzUtil.isDrivedClazz(f.getType(), PrimitiveTypeField.class)) {
                // 如果是普通字段, 生成如下代码 : 
                // msgObj._funcId = MsgInt.ifNullThenCreate(msgObj._funcId);
                // msgObj._worker = MsgStr.ifNullThenCreate(msgObj._worker);
                codeCtx._codeText.append("O.")
                    .append(f.getName())
                    .append(" = ")
                    .append(f.getType().getSimpleName())
                    .append(".ifNullThenCreate(O.")
                    .append(f.getName())
                    .append(");\n");
            } else if (f.getType().equals(MsgArrayList.class)) {
                if (hasGenericType(f) == false) {
                    // 如果是 MsgArrayList 类型, 
                    // 但如果不带有泛型参数, 
                    // 则直接抛出异常!
                    throw new MsgError(MessageFormat.format(
                        "{0} 类 {1} 字段没有声明泛型类型, 应使用类似 MsgArrayList<MsgInt> _funcIdList; 这样的定义", 
                        f.getDeclaringClass().getName(), 
                        f.getName()
                    ));
                }

                // 如果是列表字段, 生成如下代码 : 
                // MsgArrayList.readBuff(msgObj._funcIdList, buff);
                codeCtx._codeText.append("MsgArrayList.readBuff(O.")
                    .append(f.getName())
                    .append(", buff);\n");

                // 获取实际类型
                Class<?> aType = (Class<?>)FieldUtil.getGenericTypeA(f);

                // 添加到 import
                codeCtx._importClazzSet.add(MsgArrayList.class);
                codeCtx._importClazzSet.add(aType);

                // 注意 :
                // 还需要给模版参数生成 ReadHelper
                make(aType);

                // 直接退出, 不要再继续向下生成 : 
                // msgObj._funcIdList.readBuff(buff);
                // 这样的代码了...
                return;
            } else if (ClazzUtil.isConcreteDrivedClass(f.getType(), AbstractMsgObj.class)) {
                // 
                // 如果是嵌套的消息体, 生成如下代码 : 
                // if (msgObj._userInfo == null) {
                //     msgObj._userInfo = new UserInfo();
                // }
                // 
                // 先判断是否为空 ?
                codeCtx._codeText.append("if (O.")
                    .append(f.getName())
                    .append(" == null) {\n");

                // 如果为空则创建新对象
                codeCtx._codeText.append("O.")
                    .append(f.getName())
                    .append(" = new ")
                    .append(f.getType().getSimpleName())
                    .append("();\n");

                // 结束代码段
                codeCtx._codeText.append("}\n");

                // 注意 : 如果是个内置消息,
                // 那么还需要给内置消息生成 ReadHelper
                make(f.getType());
            } else {
                // 如果即不是 MsgInt, MsgStr ..., MsgArrayList, 
                // 也不是 AbstractMsgObj, 
                // 则直接退出!
                return;
            }

            codeCtx._importClazzSet.add(f.getType());
            // 生成如下代码 : 
            // msgObj._funcId.readBuff(buff);
            codeCtx._codeText.append("O.")
                .append(f.getName())
                .append(".readBuff(buff);\n");
        });
    }

    /**
     * 是否为泛型字段 ?
     * 
     * @param f
     * @return
     * 
     */
    private static boolean hasGenericType(Field f) {
        if (f == null) {
            // 如果参数对象为空, 
            // 则直接退出!
            return false;
        }

        if (!(f.getGenericType() instanceof ParameterizedType)) {
            // 如果不是泛型类型, 
            // 则直接退出!
            return false;
        }

        // 获取泛型参数
        ParameterizedType tType = (ParameterizedType)f.getGenericType();

        if (tType.getActualTypeArguments().length <= 0) {
            // 如果泛型参数太少, 
            // 则直接退出!
            return false;
        }

        return true;
    }

    /**
     * 添加 import 代码
     * 
     * @param pool
     * @param importClazzSet 
     * 
     */
    private static void importPackage(ClassPool pool, Set<Class<?>> importClazzSet) {
        if (pool == null || 
            importClazzSet == null || 
            importClazzSet.isEmpty()) {
            // 如果参数对象为空, 
            // 则直接退出!
            return;
        }

        importClazzSet.forEach((c) -> {
            // 导入要用到的类
            pool.importPackage(c.getPackage().getName());
        });
    }

    /**
     * 设置默认构造器, 会生成如下代码 : 
     * <pre>
     * Parser_Building() {}
     * </pre>
     * 
     * @param cc
     * @throws Exception 
     * 
     */
    private static void putDefaultConstructor(CtClass cc) throws Exception {
        if (cc == null) {
            // 如果参数对象为空, 
            // 则直接退出!
            return;
        }

        // 创建默认构造器
        CtConstructor constructor = new CtConstructor(new CtClass[0], cc);
        // 空函数体
        constructor.setBody("{}");
        // 添加默认构造器
        cc.addConstructor(constructor);
    }

    /**
     * 代码上下文
     * 
     * @author hjj2017
     * 
     */
    private static class CodeContext {
        /** 引用类集合 */
        public final Set<Class<?>> _importClazzSet = new HashSet<>();
        /** 用于输出的代码文本 */
        public final StringBuilder _codeText = new StringBuilder();
    }
}
