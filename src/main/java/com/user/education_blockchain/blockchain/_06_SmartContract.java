package com.user.education_blockchain.blockchain;

import java.util.ArrayList;
import java.util.List;

public class _06_SmartContract {

    private _04_Blockchain blockchain;

    private List<_01_Transaction> pendingTransactions = new ArrayList<>();

    private final int BLOCK_SIZE = 3;

    public _06_SmartContract(_04_Blockchain blockchain) {
        this.blockchain = blockchain;
    }

    public String executeTransfer(String from, String to, double amount) {
        if (_05_Wallet.transfer(from, to, amount)) {
            _01_Transaction transactionData = new _01_Transaction(from, to, amount);
            pendingTransactions.add(transactionData);

            if (pendingTransactions.size() > BLOCK_SIZE) {
                _03_Block block = new _03_Block(
                        blockchain.getChain().size(),
                        new ArrayList<>(pendingTransactions),
                        blockchain.getLatestBlock().getHash()
                );
                blockchain.addBlock(block);
                pendingTransactions.clear();
                return "✅ Block created and tx chain added";
            }
            return "ℹ️ tx added pool" + (BLOCK_SIZE - pendingTransactions.size());

        } else {
            return "❌ infussient balance";
        }
    }
}
