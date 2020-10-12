package com.leo.vhr.utils;

import com.leo.vhr.model.Hr;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 * @description:
 * @author: Leo
 * @createDate: 2020/2/20
 * @version: 1.0
 */
public class HrUtils
{
    public static Hr getCurrentHr(){
        return ((Hr) SecurityContextHolder.getContext().getAuthentication().getPrincipal());
    }
}
