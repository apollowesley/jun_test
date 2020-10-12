package cn.backflow.admin.common.secure;

import org.apache.commons.io.output.TeeOutputStream;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.*;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;
import java.io.*;

/**
 * Interceptor for logging incomming request.
 * Created by Nandy on 2016/5/6.
 */
public class RequestLoggingFilter extends GenericFilterBean {
    private Logger logger = LoggerFactory.getLogger(RequestLoggingFilter.class);

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

        final ByteArrayOutputStream baos = new ByteArrayOutputStream();

        chain.doFilter(request, new HttpServletResponseWrapper((HttpServletResponse) response) {

            DelegatingServletOutputStream out = new DelegatingServletOutputStream(
                    new TeeOutputStream(super.getOutputStream(), new PrintStream(baos))
            );

            @Override
            public ServletOutputStream getOutputStream() throws IOException {
                return out;
            }

            @Override
            public PrintWriter getWriter() throws IOException {
                return new PrintWriter(out);
            }
        });

        System.out.println(baos);
        // logger.info(baos.toString());
    }

    /**
     * Copy of Spring's DelegatingServletOutputStream
     */
    private class DelegatingServletOutputStream extends ServletOutputStream {

        private final OutputStream targetStream;

        /**
         * Create a DelegatingServletOutputStream for the given target stream.
         *
         * @param targetStream the target stream (never {@code null})
         */
        DelegatingServletOutputStream(OutputStream targetStream) {
            Assert.notNull(targetStream, "Target OutputStream must not be null");
            this.targetStream = targetStream;
        }

        /**
         * Return the underlying target stream (never {@code null}).
         */
        public final OutputStream getTargetStream() {
            return this.targetStream;
        }

        @Override
        public void write(int b) throws IOException {
            this.targetStream.write(b);
        }

        @Override
        public void flush() throws IOException {
            super.flush();
            this.targetStream.flush();
        }

        @Override
        public void close() throws IOException {
            super.close();
            this.targetStream.close();
        }

        @Override
        public boolean isReady() {
            return false;
        }

        @Override
        public void setWriteListener(WriteListener writeListener) {

        }
    }
}


