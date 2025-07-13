package com.user.education_blockchain.blockchain;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class SmartContract {

    private Blockchain blockchain;
    private List<Transaction> pendingTransactions = new ArrayList<>();
    private final int BLOCK_SIZE = 3;

    public SmartContract(Blockchain blockchain) {
        this.blockchain = blockchain;
    }

    public String executeTransfer(String from, String to, double amout) {
        if (Wallet.transfer(from, to, amout)) {
            Transaction transactionData = new Transaction(from, to, amout);
            pendingTransactions.add(transactionData);

            if (pendingTransactions.size() >= BLOCK_SIZE) {
                Block block = new Block(
                        blockchain.getChain().size(),
                        new ArrayList<>(pendingTransactions),
                        blockchain.getLatestBlock().getHash()
                );
                blockchain.addBlock(block);
                pendingTransactions.clear();
                return "\n✅ Block oluşturuldu ve işlem zinciri eklendi";
            }
            return "ℹ️ İşlem havuza eklendi. Yeni bir blok için kalan işlem ==> " + (BLOCK_SIZE - pendingTransactions.size());
        } else {
            return "❌ Bakiye yetersiz";
        }
    }
}
