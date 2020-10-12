package net.cassite.search;

/**
 * the searching result.
 */
public class Result {
        private Title title;
        private Content content;
        private Url url;

        /**
         * get the Title info.
         *
         * @return a {@link Title} object (not null)
         */
        public Title getTitle() {
                return title;
        }

        /**
         * set the Title info.
         *
         * @param title {@link Title} object (not null)
         */
        public void setTitle(Title title) {
                if (title == null) throw new NullPointerException();
                this.title = title;
        }

        /**
         * get the content info.
         *
         * @return a {@link Content} object (not null)
         */
        public Content getContent() {
                return content;
        }

        /**
         * set the content info.
         *
         * @param content {@link Content} object (not null)
         */
        public void setContent(Content content) {
                if (content == null) throw new NullPointerException();
                this.content = content;
        }

        /**
         * get the url info.
         *
         * @return a {@link Url} object (not null)
         */
        public Url getUrl() {
                return url;
        }

        /**
         * set the url info.
         *
         * @param url a {@link Url} object (not null)
         */
        public void setUrl(Url url) {
                if (url == null) throw new NullPointerException();
                this.url = url;
        }

        @Override
        public String toString() {
                return "Result{" +
                        "title=" + title +
                        ", content=" + content +
                        ", url=" + url +
                        '}';
        }
}
