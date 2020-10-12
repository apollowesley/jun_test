/*
 * Copyright (c) 2016-2018, Guangshan (guangshan1992@qq.com) and the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.flypigs.resubmit;

import org.springframework.beans.BeanUtils;
import org.springframework.core.annotation.AnnotationAwareOrderComparator;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * fastboot-weixin  ResubmitResolvers
 *
 * @author Guangshan
 * @date 2018/5/27 18:58
 * @since 0.0.1
 */
public class ResubmitResolvers {

    private List<ResubmitResolver> resubmitResolvers = new ArrayList<>();

    private Map<Class<? extends ResubmitResolver>, ? extends ResubmitResolver> customResubmitResolver = new HashMap<>();

    private ResubmitResolver defaultResubmitResolver;

    public ResubmitResolver getMatchedResubmitResolver(Method method, Resubmit resubmit) {
        ResubmitResolver matched = null;
        if (resubmit.resolver() != ResubmitResolver.class) {
            matched = resubmitResolvers.stream().filter(r -> r.getClass() == resubmit.resolver()).findFirst().orElse(null);
            if (matched != null) {
                return matched;
            }
            try {
                matched = BeanUtils.instantiateClass(resubmit.resolver());
                this.addResubmitResolver(matched);
                return matched;
            } catch (Exception e) {
                // 打一个error
            }
        }
        for (ResubmitResolver resubmitResolver : resubmitResolvers) {
            if (resubmitResolver.isMatch(resubmit)) {
                return resubmitResolver;
            }
        }
        return defaultResubmitResolver;
    }

    public void addResubmitResolver(ResubmitResolver resubmitResolver) {
        resubmitResolvers.add(resubmitResolver);
        AnnotationAwareOrderComparator.sort(resubmitResolvers);
    }

    public void addResubmitResolvers(List<ResubmitResolver> resubmitResolvers) {
        synchronized (resubmitResolvers) {
            resubmitResolvers.addAll(resubmitResolvers);
            AnnotationAwareOrderComparator.sort(resubmitResolvers);
        }
    }

    public void setDefaultResubmitResolver(ResubmitResolver resubmitResolver) {
        this.defaultResubmitResolver = resubmitResolver;
    }

}
