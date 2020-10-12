package org.frameworkset.platform.genproject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;

import org.apache.log4j.Logger;

public class StreamGobbler extends Thread {
	private static Logger log = Logger.getLogger(StreamGobbler.class);
    InputStream is;
    String      type;
    OutputStream os;
    StreamGobbler(InputStream is, String type) {
        this(is, type, null);
    }
    StreamGobbler(InputStream is, String type, OutputStream redirect) {
        this.is = is;
        this.type = type;
        this.os = redirect;
    }
    public void run() {
    	 PrintWriter pw = null;
    	  InputStreamReader isr = null;
    	  BufferedReader br = null;
        try {
           
            if (os != null)
                pw = new PrintWriter(os);
             isr = new InputStreamReader(is);
            br = new BufferedReader(isr);
            String line = null;
            while ((line = br.readLine()) != null) {
                if (pw != null)
                    pw.println(line);
                log.info(type + ">" + line);
            }
            if (pw != null)
                pw.flush();
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
        finally{
        	try {
				if(pw != null)
					pw.close();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        	try {
				if(os != null)
					os.close();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        	try {
				if(is != null)
					is.close();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        	try {
				if(isr != null)
					isr.close();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        	try {
				if(br != null)
					br.close();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        }
    }
}