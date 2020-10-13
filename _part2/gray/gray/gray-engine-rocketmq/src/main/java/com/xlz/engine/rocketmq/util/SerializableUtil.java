package com.xlz.engine.rocketmq.util;
import java.io.ByteArrayInputStream;   
import java.io.ByteArrayOutputStream;   
import java.io.IOException;   
import java.io.ObjectInputStream;   
import java.io.ObjectOutputStream;   
 
  
public class SerializableUtil {   
  
    /**  
     * 对象转数组  
     * @param obj  
     * @return  
     * @throws IOException 
     */  
    public static byte[] toByteArray (Object obj) throws IOException {      
        byte[] bytes = null;      
        ByteArrayOutputStream bos = new ByteArrayOutputStream();    
        ObjectOutputStream oos = null;
        try {        
            oos = new ObjectOutputStream(bos);         
            oos.writeObject(obj);        
            oos.flush();         
            bytes = bos.toByteArray ();      
        } catch (IOException ex) {        
            throw ex; 
        }finally{
			oos.close();         
            bos.close(); 
        }    
        return bytes;    
    }   
       
    /**  
     * 数组转对象  
     * @param bytes  
     * @return  
     * @throws IOException 
     * @throws ClassNotFoundException 
     */  
    public static Object toObject (byte[] bytes) throws IOException, ClassNotFoundException {      
        Object obj = null;    
        ObjectInputStream ois = null;
        ByteArrayInputStream bis = null;
        try {        
            bis = new ByteArrayInputStream (bytes);        
            ois = new ObjectInputStream (bis);        
            obj = ois.readObject();      
        } catch (IOException ex) {        
            throw ex;
        } catch (ClassNotFoundException e) {
			throw e;
		} finally{
			ois.close();   
            bis.close();
		}    
        return obj;    
    }   
       
    
}