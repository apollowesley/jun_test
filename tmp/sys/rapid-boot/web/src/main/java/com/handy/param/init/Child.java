package com.handy.param.init;

import lombok.Data;

import java.util.List;

/**
 * @author handy
 * @Description: {}
 * @date 2019/8/21 15:19
 */
@Data
public class Child {
    private Long id;
    private String title;
    private String href;
    private String icon;
    private String target;
    private List<Child> child;
}