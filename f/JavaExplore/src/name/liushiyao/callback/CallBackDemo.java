package name.liushiyao.callback;

/**
 * Created by 北岭山下 on 2017/3/22.
 */

/**
 * 回调机制的探究
 *      【目的】探究Java中回调机制的实现原理
 *
 *      回调机制：
 *              【组成】事件源：发生事件的实体（对象），如Button，TextView等
 *                            事件：实体（对象）发生的动作，如按下，数值的改变
 *                            监听器：监听事件的并根据事件进行相应的处理
 *              【运行过程】
 *                              1. 事件源添加监听器
 *                              2. 事件源发生变化，产生某个事件
 *                              3. 监听器检测到某个时间的发生，通知添加了该监听器的所有对象
 *                              4. 该对象根据时间执行对应的操作
 *
 *
 */

public class CallBackDemo {

        public static void main ( String[] args ) {

                //2017年7月17日15:56:17
                MyEventSource myEventSource = new MyEventSource ();
                myEventSource.setListener ( new MyListener ( ) {
                        @Override
                        public void doEvent ( MyEvent myEvent ) {
                                if(myEvent.isMove ()){
                                        System.out.println ("被移动了" );
                                }else if(myEvent.isTouch ()){
                                        System.out.println ("被触摸了" );
                                }
                        }
                } );
//                myEventSource.setMove ( true );
                myEventSource.setTouch ( true );

        }

}
