package site.yaotang.xgen.orm.mapping;

import java.util.List;

import lombok.Builder;
import lombok.Data;

/**
 * 表信息
 * @author hyt
 * @date 2017年12月30日
 */
@Data
@Builder
public class Table {
	private Id id;
	private String className;
	private String tableName;
	private List<Property> properties;
	private List<OneToMany> oneList;
	private List<ManyToOne> manyList;
}
