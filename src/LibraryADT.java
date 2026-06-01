public interface LibraryADT {

    boolean logIn(String userID, String role);

    boolean addBook(long isbn, String title, String author);
    boolean removeBook(long isbn);

    void searchByIsbn(long isbn);
    void searchBooksByTitle(String title);
    void searchBooksByAuthor(String author);

    boolean borrowBook(long isbn, String userID);
    boolean returnBook(long isbn, String userID);

    void viewAllBooks();
    void viewAllBorrowedBooks();
    void viewBorrowedBooksByUser(String userID);
    void viewBorrowedBookHistory(String userID);
    void viewRegisteredUsers();

    void checkFineStatus(String userID);
    void addFine(String userID, long isbn, int lateDays);
    void reduceFine(String userID, long isbn, double reduceAmount);

    void undoLastAction();
}
