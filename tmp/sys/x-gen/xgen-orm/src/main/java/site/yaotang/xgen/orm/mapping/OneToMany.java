package site.yaotang.xgen.orm.mapping;

import lombok.Builder;
import lombok.Data;

/**
 * 一对多关系
 * @author hyt
 * @date 2017年12月30日
 */
@Data
@Builder
public class OneToMany {
	private String name;
	private String clazz;
	private String field;
	private String column;
}
