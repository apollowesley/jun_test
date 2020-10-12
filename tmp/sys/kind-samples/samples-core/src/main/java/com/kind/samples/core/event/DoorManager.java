package com.kind.samples.core.event;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

/**
 * Created by cary on 2016/12/18.
 */
public class DoorManager {

    private Set<Object> listeners;

    public void addDoorListener(DoorListener doorListener) {
        if (listeners == null) {
            listeners = new HashSet<>();
        }
        listeners.add(doorListener);
    }

    public void removeListener(DoorListener doorListener) {
        if (listeners == null) {
            return;
        }
        listeners.remove(doorListener);
    }

    /**
     * 触发开门事件
     */
    protected void doorOpenedEvent() {
        if (listeners == null)
            return;
        DoorEvent event = new DoorEvent(this, DoorStatus.OPEN);
        notifyListeners(event);
    }

    /**
     * 触发关门事件
     */
    protected void doorClosedEvent() {
        if (listeners == null)
            return;
        DoorEvent event = new DoorEvent(this, DoorStatus.CLOSE);
        notifyListeners(event);
    }

    private void notifyListeners(DoorEvent event) {
        Iterator iter = listeners.iterator();
        while (iter.hasNext()) {
            DoorListener listener = (DoorListener) iter.next();
            listener.doorEvent(event);
        }
    }
}
