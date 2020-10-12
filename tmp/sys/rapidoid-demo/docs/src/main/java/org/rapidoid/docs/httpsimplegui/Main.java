package org.rapidoid.docs.httpsimplegui;

import org.rapidoid.setup.On;

/**
 * @author caiwl
 * @date 2020/4/24 16:23
 */
public class Main {
    public static void main(String[] args) {
        /* On [GET /hi] or [POST /hi] return a "Hello World" web page */

        On.page("/hi").mvc("Hello <b>world</b>!");
    }
}
