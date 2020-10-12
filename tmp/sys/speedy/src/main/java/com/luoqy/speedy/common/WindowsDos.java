package com.luoqy.speedy.common;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
/**
 * windos 系统下cmd命令
 * */
public class WindowsDos {
    /**
     * CMD命令执行
     * */
    public static boolean runCMD(String cmd){
        final String METHOD_NAME = "runCMD";

        // Process p = Runtime.getRuntime().exec("cmd.exe /C " + cmd);
        BufferedReader br = null;
        try {
	        Process p = Runtime.getRuntime().exec(cmd);
            // br = new BufferedReader(new InputStreamReader(p.getErrorStream()));
            br = new BufferedReader(new InputStreamReader(p.getInputStream()));
            String readLine = br.readLine();
            StringBuilder builder = new StringBuilder();
            while (readLine != null) {
                readLine = br.readLine();
                builder.append(readLine);
            }
            System.err.println(METHOD_NAME + "#readLine: " + builder.toString());

            p.waitFor();
            int i = p.exitValue();
            System.err.println(METHOD_NAME + "#exitValue = " + i);
            if (i == 0) {
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            System.err.println(METHOD_NAME + "#ErrMsg=" + e.getMessage());
            e.printStackTrace();
        } finally {
            if (br != null) {
                try {
					br.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
            }
        }
        return false;
    }
}
