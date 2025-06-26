package com.user.education_blockchain.blockchain;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
public class _03_Block {

    private int index;
    private String timestamp;
    private List<_01_Transaction> transactions;
    private String previousHash;
    private String hash;

    public _03_Block(int index, List<_01_Transaction> transactions, String previousHash) {
        this.index = index;
        this.transactions = transactions;
        this.previousHash = previousHash;
        this.timestamp = LocalDateTime.now().toString();
        this.hash = calculateHash(); // Hash hesaplama ve atama

    }

    public String calculateHash() {
        StringBuilder txData = new StringBuilder();
        for (_01_Transaction temp : transactions) {
            txData.append(temp.toString());
        }

        String input = index + timestamp + txData + previousHash;

        return _02_Utils.applySHA256(input);

        //String hash = DigestUtils.sha256Hex(input);
        //return hash
    }

    public boolean isValid() {
        return hash.equals(calculateHash());
    }

    public List<_01_Transaction> getTransactions() {
        return transactions;
    }
}
