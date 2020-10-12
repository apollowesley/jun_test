package com.kind.samples.core.event;

/**
 * Created by cary on 2016/12/18.
 */
public class Door1Listener implements DoorListener {
    @Override
    public void doorEvent(DoorEvent doorEvent) {
        if (doorEvent.doorStatus == DoorStatus.CLOSE) {
            System.out.println("门1被关闭");
        } else {
            System.out.println("门1被打开");
        }
    }
}
