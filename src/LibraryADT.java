public interface LibraryADT {
    boolean addBook(int isbn, String title, String author);
    boolean removeBook(int isbn);
    Book searchByIsbn(int isbn);
    void searchBooksByTitle(String title);
    void searchBooksByAuthor(String author);
    boolean borrowBook(int isbn, String userID);
    boolean returnBook(int isbn, String userID);
    void viewAllBooks();
    void viewBorrowedBooks(String userID);
}
