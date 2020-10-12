package cn.backflow.lib.util;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.util.*;

public class StringUtil {
    private static final Logger LOG = LoggerFactory.getLogger(StringUtil.class);
    private static final String DEFAULT_CHARSET = "utf-8";
    // 用于生成短链接的62进制字符数组
    private static final char[] DIGITS = {
            '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K',
            'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z', 'a', 'b', 'c', 'd', 'e', 'f',
            'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z',
    };
    private static final char[] HEX = Arrays.copyOfRange(DIGITS, 0, 16);

    /**
     * 测试主函数
     */
    static public void main(String[] args) throws Exception {
        int a = 0;
        List<String> list = new ArrayList<>();
        for (int i = 0; i < 12; i++) {
            list.add(reverse_shorten() + " : " + ++a);

        }
        System.out.println(list);
        Collections.sort(list);
        System.out.println(list);
    }

    /**
     * 转换当前系统时间为短链接
     */
    public static synchronized String shorten() {
        try {
            Thread.sleep(1L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return shorten(System.currentTimeMillis());
    }

    /**
     * 转换当前系统时间为短链接 (从大到小)
     */
    public static synchronized String reverse_shorten() {
        try {
            Thread.sleep(1L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return shorten(9999999999999L - System.currentTimeMillis());
    }

    /**
     * 自增序列方式生成短链接
     *
     * @param seq 自增序列值
     * @return 生成后的短链接
     */
    public static String shorten(long seq) {
        StringBuilder sb = new StringBuilder();
        int len = DIGITS.length;
        do {
            int remainder = (int) (seq % len);
            sb.append(DIGITS[remainder]);
            seq = seq / len;
        } while (seq != 0);
        return sb.reverse().toString();
    }

    public static String shorten(String longUrl) {
        String shortUrl = shorten(longUrl, 5);
        if (shortUrl.isEmpty()) {
            shortUrl = shorten(longUrl, 6);
        }
        return shortUrl;
    }

    /**
     * 基于MD5方式生成短链接
     *
     * @param longUrl 长链接
     * @param len     短链接长度
     * @return 生成的短链接
     */
    public static String shorten(String longUrl, int len) {
        if (len < 0 || len > 6) {
            throw new IllegalArgumentException("the length of url must be between 0 and 6");
        }
        String hex = DigestUtils.md5Hex(longUrl);
        // 6 digit binary can indicate 62 letter & number from 0-9a-zA-Z
        int binaryLen = len * 6;
        long binaryLenFixer = Long.valueOf(StringUtils.repeat("1", binaryLen), 0x2);
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 4; i++) {
            String sub = StringUtils.substring(hex, i * 8, (i + 1) * 8);
            sub = Long.toBinaryString(Long.valueOf(sub, 16) & binaryLenFixer);
            sub = StringUtils.leftPad(sub, binaryLen, "0");
            for (int j = 0; j < len; j++) {
                String s = StringUtils.substring(sub, j * 6, (j + 1) * 6);
                int charIndex = Integer.valueOf(s, 0x2) & 0x0000003d;
                sb.append(DIGITS[charIndex]);
            }
        }
        return sb.toString();
    }


    /**
     * 获取对象字符串值
     */
    public static String value(Object o) {
        return (o == null || isBlank(o.toString())) ? "" : o.toString();
    }

    /**
     * 判断一个字符串是否为null或是空字符串<p>
     *
     * @param cs The string for checking
     * @return true if the string is neither null nor empty string
     */
    public static boolean isEmpty(final CharSequence cs) {
        return cs == null || cs.length() == 0;
    }

    /**
     * <p>Checks if a CharSequence is whitespace, empty ("") or null.</p>
     *
     * <pre>
     * StringUtils.isBlank(null)      = true
     * StringUtils.isBlank("")        = true
     * StringUtils.isBlank(" ")       = true
     * StringUtils.isBlank("bob")     = false
     * StringUtils.isBlank("  bob  ") = false
     * </pre>
     *
     * @param cs the CharSequence to check, may be null
     * @return {@code true} if the CharSequence is null, empty or whitespace
     * @since 3.0 Changed signature from isBlank(String) to isBlank(CharSequence)
     */
    public static boolean isBlank(final CharSequence cs) {
        int strLen;
        if (cs == null || (strLen = cs.length()) == 0) {
            return true;
        }
        for (int i = 0; i < strLen; i++) {
            if (!Character.isWhitespace(cs.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    public static boolean isNotBlank(final CharSequence cs) {
        return !isBlank(cs);
    }

    /**
     * byte数组的部分字节转化为16进制的String
     *
     * @param bytes  待转换的byte数组
     * @param offset 开始位置
     * @param len    字节数
     * @return 16进制的String
     */
    public static String toHex(byte[] bytes, int offset, int len) {
        char buf[] = new char[len * 2];
        int k = 0;
        for (int i = offset; i < len; i++) {
            buf[k++] = HEX[((int) bytes[i] & 0xff) >> 4];
            buf[k++] = HEX[((int) bytes[i] & 0xff) % 16];
        }
        return new String(buf);
    }

    /**
     * 将字符串按base64方式编码
     *
     * @param s 源字符串
     * @return 编码后的字符串
     */
    public static String base64Encode(String s) {
        if (s == null) return null;
        try {
            return (new sun.misc.BASE64Encoder()).encode(s.getBytes(DEFAULT_CHARSET));
        } catch (UnsupportedEncodingException e) {
            return null;
        }
    }

    /**
     * 将 BASE64 编码的字符串 s 进行解码
     *
     * @param s base64编码后的字符串
     * @return 解码后的字符串
     */
    public static String base64Decode(String s) {
        if (s == null)
            return null;
        try {
            return new String(base64DecodeBytes(s), DEFAULT_CHARSET);
        } catch (Exception e) {
            LOG.warn("base64Decode failed", e);
            return null;
        }
    }

    public static byte[] base64DecodeBytes(String s) {
        if (s == null)
            return null;
        sun.misc.BASE64Decoder decoder = new sun.misc.BASE64Decoder();
        try {
            return decoder.decodeBuffer(s);
        } catch (Exception e) {
            LOG.warn("base64Decode failed", e);
            return null;
        }
    }

    /**
     * 计算字符串的md5的摘要信息
     *
     * @param s 源字符串
     * @return 32字节的16进制的字符串
     */
    public static String md5(String s) {
        return md5(s, null);
    }

    /**
     * 计算字符串的md5的摘要信息
     *
     * @param data 源字符串
     * @param key  salt字符串，
     * @return 32字节的16进制的字符串
     */
    public static String md5(String data, String key) {
        return digest(data, key, "MD5");
    }

    /**
     * 计算字符串的SHA1的摘要信息
     *
     * @param data 源字符串
     * @param key  salt字符串，
     * @return 32字节的16进制的字符串
     */
    public static String sha1(String data, String key) {
        return digest(data, key, "SHA1");
    }

    /**
     * 计算字符串的摘要信息
     *
     * @param data      源字符串
     * @param key       salt字符串，
     * @param algorithm 摘要算法名称，可以是MD5，SHA1等
     * @return 32字节的16进制的字符串
     */
    public static String digest(String data, String key, String algorithm) {
        if (isEmpty(data)) return "";
        String ret = "";
        try {
            byte[] keybytes = null;
            if (!isEmpty(key))
                keybytes = key.getBytes(DEFAULT_CHARSET);
            return digest(data.getBytes(DEFAULT_CHARSET), keybytes, algorithm);
        } catch (Exception e) {
            LOG.error("digest error:" + e);
        }
        return ret;
    }

    /**
     * 计算字符串的摘要信息
     *
     * @param data       源字节
     * @param key        salt，
     * @param digestName 摘要算法名称，可以是MD5，SHA1等
     * @return 32字节的16进制的字符串
     */
    public static String digest(byte[] data, byte[] key, String digestName) {
        byte[] bytes = digestBytes(data, key, digestName);
        if (bytes == null)
            return "";
        return toHex(bytes, 0, bytes.length);
    }

    public static byte[] digestBytes(byte[] data, byte[] key, String digestName) {
        if (data == null || data.length == 0) return null;
        try {
            MessageDigest mgd = MessageDigest.getInstance(digestName);
            mgd.update(data);
            byte[] bytes;
            if (key == null || key.length == 0) {
                bytes = mgd.digest();
            } else {
                bytes = mgd.digest(key);
            }
            mgd.reset();
            return bytes;
        } catch (Exception e) {
            LOG.error("digest error:" + e);
        }
        return null;
    }

    /**
     * hmacSHA1
     */
    public static String hmacSHA1(String data, String key) {
        if (isEmpty(data)) return "";
        String ret = "";
        try {
            SecretKeySpec signingKey = new SecretKeySpec(key.getBytes(DEFAULT_CHARSET), "HmacSHA1");
            Mac mac = Mac.getInstance("HmacSHA1");
            mac.init(signingKey);
            byte[] bytes = mac.doFinal(data.getBytes(DEFAULT_CHARSET));
            ret = toHex(bytes, 0, bytes.length);
            mac.reset();
        } catch (Exception ex) {
            LOG.error("hmacSHA1 error:", ex);
        }
        return ret;
    }

    public static boolean isNumeric(final CharSequence cs) {
        if (isEmpty(cs)) {
            return false;
        }
        final int sz = cs.length();
        for (int i = 0; i < sz; i++) {
            if (!Character.isDigit(cs.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    public static boolean isEmail(String s) {
        return isNotBlank(s) && s.matches("^[a-zA-Z0-9_!#$%&'*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$");
    }

    public static boolean isMobile(String s) {
        return !isEmpty(s) && s.matches("^(13|14|15|17|18)\\d{9}$");
    }

    public static boolean isOnlyChinese(String s) {
        return !isEmpty(s) && s.matches("[^u4e00-u9fa5]+$");
    }

    public static boolean isUrl(String s) {
        if (isEmpty(s)) return false;
        boolean ret = s.matches("^(https|http|ftp|rtsp|mms)?://[^\\s]*$");
        if (!ret)
            ret = s.matches("^[./?#a-zA-Z0-9-_=&;,%]*$");
        return ret;
    }

    /**
     * Map to queryString
     *
     * @param map Map对象
     * @return username=chenziwen&password=1234
     */
    public static String transMapToString(Map<String, Object> map) {
        if (map == null) return "";
        Map.Entry<String, Object> entry;
        StringBuilder sb = new StringBuilder();
        for (Iterator<Map.Entry<String, Object>> iterator = map.entrySet().iterator(); iterator.hasNext(); ) {
            entry = iterator.next();
            sb.append(entry.getKey()).append("=")
                    .append(null == entry.getValue() ? "" : entry.getValue().toString())
                    .append(iterator.hasNext() ? "&" : "");
        }
        return sb.toString();
    }
}