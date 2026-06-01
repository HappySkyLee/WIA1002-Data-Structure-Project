//Stores one borrowing history record
public class BorrowHistory {
    private long isbn;
    private String title;
    private String author;

    //Constructor
    public BorrowHistory(Book book) {
        this.isbn = book.getIsbn();
        this.title = book.getTitle();
        this.author = book.getAuthor();
    }

    //Getter
    public long getIsbn() {
        return isbn;
    }

    //Displays history record in format
    public void displayRecord() {
        System.out.println("ISBN: [" + isbn + "] Title: " + title + " by " + author);
    }
}
