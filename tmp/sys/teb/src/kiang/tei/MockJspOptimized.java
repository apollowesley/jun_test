package kiang.tei;

import kiang.teb.TebEngine;
import kiang.teb.TebModel;
import kiang.toolkit.code.Coder;
import kiang.wetter.io.CharsFloat;
import kiang.wetter.io.CharsInt;
import kiang.wetter.io.Grabber;
import kiang.wetter.io.GrabberRapid;

import java.io.IOException;
import java.io.OutputStream;
import java.io.Writer;
import java.lang.ref.SoftReference;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

/**
 * Copyright (c) 2014 by kiang
 *
 * @author kiang
 * @version 0.1-pre
 */
public final class MockJspOptimized implements TebEngine {
    private static ThreadLocal<SoftReference<byte[]>> encodeLocal = new ThreadLocal<SoftReference<byte[]>>();
    private static ThreadLocal<SoftReference<char[]>> inlineLocal = new ThreadLocal<SoftReference<char[]>>();
    private Grabber grabber;
    private Coder coder;
    private byte[] bs0;
    private byte[] bs1;
    private byte[] bs2;
    private byte[] bs3;
    private byte[] bs4;
    private byte[] bs5;
    private byte[] bs6;
    private byte[] bs7;
    private byte[] bs8;
    private byte[] bs9;
    private byte[] bs10;
    private byte[] bs11;
    private byte[] bs12;
    private byte[] bs13;
    private char[] cs0;
    private char[] cs1;
    private char[] cs2;
    private char[] cs3;
    private char[] cs4;
    private char[] cs5;
    private char[] cs6;
    private char[] cs7;
    private char[] cs8;
    private char[] cs9;
    private char[] cs10;
    private char[] cs11;
    private char[] cs12;
    private char[] cs13;

    @Override
    public TebEngine init(Properties properties) throws Exception {
        String target = properties.getProperty("target", "UTF-8");
        if(Boolean.parseBoolean(properties.getProperty("binary", "true"))) {
            this.grabber = new GrabberRapid();
            this.coder = Coder.get(target);
            bs0 = "<html>\r\n<head>\r\n    <title>NatMock!!!</title>\r\n    <meta http-equiv=\"Content-Type\" content=\"text/html; charset=".getBytes(target);
            bs1 = "\"/>\r\n    <style type=\"text/css\">\r\n        body { font-size: 10pt; color: #333333; }\r\n        thead { font-weight: bold; background-color: #C8FBAF; }\r\n        td { font-size: 10pt; text-align: center; }\r\n        .odd { background-color: #F3DEFB; }\r\n        .even { background-color: #EFFFF8; }\r\n    </style>\r\n</head>\r\n<body>\r\n    <h1>Template Engine Benchmark - NatMock!!!</h1>\r\n    <table>\r\n        <thead>\r\n            <tr>\r\n                <th>\u5E8F\u53F7</th>\r\n                <th>\u7F16\u7801</th>\r\n                <th>\u540D\u79F0</th>r\n                <th>\u65E5\u671F</th>\r\n                <th>\u503C</th>\r\n            </tr>\r\n        </thead>\r\n        <tbody>\r\n".getBytes(target);
            bs2 = "            <tr class=\"".getBytes(target);
            bs3 = "\">\r\n                <td>".getBytes(target);
            bs4 = "</td>\r\n                <td>".getBytes(target);
            bs5 = "</td>\r\n                <td>".getBytes(target);
            bs6 = "</td>\r\n                <td>".getBytes(target);
            bs7 = "</td>\r\n".getBytes(target);
            bs8 = "                <td style=\"color: red;\">".getBytes(target);
            bs9 = "</td>\r\n".getBytes(target);
            bs10 = "                <td style=\"color: blue;\">".getBytes(target);
            bs11 = "</td>\r\n".getBytes(target);
            bs12 = "            </tr>\r\n".getBytes(target);
            bs13 = "        </tbody>\r\n    </table>\r\n</body>\r\n</html>".getBytes(target);
        } else {
            cs0 = "<html>\r\n<head>\r\n    <title>NatMock!!!</title>\r\n    <meta http-equiv=\"Content-Type\" content=\"text/html; charset=".toCharArray();
            cs1 = "\"/>\r\n    <style type=\"text/css\">\r\n        body { font-size: 10pt; color: #333333; }\r\n        thead { font-weight: bold; background-color: #C8FBAF; }\r\n        td { font-size: 10pt; text-align: center; }\r\n        .odd { background-color: #F3DEFB; }\r\n        .even { background-color: #EFFFF8; }\r\n    </style>\r\n</head>\r\n<body>\r\n    <h1>Template Engine Benchmark - NatMock!!!</h1>\r\n    <table>\r\n        <thead>\r\n            <tr>\r\n                <th>\u5E8F\u53F7</th>\r\n                <th>\u7F16\u7801</th>\r\n                <th>\u540D\u79F0</th>r\n                <th>\u65E5\u671F</th>\r\n                <th>\u503C</th>\r\n            </tr>\r\n        </thead>\r\n        <tbody>\r\n".toCharArray();
            cs2 = "            <tr class=\"".toCharArray();
            cs3 = "\">\r\n                <td>".toCharArray();
            cs4 = "</td>\r\n                <td>".toCharArray();
            cs5 = "</td>\r\n                <td>".toCharArray();
            cs6 = "</td>\r\n                <td>".toCharArray();
            cs7 = "</td>\r\n".toCharArray();
            cs8 = "                <td style=\"color: red;\">".toCharArray();
            cs9 = "</td>\r\n".toCharArray();
            cs10 = "                <td style=\"color: blue;\">".toCharArray();
            cs11 = "</td>\r\n".toCharArray();
            cs12 = "            </tr>\r\n".toCharArray();
            cs13 = "        </tbody>\r\n    </table>\r\n</body>\r\n</html>".toCharArray();
        }
        return this;
    }

