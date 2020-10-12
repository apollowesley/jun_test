package com.ukefu.util;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.lionsoul.ip2region.DataBlock;
import org.lionsoul.ip2region.DbConfig;
import org.lionsoul.ip2region.DbMakerConfigException;
import org.lionsoul.ip2region.DbSearcher;

public class IPTools {
	private String IP_DATA_PATH = "WEB-INF/data/ip/ip.db";
	private static IPTools iptools = new IPTools();
	private DbSearcher _searcher = null ;
	
	public static IPTools getInstance(){
		return iptools ;
	}

	public IPTools() {
		try {
			File tempFile = File.createTempFile("ipdata", "db") ;
			FileUtils.copyInputStreamToFile(IPTools.class.getClassLoader().getResourceAsStream(IP_DATA_PATH),tempFile);
			_searcher = new DbSearcher(new DbConfig(),tempFile.getAbsolutePath());
		} catch (DbMakerConfigException | IOException e) {
			e.printStackTrace();
		} 
	}

//	public static void main(String[] args) {
//		IP ip = IPTools.getInstance().findGeography("219.128.235.211") ;
//		System.out.println(ip.getCountry());
//		System.out.println(ip.getProvince());
//		System.out.println(ip.getCity());
//	}

	public IP findGeography(String remote) {
		IP ip = new IP();
		try{
			DataBlock block = _searcher.binarySearch(remote!=null ? remote : "127.0.0.1")  ;
			if(block!=null && block.getRegion() != null){
				String[] region = block.getRegion().split("[\\|]") ;
				if(region.length == 5){
					ip.setCountry(region[0]);
					ip.setRegion(region[1]);
					ip.setProvince(region[2]);
					ip.setCity(region[3]);
					ip.setIsp(region[4]);
					
				}
			}
		}catch(Exception ex){
			ex.printStackTrace();
		}
		return ip;
	}
}
