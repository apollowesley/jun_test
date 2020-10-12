package cn.afterturn.dao.test;

import static org.junit.Assert.*;
import junit.framework.Assert;

import org.junit.Test;

import cn.afterturn.dao.resource.SQLResource;

public class SQLResourceTest {

    @Test
    public void test() {
        SQLResource.put("com.onepiece.dao.resource.SQLResourceTest.test", "test");
        Assert.assertEquals(SQLResource.get(), "test");
    }

}
