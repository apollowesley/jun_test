package cn.itcast.demo.dao;

import java.util.List;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.Node;

import cn.itcast.demo.domain.Book;
import cn.itcast.demo.util.XmlUtil;

public class BookDao {

	/**
	 * 通过id查询书籍
	 * @param bookId
	 * @return
	 */
	public Book findBookById(String bookId) throws Exception{
		
		//获得document
		Document document = XmlUtil.getDocument();
		//查询book元素    //book[@id='bookId']
		Node bookNode = document.selectSingleNode("//book[@id='"+bookId+"']");
		if(bookNode == null){
			//没有结果
			return null;
		}
		//创建javabean Book对象
		Book book = new Book();
		//查询的结果，添加到book对象中
		Element bookElement = (Element) bookNode;
		//获得id属性的值
		String id = bookElement.attributeValue("id");
		//将获得值，写入到book对象
		book.setId(id);
		
		
		//处理子元素 title price
		//获得子元素
		List childList = bookElement.elements();
		//遍历所有
		for(int c = 0 ; c < childList.size() ; c ++){
			//获得每一个子元素
			Element child = (Element) childList.get(c);
			
			//处理title
			if("title".equals(child.getName())){
				//将子元素的文本内容添加到book对象中
				String title = child.getText();
				book.setTitle(title);
			}
			
			//处理price
			if("price".equals(child.getName())){
				book.setPrice(child.getText());
			}
			
		}
		
		
		return book;// new Book("b001","title","98");
	}
	
}
