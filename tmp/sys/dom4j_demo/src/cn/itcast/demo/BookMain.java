package cn.itcast.demo;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.List;

import cn.itcast.demo.bean.BookBean;
import cn.itcast.demo.dao.BookDao;

public class BookMain {
	public static void main(String[] args) throws Exception {
		//要求获得用户输入的一行数据
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		System.out.println("欢迎进入图书信息管理系统");
		while(true){
			//提示用户选择
			System.out.println("请选择操作项：添加(C)、更新(U)、查询(R)、删除(D)、查询所有(A)、退出(exit)[不区分大小写]");
			System.out.print("请输入选择项：");
			//获得用户的输入选项
			String line = reader.readLine();
			if("exit".equalsIgnoreCase(line)){ //退出程序
				System.exit(0);
			} else if("c".equalsIgnoreCase(line)){ //添加
				//创建dao，完成查询和保存
				BookDao bookDao = new BookDao();
				
				//因为用户的输入的书籍ID，不知道是否存在，所有需要使用循环
				//最终用户输入的id
				String id = null;
				//用户提示
				String idTip = "请输入书籍编号:";
				while(id == null){
					//提示用户输入ID
					System.out.print(idTip);
					//获得用户输入ID
					id = reader.readLine();
					
					// 检查ID，如果存在要求用户重新输入
					BookBean findBookId = bookDao.findBookById(id);
					if(findBookId != null){ //存在
						//用户输入的id已存在，需要用户重新输入
						System.out.println("书籍编号["+id+"]已存在" );
						id = null;
						idTip = "请重新输入书籍编号:";
					}
				}
				
				//输入title
				System.out.print("请输入书籍名称:");
				String title= reader.readLine();
				//输入price
				System.out.print("请输入书籍价格:");
				String price= reader.readLine();
				
				BookBean newBook = new BookBean(id,title,price);
				
				String bookId = bookDao.saveBook(newBook);
				if(bookId == null){
					System.out.println("保存数据不成功，请重新操作");
					continue;
				}
				System.out.println("添加成功：" + newBook);
			}else if("u".equalsIgnoreCase(line)){ //更新
				//确定用户需要修改的书籍
				System.out.print("请输入需要修改书籍的编号:");
				String bookId = reader.readLine();
				//获得bookDao,进行查询、修改等操作
				BookDao bookDao = new BookDao();
				BookBean findBook = bookDao.findBookById(bookId);
				if(findBook == null){ //需要修改的书籍不存在
					System.out.println("您要修改编号为["+bookId+"]的书籍不存在，请重新操作");
					continue;
				}
				System.out.println("要修改的书籍内容：" + findBook);
				System.out.println("请选择修改操作项：名称[n]、价格[p]");
				System.out.print("请输入您的选项：");
				String update = reader.readLine();
				if("n".equalsIgnoreCase(update)){
					System.out.print("请输入书籍的新名称：");
					String title = reader.readLine();
					findBook.setTitle(title);
				} else if ("p".equalsIgnoreCase(update)){
					System.out.print("请输入书籍的新价格：");
					String price = reader.readLine();
					findBook.setPrice(price);
				} else {
					System.out.println("不支持当前操作");
					continue;
				}
				//需要修改好的数据保存
				String bookIdTemp = bookDao.updateBook(findBook);
				//返回值如果为null,说明没有成功
				if(bookIdTemp == null){
					System.out.println("更新出现错误，请稍后重试。");
					continue;
				}
				System.out.println("更新成功【"+findBook+"】");
			}else if("r".equalsIgnoreCase(line)){ //通过ID查询
				//提示用书如何查询
				System.out.print("请输入要查询的图书编号：");
				String bookId = reader.readLine();
				BookDao dao = new BookDao();
				BookBean book = dao.findBookById(bookId);
				//判断book是否存在
				if(book == null){
					System.out.println("所查询数据[id="+bookId+"]不存在");
					continue;
				}
				System.out.println("查询结果：" + book);
			}else if("d".equalsIgnoreCase(line)){	//通过ID删除
				//获取用户输入id
				System.out.print("请输入要删除的图书编号：");
				String bookId = reader.readLine();
				//获得bookDao，并进行查询、删除等操作
				BookDao bookDao = new BookDao();
				//查询当前bookId是否存在
				BookBean findBook = bookDao.findBookById(bookId);
				if(findBook == null){ //查询对象不存在
					continue;
				}
				System.out.print("您确定要删除【"+findBook+"】书籍吗？(Y/N)");
				String yn = reader.readLine();
				if("n".equalsIgnoreCase(yn)){
					System.out.println("您取消了当前操作。");
				} else if ("y".equalsIgnoreCase(yn)){
					String bookIdTemp = bookDao.deleteBook(bookId);
					//如果返回值是null说明删除不成功
					if(bookIdTemp == null){
						System.out.println("删除过程中出现异常，请稍后重试。");
						continue;
					}
					//提示删除成功
					System.out.println("删除成功【" + findBook + "】");
				} else {
					System.out.println("输入选项有误，请核实后重试。");
				}
			} else if ("a".equalsIgnoreCase(line)){	//查询所有
				//获得bookDao，查询所有
				BookDao bookDao = new BookDao();
				List list = bookDao.findAllBook();
				if(list == null){
					System.out.println("查询出现异常，请稍后重试。");
					continue;
				}
				//输出所有的内容
				System.out.println("查询结果("+list.size()+"条)");
				for(int n = 0 ; n < list.size() ; n ++){
					BookBean book = (BookBean) list.get(n);
					System.out.println((n+1) + ":" + book);
				}
				
			}else {
				System.out.println("系统不支持此操作，请重新输入");
			}
			
			
			
		}
		
		
		
		
	}
}
