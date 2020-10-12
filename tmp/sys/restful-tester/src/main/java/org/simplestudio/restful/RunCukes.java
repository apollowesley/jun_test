package org.simplestudio.restful;

import org.simplestudio.restful.cucumber.CucumberHelper;
import org.simplestudio.restful.httpclient.HttpInvoker;

public class RunCukes {

    public static void main(String[] args) throws Exception {
        //初始http调度器
        try {
            HttpInvoker.initInvoker();

            CucumberHelper.run();
        } finally {
            HttpInvoker.release();
        }
    }

}
