package com.kind.samples.core.event;

/**
 * Created by cary on 2016/12/18.
 */
public class DoorEventMain {
    public static void main(String[] args) {
        DoorManager manager = new DoorManager();
        manager.addDoorListener(new Door1Listener());// 给门1增加监听器
        manager.addDoorListener(new Door2Listener());// 给门2增加监听器
        // 开门
        manager.doorOpenedEvent();
        System.out.println("我已经进来了");
        // 关门
        manager.doorClosedEvent();
    }
}
