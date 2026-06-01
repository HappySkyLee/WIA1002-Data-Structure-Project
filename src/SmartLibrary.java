import java.util.HashMap;
import java.util.ArrayList;

//Manages library books, users, fines and undo actions
public class SmartLibrary implements LibraryADT {
    private BookBST catalogue;
    private HashMap<String, User> users = new HashMap<>();
    private UndoStack undoStack = new UndoStack();

    //Constructor
    public SmartLibrary() {
        this.catalogue = new BookBST();

        // Preload sample data for testing and demo purposes only
        // catalogue.insert(new Book(1047, "Data Structures", "Alex Tan"));
        // catalogue.insert(new Book(1024, "Introduction to Python", "Alex Lee"));
        // catalogue.insert(new Book(1068, "Database Systems", "Alex Tan"));
        // catalogue.insert(new Book(1015, "Java Programming Basics", "Siti Aminah"));
        // catalogue.insert(new Book(1036, "Introduction to Java", "John Tan"));
        // catalogue.insert(new Book(1052, "The Missing Semester", "Bruce Lee"));
        // catalogue.insert(new Book(1079, "Operating Systems", "Maria Wong"));

        // users.put("S001", new User("S001", "Student"));
        // users.put("L001", new User("L001", "Librarian"));
        // System.out.println("> Sample data preloaded.");
    }

    //Login as a user. If user does not exist, register as new user.
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

    //Registers a new user
    private void registerUser(String userID, String role) {
        users.put(userID, new User(userID, role));
        System.out.println("> Welcome, " + userID +  ". You have been registered as a " + role + ".");
    }

    
    
    //Add a book to BST and push undo action to stack
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

    //Removes a book from BST and push undo action to stack
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
            System.out.println("> Book removed successfully: " + removedBook.toString());
            return true;
        } 
        else {
            System.out.println("> Failed to remove book.");
            return false;
        }
        
    }

    //Searches by ISBN and displays book details
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

    //Searches by title and displays book details
    @Override
    public void searchBooksByTitle(String title) {
        if (title == null) {
            System.out.println("> Title cannot be empty.");
            return;
        }

        ArrayList<Book> foundBookByTitle = catalogue.searchByTitle(title);

        displayBooks(foundBookByTitle);
    }

    //Searches by author and displays book details
    @Override
    public void searchBooksByAuthor(String author) {
        if (author == null) {
            System.out.println("> Author cannot be empty.");
            return;
        }

        ArrayList<Book> foundBookByAuthor = catalogue.searchByAuthor(author);

        displayBooks(foundBookByAuthor);
    }

    //Displays book list for ArrayList<Book> results from search by title or author
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

    //Borrows a book if available, updates user's borrow history and push undo action to stack
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

    //Returns a book if it is currently borrowed by the user and push undo action to stack
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

    //Views all books in the library
    @Override
    public void viewAllBooks() {
        catalogue.displayAll();
    }

    //Views all borrowed books for librarian
    @Override
    public void viewAllBorrowedBooks() {
        catalogue.displayBorrowedBooks();
    }

    //Views currently borrowed books by user 
    @Override
    public void viewBorrowedBooksByUser(String userID) {
        User user = users.get(userID);

        if (user == null) {
            System.out.println("> User not found.");
            return;
        }

        catalogue.displayBorrowedBooksByUser(userID);
    }

    //Views borrow history for a user
    @Override
    public void viewBorrowedBookHistory(String userID) {
        User user = users.get(userID);

        if (user == null) {
            System.out.println("> User not found.");
            return;
        }
        user.displayBorrowHistory();
    }

    //Views all registered users for librarian
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

    //Checks fine status for a user
    @Override
    public void checkFineStatus(String userID) {
        User user = users.get(userID);

        if (user == null) {
            System.out.println("> User not found.");
            return;
        }
        user.displayFine();
    }

    //Adds a fine for a user and push undo action to stack
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

    //Reduces a fine for a user and push undo action to stack
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
        } 
        else {
            fineSnapshot = new Book(fineRecord.getIsbn(), fineRecord.getTitle(), "Unknown");
        }

        user.reduceFine(isbn, reduceAmount);
        undoStack.push(new UndoAction("Reduce_Fine", fineSnapshot, userID, reduceAmount));
        System.out.printf("> Fine reduced successfully. Total outstanding fine for user %s: RM %.2f", userID, user.getTotalFine());
        System.out.println();
    }

    //Undo last action
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

    //Undo added book 
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

    //Undo removed book
    private void undoRemoveBook(UndoAction lastAction) {
        Book bookToRestore = lastAction.getBookSnapshot();

        if (catalogue.insert(new Book(bookToRestore.getIsbn(), bookToRestore.getTitle(), bookToRestore.getAuthor()))) {
            System.out.println("> Undo successful: Removed book restored.");
        } 
        else {
            System.out.println("> Undo failed: Could not restore removed book.");
        }
    }
    
    //Undo borrowed book
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
    
    //Undo returned book
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

    //Undo added fine
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

    //Undo reduced fine
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
