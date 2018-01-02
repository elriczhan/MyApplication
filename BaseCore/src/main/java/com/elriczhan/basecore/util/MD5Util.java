package com.elriczhan.basecore.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by xinshei on 2018/1/2.
 */

public class MD5Util {
    public static String md5(String before) {
        MessageDigest m = null;
        try {
            m = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        m.update(before.getBytes(), 0, before.length());
        // get md5 bytes
        byte p_md5Data[] = m.digest();
        // create a hex string
        String after = "";
        for (int i = 0; i < p_md5Data.length; i++) {
            int b = (0xFF & p_md5Data[i]);
            // if it is a single digit, make sure it have 0 in front (proper padding)
            if (b <= 0xF)
                after += "0";
            // add number to string
            after += Integer.toHexString(b);
        }   // hex string to uppercase
        after = after.toUpperCase();
        return after;
    }
}
