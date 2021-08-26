package util;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class HashingPassword {

    public static String hash(String message) {

        String ret = null;

        try {
            MessageDigest md = MessageDigest.getInstance("SHA-512");
            byte[] b = md.digest(message.getBytes(StandardCharsets.UTF_8));

            StringBuffer buff = new StringBuffer();
            for (int i = 0; i < b.length; ++i) {
                buff.append(Integer.toString((b[i] & 0xff) + 0x100, 16).substring(1));
            }
            ret = buff.toString();

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        return ret;
    }

}