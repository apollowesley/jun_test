package com.iotechn.iot.executor.service;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.iotechn.iot.commons.entity.CommonsPage;
import com.iotechn.iot.commons.entity.IPage;
import com.iotechn.iot.commons.entity.exception.ServiceException;
import com.iotechn.iot.executor.Const;
import com.iotechn.iot.executor.api.ExecutionOpenService;
import com.iotechn.iot.executor.compment.RedisCache4Groovy;
import com.iotechn.iot.executor.dev.ICache;
import com.iotechn.iot.executor.dev.IDeviceLogger;
import com.iotechn.iot.executor.dev.IMailSender;
import com.iotechn.iot.executor.entity.ExecutorDeviceLogDo;
import com.iotechn.iot.executor.entity.ExecutorRegisterDo;
import com.iotechn.iot.executor.entity.exception.ExecutorExceptionDefinition;
import com.iotechn.iot.executor.entity.exception.ExecutorServiceException;
import com.iotechn.iot.executor.mapper.ExecutorDeviceLogMapper;
import com.iotechn.iot.executor.mapper.ExecutorRegisterMapper;
import com.iotechn.iot.executor.model.InvokeContext;
import com.iotechn.iot.executor.model.InvokerInfoModel;
import groovy.lang.GroovyClassLoader;
import okhttp3.OkHttpClient;
import org.apache.ibatis.session.RowBounds;
import org.codehaus.groovy.control.MultipleCompilationErrorsException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import sun.security.krb5.internal.PAData;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.*;
import java.util.concurrent.*;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: rize
 * Date: 2018-08-08
 * Time: 上午10:47
 */

@Service("executionOpenService")
public class ExecutionOpenServiceImpl implements ExecutionOpenService {

    private static final Logger logger = LoggerFactory.getLogger(ExecutionOpenServiceImpl.class);

    private GroovyClassLoader groovyClassLoader = new GroovyClassLoader();

    @Value("${com.iotechn.iot.executor.basePath}")
    private String basePath = "/home/rize/";

    private Map<String, Object> objectMap = new HashMap<>();

    private ExecutorService executor = Executors.newFixedThreadPool(30);

    private static final String EXE_SYSTEM_EXECUTOR = "EXE_SYSTEM_EXECUTOR";

    private static final String[] arrayEmptyArgs = new String[]{};

    private static final Class<?> arrayClass = arrayEmptyArgs.getClass();

    private static final int DEFAULT_PAGE_SIZE = 10;

    @Autowired
    private ExecutorRegisterMapper executorRegisterMapper;
    @Autowired
    private ExecutorDeviceLogMapper executorDeviceLogMapper;
    @Autowired
    private StringRedisTemplate stringRedisTemplate;
    @Autowired
    private IMailSender mailSender;
    @Autowired
    private IDeviceLogger deviceLogger;

    private OkHttpClient okHttpClient = new OkHttpClient();

    @Override
    public String invokeGroovyMethod(String uuid, String methodName, String args[],InvokerInfoModel invokerInfo) throws ServiceException {
        try {
            final InvokerInfoModel finalInvoker;
            if (invokerInfo == null)
                finalInvoker = new InvokerInfoModel();
            else
                finalInvoker = invokerInfo;
            //从数据库鉴别权限
            List<ExecutorRegisterDo> registerDos = executorRegisterMapper.selectList(new EntityWrapper<ExecutorRegisterDo>().eq("uuid", uuid));
            if (CollectionUtils.isEmpty(registerDos)) {
                throw new ExecutorServiceException(ExecutorExceptionDefinition.EXECUTOR_NOT_EXIST);
            }
            if (0 == registerDos.get(0).getStatus()) {
                throw new ExecutorServiceException(ExecutorExceptionDefinition.EXECUTOR_NOT_AUDITING);
            }
            //1.拼接clazz
            String clazz = basePath + "groovy/" + uuid + "/Main.groovy";
            Object oFromCache = objectMap.get(clazz);
            final Object o;
            if (oFromCache == null) {
                //2.获取实例
                Class clazzInstance = groovyClassLoader.parseClass(new File(clazz));
                Constructor constructor = clazzInstance.getConstructors()[0];
                Parameter[] parameters = constructor.getParameters();
                Object[] constructorArgs = new Object[parameters.length];
                for (int i = 0; i < parameters.length; i++) {
                    Parameter parameter = parameters[i];
                    if (parameter.getType() == ICache.class) {
                        constructorArgs[i] = new RedisCache4Groovy(registerDos.get(0).getId(), stringRedisTemplate);
                    } else {
                        constructorArgs[i] = null;
                    }
                }
                o = constructor.newInstance(constructorArgs);
                objectMap.put(clazz, o);
            } else {
                o = oFromCache;
            }

            Method method = o.getClass().getMethod(methodName, InvokeContext.class);
            if (method != null) {
                FutureTask<Object> futureTask = new FutureTask<Object>(new Callable<Object>() {
                    @Override
                    public Object call() throws InvocationTargetException, IllegalAccessException {
                        InvokeContext invokeContext = new InvokeContext();
                        //1.每个执行器封装一个
                        invokeContext.setCache(new RedisCache4Groovy(registerDos.get(0).getId(),stringRedisTemplate));
                        invokeContext.setOkHttpClient(okHttpClient);
                        invokeContext.setInvokerInfoModel(finalInvoker);
                        invokeContext.setMailSender(mailSender);
                        invokeContext.setDeviceLogger(deviceLogger);
                        if (args == null) {
                            invokeContext.setArgs(arrayEmptyArgs);
                            return method.invoke(o, invokeContext);
                        } else {
                            invokeContext.setArgs(args);
                            return method.invoke(o, invokeContext);
                        }
                    }
                });
                executor.execute(futureTask);

                try {
                    //从数据库里面查最长执行时间
                    Object result = futureTask.get(5, TimeUnit.SECONDS);
                    if (Const.IGNORE_PARAM_LIST.contains(result.getClass())) {
                        return result.toString();
                    } else {
                        return JSONObject.toJSONString(result);
                    }
                } catch (TimeoutException e) {
                    futureTask.cancel(true);
                    throw e;
                } finally {
                    //次数统计之类的
                }
            } else {
                throw new ExecutorServiceException(ExecutorExceptionDefinition.EXECUTOR_METHOD_NOT_EXIST);
            }
        } catch (ExecutorServiceException e) {
            throw e;
        } catch (MultipleCompilationErrorsException e) {
            throw new ExecutorServiceException(ExecutorExceptionDefinition.EXECUTOR_COMPILATION_EXCEPTION);
        } catch (Exception e) {
            logger.info("[调用Groovy方法] 异常", e);
            throw new ExecutorServiceException(ExecutorExceptionDefinition.EXECUTOR_UNKNOWN_EXCEPTION);
        }
    }

