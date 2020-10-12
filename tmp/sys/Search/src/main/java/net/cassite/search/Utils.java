package net.cassite.search;

import java.util.HashMap;
import java.util.Map;

/**
 * the utils for search engines.
 */
public class Utils {
        private Utils() {
        }

        private static final Map<String, String> htmlEscape = new HashMap<String, String>() {{
                put("&nbsp;", " ");
                put("&gt;", ">");
                put("&lt;", "<");
                put("&quot;", "\"");
                put("&amp;", "&");
        }};

        /**
         * replace html escape words into plain strings.
         *
         * @param input input html
         * @return the result string.
         */
        public static String replaceHtmlEscape(String input) {
                for (Map.Entry<String, String> entry : htmlEscape.entrySet()) {
                        input = input.replace(entry.getKey(), entry.getValue());
                }
                return input;
        }
}
