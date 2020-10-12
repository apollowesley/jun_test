package com.kind.samples.core.event;

import java.util.EventObject;

/**
 * Created by cary on 2016/12/18.
 */
class DoorEvent extends EventObject {

    public DoorStatus doorStatus;

    public DoorEvent(Object source, DoorStatus doorStatus) {
        super(source);
        this.doorStatus = doorStatus;
    }

    public DoorStatus getDoorStatus() {
        return doorStatus;
    }

    public void setDoorStatus(DoorStatus doorStatus) {
        this.doorStatus = doorStatus;
    }
}
