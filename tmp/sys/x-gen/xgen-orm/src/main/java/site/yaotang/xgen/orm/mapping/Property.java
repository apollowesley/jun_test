package site.yaotang.xgen.orm.mapping;

import lombok.Builder;
import lombok.Data;

/**
 * 属性信息（非主键）
 * @author hyt
 * @date 2017年12月30日
 */
@Data
@Builder
public class Property {
	private String name;
	private String column;
	private Object defaultValue;
}
