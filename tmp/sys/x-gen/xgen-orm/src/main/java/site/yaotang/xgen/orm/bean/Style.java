package site.yaotang.xgen.orm.bean;

import java.util.List;

import lombok.Builder;
import lombok.Data;

/**
 * 分类
 * @author hyt
 * @date 2017年12月30日
 */
@Data
@Builder
public class Style {
	private int styleId;
	private String style;
	private List<Book> bookList;
}
