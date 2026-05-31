import java.util.HashMap;
import java.util.ArrayList;

public class SmartLibrary implements LibraryADT {
    private BookBST catalogue;
    private HashMap<String, User> users = new HashMap<>();
    private UndoStack undoStack = new UndoStack();

    public SmartLibrary() {
        this.catalogue = new BookBST();
    }

    @Override
    public boolean logIn(String userID, String role) {
        if(userID == null || userID.trim().isEmpty()) {
            System.out.println("> User ID cannot be empty.");
            return false;
        }
        if(role == null || role.trim().isEmpty()) {
            System.out.println("> Role cannot be empty.");
            return false;
        }

        if(!role.equalsIgnoreCase("Librarian") && !role.equalsIgnoreCase("Student")) {
            System.out.println("> Invalid role. Please enter 'Librarian' or 'Student'.");
            return false;
        }

        User existingUser = users.get(userID);

        if(existingUser == null){
            registerUser(userID, role);
            return true;
        } 
        if(!existingUser.getUserRole().equalsIgnoreCase(role)) {
            System.out.println("> User " + userID + " is already logged in with a different role.");
            return false;
        }

        System.out.println("> Welcome back, " + userID +  ". You are logged in as a " + role + ".");
        return true;
    }

    public void registerUser(String userID, String role) {
        users.put(userID, new User(userID, role));
        System.out.println("> Welcome, " + userID +  ". You have been registered as a " + role + ".");
    }

    
    

    @Override
    public boolean addBook(long isbn, String title, String author) {
        if (isbn <= 0 || title == null || author == null|| title.trim().isEmpty() || author.trim().isEmpty()) {
            System.out.println("> Invalid book details. Please provide valid ISBN, title, and author.");
            return false;
        }

        Book newBook = new Book(isbn, title, author);

        if (catalogue.insert(newBook)) {
            undoStack.push(new UndoAction("Add_Book", new Book(newBook.getIsbn(), newBook.getTitle(), newBook.getAuthor()), null, 0.0));
            System.out.println("> Book added successfully: " + newBook.toString());
            return true;
        } 
        else {
            System.out.println("> Failed to add book.");
            return false;
        }
    }

    @Override
    public boolean removeBook(long isbn) {
        Book removedBook = catalogue.searchByIsbn(isbn);

        if (removedBook == null) {
            System.out.println("> Book with ISBN " + isbn + " not found.");
            return false;
        }
        if(removedBook.isBorrowed()) {
            System.out.println("> Cannot remove book. It is currently borrowed by " + removedBook.getBorrowBy() + ".");
            return false;
        }

        if (catalogue.remove(isbn) != null) {
            undoStack.push(new UndoAction("Remove_Book", new Book(removedBook.getIsbn(), removedBook.getTitle(), removedBook.getAuthor()), null, 0.0));
            System.out.println("> Book removed successfully: ");
            return true;
        } 
        else {
            System.out.println("> Failed to remove book.");
            return false;
        }
        
    }

    @Override
    public void searchByIsbn(long isbn) {
        Book foundBookByIsbn = catalogue.searchByIsbn(isbn);

        if (foundBookByIsbn != null) {
            System.out.println("> Book found: " + foundBookByIsbn.toString());
        } 
        else {
            System.out.println("> Book with ISBN " + isbn + " not found.");
        }
    }

    @Override
    public void searchBooksByTitle(String title) {
        if (title == null) {
            System.out.println("> Title cannot be empty.");
            return;
        }

        ArrayList<Book> foundBookByTitle = catalogue.searchByTitle(title);

        displayBooks(foundBookByTitle);
    }

    @Override
    public void searchBooksByAuthor(String author) {
        if (author == null) {
            System.out.println("> Author cannot be empty.");
            return;
        }

        ArrayList<Book> foundBookByAuthor = catalogue.searchByAuthor(author);

        displayBooks(foundBookByAuthor);
    }

    private void displayBooks(ArrayList<Book> books) {
        if (books.isEmpty()) {
            System.out.println("> No books found.");
            return;
        }

        System.out.println("> Books found:");
        for (Book book : books) {
            System.out.println(book.toString());
        }
    }


    @Override
    public boolean borrowBook(long isbn, String userID) {
        User user = users.get(userID);

        if (user == null) {
            System.out.println("> User not found.");
            return false;
        }

        Book borrowedBook = catalogue.searchByIsbn(isbn);

        if (borrowedBook == null) {
            System.out.println("> Book with ISBN " + isbn + " not found.");
            return false;
        }
        else if (borrowedBook.isBorrowed()) {
            System.out.println("> Book is currently borrowed by " + borrowedBook.getBorrowBy() + ".");
            return false;
        }
        else {
            borrowedBook.borrowBook(userID);
            user.pushBorrowHistory(borrowedBook);
            undoStack.push(new UndoAction("Borrow_Book", new Book(borrowedBook.getIsbn(), borrowedBook.getTitle(), borrowedBook.getAuthor()), userID, 0.0));
            System.out.println("> Book borrowed successfully: " + borrowedBook.toString());
            return true;
        }
        
    }

