//Stores details for one undo action
public class UndoAction {
    private String actionType;
    private Book bookSnapshot;
    private String userID;
    private double amount;
    private int lateDays;
    
    //Constructor without late days for other actions
    public UndoAction(String actionType, Book bookSnapshot, String userID, double amount) {
        this(actionType, bookSnapshot, userID, amount, 0);
    }

    //Constructor with late days for AddFine action
    public UndoAction(String actionType, Book bookSnapshot, String userID, double amount, int lateDays) {
        this.actionType = actionType;
        this.bookSnapshot = bookSnapshot;
        this.userID = userID;
        this.amount = amount;
        this.lateDays = lateDays;
    }

    //The Getter methods
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

    public int getLateDays() {
        return lateDays;
    }
}
