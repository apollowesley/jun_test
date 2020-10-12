package x.javaplus.string;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 敏感字符過濾
 * @author deng		
 * @date 2016-3-11 下午3:11:18
 */
public class WordFilter {
	private static Map<Character,List<String>> wordListToMap(List<String> sensitiveWordList){  
        Map<Character,List<String>> result=new HashMap<Character,List<String>>();  
        for (String s:sensitiveWordList){  
            char c=s.charAt(0);  
            List<String> strs=result.get(c);  
            if (strs==null){  
                strs=new ArrayList<String>();  
                result.put(c,strs);  
            }  
            strs.add(s);  
        }  
          
        return result;  
    }  
      
    public static String filter(String src,List<String> sensitiveWordList){  
        Map<Character,List<String>> wordMap=wordListToMap(sensitiveWordList);  
          
        StringBuilder strb=new StringBuilder();  
        for (int i=0;i<src.length();i++){  
            char c=src.charAt(i);  
            String find=null;  
            if (wordMap.containsKey(c)){  
                List<String> words=wordMap.get(c);  
                for (String s:words){  
                    String temp=src.substring(i,(s.length()<=(src.length()-i))?i+s.length():i);  
                    if (s.equals(temp)){  
                        find=s;  
                        break;  
                    }  
                }  
            }  
            if (find!=null){  
                strb.append("***");  
                i+=(find.length()-1);  
            } else {  
                strb.append(c);  
            }  
        }  
        return strb.toString();  
    }  
    /** 
     * @param args 
     */  
    public static void main(String[] args) {  
        List<String> wordList=new ArrayList<String>();  
        wordList.add("TMD");  
        wordList.add("变态");  
        System.out.println(filter("你TMD，也太缺德了吧",wordList));  
        System.out.println(filter("你TMD，也太缺德了TMD吧",wordList));  
        System.out.println(filter("你TMD，也太缺德了吧TM",wordList));  
        System.out.println(filter("你TMD，也太缺德了，太变态了吧TM",wordList));  
        wordList.add("TM");  
        System.out.println(filter("你TMD，也太缺德了，太变态了吧TM",wordList));  
    }  
}
