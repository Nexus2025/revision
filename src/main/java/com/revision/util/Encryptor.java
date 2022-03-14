package com.revision.util;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.io.IOException;
import java.io.InputStream;
import java.security.GeneralSecurityException;
import java.util.Properties;

public class Encryptor {

    private Encryptor(){}

    public static String encrypt (String notEncryptedPassword) throws GeneralSecurityException, IOException {
        Cipher cipher = Cipher.getInstance("AES");
        SecretKeySpec key = new SecretKeySpec(loadKeyFromProperties().getBytes(), "AES");
        cipher.init(Cipher.ENCRYPT_MODE, key);

        StringBuilder encryptedPassword = new StringBuilder();
        for (byte b : cipher.doFinal(notEncryptedPassword.getBytes())) {
            encryptedPassword.append(String.format("%02X", b));
        }
        return encryptedPassword.toString();
    }

    private static String loadKeyFromProperties() throws IOException {
        InputStream inputStream = Encryptor.class.getResourceAsStream("/encrypt.properties");
        Properties properties = new Properties();
        properties.load(inputStream);
        inputStream.close();
        return properties.getProperty("key");
    }
}
