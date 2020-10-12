package net.cassite.search;

/**
 * result url.
 */
public class Url {
        private String link;
        private String cite;

        /**
         * get link string (usually retrieved from `href` attr)
         *
         * @return the link string (not null)
         */
        public String getLink() {
                return link;
        }

        /**
         * set link string.
         *
         * @param link a string (not null)
         */
        public void setLink(String link) {
                if (link == null) throw new NullPointerException();
                this.link = link;
        }

        /**
         * get cite string (which is usually the string at the bottom of each searching result)
         *
         * @return a string (not null)
         */
        public String getCite() {
                return cite;
        }

        /**
         * set cite string.
         *
         * @param cite a string (not null)
         */
        public void setCite(String cite) {
                if (cite == null) throw new NullPointerException();
                this.cite = cite;
        }

        @Override
        public String toString() {
                return "Url{" +
                        "link='" + link + '\'' +
                        ", cite='" + cite + '\'' +
                        '}';
        }
}
