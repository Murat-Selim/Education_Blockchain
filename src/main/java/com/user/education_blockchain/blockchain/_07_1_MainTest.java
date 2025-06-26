package com.user.education_blockchain.blockchain;

public class _07_1_MainTest {
    public static void main(String[] args) {
        // ✅ 1- Blockchain Create.
        _04_Blockchain blockchain = new _04_Blockchain();

        // ✅ 2- User Wallets Create
        _05_Wallet.createWallet("Hamit");
        _05_Wallet.createWallet("Hulusi");
        _05_Wallet.createWallet("Habibe");

        // ✅ 3- Smart Contract
        _06_SmartContract smartContract = new _06_SmartContract(blockchain);


        // ✅ 4- Transfers
        System.out.println(smartContract.executeTransfer("Hamit", "Hulusi", 20));
        System.out.println(smartContract.executeTransfer("Hulusi", "Habibe", 10));
        System.out.println(smartContract.executeTransfer("Habibe", "Hamit", 5));

        System.out.println(smartContract.executeTransfer("Hamit", "Habibe", 50));
        System.out.println(smartContract.executeTransfer("Habibe", "Hulusi", 15));
        System.out.println(smartContract.executeTransfer("Hulusi", "Hamit", 25));

        // ✅ 5- Blockchain Chain Print
        System.out.println("\n ================ Blockchain Yapısı ================");
        System.out.println(blockchain.printBlockChain());

        // ✅ 6- User Account
        System.out.println("\n ================ Cüzdan Bakiyesi ================");
        _05_Wallet.getBalances().forEach(
                (user, balance) -> System.out.println(user + " bakiyesi " + balance)
        );

        // 7- Chain is Valid?
        System.out.println("\nZincir Geçerli mi ? "+(blockchain.isChainValid() ? "✅ Evet": "❌ Hayır"));

    }
}
