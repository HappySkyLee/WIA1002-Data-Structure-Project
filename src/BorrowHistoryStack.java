import java.util.ArrayList;

public class BorrowHistoryStack {
    private ArrayList<BorrowHistory> historyStack;

    public BorrowHistoryStack() {
        historyStack = new ArrayList<>();
    }

    public void push(BorrowHistory record) {
        historyStack.add(record);
    }

    public BorrowHistory pop() {
        if (isEmpty()) {
            System.out.println("> No borrowing history available.");
            return null;
        }
        return historyStack.remove(historyStack.size() - 1);
    }

    public boolean isEmpty() {
        return historyStack.isEmpty();
    }

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

    public boolean removeTopIfMatches(int isbn) {
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
