package com.jueyue.onepiece.test.request;

import com.jueyue.onepiece.test.spring.SpringTxTestCase;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.*;

public class JsonRequestTest extends SpringTxTestCase {

    @Autowired
    private JsonRequest jsonRequest;

    @Test
    public void test1() throws Exception {
        Map<String, String> map = new HashMap<String, String>();
        map.put("a", "1");
        map.put("b", "1");
        map.put("c", "1");

        String result = jsonRequest.test(map);
        System.out.println(result);

    }

}