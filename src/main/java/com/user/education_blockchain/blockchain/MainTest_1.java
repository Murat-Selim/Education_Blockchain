package com.user.education_blockchain.blockchain;

public class MainTest_1 {

    public static void main(String[] args) {

        // ✅ 1- Let's create the Blockchain.
        Blockchain blockchain = new Blockchain();

        // ✅ 2- Create user wallets
        Wallet.createWallet("Hamit");
        Wallet.createWallet("Hulusi");
        Wallet.createWallet("Habibe");

        // ✅ 3- Smart Contract
        SmartContract smartContract = new SmartContract(blockchain);

        // ✅ 4- Let's send some transactions (Transfers)
        System.out.println(smartContract.executeTransfer("Hamit", "Hulusi", 20));
        System.out.println(smartContract.executeTransfer("Hulusi", "Habibe", 10));
        System.out.println(smartContract.executeTransfer("Habibe", "Hamit", 5));

        System.out.println(smartContract.executeTransfer("Hamit", "Habibe", 50));
        System.out.println(smartContract.executeTransfer("Habibe", "Hulusi", 15));
        System.out.println(smartContract.executeTransfer("Hulusi", "Hamit", 25));

        // ✅ 5- Print the Blockchain structure
        System.out.println("\n ================ Blockchain Structure ================");
        System.out.println(blockchain.printBlockChain());

        // ✅ 6- Display user balances
        System.out.println("\n ================ Wallet Balances ================");
        Wallet.getBalances().forEach(
                (user, balance) -> System.out.println(user + " balance: " + balance)
        );

        // ✅ 7- Check if the chain is valid
        System.out.println("\nIs the chain valid? " + (blockchain.isChainValid() ? "✅ Yes" : "❌ No"));
    }
}
