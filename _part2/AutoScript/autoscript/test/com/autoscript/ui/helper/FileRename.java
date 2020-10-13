package com.autoscript.ui.helper;

import java.io.File;

public class FileRename {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		File f;
		System.out.println("args[0]:"+args[0]);
		System.out.println("args[1]:"+args[1]);
		f =new File(args[0]);
		if(f.renameTo(new File(args[1]))){
			System.out.println("Rename success!");
		}else{
			System.out.println("Rename fail!");
		}

	}

}
