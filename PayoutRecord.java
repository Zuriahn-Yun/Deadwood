/*
 * Data Class to keep track of payouts
 */
public class PayoutRecord {
    public final String playerName;
    public final int amountGained;
    public final boolean isStar;

    public PayoutRecord(String name, int amount, boolean isStar) {
        this.playerName = name;
        this.amountGained = amount;
        this.isStar = isStar;
    }
}