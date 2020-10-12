package com.mkfree.sample.filterPattern;

public interface Filter {

    void doFilter(Request request, Response response, FilterChain filterChain);

}
