package com.user.education_blockchain.blockchain;

import com.user.education_blockchain.utils.SpecialColor;
import java.util.*;

public class MainTest_2 {

    private static final Scanner scanner = new Scanner(System.in);

    private static boolean running = true;

    private static final String[] userData = new String[3];

    // Track last transaction times to prevent spam
    private static final Map<String, Long> lastTransactionTime = new HashMap<>();

    // Blockchain and smart contract instances
    static Blockchain blockchain = new Blockchain();
    static SmartContract smartContract = new SmartContract(blockchain);

    private static String[] addUser() {
        for (int i = 0; i < 3; i++) {
            System.out.print(SpecialColor.YELLOW + "Please enter user " + (i + 1) + ": " + SpecialColor.RESET);
            String temp = scanner.nextLine();
            userData[i] = temp;
        }
        for (String temp : userData) {
            System.out.print(temp + " ");
        }
        return userData;
    }

    private static void walletAdd() {
        String[] user = addUser();
        for (int i = 0; i < 3; i++) {
            Wallet wallet = new Wallet();
            Wallet.getBalances().put(user[i], 1000.0);
            Wallet.createWallet(user[i]);
        }
    }

    /**
     * Displays menu options and processes user input accordingly.
     */
    private static void userChooise() {
        walletAdd();

        while (running) {
            System.out.println("\n========= 📋 Blockchain Menu =========");
            System.out.println("1. 💸 Make a Transfer");
            System.out.println("2. 📄 Print Blockchain");
            System.out.println("3. 💼 View Wallet Balances");
            System.out.println("4. ✅ Check Chain Validity");
            System.out.println("5. 🧱 Digital Signature Test");
            System.out.println("6. 📊 Statistical Report");
            System.out.println("0. ❌ Exit");
            System.out.print("Your choice: ");

            int chooise;
            for (;;) {
                System.out.println("\n" + SpecialColor.GREEN + "Enter a valid number choice:" + SpecialColor.RESET);
                if (scanner.hasNextInt()) {
                    chooise = scanner.nextInt();
                    scanner.nextLine(); // consume newline
                    break;
                } else {
                    System.out.println(SpecialColor.RED + "Invalid input. Please enter an integer." + SpecialColor.RESET);
                    scanner.nextLine();
                }
            }

            switch (chooise) {
                case 1: // Money transfer
                    Arrays.asList(userData).forEach(user -> System.out.print(user + " "));

                    long now = System.currentTimeMillis();

                    String from;
                    do {
                        System.out.print("\n👤 Sender: ");
                        from = scanner.nextLine();

                        // Anti-spam check: 5 seconds delay per sender
                        if (lastTransactionTime.containsKey(from)) {
                            long diff = now - lastTransactionTime.get(from);
                            if (diff < 5000) {
                                System.out.println("⚠️ You're sending too fast. Please wait a moment.");
                                return;
                            }
                        }
                        lastTransactionTime.put(from, now);

                        if (!Wallet.getBalances().containsKey(from)) {
                            System.out.println("⚠️ Invalid sender! Try again.");
                        }
                    } while (!Wallet.getBalances().containsKey(from));

                    // Receiver input and validation
                    String to;
                    do {
                        System.out.print("👤 Receiver: ");
                        to = scanner.nextLine();
                        if (!Wallet.getBalances().containsKey(to)) {
                            System.out.println("⚠️ Invalid receiver! Try again.");
                        }
                    } while (!Wallet.getBalances().containsKey(to));

                    // Amount input and validation
                    double amount;
                    do {
                        System.out.print("💰 Amount: ");
                        amount = scanner.nextDouble();
                        double senderBalance = Wallet.getBalances().get(from);

                        if (senderBalance == 0) {
                            System.out.println("💸 Your balance is 0. Add funds? (yes/no)");
                            scanner.nextLine();
                            String request = scanner.nextLine().trim().toLowerCase();
                            if (request.equals("yes")) {
                                System.out.print("💰 Enter amount to add: ");
                                double newAmount = scanner.nextDouble();
                                scanner.nextLine();
                                Wallet.addBalance(from, newAmount);
                                senderBalance = Wallet.getBalances().get(from);
                                System.out.println("✅ New balance: " + senderBalance + " TL");
                            } else {
                                System.out.println("Cancelled fund addition.");
                            }
                        }

                        if (amount <= 0) {
                            System.out.println("⚠️ Amount must be greater than zero. Try again.");
                        } else if (amount > senderBalance) {
                            System.out.println("⚠️ Insufficient balance. Max allowed: " + senderBalance);
                            amount = -1;
                        }
                    } while (amount <= 0);
                    scanner.nextLine();

                    // Transaction creation, signing, and validation
                    Wallet sender = Wallet.getWalletByName(from);
                    Wallet receiver = Wallet.getWalletByName(to);

                    Transaction signedTx = new Transaction(
                            sender.getPublicKeyString(),
                            receiver.getPublicKeyString(),
                            amount
                    );

                    signedTx.signTransaction(sender.getPrivateKey());
                    boolean valid = signedTx.isSignatureValid(sender.getPublicKey());

                    if (!valid) {
                        System.out.println("❌ Invalid transaction signature! Transfer canceled.");
                        break;
                    }

                    // Record transaction
                    Wallet.incrementTransactionCount(from);
                    List<Transaction> txs = new ArrayList<>();
                    txs.add(signedTx);
                    Block newBlock = new Block(blockchain.getChain().size(), txs, blockchain.getLatestBlock().getHash());
                    blockchain.addBlock(newBlock);

                    // Confirmation & signature details
                    System.out.println("✅ Transaction completed successfully.");
                    System.out.println("\n🔐 Digital Signature Details:");
                    System.out.println("Sender: " + from);
                    System.out.println("Receiver: " + to);
                    System.out.println("Amount: " + amount);
                    System.out.println("Sender Private Key: " + sender.getPrivateKeyString());
                    System.out.println("Sender Public Key: " + sender.getPublicKeyString());
                    System.out.println("Signature Valid: " + (valid ? "Yes" : "No"));

                    System.out.println(smartContract.executeTransfer(from, to, amount));
                    break;

                case 2: // Print blockchain
                    System.out.println("\n========= Blockchain Structure =========");
                    System.out.println(blockchain.printBlockChain());
                    break;

                case 3: // Show wallet balances
                    System.out.println("\n========= Wallet Balances =========");
                    Wallet.getBalances().forEach((user, balance) ->
                            System.out.println(user + " => Balance: " + balance + " TL"));
                    break;

                case 4: // Validate chain
                    String result = blockchain.isChainValid() ? "✅ Yes" : "❌ No";
                    System.out.println("\nIs the blockchain valid? " + result);
                    break;

                case 5: // Digital signature test
                    System.out.println("=== Digital Signature and Key Test ===");
                    Wallet senderWallet = new Wallet();
                    Wallet receiverWallet = new Wallet();

                    Transaction transaction = new Transaction(
                            senderWallet.getPublicKeyString(),
                            receiverWallet.getPublicKeyString(),
                            100
                    );
                    transaction.signTransaction(senderWallet.getPrivateKey());
                    boolean isValid = transaction.isSignatureValid(senderWallet.getPublicKey());

                    System.out.println("--- Transaction Info ---");
                    System.out.println("Sender Public Key: " + senderWallet.getPublicKeyString());
                    System.out.println("Sender Private Key: " + senderWallet.getPrivateKeyString());
                    System.out.println("Receiver Public Key: " + receiverWallet.getPublicKeyString());
                    System.out.println("Amount: 100");
                    System.out.println("Signature Valid: " + (isValid ? "Yes" : "No"));
                    break;

                case 6: // Statistics
                    System.out.println("\n========= 📊 Statistics =========");
                    System.out.println("Transaction Count Per User:");
                    Wallet.getTransactionCounts().forEach((user, count) ->
                            System.out.println(user + " made " + count + " transactions."));

                    double totalAmount = blockchain.getChain().stream()
                            .flatMap(block -> block.getTransactions().stream())
                            .filter(tx -> tx.getAmount() > 0)
                            .mapToDouble(Transaction::getAmount)
                            .sum();
                    System.out.println("\nTotal Transferred Amount: " + SpecialColor.YELLOW + totalAmount + SpecialColor.RESET + " TL");

                    System.out.println("\nTransactions Per Block:");
                    int i = 0;
                    for (Block block : blockchain.getChain()) {
                        System.out.println("Block " + (i++) + ": " + block.getTransactions().size() + " transactions");
                    }
                    break;

                case 0: // Exit
                    running = false;
                    System.out.println("Exiting...");
                    System.exit(0);
                    break;

                default:
                    System.out.println("❌ Invalid selection. Please try again.");
            }
        }

        scanner.close();
    }

    public static void main(String[] args) {
        userChooise();
    }
}