    @Override
    public boolean returnBook(long isbn, String userID) {
        User user = users.get(userID);

        if (user == null) {
            System.out.println("> User not found.");
            return false;
        }

        Book returnedBook = catalogue.searchByIsbn(isbn);

        if (returnedBook == null) {
            System.out.println("> Book with ISBN " + isbn + " not found.");
            return false;
        }
        else if (!returnedBook.isBorrowed()) {
            System.out.println("> Book is not currently borrowed.");
            return false;
        }
        else if (!userID.equals(returnedBook.getBorrowBy())) {
            System.out.println("> Book is not currently borrowed by you.");
            return false;
        }
        else {
            Book snapshotBook = new Book(returnedBook.getIsbn(), returnedBook.getTitle(), returnedBook.getAuthor());

            returnedBook.returnBook();
            undoStack.push(new UndoAction("Return_Book", snapshotBook, userID, 0.0));
            System.out.println("> Book returned successfully: " + returnedBook.toString());
            return true;
        }
    }

    @Override
    public void viewAllBooks() {
        catalogue.displayAll();
    }

    @Override
    public void viewAllBorrowedBooks() {
        catalogue.displayBorrowedBooks();
    }

    @Override
    public void viewBorrowedBooksByUser(String userID) {
        User user = users.get(userID);

        if (user == null) {
            System.out.println("> User not found.");
            return;
        }

        catalogue.displayBorrowedBooksByUser(userID);
    }

    @Override
    public void viewBorrowedBookHistory(String userID) {
        User user = users.get(userID);

        if (user == null) {
            System.out.println("> User not found.");
            return;
        }
        user.displayBorrowHistory();
    }

    @Override
    public void viewRegisteredUsers() {
        if (users.isEmpty()) {
            System.out.println("> No registered users found.");
            return;
        }
        
        System.out.println("> Registered Users:");
        for (User user : users.values()) {
            System.out.println("User ID: " + user.getUserID() + " | Role: " + user.getUserRole());
        }
    }

    @Override
    public void checkFineStatus(String userID) {
        User user = users.get(userID);

        if (user == null) {
            System.out.println("> User not found.");
            return;
        }
        user.displayFine();
    }

    @Override
    public void addFine(String userID, long isbn, int lateDays) {
        User user = users.get(userID);

        if (user == null) {
            System.out.println("> User not found.");
            return;
        }

        Book finedBook = catalogue.searchByIsbn(isbn);

        if (finedBook == null) {
            System.out.println("> Book with ISBN " + isbn + " not found.");
            return;
        }

        if (lateDays <= 0) {
            System.out.println("> Late days must be greater than zero.");
            return;
        }
        
        double fineAmount = lateDays * 1.0;
        user.addFine(finedBook.getIsbn(), finedBook.getTitle(), lateDays, fineAmount);
        undoStack.push(new UndoAction("Add_Fine", new Book(finedBook.getIsbn(), finedBook.getTitle(), finedBook.getAuthor()), userID, fineAmount, lateDays));
        System.out.printf("> Fine added successfully. Total outstanding fine for user %s: RM %.2f", userID, user.getTotalFine());
        System.out.println();
    }

    @Override
    public void reduceFine(String userID, long isbn, double reduceAmount) {
        User user = users.get(userID);

        if (user == null) {
            System.out.println("> User not found.");
            return;
        }

        if (reduceAmount <= 0) {
            System.out.println("> Amount must be greater than zero.");
            return;
        }

        FineRecord fineRecord = user.findOutstandingFineByIsbn(isbn);

        if (fineRecord == null) {
            if (user.findFineRecordByIsbn(isbn) == null && catalogue.searchByIsbn(isbn) == null) {
                System.out.println("> Book with ISBN " + isbn + " not found.");
            } else {
                System.out.println("> No outstanding fine found for ISBN " + isbn + ".");
            }
            return;
        }

        if (reduceAmount > fineRecord.getRemainingAmount()) {
            System.out.printf("> Reduction amount exceeds outstanding fine. Outstanding fine for ISBN %d: RM %.2f%n", isbn, fineRecord.getRemainingAmount());
            return;
        }

        Book catalogueBook = catalogue.searchByIsbn(isbn);
        Book fineSnapshot;

        if (catalogueBook != null) {
            fineSnapshot = new Book(catalogueBook.getIsbn(), catalogueBook.getTitle(), catalogueBook.getAuthor());
        } else {
            fineSnapshot = new Book(fineRecord.getIsbn(), fineRecord.getTitle(), "Unknown");
        }

        user.reduceFine(isbn, reduceAmount);
        undoStack.push(new UndoAction("Reduce_Fine", fineSnapshot, userID, reduceAmount));
        System.out.printf("> Fine reduced successfully. Total outstanding fine for user %s: RM %.2f", userID, user.getTotalFine());
        System.out.println();
    }

