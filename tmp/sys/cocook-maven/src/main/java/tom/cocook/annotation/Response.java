package tom.cocook.annotation;

import java.lang.annotation.*;

public class Response {
	/**
	 * response返回inputStream解析
	 */
	@Target(ElementType.METHOD)
	@Retention(RetentionPolicy.RUNTIME)
	public static @interface Stream {
		public ContentType value();
	}

	@Target(ElementType.METHOD)
	@Retention(RetentionPolicy.RUNTIME)
	public @interface Json {
	}
	
	
	@Target(ElementType.METHOD)
	@Retention(RetentionPolicy.RUNTIME)
	public @interface Html {
	}
}
