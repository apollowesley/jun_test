package name.liushiyao.junit;

import org.junit.*;

import static org.junit.Assert.assertEquals;

/**
 * 单元测试：是对类中的每一个方法提供一个或多个测试的一种实践，
 * 其目的是为了有规律地测试一个类的各个部分是否具有正确的行为。
 *
 * 程序员编写单元测试时，唯一做的就是决定测试是成功还是失败(如果返回值为boolean)
 *
 */



public class MyMathTest {

        private String name = "name";

        @Test
        public void add ( ) throws Exception {
                assertEquals ( MyMath.add ( 1,2 ),3);
                System.out.println (name );
        }

        @Ignore
        public void dec ( ) throws Exception {
                System.out.println ("Ignore running" );
        }
        //在测试方法开始之前运行
        @Before
        public void testBefore(){

                System.out.println ("Before running..." );

        }
        //在测试方法结束之后运行
        @After
        public void testAfter(){
                System.out.println ("After running..." );
        }
        //在所有测试方法开始之前运行
        @BeforeClass
        public static void testBeforeClass(){
                System.out.println ("Before class running" );
        }
        //在所有测试结束之后运行
        @AfterClass
        public static void testAfterClass(){
                System.out.println ("After class running" );
        }

}