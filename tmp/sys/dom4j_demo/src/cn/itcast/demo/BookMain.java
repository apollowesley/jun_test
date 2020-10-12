package cn.itcast.demo;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.List;

import cn.itcast.demo.bean.BookBean;
import cn.itcast.demo.dao.BookDao;

public class BookMain {
	public static void main(String[] args) throws Exception {
		//Ҫ�����û������һ������
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		System.out.println("��ӭ����ͼ����Ϣ����ϵͳ");
		while(true){
			//��ʾ�û�ѡ��
			System.out.println("��ѡ���������(C)������(U)����ѯ(R)��ɾ��(D)����ѯ����(A)���˳�(exit)[�����ִ�Сд]");
			System.out.print("������ѡ���");
			//����û�������ѡ��
			String line = reader.readLine();
			if("exit".equalsIgnoreCase(line)){ //�˳�����
				System.exit(0);
			} else if("c".equalsIgnoreCase(line)){ //���
				//����dao����ɲ�ѯ�ͱ���
				BookDao bookDao = new BookDao();
				
				//��Ϊ�û���������鼮ID����֪���Ƿ���ڣ�������Ҫʹ��ѭ��
				//�����û������id
				String id = null;
				//�û���ʾ
				String idTip = "�������鼮���:";
				while(id == null){
					//��ʾ�û�����ID
					System.out.print(idTip);
					//����û�����ID
					id = reader.readLine();
					
					// ���ID���������Ҫ���û���������
					BookBean findBookId = bookDao.findBookById(id);
					if(findBookId != null){ //����
						//�û������id�Ѵ��ڣ���Ҫ�û���������
						System.out.println("�鼮���["+id+"]�Ѵ���" );
						id = null;
						idTip = "�����������鼮���:";
					}
				}
				
				//����title
				System.out.print("�������鼮����:");
				String title= reader.readLine();
				//����price
				System.out.print("�������鼮�۸�:");
				String price= reader.readLine();
				
				BookBean newBook = new BookBean(id,title,price);
				
				String bookId = bookDao.saveBook(newBook);
				if(bookId == null){
					System.out.println("�������ݲ��ɹ��������²���");
					continue;
				}
				System.out.println("��ӳɹ���" + newBook);
			}else if("u".equalsIgnoreCase(line)){ //����
				//ȷ���û���Ҫ�޸ĵ��鼮
				System.out.print("��������Ҫ�޸��鼮�ı��:");
				String bookId = reader.readLine();
				//���bookDao,���в�ѯ���޸ĵȲ���
				BookDao bookDao = new BookDao();
				BookBean findBook = bookDao.findBookById(bookId);
				if(findBook == null){ //��Ҫ�޸ĵ��鼮������
					System.out.println("��Ҫ�޸ı��Ϊ["+bookId+"]���鼮�����ڣ������²���");
					continue;
				}
				System.out.println("Ҫ�޸ĵ��鼮���ݣ�" + findBook);
				System.out.println("��ѡ���޸Ĳ��������[n]���۸�[p]");
				System.out.print("����������ѡ�");
				String update = reader.readLine();
				if("n".equalsIgnoreCase(update)){
					System.out.print("�������鼮�������ƣ�");
					String title = reader.readLine();
					findBook.setTitle(title);
				} else if ("p".equalsIgnoreCase(update)){
					System.out.print("�������鼮���¼۸�");
					String price = reader.readLine();
					findBook.setPrice(price);
				} else {
					System.out.println("��֧�ֵ�ǰ����");
					continue;
				}
				//��Ҫ�޸ĺõ����ݱ���
				String bookIdTemp = bookDao.updateBook(findBook);
				//����ֵ���Ϊnull,˵��û�гɹ�
				if(bookIdTemp == null){
					System.out.println("���³��ִ������Ժ����ԡ�");
					continue;
				}
				System.out.println("���³ɹ���"+findBook+"��");
			}else if("r".equalsIgnoreCase(line)){ //ͨ��ID��ѯ
				//��ʾ������β�ѯ
				System.out.print("������Ҫ��ѯ��ͼ���ţ�");
				String bookId = reader.readLine();
				BookDao dao = new BookDao();
				BookBean book = dao.findBookById(bookId);
				//�ж�book�Ƿ����
				if(book == null){
					System.out.println("����ѯ����[id="+bookId+"]������");
					continue;
				}
				System.out.println("��ѯ�����" + book);
			}else if("d".equalsIgnoreCase(line)){	//ͨ��IDɾ��
				//��ȡ�û�����id
				System.out.print("������Ҫɾ����ͼ���ţ�");
				String bookId = reader.readLine();
				//���bookDao�������в�ѯ��ɾ���Ȳ���
				BookDao bookDao = new BookDao();
				//��ѯ��ǰbookId�Ƿ����
				BookBean findBook = bookDao.findBookById(bookId);
				if(findBook == null){ //��ѯ���󲻴���
					continue;
				}
				System.out.print("��ȷ��Ҫɾ����"+findBook+"���鼮��(Y/N)");
				String yn = reader.readLine();
				if("n".equalsIgnoreCase(yn)){
					System.out.println("��ȡ���˵�ǰ������");
				} else if ("y".equalsIgnoreCase(yn)){
					String bookIdTemp = bookDao.deleteBook(bookId);
					//�������ֵ��null˵��ɾ�����ɹ�
					if(bookIdTemp == null){
						System.out.println("ɾ�������г����쳣�����Ժ����ԡ�");
						continue;
					}
					//��ʾɾ���ɹ�
					System.out.println("ɾ���ɹ���" + findBook + "��");
				} else {
					System.out.println("����ѡ���������ʵ�����ԡ�");
				}
			} else if ("a".equalsIgnoreCase(line)){	//��ѯ����
				//���bookDao����ѯ����
				BookDao bookDao = new BookDao();
				List list = bookDao.findAllBook();
				if(list == null){
					System.out.println("��ѯ�����쳣�����Ժ����ԡ�");
					continue;
				}
				//������е�����
				System.out.println("��ѯ���("+list.size()+"��)");
				for(int n = 0 ; n < list.size() ; n ++){
					BookBean book = (BookBean) list.get(n);
					System.out.println((n+1) + ":" + book);
				}
				
			}else {
				System.out.println("ϵͳ��֧�ִ˲���������������");
			}
			
			
			
		}
		
		
		
		
	}
}
