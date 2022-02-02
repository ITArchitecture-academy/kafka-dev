package example.common;

import java.util.List;
import java.util.Random;
import java.util.function.Supplier;

public class BankTransactionSupplier implements Supplier<BankTransaction> {
    List<String> RANDOM_NAMES = List.of("Alice", "Bob", "Charlie", "Dave", "Eve", "Francis");
    private final Random rand = new Random();

    private final int msgsPerSec;
    private int msgCount;

    public BankTransactionSupplier(int msgsPerSec) {
        this.msgsPerSec = msgsPerSec;
        msgCount = 0;
    }

    @Override
    public BankTransaction get() {
        BankTransaction transaction = new BankTransaction();
        transaction.sender_account = RANDOM_NAMES.get(rand.nextInt(RANDOM_NAMES.size()));
        transaction.receiver_account = RANDOM_NAMES.get(rand.nextInt(RANDOM_NAMES.size()));
        transaction.amount = rand.nextInt(100) * (rand.nextInt(10) + 1);
        transaction.isSuspicious = rand.nextDouble() < 0.30;
        if (msgCount >= msgsPerSec && msgsPerSec != -1) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            msgCount = 0;
        }
        msgCount++;
        return transaction;
    }
}
