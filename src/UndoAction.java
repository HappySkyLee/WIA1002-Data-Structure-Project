public class UndoAction {
    private String actionType;
    private Book bookSnapshot;
    private String userID;
    private double amount;
    
    public UndoAction(String actionType, Book bookSnapshot, String userID, double amount) {
        this.actionType = actionType;
        this.bookSnapshot = bookSnapshot;
        this.userID = userID;
        this.amount = amount;
    }

    public String getActionType() {
        return actionType;
    }

    public Book getBookSnapshot() {
        return bookSnapshot;
    }

    public String getUserID() {
        return userID;
    }

    public double getAmount() {
        return amount;
    }
}
