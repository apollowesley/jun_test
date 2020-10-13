package name.liushiyao.callback;

/**
 * Created by 北岭山下 on 2017/3/22.
 */

/**
 * 事件源
 */
public class MyEventSource implements MyEvent {

        private MyListener myListener;
        private boolean isMove;
        private boolean isTouch;


        @Override
        public void setListener ( MyListener myListener ) {
                this.myListener = myListener;
        }

        @Override
        public boolean isMove ( ) {
                return isMove;
        }

        @Override
        public boolean isTouch ( ) {
                return isTouch;
        }

        public void setMove ( boolean move ) {
                isMove = move;
                myListener.doEvent (this);
        }

        public void setTouch ( boolean touch ) {
                isTouch = touch;
                myListener.doEvent (this);
        }
}
