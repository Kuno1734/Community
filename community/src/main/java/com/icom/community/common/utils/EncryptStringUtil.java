package com.icom.community.common.utils;

import lombok.extern.slf4j.Slf4j;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.util.Base64;
import java.util.UUID;

import javax.crypto.Cipher;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;


@Slf4j
public class EncryptStringUtil {
    private static final int SALT_LENGTH = 16;
    private static final int IV_LENGTH = 16;
    private static final int ITERATIONS = 65536;
    private static final int KEY_LENGTH = 256;
    private static final String AES_ALGORITHM = "AES/CBC/PKCS5Padding";
    private static final String PBKDF2_ALGORITHM = "PBKDF2WithHmacSHA256";


    public static String generateUniqueId() {
        return UUID.randomUUID()
                .toString()
                .replaceAll("-", "");
    }


    private static byte[] generateSalt() {
        byte[] salt = new byte[SALT_LENGTH];
        new SecureRandom().nextBytes(salt);
        return salt;
    }

    //SHA256 패스워드 암호화
    public static String hashPassword(String password) {
        try {
            byte[] salt = generateSalt();
            SecretKeyFactory skf = SecretKeyFactory.getInstance(PBKDF2_ALGORITHM);
            KeySpec spec = new PBEKeySpec(password.toCharArray(), salt, ITERATIONS, KEY_LENGTH);
            byte[] hash = skf.generateSecret(spec).getEncoded();
            return Base64.getEncoder().encodeToString(salt)
                    + ":"
                    + Base64.getEncoder().encodeToString(hash);
        }catch (NoSuchAlgorithmException algorithmException){
            log.error("[hashPassword][Error] NoSuchAlgorithmException : \n{}" , algorithmException.getMessage());
            return null;
        }catch (InvalidKeySpecException invalidKeySpecException){
            log.error("[hashPassword][Error] InvalidKeySpecException : \n{}" , invalidKeySpecException.getMessage());
            return null;
        }catch (Exception exception){
            log.error("[hashPassword][Error] NormalException : \n{}" , exception.getMessage());
            return null;
        }
    }

    /**
     * 패스워드 검증함수
     * pssword : 입력값
     * stored : db 저장값(비교 값)
     */
    public static boolean verifyPassword(String password, String stored) {
        try {
            String[] parts = stored.split(":");
            byte[] salt = Base64.getDecoder().decode(parts[0]);
            byte[] hash = Base64.getDecoder().decode(parts[1]);

            SecretKeyFactory skf = SecretKeyFactory.getInstance(PBKDF2_ALGORITHM);
            KeySpec spec = new PBEKeySpec(password.toCharArray(), salt, ITERATIONS, KEY_LENGTH);
            byte[] testHash = skf.generateSecret(spec).getEncoded();

            return MessageDigest.isEqual(hash, testHash);
        }catch(NoSuchAlgorithmException algorithmException){
            log.error("[verifyPassword][Error] NoSuchAlgorithmException : \n{}" , algorithmException.getMessage());
            return false;
        }catch (InvalidKeySpecException invalidKeySpecException){
            log.error("[verifyPassword][Error] InvalidKeySpecException : \n{}" , invalidKeySpecException.getMessage());
            return false;
        }catch (Exception exception){
            log.error("[verifyPassword][Error] NormalException : \n{}" , exception.getMessage());
            return false;
        }
    }


    public static String encryptAES(String data, String key) throws Exception {
        byte[] iv = new byte[IV_LENGTH];
        new SecureRandom().nextBytes(iv);
        IvParameterSpec ivSpec = new IvParameterSpec(iv);

        SecretKeySpec keySpec = new SecretKeySpec(
                key.getBytes(StandardCharsets.UTF_8), "AES");

        Cipher cipher = Cipher.getInstance(AES_ALGORITHM);
        cipher.init(Cipher.ENCRYPT_MODE, keySpec, ivSpec);
        byte[] encrypted = cipher.doFinal(data.getBytes(StandardCharsets.UTF_8));

        byte[] combined = new byte[IV_LENGTH + encrypted.length];
        System.arraycopy(iv, 0, combined, 0, IV_LENGTH);
        System.arraycopy(encrypted, 0, combined, IV_LENGTH, encrypted.length);
        return Base64.getEncoder().encodeToString(combined);
    }


    public static String decryptAES(String encryptedData, String key) throws Exception {
        byte[] combined = Base64.getDecoder().decode(encryptedData);
        byte[] iv = new byte[IV_LENGTH];
        System.arraycopy(combined, 0, iv, 0, IV_LENGTH);
        IvParameterSpec ivSpec = new IvParameterSpec(iv);

        int cipherLength = combined.length - IV_LENGTH;
        byte[] cipherText = new byte[cipherLength];
        System.arraycopy(combined, IV_LENGTH, cipherText, 0, cipherLength);

        SecretKeySpec keySpec = new SecretKeySpec(
                key.getBytes(StandardCharsets.UTF_8), "AES");
        Cipher cipher = Cipher.getInstance(AES_ALGORITHM);
        cipher.init(Cipher.DECRYPT_MODE, keySpec, ivSpec);
        byte[] original = cipher.doFinal(cipherText);
        return new String(original, StandardCharsets.UTF_8);
    }
}
