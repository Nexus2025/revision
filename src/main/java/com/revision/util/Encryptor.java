package com.revision.util;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.security.GeneralSecurityException;

public class Encryptor {

    public static String encrypt (String notEncryptedPassword) throws GeneralSecurityException {
        Cipher cipher = Cipher.getInstance("AES");
        SecretKeySpec key = new SecretKeySpec("%eoSq83k2!hMA875".getBytes(), "AES");
        cipher.init(Cipher.ENCRYPT_MODE, key);

        StringBuilder encryptedPassword = new StringBuilder();
        for (byte b : cipher.doFinal(notEncryptedPassword.getBytes())) {
            encryptedPassword.append(String.format("%02X", b));
        }
        return encryptedPassword.toString();
    }

    private Encryptor(){}
}
