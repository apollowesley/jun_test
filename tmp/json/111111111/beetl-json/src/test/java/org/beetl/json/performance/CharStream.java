package org.beetl.json.performance;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.io.Writer;

/**
 * @author joelli
 * @see
 */
public final class CharStream extends Writer  {
    private long size;
    private long count;
    private Writer writer;

    public CharStream()  {
       
    }

    @Override
    public void write(int c) throws IOException {
     
    }

    @Override
    public void write(char[] source) throws IOException {
    
    }

    @Override
    public void write(char[] source, int offset, int length) throws IOException {
    
    }

    @Override
    public void write(String source) throws IOException {
    
    }

    @Override
    public void write(String source, int offset, int length) throws IOException {
    
    }

    @Override
    public Writer append(CharSequence source) throws IOException {
     
        return this;
    }

    @Override
    public Writer append(CharSequence source, int start, int end) throws IOException {
      
        return this;
    }

    @Override
    public Writer append(char c) throws IOException {
       
        return this;
    }

    @Override
    public void flush() throws IOException {
        
    }

    @Override
    public void close() throws IOException {
    }

   

   
}
