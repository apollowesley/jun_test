package 行为型.观察者;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Tianjun
 * Date: 14-9-17
 * Time: 下午2:41
 * To change this template use File | Settings | File Templates.
 */
public class Client {
    public static void main(String []args){
        Subject s = new Subject(new MySubject("my1"));
        s.addListen(new ObserverImpl("代理1"));
        s.add();
        s.update();

        s.setSubject(new MySubject("my2"));
        s.update();
    }
}
interface Observer {
    public void update();
    public void add();
}
class ObserverImpl implements Observer {
    private String name;

    ObserverImpl(String name) {
        this.name = name;
    }

    @Override
    public void update() {
            System.out.println(name+"检测到修改");
    }
    @Override
    public void add() {
        System.out.println(name+"检测到新增");
    }
}

interface ISubject{
    void add();
    void update();
}
class MySubject implements ISubject {
    private String name;

    MySubject(String name) {
        this.name = name;
    }

    @Override
    public void add() {
        System.out.println(name+"add");
    }
    public void update() {
        System.out.println(name+"update");
    }
}

class Subject implements ISubject{
    private ISubject subject;
    private List<Observer> observerList = new ArrayList<Observer>();
    Subject(ISubject subject) {
        this.subject = subject;
    }

    void setSubject(ISubject subject) {
        this.subject = subject;
    }

    public Subject addListen(Observer observer){
        observerList.add(observer);
        return this;
    }
    public Subject deleteListen(Observer observer){
        observerList.remove(observer);
        return this;
    }
    protected void notifyUpdate(){
        for(Observer o: observerList){
            o.update();
        }
    }
    protected void notifyAdd(){
        for(Observer o: observerList){
            o.add();
        }
    }

    @Override
    public void add() {
        this.subject.add();
        this.notifyAdd();
    }

    @Override
    public void update() {
        this.subject.update();
        this.notifyUpdate();
    }
}

