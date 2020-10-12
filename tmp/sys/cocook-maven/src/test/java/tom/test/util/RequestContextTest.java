package tom.test.util;

import tom.cocook.core.RequestContext;

public class RequestContextTest {
	
	public static void main(String[] args) {
		System.out.println(new RequestContext().getDomainOfServerName("http://wap.ganji.com/bj/fang5/?domain=bj&url=fang5&page=10&page=20"));;
		System.out.println(new RequestContext().getDomainOfServerName("http://www.ganji.com/bj/fang5/?domain=bj&url=fang5&page=10&page=20"));;
		
	}

}
