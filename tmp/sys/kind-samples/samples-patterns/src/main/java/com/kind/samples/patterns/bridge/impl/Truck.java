package com.kind.samples.patterns.bridge.impl;

import bridge.interfaces.Car;
import bridge.interfaces.Engine;
import utility.MyUtility;

public class Truck implements Car {
    private Engine engine;

    public Truck(Engine engine) {
        this.engine = engine;
    }

    public void startEngine() {
        MyUtility.myPrint("Truck with engine " + this.engine.getEngineName() + " started.");
    }

}
