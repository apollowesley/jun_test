package org.nature4j.framework.interceptor;

import javax.servlet.http.HttpSession;

import org.nature4j.framework.cache.InvocationContext;
import org.nature4j.framework.cache.InvocationTokenStore;
import org.nature4j.framework.cache.NatureContext;
import org.nature4j.framework.util.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TokenInterceptor implements NatureInterceptor {

	private static Logger LOGGER = LoggerFactory.getLogger(TokenInterceptor.class);

	public void intercept(Invocation invocation) {
		HttpSession session = NatureContext.getRequest().getSession();
		String token = invocation.getRequestParams().getString("token");
		if (StringUtil.isBank(token)) {
			invocation.invoke();
		} else {
			synchronized (session.getId().intern()) {
				String myToken = InvocationTokenStore.getToken();
				if (myToken.equals(token)) {
					InvocationTokenStore.remove();
					invocation.invoke();
					InvocationTokenStore.put(token,new InvocationContext(invocation.getReturnValue(), invocation.getTargetObject()));
				} else {
					InvocationContext myInvocation = InvocationTokenStore.getInvocation(token);
					if (myInvocation != null) {
						invocation.setReturnValue(myInvocation.getReturnValue());
						invocation.setTargetObject(myInvocation.getTargetObject());
					} else {
						invocation.invoke();
					}
				}
			}
		}
	}

	public int level() {
		return 1022;
	}

}
