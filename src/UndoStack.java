import java.util.ArrayList;

//Stores undo actions in stack order
public class UndoStack {
    private ArrayList<UndoAction> undoStack;

    //Constructor
    public UndoStack() {
        undoStack = new ArrayList<>();
    }

    //Pushes undo action to stack
    public void push(UndoAction action) {
        undoStack.add(action);
    }

    //Deletes and returns the top undo action
    public UndoAction pop() {
        if (isEmpty()) {
            return null;
        }
        return undoStack.remove(undoStack.size() - 1);
    }

    //Checks if stack is empty
    public boolean isEmpty() {
        return undoStack.isEmpty();
    }
}
