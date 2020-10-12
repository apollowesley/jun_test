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
		//��serverClientSocket�������������ȡ���ݻ��߷�����Ϣ��ȥ
		
		/*Accept֮��,Ҫ��ͣ��, �������ݶ���û�н������*/
		/**���������Demo**/
		try
		{
		Thread.sleep(100);
		InputStream in = serverClientSocket.getInputStream();
		OutputStream out = serverClientSocket.getOutputStream();
		Long currentThreadId = Thread.currentThread().getId();
		System.out.println("�����Ѿ�����, ��ǰ������߳�IDΪ:" +  currentThreadId);
		
		
		readRemote(in, out);
		
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	//���д���ͷַ������̶���
	private void readRemote(InputStream in, OutputStream out) throws Exception
	{
		
		byte[] bufIn = new byte[BUFFER_SIZE];
		int bytesLen = -1;
		
		//ע��, �����ķ�ʽȡ���ַ�, �����˾ͻ�����
		try
		{
			while( (bytesLen = in.read(bufIn)) > 0)
			{
				System.out.println("�ܵ�������Ϊ:" + new String(bufIn).trim());
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
			System.out.println("�ͻ��˷��������Ѿ��ж�");
		}

	}
	// ����һ�ν������ݵĴ�С, ���������,Ĭ��Ϊ1M, ������ò�Ҫ�޸�
	private static int BUFFER_SIZE = 1024 * 1024;
	
	


}
