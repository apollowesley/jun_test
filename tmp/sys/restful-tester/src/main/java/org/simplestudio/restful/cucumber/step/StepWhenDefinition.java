package org.simplestudio.restful.cucumber.step;

import org.simplestudio.restful.httpclient.HttpInvoker;
import org.simplestudio.restful.httpclient.cache.FeatureCache;

import cucumber.api.DataTable;
import cucumber.api.java.en.When;

public class StepWhenDefinition {
    @When("^采用\\[(.+)\\]方式请求URL\\[(.+)\\],不传参数,不记录返回值$")
    public void request(String methodName, String url) throws Exception {
        HttpInvoker.invoke(methodName, url, null);
    }

    @When("^采用\\[(.+)\\]方式请求URL\\[(.+)\\],不记录返回值$")
    public void request(String methodName, String url, DataTable dataTable) throws Exception {
        HttpInvoker.invoke(methodName, url, dataTable);
    }

    @When("^采用\\[(.+)\\]方式请求URL\\[(.+)\\],不传参数,并记录返回值为\\[(.+)\\]$")
    public void requestAndRecord(String methodName, String url, String recordName)
            throws Exception {
        String responseStr = HttpInvoker.invoke(methodName, url, null);
        FeatureCache.put(recordName, responseStr);
    }

    @When("^采用\\[(.+)\\]方式请求URL\\[(.+)\\],并记录返回值为\\[(.+)\\]$")
    public void requestAndRecord(String methodName, String url, String recordName,
            DataTable dataTable) throws Exception {
        String responseStr = HttpInvoker.invoke(methodName, url, dataTable);
        FeatureCache.put(recordName, responseStr);
    }
}
