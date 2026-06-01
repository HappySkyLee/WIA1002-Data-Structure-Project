//Stores fine details for a book
public class FineRecord {
    private final long isbn;
    private final String title;
    private int lateDays;
    private double remainingAmount;

    //Constructor
    public FineRecord(long isbn, String title, int lateDays, double remainingAmount) {
        this.isbn = isbn;
        this.title = title;
        this.lateDays = lateDays;
        this.remainingAmount = remainingAmount;
    }

    //The Getter methods
    public long getIsbn() {
        return isbn;
    }

    public String getTitle() {
        return title;
    }

    public int getLateDays() {
        return lateDays;
    }

    public double getRemainingAmount() {
        return remainingAmount;
    }

    //Checks unpaid fine
    public boolean hasOutstandingFine() {
        return remainingAmount > 0;
    }

    //Adds fine amount
    public void addFine(int additionalLateDays, double amount) {
        if (additionalLateDays > 0) {
            lateDays += additionalLateDays;
        }

        if (amount > 0) {
            remainingAmount += amount;
        }
    }

    //Reduces fine amount
    public boolean reduceAmount(double amount) {
        if (amount <= 0 || amount > remainingAmount) {
            return false;
        }

        remainingAmount -= amount;
        if (remainingAmount < 0.01) {
            remainingAmount = 0.0;
        }
        return true;
    }

    //Restores fine amount
    public void restoreAmount(double amount) {
        if (amount > 0) {
            remainingAmount += amount;
        }
    }

    //Reverses added fine by reducing the fine amount and late days.
    public void reverseAddedFine(int lateDaysToRemove, double amountToRemove) {
        if (lateDaysToRemove > 0) {
            lateDays -= lateDaysToRemove;
            if (lateDays < 0) {
                lateDays = 0;
            }
        }

        if (amountToRemove > 0) {
            remainingAmount -= amountToRemove;
            if (remainingAmount < 0.01) {
                remainingAmount = 0.0;
            }
        }
    }
}
