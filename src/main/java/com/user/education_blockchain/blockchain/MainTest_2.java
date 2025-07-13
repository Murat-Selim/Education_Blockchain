package com.user.education_blockchain.blockchain;

import com.user.education_blockchain.utils.SpecialColor;

import java.util.*;

public class MainTest_2 {

    private static final Scanner scanner = new Scanner(System.in);

    private static boolean running = true;

    private static final String[] userData = new String[3];

    private static final Map<String, Long> lastTransactionTime = new HashMap<>();

    static Blockchain blockchain = new Blockchain();
    static SmartContract smartContract = new SmartContract(blockchain);

    private static String[] addUser() {
        for (int i = 0; i < 3; i++) {
            System.out.print(SpecialColor.YELLOW + "Lütfen " + (i + 1) + " kullanıcıyı yazınız: " + SpecialColor.RESET);
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

    private static void userChooise() {
        walletAdd();
        while (running) {
            System.out.println("\n========= 📋 Blockchain Menü =========");
            System.out.println("1. 💸 Transfer Yap");
            System.out.println("2. 📄 Blockchain Yazdır");
            System.out.println("3. 💼 Cüzdan Bakiyelerini Görüntüle");
            System.out.println("4. ✅ Zincir Geçerliliğini Kontrol Et");
            System.out.println("5. 🧱 Dijital İmza Ve Anahtar Yönetimi Testi");
            System.out.println("6. ✅ İstatiksel Gösterim");
            System.out.println("0. ❌ Çıkış");
            System.out.print("Seçiminiz: ");

            int chooise ;

            for(;;){
                System.out.println("\n" +SpecialColor.GREEN+"Seçiminizi yapınız sadece sayı giriniz"+SpecialColor.RESET);
                if(scanner.hasNextInt()){
                    chooise = scanner.nextInt();
                    scanner.nextLine();
                    break;
                } else{
                    System.out.println(SpecialColor.RED+"Hatalı giriş yaptınız Lütfen sadece tamsayı(int) giriniz"+SpecialColor.RESET);
                    scanner.nextLine();
                }
            }

            switch (chooise) {
                case 1:
                    Arrays.asList(userData).forEach(user -> System.out.print(user + " "));
                    // scanner.nextLine();

                    long now = System.currentTimeMillis();

                    String from;
                    do {
                        System.out.print("\n👤 Gönderen: ");
                        from = scanner.nextLine();

                        if(lastTransactionTime.containsKey(from)){
                            long diff = now -lastTransactionTime.get(from);
                            if(diff<5000){
                                System.out.println("⚠️ Çok sık işlem gönderiyorsunuz ne yapıyorsun sen !!!!, Lütfen bekleyin");
                                return;
                            }
                        }
                        lastTransactionTime.put(from,now);
                        if (!Wallet.getBalances().containsKey(from)) {
                            System.out.println("⚠️ Geçersiz gönderen kullanıcı! Tekrar deneyin.");
                        }
                    } while (!Wallet.getBalances().containsKey(from));


                    String to;
                    do {
                        System.out.print("👤 Alıcı: ");
                        to = scanner.nextLine();
                        if (!Wallet.getBalances().containsKey(to)) {
                            System.out.println("⚠️ Geçersiz alıcı kullanıcı! Tekrar deneyin.");
                        }
                    } while (!Wallet.getBalances().containsKey(to));

                    double amount;
                    do {
                        System.out.print("💰 Miktar: ");
                        amount = scanner.nextDouble();
                        double senderBalance = Wallet.getBalances().get(from);

                        if (senderBalance == 0) {
                            System.out.println("💸 Bakiyeniz 0TL. Yeni para eklemek ister misiniz ? (evet/hayır) ");
                            String request = scanner.nextLine().trim().toLowerCase();
                            if (request.equals("evet")) {
                                System.out.println("💰 Eklenek para miktarı");
                                double newAmount = scanner.nextDouble();
                                scanner.nextLine();
                                Wallet.addBalance(from, newAmount);
                                senderBalance = Wallet.getBalances().get(from);
                                System.out.println("✅ Yeni bakiye: " + senderBalance + " TL");
                            } else {
                                System.out.println("Cüzdana para ekleme iptal edildi");
                            }
                        }

                        if (amount <= 0) {
                            System.out.println("⚠️ Miktar olarak sıfırdan büyük olmalıdır. Tekrar giriniz.");
                        } else if (amount > senderBalance) {
                            System.out.println("⚠️ Bakiye yetersiz! En fazla " + senderBalance + " kadar gönderebilirsiniz.");
                            amount = -1;
                        }
                    } while (amount <= 0);
                    scanner.nextLine();

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
                        System.out.println(" işlem imzası geçersiz! Transfer iptal edildi.");
                        break;
                    }

                    Wallet.incrementTransactionCount(from);

                    List<Transaction> txs = new ArrayList<>();
                    txs.add(signedTx);
                    Block newBlock = new Block(blockchain.getChain().size(), txs, blockchain.getLatestBlock().getHash());
                    blockchain.addBlock(newBlock);

                    System.out.println("ilem baarıyla gerçekletirildi.");

                    System.out.println("\n Dijital imza Detayları:");
                    System.out.println("Gönderen Adı: " + from);
                    System.out.println(" Alıcı Adı: " + to);
                    System.out.println(" Miktar: " + amount);
                    System.out.println(" Gönderen Private Key: " + sender.getPrivateKeyString());
                    System.out.println(" Gönderen Public Key: " + sender.getPublicKeyString());
                    System.out.println(" imza Dogrulama Sonucu: " + (valid?  "Geçerli" : "Geçersiz"));

                    System.out.println(smartContract.executeTransfer(from, to, amount));
                    break;

                case 2:
                    System.out.println("\n=========Blockchain Yapısı========");
                    System.out.println(blockchain.printBlockChain());
                    break;

                case 3:
                    System.out.println("\n=========Cüzdan Bakiyesi========");
                    Wallet.getBalances().forEach((user, balance) -> {
                        System.out.println(user + " ==> Bakiyesi: " + balance);
                    });
                    break;

                case 4:
                    String result = blockchain.isChainValid() ? "✅ Evet" : "❌ Hayır";
                    System.out.println("\nZincir Geçerli mi ? " + (result));
                    break;

                case 5:
                    System.out.println("===  Dijital imza ve Anahtar Yönetimi Testi ===");
                    Wallet senderWallet = new Wallet();
                    Wallet receiverWallet = new Wallet();
                    Transaction transaction = new Transaction(
                            senderWallet.getPublicKeyString(),
                            receiverWallet.getPublicKeyString(),
                            100
                    );
                    transaction.signTransaction(senderWallet.getPrivateKey());
                    boolean isValid = transaction.isSignatureValid(senderWallet.getPublicKey());

                    System.out.println("--- islem Detayları ---");
                    System.out.println(" Gönderen Public Key: " + senderWallet.getPublicKeyString());
                    System.out.println(" Gönderen Private Key: " + senderWallet.getPrivateKeyString());
                    System.out.println(" Alıcı Public Key: " + receiverWallet.getPublicKeyString());
                    System.out.println(" Transfer Miktarı: 100");
                    System.out.println(" imza Geçerliliği: " + (isValid?  "Geçerli" : "Geçersiz"));
                    break;

                case 6:
                    System.out.println("\n=========İstatiksel Gösterim======== ");
                    System.out.println("Kullanıcı İşlem Sayısı");
                    Wallet.getTransactionCounts().forEach((user, count) -> {
                        System.out.println(user + " kullanıcısı " + count + " işlem yaptı");
                    });

                    double totalAmount = blockchain.getChain().stream()
                            .flatMap(block -> block.getTransactions().stream())
                            .filter(temp -> temp.getAmount() > 0)
                            .mapToDouble(Transaction::getAmount)
                            .sum();
                    System.out.println("\nToplam Transfer Edilen Miktar: " +SpecialColor.YELLOW+ totalAmount+SpecialColor.RESET);

                    System.out.println("\nBlok bazlı işlem sayısı");
                    int i = 0;
                    for (Block block : blockchain.getChain()) {
                        System.out.println("Block " + (i++) + ": " + block.getTransactions().size() + " işlem");
                    }
                    break;

                case 0:
                    running = false;
                    System.out.println("Çıkış yapılıyor");
                    System.exit(0);
                    break;

                default:
                    System.out.println("❌ Geçersiz seçim yaptınız tekrar deneyiniz ");
            }
        }

        scanner.close();
    }

    public static void main(String[] args) {
        userChooise();
    }
}