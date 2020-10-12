package doroodoplug.tcpser;

import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.SocketException;

import doroodoplug.tools.Tools;
import doroodoplug.util.socket.SingleTask;


public class SimpleTask extends SingleTask {


	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		//从serverClientSocket这个属性中区获取数据或者发送消息出去
		
		/*Accept之后,要做停顿, 否则数据都还没有接收完毕*/
		/**下面仅仅是Demo**/
		try
		{
		Thread.sleep(100);
		InputStream in = serverClientSocket.getInputStream();
		OutputStream out = serverClientSocket.getOutputStream();
		Long currentThreadId = Thread.currentThread().getId();
		System.out.println("连接已经建立, 当前处理的线程ID为:" +  currentThreadId);
		
		
		readRemote(in, out);
		
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	//进行处理和分发的征程动作
	private void readRemote(InputStream in, OutputStream out) throws Exception
	{
		
		byte[] bufIn = new byte[BUFFER_SIZE];
		int bytesLen = -1;
		
		//注意, 阻塞的方式取得字符, 读完了就会阻塞
		try
		{
			while( (bytesLen = in.read(bufIn)) > 0)
			{
				System.out.println("受到的请求为:" + new String(bufIn).trim());
				String line=new String(bufIn).trim();
				PrintWriter printWriter =new PrintWriter(out,true);
				if (line.indexOf("reshproject-") != -1){
					boolean flag = Tools.reshProjectFile(line.split("-")[1]);
					printWriter.println((new StringBuilder(String.valueOf(line))).append(":").append(flag).toString());
				}else if(line.indexOf("getprojectpath-") != -1){
					printWriter.println(Tools.getProjectPath(line.split("-")[1]));
				}
				printWriter.flush();
				printWriter.close();
			}
		}
		
		catch(SocketException e)
		{
			System.out.println("客户端发的连接已经中断");
		}

	}
	// 设置一次接收数据的大小, 如果不设置,默认为1M, 后续最好不要修改
	private static int BUFFER_SIZE = 1024 * 1024;
	
	


}
