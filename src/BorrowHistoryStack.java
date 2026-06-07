import java.util.Stack;

//Stores borrowing history in stack order
public class BorrowHistoryStack {
    private Stack<BorrowHistory> historyStack;

    //Constructor
    public BorrowHistoryStack() {
        historyStack = new Stack<>();
    }

    //Pushes history record
    public void push(BorrowHistory record) {
        historyStack.push(record);
    }

    //Deletes the top history record
    public BorrowHistory pop() {
        if (isEmpty()) {
            System.out.println("> No borrowing history available.");
            return null;
        }
        return historyStack.pop();
    }

    //Checks if stack is empty
    public boolean isEmpty() {
        return historyStack.isEmpty();
    }

    //Displays history in reverse order LIFO
    public void displayHistory() {
        if (isEmpty()) {
            System.out.println("> No borrowing history available.");
            return;
        }
        System.out.println("> Borrowing History:");
        for (int i = historyStack.size() - 1; i >= 0; i--) {
            historyStack.get(i).displayRecord();
        }
    }

    //Removes latest matching record
    public boolean removeTopIfMatches(long isbn) {
        if (isEmpty()) {
            return false;
        }

        BorrowHistory latestRecord = historyStack.peek();

        if (latestRecord.getIsbn() == isbn) {
            pop();
            return true;
        }

        return false;
    }
}
