/**
 * Copyright (c) 2015, biezhi 王爵 (biezhi.me@gmail.com)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * 	http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.blade.context;

import javax.servlet.ServletContext;

import com.blade.web.http.Request;
import com.blade.web.http.Response;
import com.blade.web.http.wrapper.Session;

/**
 * BladeWebContext
 *
 * @author	<a href="mailto:biezhi.me@gmail.com" target="_blank">biezhi</a>
 * @since	1.0
 */
public class BladeWebContext {
	
	/**
	 * BladeWebContext object for the current thread
	 */
    private static ThreadLocal<BladeWebContext> ctx = new ThreadLocal<BladeWebContext>();
    
    /**
     * ServletContext Object that is created when the application is initialized
     */
    private ServletContext context; 
    
    /**
     * Request
     */
    private Request request;
    
    /**
     * Response
     */
    private Response response;
    
    private BladeWebContext(){}
    
    public static BladeWebContext me(){
    	return ctx.get();
    }
    
    public static void setContext(ServletContext context) {
    	BladeWebContext bladeWebContext = new BladeWebContext();
    	bladeWebContext.context = context;
    	ctx.set(bladeWebContext);
    }
    
    public static void setContext(ServletContext context, Request request, Response response) {
    	BladeWebContext bladeWebContext = new BladeWebContext();
    	bladeWebContext.context = context;
    	bladeWebContext.request = request;
    	bladeWebContext.response = response;
    	ctx.set(bladeWebContext);
    }
    
    /**
     * 移除当前线程的Request、Response对象
     */
    public static void remove(){
    	ctx.remove();
    }
    
    public static Request request() {
        return me().request;
    }
    
    public static Response response() {
        return me().response;
    }
    
    public static Session session() {
        return request().session();
    }
    
	public static ServletContext servletContext() {
		return me().context;
	}
	
	public ServletContext getContext() {
		return context;
	}
	
	public Request getRequest() {
		return request;
	}

	public Response getResponse() {
		return response;
	}
	
}