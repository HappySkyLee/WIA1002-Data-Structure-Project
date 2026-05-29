public interface LibraryADT {
    boolean addBook(int isbn, String title, String author);
    boolean removeBook(int isbn);
    Book searchByIsbn(int isbn);
    void searchBooksByTitle(String title);
    void searchBooksByAuthor(String author);
    boolean borrowBook(int isbn, String studentID);
    boolean returnBook(int isbn, String studentID);
    void viewAllBooks();
    void viewBorrowedBooks(String studentID);
}
