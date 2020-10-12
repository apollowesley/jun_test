package com.mkfree.sample.filterPattern;

import java.util.ArrayList;
import java.util.List;

public class FilterChain {

    private List<Filter> filterList = new ArrayList<>();

    private int index = 0;

    public void addFilterChain(Filter filter) {
        this.filterList.add(filter);
    }

    public void doFilter(Request request, Response response) {
        if (index == filterList.size()) {
            return;
        }
        Filter filter = this.filterList.get(index);
        index++;
        filter.doFilter(request, response, this);
    }
}
