package com.navercorp.pinpoint.plugin.xxljob.transform;

import com.navercorp.pinpoint.bootstrap.instrument.InstrumentClass;
import com.navercorp.pinpoint.bootstrap.instrument.InstrumentException;
import com.navercorp.pinpoint.bootstrap.instrument.InstrumentMethod;
import com.navercorp.pinpoint.bootstrap.instrument.Instrumentor;
import com.navercorp.pinpoint.bootstrap.instrument.transformer.TransformCallback;
import com.navercorp.pinpoint.bootstrap.interceptor.BasicMethodInterceptor;
import com.navercorp.pinpoint.common.util.VarArgs;
import com.navercorp.pinpoint.plugin.xxljob.PluginConstants;

import java.security.ProtectionDomain;

/** 
 * Future get方法注入拦截器
 *
 * @author YY
 * @version 1.0 
 * @date 2019-05-17 16:46
 */ 
public class FutureTransform implements TransformCallback {

    @Override
    public byte[] doInTransform(Instrumentor instrumentor, ClassLoader classLoader, String className, Class<?> classBeingRedefined, ProtectionDomain protectionDomain, byte[] classfileBuffer) throws InstrumentException {
        InstrumentClass target = instrumentor.getInstrumentClass(classLoader, className, classfileBuffer);
        //获取声明的get方法注入拦截器
        InstrumentMethod get = target.getDeclaredMethod("get");
        get.addInterceptor(BasicMethodInterceptor.class.getName(), VarArgs.va(PluginConstants.SERVICE_TYPE));

        return target.toBytecode();
    }
}
