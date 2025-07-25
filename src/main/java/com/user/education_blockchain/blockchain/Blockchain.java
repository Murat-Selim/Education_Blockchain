package com.user.education_blockchain.blockchain;

import com.user.education_blockchain.utils.SpecialColor;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class Blockchain {

    private List<Block> chain = new ArrayList<>();

    public Blockchain() {
        chain.add(createGenesisBlock());
    }

    @Override
    public String toString() {
        return "Blockchain{" +
                "chain=" + chain +
                '}';
    }

    public Block createGenesisBlock() {
        List<Transaction> genesisTx = new ArrayList<>();
        genesisTx.add(new Transaction("Genesis", "System", 0));
        return new Block(0, genesisTx, "0");
    }

    public Block getLatestBlock() {
        return chain.get(chain.size() - 1);
    }

    public void addBlock(Block newBlock) {
        if (newBlock.getPreviousHash().equals(getLatestBlock().getHash())) {
            chain.add(newBlock);
        }
    }

    public boolean isChainValid() {
        for (int i = 1; i < chain.size(); i++) {
            Block current = chain.get(i);
            Block previous = chain.get(i - 1);

            if (!current.getHash().equals(current.calculateHash()))
                return false;

            if (!current.getPreviousHash().equals(previous.calculateHash()))
                return false;
        }
        return true;
    }

    public List<Block> getChain() {
        return chain;
    }

    public String printBlockChain() {
        StringBuilder builder = new StringBuilder();
        for (Block block : chain) {
            builder.append(SpecialColor.BLUE).append("Block ").append(SpecialColor.RESET).append(block.getIndex()).append("\n")
                    .append(SpecialColor.BLUE).append("\uD83D\uDD52 Time: ").append(SpecialColor.RESET).append(block.getTimestamp()).append("\n")
                    .append(SpecialColor.BLUE).append("\uD83D\uDD22 Hash: ").append(SpecialColor.RESET).append(block.getHash()).append("\n")
                    .append(SpecialColor.BLUE).append("↩\uFE0F Prev: ").append(SpecialColor.RESET).append(block.getPreviousHash()).append("\n")
                    .append(SpecialColor.YELLOW).append("\uD83D\uDCE6 Transaction \n").append(SpecialColor.RESET);

            for (Transaction transaction : block.getTransactions()) {
                builder.append(".").append(transaction.toString()).append("\n");
            }
            builder.append("\n");
        }
        return builder.toString();
    }
}
