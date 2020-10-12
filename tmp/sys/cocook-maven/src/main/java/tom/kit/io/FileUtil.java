package tom.kit.io;

import java.io.*;
import java.net.URLEncoder;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;


import org.apache.commons.lang.RandomStringUtils;


/**
 * File 工具包,已测试
 * 
 * @author tomsun
 */
public class FileUtil {

	public static String readLine(File _file, String encode) {
		StringBuffer buff = new StringBuffer();
		String s = null;
		BufferedReader reader = null;
		try {
			reader = new BufferedReader(new InputStreamReader(new FileInputStream(_file), encode));
			while ((s = reader.readLine()) != null) {
				buff.append(s);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (reader != null)
					reader.close();
			} catch (IOException e) {
				reader = null;
			}
		}
		return buff.toString();
	}

	public static String streamToStr(InputStream in, String encode) {
		StringBuffer buff = new StringBuffer();
		String s = null;
		BufferedReader reader = null;
		try {
			reader = new BufferedReader(new InputStreamReader(in, encode));
			while ((s = reader.readLine()) != null) {
				buff.append(s); // \r 回车, \n 换行, 一般同时使用
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (reader != null)
					reader.close();
			} catch (IOException e) {
				reader = null;
			}
		}
		return buff.toString();
	}

	public static InputStream strToStream(String str) {
		return new ByteArrayInputStream(str.getBytes());
	}

	public static InputStream ToInStream(ByteArrayOutputStream out) {
		return new ByteArrayInputStream(out.toByteArray());
	}

	
	/**
	 * 读取图片显示在页面
	 */
//	public static void readImage(HttpServletResponse res, InputStream _in) {
//		String contentType = "image/jpeg";
//		String header[] = { "Cache-Control", "no-cache" };
//		try{
//			DownLoadComm(res, _in, contentType, header);
//		}catch(Exception e){
//			e.printStackTrace();
//		}
//	}

	/**
	 * @param resContext
	 * @param _file
	 * @param _encoding 添加编码
	 * @param _suffix  添加后缀
	 * @return
	 * @throws Exception
	 */
//	public static int downLoad(HttpServletResponse res, File _file, String _encoding, String _suffix) {
//		InputStream in = null;
//		String encoding = _encoding.isEmpty() ? "UTF-8" : _encoding;
//		String fileName = _suffix.isEmpty() ? _file.getName() : _file.getName() + "." + _suffix;
//
//		try {
//			fileName = URLEncoder.encode(fileName, encoding);
//			String contentType = "APPLICATION/OCTET-STREAM";
//			String[] header = { "Content-Disposition", "attachment; filename=" + fileName };
//			in = new BufferedInputStream(new FileInputStream(_file));
//			DownLoadComm(res, in, contentType, header);
//		} catch (Exception e) {
//			e.printStackTrace();
//			return 1;
//		}
//		return 0;
//	}

	/**
	 * 下载通用类
	 * 
	 * @param resContext
	 * @param _in
	 * @param _contentType
	 * @param _header
	 */
//	public static void DownLoadComm(HttpServletResponse res, InputStream _in, String _contentType, String _header[]) throws IOException{
//		res.setContentType(_contentType);
//		res.setHeader(_header[0], _header[1]);
//		copy(_in, res.getOutputStream());
//	}
	
	/**
	 * copy一个文件夹下面的文件到另一个文件夹
	 * @param source 必须是文件夹
	 * @param target 必须是文件夹
	 * @throws IOException
	 */
	public static void copyFolder(String source, String target){
		new File(target).mkdirs(); //目标文件夹
		File fsource = new File(source);
		String[] fNames = fsource.list();
		for(String name : fNames){
			File temp  = new File(source + "/" + name);
			if(temp.isFile()){
				try {
					copy(new FileInputStream(temp),new FileOutputStream(target+"/"+name));
				} catch (FileNotFoundException e) {
				}
			}else{
				copyFolder(source + "/" + name, target+"/"+name);
			}
		}
	}
	
	
	/**
	 * 删除某个文件夹中的文件
	 * @param ObjectPath
	 */
	public static void deleteDirectory(String ObjectPath) {
		File file = new File(ObjectPath);
		File[] files =  file.listFiles();
		for(File f : files){
			if(f.isFile()){
				f.delete();
			}else{
				/*先删除文件夹的内文件, 再删文件夹*/
				deleteDirectory(ObjectPath+"/"+f.getName());
				f.delete();
			}
		}
	}

	/**
	 * IO流复制
	 * @param _out
	 * @param _in
	 * @return
	 * @throws Exception
	 */
	public static void copy(InputStream in, OutputStream out) {
		InputStream _in = null;
		OutputStream _out = null;
		try {
			_in = new BufferedInputStream(in);
			_out = new BufferedOutputStream(out);
			byte[] b = new byte[1024];
			int len = 0;
			while ((len = _in.read(b, 0, b.length)) != -1) {
				_out.write(b, 0, len);
			}
			_out.flush();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			close(_in, _out);
		}
	}

	/**
	 * 文件拷贝
	 * 
	 * @param _in
	 * @param _out
	 * @throws Exception
	 */
	public static void transfer(FileInputStream in, FileOutputStream out) {
		FileChannel source = null, target = null;
		try {
			source = in.getChannel();
			target = out.getChannel();
			long size = source.size();
			source.transferTo(0, size, target);
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			close(source, target);
		}
	}

	/**
	 * 文件复制
	 * 
	 * @throws IOException
	 */
	public static void copyFile(File source, File target) throws IOException {
		FileChannel inChannel = new FileInputStream(source).getChannel();
		FileChannel outChannel = new FileOutputStream(target).getChannel();
		ByteBuffer bb = ByteBuffer.allocate(1024);
		try {
			/* 从inChannel文件中读出count bytes ，并写入outChannel文件。 */
			while (inChannel.read(bb) != -1) {
				bb.flip();
				outChannel.write(bb);
				bb.clear(); // prepare for reading;清空缓冲区；
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			close(inChannel, outChannel);
		}
	}

	public static void close(InputStream in, OutputStream out) {
		if (in != null) {
			try {
				in.close();
			} catch (IOException e) {
				in = null;
			}
		}
		if (out != null) {
			try {
				out.close();
			} catch (IOException e) {
				in = null;
			}
		}
	}

	public static void close(FileChannel source, FileChannel target) {

		if (source != null) {
			try {
				source.close();
			} catch (IOException e) {
				source = null;
			}
		}
		if (target != null) {
			try {
				target.close();
			} catch (IOException e) {
				target = null;
			}
		}
	}
	
	public static String randomstr(int size){
		return RandomStringUtils.randomAlphanumeric(size);
	}
}
