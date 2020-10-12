package com.cnbbx;

/**
 * @author jinge
 * @email admin(a)cnbbx.com
 */

public class GeneratorMain {
    public static void main(String[] args) throws Exception {
        Generator g = new Generator();
        g.clean();
//        g.generateTable("cnbbx_admin");
//        g.generateTable("cnbbx_books");
//        g.generateTable("cnbbx_userinfo");
//        g.generateTable("cnbbx_order");
        g.generateAllTable();
    }
}
