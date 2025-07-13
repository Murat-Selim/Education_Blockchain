package com.user.education_blockchain.blockchain;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
public class Block {

    private int index;
    private String timestamp;
    private List<Transaction> transactions;
    private String previousHash;
    private String hash;

    public Block(int index, List<Transaction> transactions, String previousHash) {
        this.index = index;
        this.transactions = transactions;
        this.previousHash = previousHash;
        this.timestamp = LocalDateTime.now().toString();
        this.hash = calculateHash();
    }

    public String calculateHash() {
        StringBuilder txData = new StringBuilder();
        for (Transaction temp : transactions) {
            txData.append(temp.toString());
        }
        String input = index + timestamp + txData + previousHash;
        return Utils.applySHA256(input);
    }

    public boolean isValid() {
        return hash.equals(calculateHash());
    }

    public List<Transaction> getTransactions() {
        return transactions;
    }

    @Override
    public String toString() {
        return "Block {" +
                "index=" + index +
                ", timestamp='" + timestamp + '\'' +
                ", transactions=" + transactions +
                ", previousHash='" + previousHash + '\'' +
                ", hash='" + hash + '\'' +
                '}';
    }

    public int getIndex() {
        return index;
    }

    public String getPreviousHash() {
        return previousHash;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public String getHash() {
        return hash;
    }
}
