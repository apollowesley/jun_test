//package com.flypigs.lock;
//
///**
// * Unit test for simple App.
// */
//public class AppTest
//{
//        public static class LazyHolder {
//            private static final AppTest INSTANCE = new AppTest();
//            static {
//                System.out.println("LazyHolder static code block");
//            }
//            {
//                System.out.println("LazyHolder code block");
//            }
//        }
//        private AppTest (){
//            System.out.println("AppTest()");
//        }
//        public static final AppTest getInstance() {
//            return LazyHolder.INSTANCE;
//        }
//
//    static {
//        System.out.println("AppTest static code block");
//    }
//    {
//        System.out.println("AppTest code block");
//    }
//
//    public static void main(String args[]){
//        //AppTest appTest = AppTest.getInstance();
//        Class clazz = AppTest.class;
//        System.out.println("================");
//        final AppTest instance = LazyHolder.INSTANCE;
//        //new AppTest.LazyHolder();
//    }
//}
