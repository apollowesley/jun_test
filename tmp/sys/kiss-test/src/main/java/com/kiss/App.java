package com.kiss;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        System.out.println( "Hello World!" );
        String str = null;
        System.out.println("".equals(null));
        System.out.println("".equals(str));
        for(int i=0;i<100;i++){
        	if(i==10)break;
        	System.out.println(i);
        }
        for(int i=0;i<100;i++){
        	if(i==10)continue;
        	System.out.println(i);
        }
    }
}
