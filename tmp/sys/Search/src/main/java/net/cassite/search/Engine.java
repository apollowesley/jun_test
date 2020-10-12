package net.cassite.search;

import org.jsoup.nodes.Document;

import java.util.List;

/**
 * the search engine
 */
public interface Engine {
        /**
         * build connection url.
         *
         * @param request the request object (not null)
         * @return the connection url string (not null)
         * @throws Exception exception
         */
        String buildURL(Request request) throws Exception;

        /**
         * connection method (get or post)
         *
         * @return the method (not null)
         */
        Method method();

        /**
         * extract results from document
         *
         * @param document document retrieved from Jsoup (not null)
         * @return the extract result list (not null)
         * @throws Exception exception
         */
        List<Result> extract(Document document) throws Exception;

        /**
         * the UA
         *
         * @return UA string
         */
        String UA();
}
