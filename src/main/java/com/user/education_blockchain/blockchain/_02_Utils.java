package com.user.education_blockchain.blockchain;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.security.MessageDigest;

@Getter
@Setter
public class _02_Utils {

    private final static String ALGORITHM_256 = "SHA-256";
    private final static String UTF8 = "UTF-8";

    public static String applySHA256(String input) {
        try {
            MessageDigest digest = MessageDigest.getInstance(ALGORITHM_256);

            byte[] hash = digest.digest(input.getBytes(UTF8));

            StringBuilder hexString = new StringBuilder();

            for (byte temp : hash) {
                String hex = Integer.toHexString(0xff & temp);

                if (hex.length() == 1) {
                    hexString.append('0');
                }
                hexString.append(hex);
            }
            return hexString.toString();
        } catch (Exception exception) {
            throw new RuntimeException(exception);
        }
    }
}
