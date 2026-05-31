public class FineRecord {
    private final long isbn;
    private final String title;
    private int lateDays;
    private double remainingAmount;

    public FineRecord(long isbn, String title, int lateDays, double remainingAmount) {
        this.isbn = isbn;
        this.title = title;
        this.lateDays = lateDays;
        this.remainingAmount = remainingAmount;
    }

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

    public boolean hasOutstandingFine() {
        return remainingAmount > 0;
    }

    public void addFine(int additionalLateDays, double amount) {
        if (additionalLateDays > 0) {
            lateDays += additionalLateDays;
        }

        if (amount > 0) {
            remainingAmount += amount;
        }
    }

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

    public void restoreAmount(double amount) {
        if (amount > 0) {
            remainingAmount += amount;
        }
    }

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
