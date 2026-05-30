public interface LibraryADT {
    boolean logIn(String userID, String role);
    boolean addBook(int isbn, String title, String author);
    boolean removeBook(int isbn);
    void searchByIsbn(int isbn);
    void searchBooksByTitle(String title);
    void searchBooksByAuthor(String author);
    boolean borrowBook(int isbn, String userID);
    boolean returnBook(int isbn, String userID);
    void viewAllBooks();
    void viewAllBorrowedBooks();
    void viewOwnBorrowedBooks(String userID);
    void checkFineStatus(String userID);
    void addFine(String userID, int lateDays);
    void reduceFine(String userID, double reduceAmount);
    void undoLastAction();
}
