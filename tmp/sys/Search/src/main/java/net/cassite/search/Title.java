package net.cassite.search;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * the result title
 */
public class Title {
        private String title;
        private final Map<Integer, String> keyWord = new LinkedHashMap<Integer, String>();

        /**
         * get title string.
         *
         * @return a string (not null)
         */
        public String getTitle() {
                return title;
        }

        /**
         * get a map of keywords. (index =&gt; keyword)
         *
         * @return a keyword map (not null)
         */
        public Map<Integer, String> getKeyWord() {
                return keyWord;
        }

        /**
         * set title string.
         *
         * @param title a string (not null)
         */
        public void setTitle(String title) {
                if (title == null) throw new NullPointerException();
                this.title = title;
        }

        @Override
        public String toString() {
                return "Title{" +
                        "title='" + title + '\'' +
                        ", keyWord=" + keyWord +
                        '}';
        }
}