    @Override
    public void test(Map arguments, Writer writer) throws Exception {
        final Grabber grabber = this.grabber;
        writer.write(cs0);
        writer.write(grabber.grab((String) arguments.get("target")));
        writer.write(cs1);
        TebModel model;
        List<TebModel> models = (List<TebModel>)arguments.get("models");
        for(int i=0, n = models.size(); i<n; i++) {
            model = models.get(i);
            writer.write(cs2);
            writer.write(grabber.grab(i % 2 == 0 ? "odd" : "even"));
            writer.write(cs3);
            this.print(writer, i);
            writer.write(cs4);
            this.print(writer, model.getCode());
            writer.write(cs5);
            writer.write(grabber.grab(model.getName()));
            writer.write(cs6);
            writer.write(grabber.grab(String.valueOf(model.getDate())));
            writer.write(cs7);
            if(model.getValue() > 105.5) {
                writer.write(cs8);
                this.print(writer, model.getValue());
                writer.write(cs9);
            } else {
                writer.write(cs10);
                this.print(writer, model.getValue());
                writer.write(cs11);
            }
            writer.write(cs12);
        }
        writer.write(cs13);
    }

    @Override
    public void test(Map arguments, OutputStream output) throws Exception {
        output.write(bs0);
        this.print(output, (String) arguments.get("target"));
        output.write(bs1);
        TebModel model;
        List<TebModel> models = (List<TebModel>)arguments.get("models");
        for(int i=0, n = models.size(); i<n; i++) {
            model = models.get(i);
            output.write(bs2);
            this.print(output, i % 2 == 0 ? "odd" : "even");
            output.write(bs3);
            this.print(output, i);
            output.write(bs4);
            this.print(output, model.getCode());
            output.write(bs5);
            this.print(output, model.getName());
            output.write(bs6);
            this.print(output, String.valueOf(model.getDate()));
            output.write(bs7);
            if(model.getValue() > 105.5) {
                output.write(bs8);
                this.print(output, model.getValue());
                output.write(bs9);
            } else {
                output.write(bs10);
                this.print(output, model.getValue());
                output.write(bs11);
            }
            output.write(bs12);
        }
        output.write(bs13);
    }

    @Override
    public boolean isBinarySupport() {
        return true;
    }

    @Override
    public void shut() throws Exception {
    }

    public final void print(final OutputStream output, final String string) throws IOException {
        int length = string.length();
        final byte[] bytes = this.getEncodeLocal(length);
        length = this.coder.encode(this.grabber.grab(string), 0, length, bytes);
        output.write(bytes, 0, length);
    }

    private void print(OutputStream output, int value) throws IOException {
        final byte[] array = this.getEncodeLocal(64);
        final int size = CharsInt.encode(value, array);
        output.write(array, 0, size);
    }

    private void print(Writer writer, int value) throws IOException {
        final char[] array = this.getInlineLocal(64);
        final int size = CharsInt.encode(value, array);
        writer.write(array, 0, size);
    }

    private void print(OutputStream output, double value) throws IOException {
        final byte[] array = this.getEncodeLocal(128);
        final int size = CharsFloat.getBytes(value, array);
        output.write(array, 0, size);
    }

    private void print(Writer writer, double value) throws IOException {
        final char[] array = this.getInlineLocal(128);
        final int size = CharsFloat.getChars(value, array);
        writer.write(array, 0, size);
    }

    private byte[] getEncodeLocal(int length) {
        length = this.coder.encode(length);
        final SoftReference<byte[]> refer = encodeLocal.get();
        byte[] array = refer == null ? null : refer.get();
        if(array == null || array.length < length) {
            encodeLocal.set(new SoftReference<byte[]>(array = new byte[length]));
        }
        return array;
    }

    private char[] getInlineLocal(int length) {
        final SoftReference<char[]> refer = inlineLocal.get();
        char[] array = refer == null ? null : refer.get();
        if (array == null || array.length < length) {
            inlineLocal.set(new SoftReference<char[]>(array = new char[Math.max(length, 1024)]));
        }
        return array;
    }

    public static void main(String args[]) throws Exception {
        String source="UTF-8", target = "UTF-8";
        OutputStream output = System.out;
        Map data = new HashMap();
        data.put("target", target);
        data.put("models", TebModel.dummyModels(20));
        Properties properties = new Properties();
        properties.setProperty("source", source);
        properties.setProperty("target", target);
        properties.setProperty("binary", String.valueOf(true));
        TebEngine engine = new MockJspOptimized().init(properties);
        engine.test(data, output);
        output.flush();
        engine.shut();
    }
}
