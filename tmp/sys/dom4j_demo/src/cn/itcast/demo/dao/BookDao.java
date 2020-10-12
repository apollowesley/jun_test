package cn.itcast.demo.dao;

import java.util.ArrayList;
import java.util.List;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.Node;

import cn.itcast.demo.bean.BookBean;
import cn.itcast.demo.util.XmlUtil;

public class BookDao {
	
	/**
	 * 保存数据
	 * @param book
	 * @return bookId
	 */
	public String saveBook(BookBean book){
		try {
			//获得document
			Document document = XmlUtil.getDocument();
			Element rootElement = document.getRootElement();
			//将book生成片段
			Element bookEle = rootElement.addElement("book");
			//设置ID属性
			bookEle.addAttribute("id", book.getId());
			//设置title
			Element titleEle = bookEle.addElement("title");
			titleEle.setText(book.getTitle());
			//设置price
			Element priceEle = bookEle.addElement("price");
			priceEle.setText(book.getPrice());
			
			//保存
			XmlUtil.saveXml(document);
			
			return book.getId();
		} catch (Exception e) {
			return null;
		}
	}
	
	/**
	 * 通过Id查询书籍
	 * @param bookId
	 * @return
	 * @throws Exception 
	 */
	public BookBean findBookById(String bookId) throws Exception{
		//TODO findBookById
		//获得document
		Document document = XmlUtil.getDocument();
		//获得根
		Element rootElement = document.getRootElement();
		//通过id查询
		Node node = rootElement.selectSingleNode("//book[@id='" + bookId + "']");
		if(node != null){
			BookBean book = new BookBean();
			Element bookEle = (Element)node;
			//设置属性id
			String id = bookEle.attributeValue("id");
			book.setId(id);
			//设置title、price
			List childList = bookEle.elements();
			
			for(int m = 0 ; m < childList.size(); m++){
				Element child = (Element)childList.get(m);
				//titile
				if("title".equals(child.getName())){
					book.setTitle(child.getText());
				}
				//price
				if("price".equals(child.getName())){
					book.setPrice(child.getText());
				}
			}
			
			return book;
		} else {
			return null;
		}
	}

	/**
	 * 通过Id删除书籍
	 * @param bookId
	 * @return 返回书籍编号
	 */
	public String deleteBook(String bookId) {
		try {
			//获得document对象
			Document document = XmlUtil.getDocument();
			//获得根节点
			Element rootElement = document.getRootElement();
			//查询book id=
			Node findBook = rootElement.selectSingleNode("//book[@id='"+bookId+"']");
			//通过父节点删除当前节点
			findBook.getParent().remove(findBook);
			
			//保存结果
			XmlUtil.saveXml(document);
			return bookId;
		} catch (Exception e) {
			return null;
		}
	}

	/**
	 * 更新操作
	 * @param findBook
	 * @return
	 */
	public String updateBook(BookBean book) {
		
		try {
			//获得document
			Document document = XmlUtil.getDocument();
			//获得根元素
			Element rootElement = document.getRootElement();
			//查询bookId,修改相应的内容。因为不知道修改哪一项，所有除了ID其他的全部修改
			//查询bookId
			Node findBookNode = rootElement.selectSingleNode("//book[@id='" + book.getId() + "']");
			Element fingBook = (Element) findBookNode;
			//获得所有的孩子
			List childList = fingBook.elements();
			//遍历所有的孩子
			for(int n = 0 ; n < childList.size() ; n ++){
				//获得每一个孩子
				Element child = (Element) childList.get(n);
				//处理title
				if("title".equals(child.getName())){
					child.setText(book.getTitle());
				}
				//处理price
				if("price".equals(child.getName())){
					child.setText(book.getPrice());
				}
			}
			//保存
			XmlUtil.saveXml(document);
			return book.getId();
		} catch (Exception e) {
			return null;
		}
		
	}

	/**
	 * 查询所有
	 * @return
	 */
	public List findAllBook() {
		List list = new ArrayList();
		try {
			//获得document
			Document document = XmlUtil.getDocument();
			//获得根元素
			Element rootElement = document.getRootElement();
			//获得所有的book
			List bookList = rootElement.elements();
			for(int m = 0 ; m < bookList.size() ; m++){
				BookBean newBook = new BookBean();
				//获得每一个book元素
				Element bookEle = (Element)bookList.get(m);
				//获得id属性
				String id = bookEle.attributeValue("id");
				//设置id
				newBook.setId(id);
				
				//获得所有的孩子节点 -- title year
				List childList = bookEle.elements();
				for(int n = 0 ; n < childList.size() ; n++){
					//获得每一个孩子节点
					Element child = (Element) childList.get(n);
					//确定title
					if("title".equals(child.getName())){
						newBook.setTitle(child.getText());
					}
					//确定price
					if("price".equals(child.getName())){
						newBook.setPrice(child.getText());
					}
				}
				list.add(newBook);
			}
			return list;
		} catch (Exception e) {
			return null;
		}
	}

}