    @Override
    @Transactional(rollbackFor = ExecutorServiceException.class)
    public String addClass(String content, String title, String description, Long userId) throws ServiceException {
        File file = null;
        try {
            //1.生成uuid
            String uuid = UUID.randomUUID().toString().replace("-", "");
            //2.1.将Content持久化
            file = rewrite(content, uuid);
            //2.2.将权限刷到数据库
            ExecutorRegisterDo executorRegisterDo = new ExecutorRegisterDo();
            executorRegisterDo.setUserId(userId);
            executorRegisterDo.setTitle(StringUtils.isEmpty(title) ? ("CLASS" + uuid.substring(24)) : title);
            executorRegisterDo.setGmtCreate(new Date());
            executorRegisterDo.setUuid(uuid);
            executorRegisterDo.setDescription(description);
            executorRegisterMapper.insert(executorRegisterDo);
            //3.classloader 加载 class
            groovyClassLoader.parseClass(file);
            return uuid;
        } catch (ExecutorServiceException e) {
            throw e;
        } catch (MultipleCompilationErrorsException e) {
            if (file != null)
                file.delete();
            throw new ExecutorServiceException(ExecutorExceptionDefinition.EXECUTOR_COMPILATION_EXCEPTION);
        } catch (Exception e) {
            if (file != null)
                file.delete();
            logger.error("[添加类] 异常", e);
            throw new ExecutorServiceException(ExecutorExceptionDefinition.EXECUTOR_UNKNOWN_EXCEPTION);
        }
    }

    private File rewrite(String content, String uuid) throws Exception {
        //2.将Content持久化
        File dir = new File(basePath + "groovy/" + uuid);
        File file = new File(dir, "/Main.groovy");
        if (!dir.exists()) {
            dir.mkdirs();
        }
        if (!file.exists()) {
            file.createNewFile();
        }
        FileOutputStream fileOutputStream = new FileOutputStream(file);
        fileOutputStream.write(content.getBytes());
        fileOutputStream.close();
        return file;
    }

    @Override
    @Transactional(rollbackFor = ExecutorServiceException.class)
    public String reloadClass(String uuid, String title, String description, String content, Long userId) throws ServiceException {
        try {
            //从数据库鉴权
            List<ExecutorRegisterDo> registerDos = executorRegisterMapper.selectList
                    (new EntityWrapper<ExecutorRegisterDo>()
                            .eq("uuid", uuid)
                            .eq("user_id", userId));
            if (CollectionUtils.isEmpty(registerDos)) {
                throw new ExecutorServiceException(ExecutorExceptionDefinition.EXECUTOR_NOT_EXIST);
            }
            ExecutorRegisterDo executorRegisterDo = registerDos.get(0);
            if (!StringUtils.isEmpty(title))
                executorRegisterDo.setTitle(title);
            if (!StringUtils.isEmpty(description))
                executorRegisterDo.setDescription(description);
            executorRegisterDo.setGmtUpdate(new Date());
            executorRegisterMapper.updateById(executorRegisterDo);

            //2.重写
            File file = rewrite(content, uuid);
            //3.classloader 加载 class
            groovyClassLoader.parseClass(file);
            //4.刷新缓存
            groovyClassLoader.clearCache();
            //5.拼接clazz
            String clazz = basePath + "groovy/" + uuid + "/Main.groovy";
            objectMap.remove(clazz);
            return uuid;
        } catch (ExecutorServiceException e) {
            throw e;
        } catch (MultipleCompilationErrorsException e) {
            throw new ExecutorServiceException(ExecutorExceptionDefinition.EXECUTOR_COMPILATION_EXCEPTION);
        } catch (Exception e) {
            logger.error("[修改类] 异常 传入参数 uuid=" + uuid + ";userId=" + userId + ";content=" + content, e);
            throw new ExecutorServiceException(ExecutorExceptionDefinition.EXECUTOR_UNKNOWN_EXCEPTION);
        }
    }

