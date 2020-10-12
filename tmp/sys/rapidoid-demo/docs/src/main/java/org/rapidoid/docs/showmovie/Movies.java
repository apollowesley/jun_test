package org.rapidoid.docs.showmovie;

import org.rapidoid.annotation.Controller;
import org.rapidoid.annotation.Page;
import org.rapidoid.gui.GUI;
import org.rapidoid.gui.input.Form;

/**
 * @author caiwl
 * @date 2020/4/24 13:53
 */
@Controller
public class Movies {
    @Page("/")
    public Object movie() {
        Movie movie = new Movie();
        movie.title = "Chappie";
        movie.year = 2015;

        Form form = GUI.show(movie).buttons(GUI.btn("OK"));
        return GUI.page(form).brand("Movie details");
    }
}
