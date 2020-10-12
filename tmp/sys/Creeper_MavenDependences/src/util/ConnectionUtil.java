/**
 * @author:稀饭
 * @time:上午12:44:01
 * @filename:ConnectionWork.java
 */
package util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.RandomAccessFile;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.NumberFormat;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class ConnectionUtil {
	private HttpURLConnection conn = null;
	private URL riceUrl = null;
	private BufferedReader in = null;
	private PrintWriter out = null;
	private File file = null;
	private FileWriter fileWriter = null;

	// private InputStream inputStream = null;

	/**
	 * 向指定URL发送get方法的请求
	 * 
	 * @param url
	 *            发送请求的URL
	 * @param requestProperty
	 *            header必備的些許數據。:后記得多加個空格
	 */
	public String sendGet(String url, String[] requestProperty) {
		String result = null;
		try {
			riceUrl = new URL(url);
		} catch (MalformedURLException e2) {
			// TODO Auto-generated catch block
			// e2.printStackTrace();
		}
		// 打开和URL之间的连接
		try {
			conn = (HttpURLConnection) riceUrl.openConnection();
		} catch (IOException e2) {
			// TODO Auto-generated catch block
			// e2.printStackTrace();
		}
		// System.out
		// .println("===================這是require的header========================");
		if (requestProperty != null) {
			for (int i = 0; i < requestProperty.length; i++) {
				String[] index = requestProperty[i].split(": ");
				// System.out.println(index[0] + "======>" + index[1]);
				conn.setRequestProperty(index[0], index[1]);
			}
		}
//		conn.setDoOutput(false);
//		conn.setDoInput(true);
//		conn.setConnectTimeout(3 * 1000);
//		conn.setReadTimeout(3 * 1000);
		try {
			if (conn != null)
				in = new BufferedReader(new InputStreamReader(
						conn.getInputStream(), "utf-8"));
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			// e1.printStackTrace();
		}
		if (in != null) {
			/* 输出response的header */
			// Map<String, List<String>> map = conn.getHeaderFields();
			// Set set = map.keySet();
			// Iterator<String> iterator = set.iterator();
			// System.out
			// .println("===================這是response的header========================");
			// for (; iterator.hasNext();) {
			// String key = (String) iterator.next();
			// List<String> list = map.get(key);
			// StringBuilder builder = new StringBuilder();
			// for (String str : list) {
			// builder.append(str).toString();
			// }
			// String firstCookie = builder.toString();
			// System.out.println(key = "===>" + firstCookie);
			// }
			String line = null;
			/* 获取response的值 */
			try {
				while ((line = in.readLine()) != null) {
					result += "\n" + line;
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				// e.printStackTrace();
			}
		}
		return result;
	}

	/**
	 * 向指定URL发送POST方法的请求
	 * 
	 * @param url
	 *            发送请求的URL
	 * @param param
	 *            请求参数，请求参数应该是name1=value1&name2=value2的形式。
	 * @param requestProperty
	 *            header必備的些許數據。
	 * @return String所代表远程资源的响应
	 */
	public String sendPost(String url, String[] requestProperty, String param) {
		try {
			riceUrl = new URL(url);
		} catch (MalformedURLException e2) {
			// TODO Auto-generated catch block
			// e2.printStackTrace();
		}
		// 打开和URL之间的连接
		try {
			conn = (HttpURLConnection) riceUrl.openConnection();
		} catch (IOException e2) {
			// TODO Auto-generated catch block
			// e2.printStackTrace();
		}
		// System.out
		// .println("===================這是require的header========================");
		for (int i = 0; i < requestProperty.length; i++) {
			String[] index = requestProperty[i].split(": ");
			// System.out.println(index[0] + "======>" + index[1]);
			conn.setRequestProperty(index[0], index[1]);
		}
		// 发送POST请求必须设置如下两行
		conn.setDoOutput(true);
		conn.setDoInput(true);

		// 获取URLConnection对象对应的输出流
		try {
			out = new PrintWriter(conn.getOutputStream());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// 发送请求参数
		out.print(param);
		// flush输出流的缓冲
		out.flush();
		// 定义BufferedReader输入流来读取URL的响应
		try {
			in = new BufferedReader(
					new InputStreamReader(conn.getInputStream()));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String line = null;
		String result = null;
		try {
			while ((line = in.readLine()) != null) {
				result += "\n" + line;
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Map<String, List<String>> map = conn.getHeaderFields();
		Set<String> set = map.keySet();
		Iterator<String> iterator = set.iterator();
		System.out.println("==================这是response的header=============");
		for (; iterator.hasNext();) {
			String key = (String) iterator.next();
			List<String> list = map.get(key);
			StringBuilder builder = new StringBuilder();
			for (String str : list) {
				builder.append(str).toString();
			}
			String firstCookie = builder.toString();
			System.out.println(key + "=====>" + firstCookie);
		}
		return result;
	}

	/**
	 * 向指定URL发送POST方法的请求
	 * 
	 * @param loginAction
	 *            发送请求的URL
	 * @param param
	 *            请求参数，请求参数应该是name1=value1&name2=value2的形式。
	 * @return String代表获取到的cookie
	 */
	public String getCookie(String param, String loginAction) throws Exception {
		// 登录
		riceUrl = new URL(loginAction);
		// String param = "username="+username+"&password="+password;
		conn = (HttpURLConnection) riceUrl.openConnection();
		conn.setDoInput(true);
		conn.setDoOutput(true);
		conn.setRequestMethod("POST");
		OutputStream out = conn.getOutputStream();
		out.write(param.getBytes());
		out.flush();
		out.close();
		String sessionId = "";
		String cookieVal = "";
		String key = null;
		// 取cookie
		for (int i = 1; (key = conn.getHeaderFieldKey(i)) != null; i++) {
			if (key.equalsIgnoreCase("set-cookie")) {
				cookieVal = conn.getHeaderField(i);
				cookieVal = cookieVal.substring(0, cookieVal.indexOf(";"));
				sessionId = sessionId + cookieVal + ";";
			}
		}
		return sessionId;
	}
	/**
	 * @Title: downloadFile
	 * @Description: 下载文件
	 * @param @param fileUrl
	 * @param @param path
	 * @param @param name
	 * @return void
	 */
	public void downloadFile(String fileUrl, String path, String name) {
		URL url = null;
		try {
			url = new URL(fileUrl);
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (url != null) {
			// String namepath =
			// fileUrl.replace("http://repo1.maven.org/maven2/",
			// "");
			File file = new File(path + name);
			createDir(path);
			HttpURLConnection conn = null;
			try {
				conn = (HttpURLConnection) url.openConnection();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			try {
				if (conn.getResponseCode() == 200) {
					InputStream inputStream = null;
					try {
						inputStream = conn.getInputStream();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					// String fileName =
					// fileUrl.split("/")[fileUrl.split("/").length -
					// 1];
					// 获取待上传的文件大小
					long size = conn.getContentLength();
					if (file.exists()) {
						if (size > file.length()) {
							file.delete();
							if (inputStream != null) {
								// upload(inputStream, file, fileName, size);
							}
						} else {
							System.out.println(name + "已存在文件夹中，无须下载，且大小为"
									+ file.length() + "，系统文件大小为" + size);
						}
					} else {
						if (inputStream != null)
							upload(inputStream, file, name, size);
					}
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}

	/**
	 * @Title: appendFile
	 * @Description: 往指定文件后面添加指定内容
	 * @param @param filePath 路徑
	 * @param @param htmlName 文明名（包含类型）
	 * @param @param content 指定內容
	 * @return void
	 */
	public void appendFile(String filePath, String htmlName, String content) {
		RandomAccessFile rf = null;
		try {
			rf = new RandomAccessFile(filePath + htmlName, "rw");
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			rf.seek(rf.length());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} // 将指针移动到文件末尾
		try {
			rf.writeBytes(content);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			rf.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}// 关闭文件流
	}

	/**
	 * @Title: createHtml
	 * @Description: 生成文件
	 * @param filePath
	 *            路径名称
	 * @param htmlName
	 *            文件的名字，不用加.txt
	 * @param contentData
	 *            内容
	 * @return void
	 */
	public void createHtml(String filePath, String htmlName, String contentData) {
		file = new File(filePath + htmlName);
		try {
			fileWriter = new FileWriter(file);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			// e.printStackTrace();
		}
		try {
			fileWriter.write(contentData);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			// e.printStackTrace();
		}
		try {
			if (fileWriter != null)
				fileWriter.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	// 创建路径，传过来的参数为 xxx/xxx/xxx
	public void createDir(String path) {

		File pathFile = new File(path);
		if (!pathFile.exists()) {
			pathFile.mkdirs();
		}
	}

	/**
	 * @Title: upload
	 * @Description: TODO
	 * @param @param inputStream
	 * @param @param file
	 * @param @param fileName
	 * @param @param size
	 * @return 開始下載
	 */
	public void upload(InputStream inputStream, File file, String fileName,
			long size) {
		long alreadySize = 0;
		FileOutputStream fos = null;
		try {
			fos = new FileOutputStream(file);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			// e.printStackTrace();
		}
		byte[] temp = new byte[1024];
		int len;
		try {
			while ((len = inputStream.read(temp)) != -1) {

				fos.write(temp, 0, len);
				alreadySize += len;
				System.out.println(fileName + ",总共" + size + ",已下载"
						+ alreadySize);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			// e.printStackTrace();
		}
		System.out.println(fileName + "已下载完成");
		try {
			fos.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			// e.printStackTrace();
		}
		try {
			inputStream.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			// e.printStackTrace();
		}
	}

	/**
	 * 获取完成度
	 * 
	 * @param p1
	 * @param p2
	 * @return
	 */
	public String percent(double p1, double p2) {
		String str;
		double p3 = p2 / p1;
		NumberFormat nf = NumberFormat.getPercentInstance();
		nf.setMaximumFractionDigits(0);
		str = nf.format(p3);
		return str;
	}
}
