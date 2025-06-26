package com.user.education_blockchain.blockchain;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;
import java.util.Map;

@Getter
@Setter
public class _05_Wallet {

    private static Map<String, Double> balances = new HashMap<>();

    public static void createWallet(String user) {
        balances.putIfAbsent(user,100.0);
    }

    public static boolean transfer(String from, String to, double amount) {
        if (balances.getOrDefault(from,0.0) > amount) {
            balances.put(from,balances.get(from)-amount);
            balances.put(to,balances.getOrDefault(to,0.0));
            return true;
        }
        return false;
    }

    public static Map<String, Double> getBalances() {
        return balances;
    }
}
