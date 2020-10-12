package org.rapidoid.docs.beanjson;

import org.rapidoid.annotation.Controller;
import org.rapidoid.annotation.GET;

/**
 * @author caiwl
 * @date 2020/4/24 13:57
 */
@Controller
public class EasyBeans {
    @GET
    public Book echo(Book book) {
        return book;
    }
}
