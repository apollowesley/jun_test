package cn.itcast.demo;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import cn.itcast.demo.dao.BookDao;
import cn.itcast.demo.domain.Book;

/**
 * 整个程序的入口
 * @author <a href="mailto:liangtong@itcast.cn">梁桐</a>
 * @see java.lang.String
 */
public class DemoMain {
	
	public static void main(String[] args) throws Exception {
		System.out.println("欢迎进入图书管理系统");
		//获取用户的输入内容  -- 获取用户输入的一条数据
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		
		while(true){  
			System.out.println("查询(R)、添加(C)、修改(U)、删除(D)、退出(exit)【不区分大小写】");
			String line = reader.readLine();
			//退出
			if("exit".equalsIgnoreCase(line)){
//				System.exit(0);
				break;
			} else if ("r".equalsIgnoreCase(line)){
				//TODO 查询
				//通过书籍编号进行书籍的查询，如果有结果将显示，否则提示没有结果
				// * 获得用户输入的编号id
				System.out.print("请输入书籍编号：");
				String id = reader.readLine();
				
				//查询
				// * 创建dao
				BookDao bookDao = new BookDao();
				// * 通过dao
				Book book = bookDao.findBookById(id);
				
				//结果的处理
				if(book == null){
					//没有查询结果
					System.out.println("未找到相应的书籍【编号："+id+"】");
					continue;
				}
				System.out.println("查询结果 " + book);
				
				
				
			}  else if ("c".equalsIgnoreCase(line)){
				//TODO 添加
			}  else if ("u".equalsIgnoreCase(line)){
				//TODO 修改
			}  else if ("d".equalsIgnoreCase(line)){
				//TODO 删除
			} 
			
		}
		
	}

}
