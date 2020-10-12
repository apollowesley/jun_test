package net.cassite.search;

import org.jsoup.nodes.Document;

/**
 * provides {@link org.jsoup.nodes.Document} object as argument of {@link Engine#extract(Document)}
 */
public interface DocumentProvider {
        /**
         * arguments of the {@link #provideWith(Arguments)} method.
         */
        class Arguments {
                /**
                 * connection url
                 */
                public String url;
                /**
                 * connecting method
                 */
                public Method method;
                /**
                 * UA
                 */
                public String UA;
        }

        /**
         * provide a {@link Document} object.
         *
         * @param arguments the arguments (not null)
         * @return {@link Document} object (not null)
         * @throws Exception exception
         */
        Document provideWith(Arguments arguments) throws Exception;
}
