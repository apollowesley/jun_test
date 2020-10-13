package com.wangxin.common.utils;

import org.springframework.data.domain.Page;

public class SolrPageUtil {


    static int navigatePages = 8;// 导航页码数

    public static int[] getNavigatepageNums(Page<?> page) {
        int pages = (int) page.getTotalPages();// 总页数
        pages = pages - 1 < 1 ? pages : pages - 1;
        int[] navigatepageNums;//// 所有导航页号
        int pageNum = page.getNumber();// 当前页
        // 当总页数小于或等于导航页码数时
        if (pages <= navigatePages) {
            navigatepageNums = new int[pages];
            for (int i = 0; i < pages; i++) {
                navigatepageNums[i] = i + 1;
            }
        } else { // 当总页数大于导航页码数时
            navigatepageNums = new int[navigatePages];
            int startNum = pageNum - navigatePages / 2;
            int endNum = pageNum + navigatePages / 2;

            if (startNum < 1) {
                startNum = 1;
                // (最前navigatePages页
                for (int i = 0; i < navigatePages; i++) {
                    navigatepageNums[i] = startNum++;
                }
            } else if (endNum > pages) {
                endNum = pages;
                // 最后navigatePages页
                for (int i = navigatePages - 1; i >= 0; i--) {
                    navigatepageNums[i] = endNum--;
                }
            } else {
                // 所有中间页
                for (int i = 0; i < navigatePages; i++) {
                    navigatepageNums[i] = startNum++;
                }
            }
        }
        return navigatepageNums;
    }
}
