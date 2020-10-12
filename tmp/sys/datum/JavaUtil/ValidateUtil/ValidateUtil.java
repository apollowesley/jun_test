package com.omniselling.common.util;

import java.lang.reflect.Method;
import java.util.Collection;
import java.util.Map;
import java.util.Map.Entry;

import com.omniselling.common.enumeration.CommonErrorCode;
import com.omniselling.common.enumeration.LanguageCode;
import com.omniselling.common.enumeration.ValidateType;
import com.omniselling.common.model.BaseResponse;
import com.omniselling.common.model.ErrorInfo.CATEGORY;
import com.omniselling.common.model.ErrorInfo.SEVERITY;

/**
 * 
 * @author junhu
 *
 */
public class ValidateUtil
{
    public static <T> BaseResponse<Boolean> validate(T object, Map<String, Map<ValidateType, String>> fileds,
            String language)
    {
        BaseResponse<Boolean> baseResponse = new BaseResponse<Boolean>();
        try
        {
            Class<?> class1 = object.getClass();
            for (String filed : fileds.keySet())
            {
                String methodName = "get" + filed.substring(0, 1).toUpperCase() + filed.substring(1);
                Method method = class1.getMethod(methodName, null);
                Object filedValue = method.invoke(object, null);
                for (Entry<ValidateType, String> entry : fileds.get(filed).entrySet())
                {
                    ValidateType validateType = entry.getKey();
                    String value = entry.getValue();
                    if (validateType.name().equals(ValidateType.NULL.name()))
                    {
                        if (value.equalsIgnoreCase("false"))
                        {
                            if (filedValue instanceof String)
                            {
                                String filedValueStr = String.valueOf(filedValue);
                                if (StringUtils.isEmpty(filedValueStr))
                                {
                                    baseResponse.addError(ErrorInfoBuilder.build(CommonErrorCode.PARAM_ERROR,
                                            SEVERITY.ERROR, CATEGORY.BIZ, LanguageCode.getByValue(language), filed));
                                    baseResponse.setData(false);
                                    return baseResponse;
                                }
                            }
                            else if (filedValue instanceof Collection)
                            {
                                Collection collection = (Collection) filedValue;
                                if (collection == null || collection.size() == 0)
                                {
                                    baseResponse.addError(ErrorInfoBuilder.build(CommonErrorCode.PARAM_ERROR,
                                            SEVERITY.ERROR, CATEGORY.BIZ, LanguageCode.getByValue(language), filed));
                                    baseResponse.setData(false);
                                    return baseResponse;
                                }
                            }
                            else
                            {
                                if (filedValue == null)
                                {
                                    baseResponse.addError(ErrorInfoBuilder.build(CommonErrorCode.PARAM_ERROR,
                                            SEVERITY.ERROR, CATEGORY.BIZ, LanguageCode.getByValue(language), filed));
                                    baseResponse.setData(false);
                                    return baseResponse;
                                }
                            }
                        }
                    }
                    if (validateType.name().equals(ValidateType.LENGTH.name()))
                    {
                        if (filedValue instanceof String)
                        {
                            String filedValueStr = String.valueOf(filedValue);
                            Integer valueInt = Integer.valueOf(value);
                            if (filedValueStr.length() > valueInt)
                            {
                                baseResponse.addError(ErrorInfoBuilder.build(CommonErrorCode.PARAM_ERROR,
                                        SEVERITY.ERROR, CATEGORY.BIZ, LanguageCode.getByValue(language), filed));
                                baseResponse.setData(false);
                                return baseResponse;
                            }
                        }
                    }
                }
            }
            baseResponse.setData(true);
        }
        catch (Exception e)
        {
            baseResponse.addError(ErrorInfoBuilder.build(CommonErrorCode.SYSTEM_ERROR, SEVERITY.ERROR, CATEGORY.BIZ,
                    LanguageCode.getByValue(language), e.getMessage()));
            baseResponse.setData(false);
        }
        return baseResponse;
    }
}
