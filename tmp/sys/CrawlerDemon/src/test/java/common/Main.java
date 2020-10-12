/*
 * developer spirit_demon  @ 2015.
 */

package common;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Administrator on 2015/7/15.
 */
public class Main {


    public static void main(String[] args) {
        String txt = "createPageHTML('page_div','15','1','10','148');";

        //String re1="\\w+\\W{2}\\w+\\W{3}\\d+\\W{3}\\d+\\W{3}\\d+\\W{3}\\d+\\W{3}";	// Word 1
        String re1 = "\\d+";    // Word 1
        Pattern p = Pattern.compile(re1, Pattern.CASE_INSENSITIVE | Pattern.DOTALL);
        Matcher m = p.matcher(txt);
        if (m.find()) {
            for (int i = 0; i <= m.groupCount(); i++) {
                System.out.println(m.group(i).toString());
            }


        }

        //   boolean fa=   data.matches("createPageHTML('page_div','\\d','\\d','\\d','\\d');");


        //System.out.print(fa);
    }

}
