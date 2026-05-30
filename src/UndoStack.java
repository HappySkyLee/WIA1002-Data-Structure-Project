import java.util.ArrayList;

public class UndoStack {
    private ArrayList<String> undoStack;

    public UndoStack() {
        undoStack = new ArrayList<>();
    }

    public void push(String action) {
        undoStack.add(action);
    }

    public String pop() {
        if (isEmpty()) {
            System.out.println("> No actions available to undo.");
            return null;
        }
        return undoStack.remove(undoStack.size() - 1);
    }

    public boolean isEmpty() {
        return undoStack.isEmpty();
    }
}