    @Override
    public void undoLastAction() {
        UndoAction lastAction = undoStack.pop();

        if (lastAction == null) {
            System.out.println("> No actions to undo.");
            return;
        }

        switch (lastAction.getActionType()) {
            case "Add_Book":
                undoAddBook(lastAction);
                break;

            case "Remove_Book":
                undoRemoveBook(lastAction);
                break;

            case "Borrow_Book":
                undoBorrowBook(lastAction);
                break;

            case "Return_Book":
                undoReturnBook(lastAction);
                break;

            case "Add_Fine":
                undoAddFine(lastAction);
                break;

            case "Reduce_Fine":
                undoReduceFine(lastAction);
                break;

            default:
                System.out.println("> Unknown action type. Cannot undo.");
        }


    }

    private void undoAddBook(UndoAction lastAction) {
        long isbn = lastAction.getBookSnapshot().getIsbn();
        Book addedBook = catalogue.searchByIsbn(isbn);

        if(addedBook == null) {
            System.out.println("> Book not found. Cannot undo add book action.");
            return;
        }

        if(addedBook.isBorrowed()) {
            System.out.println("> Undo failed: Book is currently borrowed by " + addedBook.getBorrowBy() + ".");
            return;
        }

        catalogue.remove(isbn);
        System.out.println("> Undo successful: Added book removed.");
    }

    private void undoRemoveBook(UndoAction lastAction) {
        Book bookToRestore = lastAction.getBookSnapshot();

        if (catalogue.insert(new Book(bookToRestore.getIsbn(), bookToRestore.getTitle(), bookToRestore.getAuthor()))) {
            System.out.println("> Undo successful: Removed book restored.");
        } 
        else {
            System.out.println("> Undo failed: Could not restore removed book.");
        }
    }
    
    private void undoBorrowBook(UndoAction lastAction) {
        long isbn = lastAction.getBookSnapshot().getIsbn();
        Book borrowedBook = catalogue.searchByIsbn(isbn);
        User user = users.get(lastAction.getUserID());

        if (borrowedBook == null) {
            System.out.println("> Book not found. Cannot undo borrow book action.");
            return;
        }

        if (!borrowedBook.isBorrowed() || !borrowedBook.getBorrowBy().equals(lastAction.getUserID())) {
            System.out.println("> Undo failed: Book is not currently borrowed by the user.");
            return;
        }

        boolean historyRemoved = user.removeLatestBorrowHistoryIfMatches(isbn);

        if(!historyRemoved) {
            System.out.println("> Undo failed: Borrow history mismatch.");
        }

        borrowedBook.returnBook();
        System.out.println("> Undo successful: Book borrow reversed.");
    }
    
    private void undoReturnBook(UndoAction lastAction) {
        long isbn = lastAction.getBookSnapshot().getIsbn();
        Book returnedBook = catalogue.searchByIsbn(isbn);

        if (returnedBook == null) {
            System.out.println("> Book not found.");
            return;
        }

        if (returnedBook.isBorrowed()) {
            System.out.println("> Undo failed: Book is currently borrowed by " + returnedBook.getBorrowBy() + ".");
            return;
        }

        returnedBook.borrowBook(lastAction.getUserID());
        System.out.println("> Undo successful: Book return reversed.");
    }

    private void undoAddFine(UndoAction lastAction) {
        User user = users.get(lastAction.getUserID());

        if (user == null) {
            System.out.println("> User not found.");
            return;
        }

        if (lastAction.getBookSnapshot() == null) {
            System.out.println("> Undo failed: Fine book record not found.");
            return;
        }

        boolean reversed = user.undoAddedFine(lastAction.getBookSnapshot().getIsbn(), lastAction.getLateDays(), lastAction.getAmount());

        if (reversed) {
            System.out.println("> Undo successful: Fine addition reversed.");
        } else {
            System.out.println("> Undo failed: Fine record not found.");
        }
    }

    private void undoReduceFine(UndoAction lastAction) {
        User user = users.get(lastAction.getUserID());

        if (user == null) {
            System.out.println("> User not found.");
            return;
        }

        if (lastAction.getBookSnapshot() == null) {
            System.out.println("> Undo failed: Fine book record not found.");
            return;
        }

        boolean restored = user.restoreReducedFine(lastAction.getBookSnapshot().getIsbn(), lastAction.getAmount());

        if (restored) {
            System.out.println("> Undo successful: Fine reduction reversed.");
        } else {
            System.out.println("> Undo failed: Fine record not found.");
        }
    }
}
