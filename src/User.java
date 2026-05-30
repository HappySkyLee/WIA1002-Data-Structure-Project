public class User {
    private String userID;
    private String userRole;
    private double fine;
    private BorrowHistoryStack borrowHistory;

    public User(String userID, String userRole) {
        this.userID = userID;
        this.userRole = userRole;
        this.fine = 0.0;
        this.borrowHistory = new BorrowHistoryStack();
    }

    public String getUserID() {
        return userID;
    }

    public String getUserRole() {
        return userRole;
    }

    public double getFine() {
        return fine;
    }

    public void addFine(double amount) {
        if(amount > 0) {
            this.fine += amount;
        }
    }

    public void reduceFine(double amount) {
        if(amount > 0 && amount <= fine) {
            this.fine -= amount;
        }
        else {
            fine = 0.0; 
        }
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
        System.out.println("Current fine for user: " + userID);
        System.out.println("Role: " + userRole);
        System.out.printf("Fine: RM %.2f%n", fine);
    }

    public boolean removeLatestBorrowHistoryIfMatches(int isbn) {
        return borrowHistory.removeTopIfMatches(isbn);
    }
}
