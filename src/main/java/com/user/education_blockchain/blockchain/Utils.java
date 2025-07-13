package com.user.education_blockchain.blockchain;

import lombok.Getter;
import lombok.Setter;

import java.nio.charset.StandardCharsets;
import java.security.*;

@Getter
@Setter
public class Utils {

    // SHA-256
    private final static String ALGORITHM_256 = "SHA-256";

    // PublicKey - PrivateKey
    private final static String SHA256_WITH_RSA = "SHA256withRSA";

    private final static String UTF8 = "UTF-8";

    public static String applySHA256(String input) {
        try {
            MessageDigest digest = MessageDigest.getInstance(ALGORITHM_256);

            byte[] hash = digest.digest(input.getBytes(UTF8));

            StringBuilder hexSting = new StringBuilder();

            for (byte temp : hash) {
                String hex = Integer.toHexString(0xff & temp);
                if (hex.length() == 1) {
                    hexSting.append('0');
                }
                hexSting.append(hex);
            }

            return hexSting.toString();
        } catch (Exception exception) {
            throw new RuntimeException(exception);
        }
    }

    // Private Key (sign)
    public static byte[] sign (String data, PrivateKey privateKey)  {
        try{
            Signature privateSignature = Signature.getInstance(SHA256_WITH_RSA);
            privateSignature.initSign(privateKey);
            privateSignature.update(data.getBytes(StandardCharsets.UTF_8));
            return privateSignature.sign();
        }catch (Exception exception){
            exception.printStackTrace();
            throw new RuntimeException(exception);
        }
    }

    // Public Key (Verify)
    public static boolean verify(String data, byte [] signature, PublicKey publicKey){
        try{
            Signature publicSignature = Signature.getInstance(SHA256_WITH_RSA);
            publicSignature.initVerify(publicKey);
            publicSignature.update(data.getBytes(StandardCharsets.UTF_8));
            return publicSignature.verify(signature);
        } catch (Exception exception){
            exception.printStackTrace();
            throw new RuntimeException(exception);
        }
    }
}
