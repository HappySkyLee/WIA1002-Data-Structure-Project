public class BorrowHistory {
    private int isbn;
    private String title;
    private String author;

    public BorrowHistory(Book book) {
        this.isbn = book.getIsbn();
        this.title = book.getTitle();
        this.author = book.getAuthor();
    }

    public int getIsbn() {
        return isbn;
    }

    public void displayRecord() {
        System.out.println("ISBN: [" + isbn + "] Title: " + title + " by " + author);
    }
}
