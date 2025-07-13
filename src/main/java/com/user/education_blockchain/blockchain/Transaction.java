package com.user.education_blockchain.blockchain;

import lombok.Getter;
import lombok.Setter;

import java.security.PrivateKey;
import java.security.PublicKey;

@Getter
@Setter
public class Transaction {

    public String from;
    public String to;
    public double amount;

    private byte[] signature;

    public Transaction(String from, String to, double amount) {
        this.from = from;
        this.to = to;
        this.amount = amount;
    }

    @Override
    public String toString() {
        return "Transaction{" +
                "from='" + from + '\'' +
                ", to='" + to + '\'' +
                ", amount=" + amount +
                '}';
    }

    // PRIVATE-KEY
    public void signTransaction(PrivateKey privateKey) {
        String data = "gönderici: " + from + " alıcı: " + to + "Miktar" + amount;
        this.signature = Utils.sign(data, privateKey);
    }

    // PUBLIC-KEY
    public boolean isSignatureValid(PublicKey publicKey) {
        String data = "gönderici: " + from + " alıcı: " + to + "Miktar" + amount;
        return Utils.verify(data, signature, publicKey);
    }

}