    @Override
    @DS("slave")
    public IPage<ExecutorRegisterDo> getExecutors(Integer page, Long userId) throws ServiceException {
        try {
            Wrapper<ExecutorRegisterDo> wrapper = new EntityWrapper<ExecutorRegisterDo>()
                    .eq("user_id", userId);
            Integer count = executorRegisterMapper.selectCount(wrapper);
            String jsonExecutors = stringRedisTemplate.opsForValue().get(EXE_SYSTEM_EXECUTOR);
            List<ExecutorRegisterDo> executorRegisterDos = null;
            if (!StringUtils.isEmpty(jsonExecutors)) {
                executorRegisterDos = JSONObject.parseArray(jsonExecutors,ExecutorRegisterDo.class);
            }else {
                executorRegisterDos = executorRegisterMapper.selectList(new EntityWrapper<ExecutorRegisterDo>()
                        .eq("status",2));
                stringRedisTemplate.opsForValue().set(EXE_SYSTEM_EXECUTOR, JSONObject.toJSONString(executorRegisterDos));
            }

            if (count == 0) {
                return new CommonsPage<>(executorRegisterDos, page, DEFAULT_PAGE_SIZE, count);
            }
            List<ExecutorRegisterDo> registerDos = executorRegisterMapper.selectPage(
                    new RowBounds((page - 1) * DEFAULT_PAGE_SIZE, DEFAULT_PAGE_SIZE), wrapper);
            return new CommonsPage(registerDos, page, DEFAULT_PAGE_SIZE, count);
        } catch (Exception e) {
             logger.error("[获取执行器] 异常",e);
             throw new ExecutorServiceException(ExecutorExceptionDefinition.EXECUTOR_UNKNOWN_EXCEPTION);
        }
    }

    @Override
    @DS("slave")
    public String getExecutorContent(Long userId, String uuid) throws ServiceException {
        executorRegisterMapper.selectList(new EntityWrapper<ExecutorRegisterDo>().eq("uuid",uuid));
        File dir = new File(basePath + "groovy/" + uuid);
        File file = new File(dir, "/Main.groovy");
        if (!dir.exists()) {
            throw new ExecutorServiceException(ExecutorExceptionDefinition.EXECUTOR_NOT_EXIST);
        }
        if (!file.exists()) {
            throw new ExecutorServiceException(ExecutorExceptionDefinition.EXECUTOR_NOT_EXIST);
        }
        try {
            FileInputStream fileInputStream = new FileInputStream(file);
            byte[] buffer = new byte[256];
            StringBuilder sb = new StringBuilder();
            int length;
            while ((length = fileInputStream.read(buffer)) > 0) {
                sb.append(new String(buffer, 0, length, "utf-8"));
            }
            return sb.toString().replace("\0000", "");
        } catch (FileNotFoundException e) {
            throw new ExecutorServiceException(ExecutorExceptionDefinition.EXECUTOR_NOT_EXIST);
        } catch (Exception e) {
            logger.error("[获取Executor内容] 异常", e);
            throw new ExecutorServiceException(ExecutorExceptionDefinition.EXECUTOR_UNKNOWN_EXCEPTION);
        }

    }

    @Override
    public List<String> getDeviceLogKeysBySk(String secretKey) throws ServiceException {
        List<String> list = executorDeviceLogMapper.selectKeys(secretKey);
        return list;
    }

    @Override
    public IPage<ExecutorDeviceLogDo> getDeviceLogPage(String secretKey, String key, Integer pageNo) throws ServiceException {
        Wrapper<ExecutorDeviceLogDo> wrapper = new EntityWrapper<ExecutorDeviceLogDo>()
                .eq("secret_key", secretKey)
                .eq("log_key", key);
        Integer count = executorDeviceLogMapper.selectCount(wrapper);
        List<ExecutorDeviceLogDo> executorDeviceLogDos;
        if (count > 0) {
            executorDeviceLogDos = executorDeviceLogMapper
                    .selectPage(new RowBounds((pageNo - 1) * DEFAULT_PAGE_SIZE, DEFAULT_PAGE_SIZE),
                            wrapper.orderBy("id", false));

        } else {
            executorDeviceLogDos = new ArrayList<>();
        }
        IPage<ExecutorDeviceLogDo> page = new CommonsPage<ExecutorDeviceLogDo>(executorDeviceLogDos,DEFAULT_PAGE_SIZE, pageNo, count);
        return page;
    }

    @Override
    public String test(String abc) {
        System.out.println(abc);
        return abc;
    }


}
