package cn.org.rapid_framework.generator;

import java.util.HashSet;
import java.util.Set;

public final class ColumnPrefix {

    public static final Set<String> prefix = new HashSet<String>(){{
        add("c_");
        add("d_");
        add("l_");
    }};
}
