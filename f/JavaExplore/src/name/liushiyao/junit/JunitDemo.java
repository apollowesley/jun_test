package name.liushiyao.junit;


import junit.framework.TestCase;
import org.junit.Test;

/**
 * Created by 北岭山下 on 2017/5/25.
 */
public class JunitDemo {

        @Test
        public void   addMathTest(){

                TestCase.assertEquals ( MyMath.add ( 2,2 ),3);

        }
        @Test
        public void addStringTest(){

                TestCase.assertEquals ( StringUtils.addString ( "123","56" ),"123456" );

        }

        public static void main ( String[] args ) {

                System.out.println ( );

        }

}
