package name.liushiyao.callback;

/**
 * Created by 北岭山下 on 2017/3/22.
 */

/**
 * 事件
 */

public interface MyEvent  {

        void setListener(MyListener myListener);

        boolean  isMove();
        boolean  isTouch();




}
