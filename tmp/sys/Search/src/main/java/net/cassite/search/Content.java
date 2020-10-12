package net.cassite.search;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * the result content.
 */
public class Content {
        private String content;
        private final Map<Integer, String> keywords = new LinkedHashMap<Integer, String>();

        /**
         * the content string
         *
         * @return a string (not null)
         */
        public String getContent() {
                return content;
        }

        /**
         * the keywords map (index =&gt; keyword)
         *
         * @return a map of keywords (not null)
         */
        public Map<Integer, String> getKeywords() {
                return keywords;
        }

        /**
         * set content string
         *
         * @param content content (not null)
         */
        public void setContent(String content) {
                if (content == null) throw new NullPointerException();
                this.content = content;
        }

        @Override
        public String toString() {
                return "Content{" +
                        "content='" + content + '\'' +
                        ", keywords=" + keywords +
                        '}';
        }
}
