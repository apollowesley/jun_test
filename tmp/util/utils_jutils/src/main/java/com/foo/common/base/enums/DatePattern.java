package com.foo.common.base.enums;

public enum DatePattern {

    year_to_second("yyyy-MM-dd HH:mm:ss"),
    year_to_day("yyyy-MM-dd HH:mm:ss"),
    hour_to_second("HH:mm:ss"),
    year_to_hour("yyyy-MM-dd HH"),
    year_to_minute("yyyy-MM-dd HH:mm");

    private String pattern;

    DatePattern(String pattern) {
        this.pattern = pattern;
    }

    @Override
    public String toString() {
        return this.pattern;
    }
}
