package com.jfast.web.common.security;

import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Unit test for simple App.
 */
public class AppTest 
{
    /**
     * Rigorous Test :-)
     */
    @Test
    public void shouldAnswerWithTrue()
    {
        List<SimpleGrantedAuthority> list = new ArrayList<>();
        List<String> list1 = new ArrayList<>();
        list1.add("insert");
        list1.add("update");
        list1.add("delete");
        list1.add("select");
        list1.add("select:insert");
        list1.add("select:update");



        for (int i = 0; i < 6; i++) {
            list.add(new SimpleGrantedAuthority(list1.get(i)));
        }
        boolean flag = list.stream().map(SimpleGrantedAuthority :: getAuthority)
                .anyMatch(value -> value.equals("ssf"));
        System.err.println(flag);
        assertTrue( true );
    }
}
