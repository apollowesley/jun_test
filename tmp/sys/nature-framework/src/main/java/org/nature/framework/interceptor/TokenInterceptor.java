package org.nature.framework.interceptor;

import javax.servlet.http.HttpServletRequest;

import org.nature.framework.cache.InvocationContext;
import org.nature.framework.cache.InvocationTokenStore;
import org.nature.framework.cache.NatureContext;
import org.nature.framework.util.StringUtil;

public class TokenInterceptor implements NatureInterceptor {

	public void intercept(Invocation invocation) {
		HttpServletRequest request = invocation.getRequest();
		String key = NatureContext.getNatureCtrl().getSession().getId();
		synchronized (key.intern()) {
			String token = request.getParameter("token");
			if (StringUtil.isBank(token)) {
				invocation.invoke();
			} else {
				String myToken = InvocationTokenStore.getToken();
				if (myToken.equals(token)) {
					InvocationTokenStore.remove();
					invocation.invoke(); 
					InvocationTokenStore.put(token, new InvocationContext(invocation.getReturnValue(),invocation.getTargetObject()));
				} else {
					InvocationContext myInvocation = InvocationTokenStore.getInvocation(token);
					if (myInvocation!=null) {
						invocation.setReturnValue(myInvocation.getReturnValue());
						invocation.setTargetObject(myInvocation.getTargetObject());
					}else{
						//TODO 
					}
					
				}
			}
		}
	}
	

}
