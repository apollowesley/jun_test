package utils;

/**
 * Created by eason on 2017/8/23.
 */
public class StringUtils {
    public  static  String  StringTraslation(String value){
        if(value==null || value.length()<1) return value;
        char[] cs=value.toCharArray();
        cs[0]-=32;
        for(int i= 0;i<cs.length;i++){
            if(cs[i]=='_'){
                if(i + 1<cs.length) {
                    if(cs[i+1]>96 &&cs[i+1]<123) {
                        cs[i + 1] -= 32;
                    }
                }
            }
        }
        return String.valueOf(cs).replace("_","");
    }

    public static void main(String[] args) {
        System.out.println(StringTraslation("gds_class"));
    }

    public  static boolean isEmpty(String value){
        if(value==null)return true;
        if(value.length()==0)return true;
    return false;}
}
