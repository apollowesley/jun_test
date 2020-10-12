package site.yaotang.xgen.orm.bean;

import lombok.Builder;
import lombok.Data;

/**
 * 书本
 * @author hyt
 * @date 2017年12月30日
 */
@Data
@Builder
public class Book {
	private int bookId;
	private String bookName;
	private String author;
	private String publisher;
	private Style style;
}
