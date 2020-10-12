package com.hong.generate.common;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @description:
 * @author: YJH
 * @time: 2020/4/5 10:47
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PageResult <T>{

     private long total;
     private List<T> rows;
}
