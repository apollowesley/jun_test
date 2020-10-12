package com.kind.samples.core.event;

import java.util.EventListener;

/**
 * Created by cary on 2016/12/18.
 */
public interface DoorListener extends EventListener {
    public void doorEvent(DoorEvent doorEvent);
}
