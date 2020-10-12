package com.foo.common.base.utils;


import org.apache.commons.codec.binary.Hex;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;


public class Md5PasswordEncoder {
    private String salt = "defaultSalt";

    public String encodePassword(String rawPass, String salt) {
        String saltedPass = mergePasswordAndSalt(rawPass, salt, false);
        MessageDigest messageDigest = getMessageDigest();
        byte[] digest;
        try {
            digest = messageDigest.digest(saltedPass.getBytes("UTF-8"));
        } catch (UnsupportedEncodingException e) {
            throw new IllegalStateException("UTF-8 not supported!");
        }
        return new String(Hex.encodeHex(digest));
    }

    public boolean isPasswordValid(String encPass, String rawPass, String salt) {
        String pass1 = "" + encPass;
        String pass2 = encodePassword(rawPass, salt);
        return pass1.equals(pass2);
    }

    protected final MessageDigest getMessageDigest() {
        String algorithm = "MD5";
        try {
            return MessageDigest.getInstance(algorithm);
        } catch (NoSuchAlgorithmException e) {
            throw new IllegalArgumentException("No such algorithm ["
                    + algorithm + "]");
        }
    }

    /**
     * Used by subclasses to generate a merged password and salt <code>String</code>.<P>The generated password
     * will be in the form of <code>password{salt}</code>.</p>
     * <p>A <code>null</code> can be passed to either method, and will be handled correctly. If the
     * <code>salt</code> is <code>null</code> or empty, the resulting generated password will simply be the passed
     * <code>password</code>. The <code>toString</code> method of the <code>salt</code> will be used to represent the
     * salt.</p>
     *
     * @param password the password to be used (can be <code>null</code>)
     * @param salt     the salt to be used (can be <code>null</code>)
     * @param strict   ensures salt doesn't contain the delimiters
     * @return a merged password and salt <code>String</code>
     * @throws IllegalArgumentException if the salt contains '{' or '}' characters.
     */
    protected String mergePasswordAndSalt(String password, Object salt,
                                          boolean strict) {
        if (password == null) {
            password = "";
        }
        if (strict && (salt != null)) {
            if ((salt.toString().lastIndexOf("{") != -1)
                    || (salt.toString().lastIndexOf("}") != -1)) {
                throw new IllegalArgumentException("Cannot use { or } in salt.toString()");
            }
        }
        if ((salt == null) || "".equals(salt)) {
            return password;
        } else {
            return password + "{" + salt.toString() + "}";
        }
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }
}
