import java.util.ArrayList;

public class UndoStack {
    private ArrayList<UndoAction> undoStack;

    public UndoStack() {
        undoStack = new ArrayList<>();
    }

    public void push(UndoAction action) {
        undoStack.add(action);
    }

    public UndoAction pop() {
        if (isEmpty()) {
            return null;
        }
        return undoStack.remove(undoStack.size() - 1);
    }

    public boolean isEmpty() {
        return undoStack.isEmpty();
    }
}
