package site.yaotang.xgen.orm.cache;

import java.time.LocalDateTime;

import lombok.Builder;
import lombok.Data;

/**
 * 缓存对象
 * @author hyt
 * @date 2018年1月1日
 */
@Data
@Builder
public class Cache {
	private String name;
	private Object value;
	private LocalDateTime startTime;
	private LocalDateTime endTime;

	public boolean isInvalid() {
		return LocalDateTime.now().compareTo(endTime) > 0;
	}
}
