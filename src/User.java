import java.util.ArrayList;

public class User {
    private String userID;
    private String userRole;
    private ArrayList<FineRecord> fineRecords;
    private BorrowHistoryStack borrowHistory;

    public User(String userID, String userRole) {
        this.userID = userID;
        this.userRole = userRole;
        this.fineRecords = new ArrayList<>();
        this.borrowHistory = new BorrowHistoryStack();
    }

    public String getUserID() {
        return userID;
    }

    public String getUserRole() {
        return userRole;
    }

    public double getTotalFine() {
        double totalFine = 0.0;

        for (FineRecord fineRecord : fineRecords) {
            totalFine += fineRecord.getRemainingAmount();
        }

        return totalFine;
    }

    public FineRecord findFineRecordByIsbn(long isbn) {
        for (FineRecord fineRecord : fineRecords) {
            if (fineRecord.getIsbn() == isbn) {
                return fineRecord;
            }
        }

        return null;
    }

    public FineRecord findOutstandingFineByIsbn(long isbn) {
        FineRecord fineRecord = findFineRecordByIsbn(isbn);

        if (fineRecord != null && fineRecord.hasOutstandingFine()) {
            return fineRecord;
        }

        return null;
    }

    public void addFine(long isbn, String title, int lateDays, double amount) {
        FineRecord fineRecord = findFineRecordByIsbn(isbn);

        if (fineRecord == null) {
            fineRecords.add(new FineRecord(isbn, title, lateDays, amount));
        } else {
            fineRecord.addFine(lateDays, amount);
        }
    }

    public boolean reduceFine(long isbn, double amount) {
        FineRecord fineRecord = findOutstandingFineByIsbn(isbn);

        if (fineRecord == null) {
            return false;
        }

        return fineRecord.reduceAmount(amount);
    }

    public boolean undoAddedFine(long isbn, int lateDays, double amount) {
        FineRecord fineRecord = findFineRecordByIsbn(isbn);

        if (fineRecord == null) {
            return false;
        }

        fineRecord.reverseAddedFine(lateDays, amount);
        return true;
    }

    public boolean restoreReducedFine(long isbn, double amount) {
        FineRecord fineRecord = findFineRecordByIsbn(isbn);

        if (fineRecord == null) {
            return false;
        }

        fineRecord.restoreAmount(amount);
        return true;
    }

    public void pushBorrowHistory(Book book) {
        borrowHistory.push(new BorrowHistory(book));
    }

    public void displayBorrowHistory() {
        System.out.println("Borrow History for User: " + userID);
        System.out.println("Role: " + userRole);
        borrowHistory.displayHistory();
    }

    public void displayFine() {
        System.out.println("User: " + userID);
        System.out.println("Role: " + userRole);

        if (getTotalFine() <= 0) {
            System.out.println("> No outstanding fines.");
            System.out.printf("Total Outstanding Fine: RM %.2f%n", getTotalFine());
            return;
        }

        System.out.println("> Outstanding Fine Records:");
        for (FineRecord fineRecord : fineRecords) {
            if (fineRecord.hasOutstandingFine()) {
                System.out.printf("ISBN: [%d] Title: %s | Late Days: %d | Outstanding Fine: RM %.2f%n",fineRecord.getIsbn(),fineRecord.getTitle(),fineRecord.getLateDays(),fineRecord.getRemainingAmount());
            }
        }
        System.out.printf("Total Outstanding Fine: RM %.2f%n", getTotalFine());
    }

    public boolean removeLatestBorrowHistoryIfMatches(long isbn) {
        return borrowHistory.removeTopIfMatches(isbn);
    }
}
