public class UndoAction {
    private String actionType;
    private Book bookSnapshot;
    private String studentId;
    private double amount;
    
    public UndoAction(String actionType, Book bookSnapshot, String studentId, double amount) {
        this.actionType = actionType;
        this.bookSnapshot = bookSnapshot;
        this.studentId = studentId;
        this.amount = amount;
    }

    public String getActionType() {
        return actionType;
    }

    public Book getBookSnapshot() {
        return bookSnapshot;
    }

    public String getStudentId() {
        return studentId;
    }

    public double getAmount() {
        return amount;
    }
}
