/**
 * Project Name:samples-patterns
 * Package Name:com.kind.samples.patterns.adapter.impl
 * Created on:2016年9月22日下午3:36:38
 * Copyright (c) 2016, http://www.mcake.com All Rights Reserved.
 */

package com.kind.samples.patterns.adapter.impl;

import com.kind.samples.patterns.adapter.Target;

/**
 * Function: TODO ADD FUNCTION. <br/>
 *
 * @author weiguo.liu
 * @see
 * @since JDK 1.7
 */
public class Adapter implements Target {
    private AdapterOne adapterOne;


    public Adapter() {
        this.setAdapterOne(new AdapterOne());
    }

    @Override
    public void sandMail() {
        if (this.adapterOne == null) {
            return;
        }
        System.out.println("Sending email form Adapter.");
        this.adapterOne.sendSMS();

    }

    public void setAdapterOne(AdapterOne adapterOne) {
        this.adapterOne = adapterOne;
    }


}

