package fun.oook.c10;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.io.IOException;
import java.util.*;

/**
 * AutoCorrectFilter
 *
 * @author ZhouYu
 * @version 1.0.0
 * @since 2020/4/11 17:38
 */
@WebFilter(filterName = "AutoCorrectFilter", urlPatterns = {"/c10/*"})
public class AutoCorrectFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) {
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        final HttpServletRequest req = (HttpServletRequest) request;
        final AutoCorrectHttpServletRequestWrapper requestWrapper = new AutoCorrectHttpServletRequestWrapper(req);

        chain.doFilter(requestWrapper, response);
    }

    @Override
    public void destroy() {
    }

    class AutoCorrectHttpServletRequestWrapper extends HttpServletRequestWrapper {

        private HttpServletRequest req;

        public AutoCorrectHttpServletRequestWrapper(HttpServletRequest request) {
            super(request);
            this.req = request;
        }

        @Override
        public String getParameter(String name) {
            return autoCorrect(req.getParameter(name));
        }

        @Override
        public String[] getParameterValues(String name) {
            return autoCorrect(req.getParameterValues(name));
        }

        @Override
        public Map<String, String[]> getParameterMap() {
            final Map<String, String[]> parameterMap = req.getParameterMap();

            final Map<String, String[]> newMap = new Map<String, String[]>() {
                @Override
                public int size() {
                    return parameterMap.size();
                }

                @Override
                public boolean isEmpty() {
                    return parameterMap.isEmpty();
                }

                @Override
                public boolean containsKey(Object key) {
                    return parameterMap.containsKey(key);
                }

                @Override
                public boolean containsValue(Object value) {
                    return parameterMap.containsValue(value);
                }

                @Override
                public String[] get(Object key) {
                    return autoCorrect(parameterMap.get(key));
                }

                @Override
                public String[] put(String key, String[] value) {
                    // this will throw an IllegalStateException
                    // but let the user get the original exception
                    return parameterMap.put(key, value);
                }

                @Override
                public String[] remove(Object key) {
                    // this will throw an IllegalStateException
                    // but let the user get the original exception
                    return parameterMap.remove(key);
                }

                @Override
                public void putAll(Map<? extends String, ? extends String[]> m) {
                    // this will throw an IllegalStateException
                    // but let the user get the original exception
                    parameterMap.putAll(m);
                }

                @Override
                public void clear() {
                    // this will throw an IllegalStateException
                    // but let the user get the original exception
                    parameterMap.clear();
                }

                @Override
                public Set<String> keySet() {
                    return parameterMap.keySet();
                }

                @Override
                public Collection<String[]> values() {
                    return autoCorrect(parameterMap.values());
                }

                @Override
                public Set<Entry<String, String[]>> entrySet() {
                    return autoCorrect(parameterMap.entrySet());
                }
            };
            return newMap;
        }
    }

    private String autoCorrect(String value) {
        if (value == null) {
            return null;
        }

        value = value.trim();
        final int length = value.length();
        final StringBuilder temp = new StringBuilder();
        boolean lastCharWasSpace = false;
        for (int i = 0; i < length; i++) {
            final char c = value.charAt(i);
            if (c == ' ') {
                if (!lastCharWasSpace) {
                    temp.append(c);
                }
                lastCharWasSpace = true;
            } else {
                temp.append(c);
                lastCharWasSpace = true;
            }
        }
        return temp.toString();
    }

    private String[] autoCorrect(String[] values) {
        if (values == null) {
            return null;
        }

        final int length = values.length;
        for (int i = 0; i < length; i++) {
            values[i] = autoCorrect(values[i]);
        }
        return values;
    }

    private Collection<String[]> autoCorrect(Collection<String[]> valueCollection) {
        final Collection<String[]> newCollection = new ArrayList<>();
        for (String[] values : valueCollection) {
            newCollection.add(autoCorrect(values));
        }
        return newCollection;
    }

    private Set<Map.Entry<String, String[]>> autoCorrect(Set<Map.Entry<String, String[]>> entrySet) {
        Set<Map.Entry<String, String[]>> newSet = new HashSet<>();

        for (Map.Entry<String, String[]> entry : entrySet) {
            Map.Entry<String, String[]> newEntry = new Map.Entry<String, String[]>() {
                @Override
                public String getKey() {
                    return entry.getKey();
                }

                @Override
                public String[] getValue() {
                    return autoCorrect(entry.getValue());
                }

                @Override
                public String[] setValue(String[] value) {
                    return entry.setValue(value);
                }
            };
            newSet.add(newEntry);
        }
        return newSet;
    }
}
