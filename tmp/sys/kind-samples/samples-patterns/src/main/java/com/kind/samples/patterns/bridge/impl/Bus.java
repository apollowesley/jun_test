package com.kind.samples.patterns.bridge.impl;

import com.kind.samples.patterns.bridge.interfaces.Car;
import com.kind.samples.patterns.bridge.interfaces.Engine;

public class Bus implements Car {
    private Engine engine;

    public Bus(Engine engine) {
        this.engine = engine;
    }

    public void startEngine() {
        System.out.println("Bus with engine " + this.engine.getEngineName() + " started.");
    }

}
