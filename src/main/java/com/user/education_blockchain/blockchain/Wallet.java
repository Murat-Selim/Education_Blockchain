package com.user.education_blockchain.blockchain;

import lombok.Getter;
import lombok.Setter;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

@Getter
@Setter
public class Wallet {

    // Map the username (or ID) to the balance
    private static Map<String, Double> balances = new HashMap<>();
    private static Map<String, Wallet> walletMap = new HashMap<>();
    private static Map<String, Integer> transactionCounts = new HashMap<>();
    private KeyPair keyPair;

    public Wallet() {
        try {
            KeyPairGenerator keyGen = KeyPairGenerator.getInstance("RSA");
            keyGen.initialize(2048);
            this.keyPair= keyGen.generateKeyPair();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public static void createWallet(String name) {
        balances.putIfAbsent(name, 100.0);

        Wallet wallet= new Wallet();
        walletMap.put(name,wallet);
        transactionCounts.putIfAbsent(name,0);
    }

    public static Wallet getWalletByName(String name){
        return walletMap.get(name);
    }

    public static boolean transfer(String from, String to, double amount) {
        if (balances.getOrDefault(from, 0.0) >= amount) {
            balances.put(from, balances.get(from) - amount);
            balances.put(to, balances.getOrDefault(to, 0.0) + amount);
            return true;
        }
        return false;
    }

    public static Map<String, Double> getBalances() {
        return balances;
    }

    public static void incrementTransactionCount(String name){
        transactionCounts.put(name,transactionCounts.getOrDefault(name,0)+1);
    }

    public static void addBalance(String walletName, double amount) {
        double current = balances.getOrDefault(walletName,0.0);
        balances.put(walletName,current+amount);
    }

    public static Map<String, Integer> getTransactionCounts() {
        return transactionCounts;
    }

    public PrivateKey getPrivateKey(){
        return keyPair.getPrivate();
    }

    public PublicKey getPublicKey(){
        return keyPair.getPublic();
    }

    // Encoder getPublicKeyString
    public String getPublicKeyString(){
        return Base64.getEncoder().encodeToString(keyPair.getPublic().getEncoded());
    }

    // Encoder getPrivateKeyString
    public String getPrivateKeyString(){
        return Base64.getEncoder().encodeToString(keyPair.getPrivate().getEncoded());
    }
}
