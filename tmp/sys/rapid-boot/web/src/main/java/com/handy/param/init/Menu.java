package com.handy.param.init;

import lombok.Data;

import java.util.List;

/**
 * @author handy
 * @Description: {菜单}
 * @date 2019/8/21 15:19
 */
@Data
public class Menu {
    private Long id;
    private String title;
    private String icon;
    private List<Child> child;
}