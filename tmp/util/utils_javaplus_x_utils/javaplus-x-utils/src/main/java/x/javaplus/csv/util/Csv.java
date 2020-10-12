package x.javaplus.csv.util;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import x.javaplus.collections.Lists;
import x.javaplus.util.Util;

/**
 * 单个 Csv 结构
 * @author deng		
 * @date 2015-6-19 下午12:57:53
 */
public class Csv {

	private String filePath;
	private String fileName;
	
	// 原数据
	private List<String> content;
	// 值
	private List<Map<String, String>> values;
	
	private Map<Integer, Head> heads ;
	
	public String getFileName(){ return fileName; }
	public String getFilePath(){ return filePath; }
	public List<String> getContent(){ return content; }
	public List<Map<String, String>> getValues(){ return values; }
	
	public Csv( String path ){
		
		filePath = path;
		fileName = generatorFileName();
		
		content = Util.File.getLines( filePath );
		if( content.size() < 2 ) 
			throw new RuntimeException( filePath+" 文件小于两行数据" );
		
		// 包装 类型和字段名
		wrapTypeAndName( );
		
		wrapData( );
	}

	
	
	private void wrapTypeAndName() {
		
		heads 	= new HashMap<Integer, Head>();
		
		String[] types = content.get(0).split(",");
		String[] names = content.get(1).split(",");
		
		for( int i = 0; i < types.length; i++ ){
			
			String t = types[i].trim();
			String n = names[i].trim();
			if( t.isEmpty() || n.isEmpty() ) 
				continue;
			
			heads.put( i , new Head( i, types[i], names[i] ) );
		}
	}
	
	private String generatorFileName() {
		int index = filePath.lastIndexOf( "/" );
		if( index == -1 )
			index = filePath.lastIndexOf( "\\" );
		return filePath.substring( index+1, filePath.length() );
	}
	
	
	private void wrapData() {
		
		values 	= Lists.newArrayList();
		
		for( int i = 2; i < content.size(); i++ ){
			
			String[] col = content.get(i).split(",");
			
			Map<String, String> data = new HashMap<String, String>();
			
			for( Head head : heads.values() ){
				try {
					data.put( head.name, col[head.i].trim() );
				} catch (Exception e) {
					data.put( head.name, "" );
				}
			}
//			System.out.println( content.get(i) );
			values.add( data );
		}
	}
	
	/**
	 * 精确获取
	 * @param col 第几行
	 * @param name 字段名
	 * @return
	 */
	public String getValue( int col, String name ){
		return values.get(col).get(name);
	}
	
	/**
	 * 获取字段 整列数据
	 * @param name
	 * @return
	 */
	public List<String> getValue( String name ){
		List<String> ret = Lists.newArrayList();
		for( int i = 0; i < values.size(); i++ ){
			Map<String, String> data = values.get(i);
			ret.add( data.get(name) );
		}
		return ret;
	}
	
	/**
	 * 获取 字段列表
	 * @return
	 */
	public List<String> getNames(){ 
		List<String> ret = Lists.newArrayList();
		for( Head head : heads.values() )
			ret.add( head.name );
		return ret ; 
	}
	
	/**
	 * 获取类型列表
	 * @return
	 */
	public List<CsvType> getTypes(){ 
		List<CsvType> ret = Lists.newArrayList();
		for( Head head : heads.values() )
			ret.add( new CsvType( head.type ) );
		return ret ; 
	}
	
}

class Head{
	int i;
	String type;
	String name;
	public Head( int i, String type, String name) {
		this.i = i;
		this.type = type;
		this.name = name;
	}
}
