package net.cassite.search;

import java.util.ArrayList;
import java.util.List;

/**
 * the searching request.
 */
public class Request {

        private final List<String> keywords = new ArrayList<String>();
        private String site;
        private int page;

        /**
         * the keyword list. The list can be modified.
         *
         * @return the keyword list.
         */
        public List<String> getKeywords() {
                return keywords;
        }

        /**
         * get site
         *
         * @return the site (can be null)
         */
        public String getSite() {
                return site;
        }

        /**
         * only search for the site.
         *
         * @param site the site (can be null)
         */
        public void setSite(String site) {
                if (site == null) throw new NullPointerException();
                this.site = site;
        }

        /**
         * get page
         *
         * @return page
         */
        public int getPage() {
                return page;
        }

        /**
         * set required page
         *
         * @param page a number greater or equal to 0
         */
        public void setPage(int page) {
                if (page < 0) throw new IllegalArgumentException("page >= 0");
                this.page = page;
        }

        @Override
        public String toString() {
                return "Request{" +
                        "keywords=" + keywords +
                        ", site='" + site + '\'' +
                        ", page=" + page +
                        '}';
        }
}
