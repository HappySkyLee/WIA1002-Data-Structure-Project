import java.util.ArrayList;

//Stores user details, fines and borrowing history
public class User {
    private String userID;
    private String userRole;
    private ArrayList<FineRecord> fineRecords;
    private BorrowHistoryStack borrowHistory;

    //Constructor
    public User(String userID, String userRole) {
        this.userID = userID;
        this.userRole = userRole;
        this.fineRecords = new ArrayList<>();
        this.borrowHistory = new BorrowHistoryStack();
    }

    //The Getter methods
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

    //Finds a fine record by ISBN
    public FineRecord findFineRecordByIsbn(long isbn) {
        for (FineRecord fineRecord : fineRecords) {
            if (fineRecord.getIsbn() == isbn) {
                return fineRecord;
            }
        }

        return null;
    }

    //Finds unpaid fine record by ISBN.
    public FineRecord findOutstandingFineByIsbn(long isbn) {
        FineRecord fineRecord = findFineRecordByIsbn(isbn);

        if (fineRecord != null && fineRecord.hasOutstandingFine()) {
            return fineRecord;
        }

        return null;
    }

    //Adds a fine record or updates existing one for a specific ISBN
    public void addFine(long isbn, String title, int lateDays, double amount) {
        FineRecord fineRecord = findFineRecordByIsbn(isbn);

        if (fineRecord == null) {
            fineRecords.add(new FineRecord(isbn, title, lateDays, amount));
        } else {
            fineRecord.addFine(lateDays, amount);
        }
    }

    //Reduces a fine amount for a specific ISBN
    public boolean reduceFine(long isbn, double amount) {
        FineRecord fineRecord = findOutstandingFineByIsbn(isbn);

        if (fineRecord == null) {
            return false;
        }

        return fineRecord.reduceAmount(amount);
    }

    //Undo added fine by reducing the fine amount and late days
    public boolean undoAddedFine(long isbn, int lateDays, double amount) {
        FineRecord fineRecord = findFineRecordByIsbn(isbn);

        if (fineRecord == null) {
            return false;
        }

        fineRecord.reverseAddedFine(lateDays, amount);
        return true;
    }

    //Restores reduced fine amount by increasing the fine amount
    public boolean restoreReducedFine(long isbn, double amount) {
        FineRecord fineRecord = findFineRecordByIsbn(isbn);

        if (fineRecord == null) {
            return false;
        }

        fineRecord.restoreAmount(amount);
        return true;
    }

    //Adds borrow history record for a borrowed book
    public void pushBorrowHistory(Book book) {
        borrowHistory.push(new BorrowHistory(book));
    }

    //Displays borrow history 
    public void displayBorrowHistory() {
        System.out.println("Borrow History for User: " + userID);
        System.out.println("Role: " + userRole);
        borrowHistory.displayHistory();
    }

    //Displays fine details
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

    //Removes latest matching history for Undo return book action
    public boolean removeLatestBorrowHistoryIfMatches(long isbn) {
        return borrowHistory.removeTopIfMatches(isbn);
    }
}
