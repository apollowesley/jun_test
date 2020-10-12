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
	 * ��������
	 * @param book
	 * @return bookId
	 */
	public String saveBook(BookBean book){
		try {
			//���document
			Document document = XmlUtil.getDocument();
			Element rootElement = document.getRootElement();
			//��book����Ƭ��
			Element bookEle = rootElement.addElement("book");
			//����ID����
			bookEle.addAttribute("id", book.getId());
			//����title
			Element titleEle = bookEle.addElement("title");
			titleEle.setText(book.getTitle());
			//����price
			Element priceEle = bookEle.addElement("price");
			priceEle.setText(book.getPrice());
			
			//����
			XmlUtil.saveXml(document);
			
			return book.getId();
		} catch (Exception e) {
			return null;
		}
	}
	
	/**
	 * ͨ��Id��ѯ�鼮
	 * @param bookId
	 * @return
	 * @throws Exception 
	 */
	public BookBean findBookById(String bookId) throws Exception{
		//TODO findBookById
		//���document
		Document document = XmlUtil.getDocument();
		//��ø�
		Element rootElement = document.getRootElement();
		//ͨ��id��ѯ
		Node node = rootElement.selectSingleNode("//book[@id='" + bookId + "']");
		if(node != null){
			BookBean book = new BookBean();
			Element bookEle = (Element)node;
			//��������id
			String id = bookEle.attributeValue("id");
			book.setId(id);
			//����title��price
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
	 * ͨ��Idɾ���鼮
	 * @param bookId
	 * @return �����鼮���
	 */
	public String deleteBook(String bookId) {
		try {
			//���document����
			Document document = XmlUtil.getDocument();
			//��ø��ڵ�
			Element rootElement = document.getRootElement();
			//��ѯbook id=
			Node findBook = rootElement.selectSingleNode("//book[@id='"+bookId+"']");
			//ͨ�����ڵ�ɾ����ǰ�ڵ�
			findBook.getParent().remove(findBook);
			
			//������
			XmlUtil.saveXml(document);
			return bookId;
		} catch (Exception e) {
			return null;
		}
	}

	/**
	 * ���²���
	 * @param findBook
	 * @return
	 */
	public String updateBook(BookBean book) {
		
		try {
			//���document
			Document document = XmlUtil.getDocument();
			//��ø�Ԫ��
			Element rootElement = document.getRootElement();
			//��ѯbookId,�޸���Ӧ�����ݡ���Ϊ��֪���޸���һ����г���ID������ȫ���޸�
			//��ѯbookId
			Node findBookNode = rootElement.selectSingleNode("//book[@id='" + book.getId() + "']");
			Element fingBook = (Element) findBookNode;
			//������еĺ���
			List childList = fingBook.elements();
			//�������еĺ���
			for(int n = 0 ; n < childList.size() ; n ++){
				//���ÿһ������
				Element child = (Element) childList.get(n);
				//����title
				if("title".equals(child.getName())){
					child.setText(book.getTitle());
				}
				//����price
				if("price".equals(child.getName())){
					child.setText(book.getPrice());
				}
			}
			//����
			XmlUtil.saveXml(document);
			return book.getId();
		} catch (Exception e) {
			return null;
		}
		
	}

	/**
	 * ��ѯ����
	 * @return
	 */
	public List findAllBook() {
		List list = new ArrayList();
		try {
			//���document
			Document document = XmlUtil.getDocument();
			//��ø�Ԫ��
			Element rootElement = document.getRootElement();
			//������е�book
			List bookList = rootElement.elements();
			for(int m = 0 ; m < bookList.size() ; m++){
				BookBean newBook = new BookBean();
				//���ÿһ��bookԪ��
				Element bookEle = (Element)bookList.get(m);
				//���id����
				String id = bookEle.attributeValue("id");
				//����id
				newBook.setId(id);
				
				//������еĺ��ӽڵ� -- title year
				List childList = bookEle.elements();
				for(int n = 0 ; n < childList.size() ; n++){
					//���ÿһ�����ӽڵ�
					Element child = (Element) childList.get(n);
					//ȷ��title
					if("title".equals(child.getName())){
						newBook.setTitle(child.getText());
					}
					//ȷ��price
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
