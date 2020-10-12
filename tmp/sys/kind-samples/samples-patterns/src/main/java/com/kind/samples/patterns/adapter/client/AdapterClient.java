package com.kind.samples.patterns.adapter.client;

import com.kind.samples.patterns.adapter.Target;
import com.kind.samples.patterns.adapter.impl.Adapter;

/**
 * Created by weiguo.liu on 2016/10/14.
 */
public class AdapterClient {
    public void testSendMail() {
        Target target = new Adapter();
        target.sandMail();
    }

    public static void main(String[] args) {
        new AdapterClient().testSendMail();
    }
}
