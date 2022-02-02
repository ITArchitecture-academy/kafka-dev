package example.common;

public class BankTransaction {
    public String sender_account;
    public String receiver_account;
    public int amount;
    public boolean isSuspicious;

    @Override
    public String toString() {
        return "BankTransaction{" +
                "sender_account='" + sender_account + '\'' +
                ", receiver_account='" + receiver_account + '\'' +
                ", amount=" + amount +
                ", isSuspicious=" + isSuspicious +
                '}';
    }
}
