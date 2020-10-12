package site.yaotang.xgen.orm.mapping;

import lombok.Builder;
import lombok.Data;

/**
 * 主键信息
 * @author hyt
 * @date 2017年12月30日
 */
@Data
@Builder
public class Id {
	private String name;
	private String column;
	private boolean autoIncrement;
}
