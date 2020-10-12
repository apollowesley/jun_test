package org.rapidoid.docs.jpascaffold;

import org.rapidoid.goodies.X;
import org.rapidoid.gui.GUI;
import org.rapidoid.jpa.JPA;
import org.rapidoid.log.Log;
import org.rapidoid.setup.On;
import org.rapidoid.u.U;

import java.util.List;

/**
 * @author caiwl
 * @date 2020/4/24 16:30
 */
public class Main extends GUI {

    @SuppressWarnings("unchecked")
    public static void main(String[] args) {

        On.page("/").mvc("Welcome!");
        X.scaffold(Book.class);

        String search = "FROM Book b WHERE b.title LIKE ?1";
        On.page("/search").mvc((String find) -> {
            Log.debug("this is debug log");
            List<Book> records = JPA.jpql(search, "%" + find + "%").all();
            return U.list(h2("Searching for: ", find), grid(records));
        });

    }

}
