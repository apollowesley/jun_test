package tom.cocook.handler;

import java.util.HashMap;

import tom.cocook.annotation.HttpMethod;

public class RequestMapping {
	
	private String path;
	private HttpMethod method;
	
	public RequestMapping() {
	}
	
	public RequestMapping(String path, HttpMethod method) {
		this.path = path;
		this.method = method;
	}

	public String getPath() {
		return path;
	}
	public void setPath(String path) {
		this.path = path;
	}
	
	public HttpMethod getMethod() {
		return method;
	}
	public void setMethod(HttpMethod method) {
		this.method = method;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((method == null) ? 0 : method.hashCode());
		result = prime * result + ((path == null) ? 0 : path.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		RequestMapping other = (RequestMapping) obj;
		if (method != other.method)
			return false;
		if (path == null) {
			if (other.path != null)
				return false;
		} else if (!path.equals(other.path))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "RequestMapping [path=" + path + ", method=" + method + "]";
	}
	
	
	public static void main(String[] args) {

		long a = System.currentTimeMillis();
		RequestMapping m = new RequestMapping("123", HttpMethod.POST);
		RequestMapping m1 = new RequestMapping("234", HttpMethod.GET);
		HashMap<RequestMapping, Object> map = new HashMap<RequestMapping, Object>();
		map.put(m, m);
		map.put(m1, m1);
		
		RequestMapping m3 = new RequestMapping("123", HttpMethod.POST);
		RequestMapping m4 = new RequestMapping("234", HttpMethod.GET);
		
		System.out.println(m3+"======"+map.get(m3));
		System.out.println(m4+"======="+map.get(m4));
		System.out.println(System.currentTimeMillis() - a);
		
	
	}




}
