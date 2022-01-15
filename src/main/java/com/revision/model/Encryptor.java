package com.revision.model;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Encryptor {

    public String encrypt (String password) {

        String result = null;

        try {
            MessageDigest md5 = MessageDigest.getInstance("MD5");
            byte[] bytes = md5.digest(password.getBytes());
            StringBuilder sb = new StringBuilder();
            for (byte b : bytes) {
                sb.append(String.format("%02X", b));
            }
            result = sb.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        return result;
    }
}
