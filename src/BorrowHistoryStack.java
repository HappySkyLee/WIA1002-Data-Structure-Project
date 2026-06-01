import java.util.ArrayList;

//Stores borrowing history in stack order
public class BorrowHistoryStack {
    private ArrayList<BorrowHistory> historyStack;

    //Constructor
    public BorrowHistoryStack() {
        historyStack = new ArrayList<>();
    }

    //Pushes history record
    public void push(BorrowHistory record) {
        historyStack.add(record);
    }

    //Deletes the top history record
    public BorrowHistory pop() {
        if (isEmpty()) {
            System.out.println("> No borrowing history available.");
            return null;
        }
        return historyStack.remove(historyStack.size() - 1);
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

        BorrowHistory latestRecord = historyStack.get(historyStack.size() - 1);

        if (latestRecord.getIsbn() == isbn) {
            pop();
            return true;
        }

        return false;
    }
}
