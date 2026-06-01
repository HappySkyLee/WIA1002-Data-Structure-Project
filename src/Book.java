//Stores book details and borrow status
public class Book {
    private final long isbn;
    private final String title;
    private final String author;
    private boolean borrowed;
    private String borrowBy;

    //Constructor
    public Book(long isbn, String title, String author){
        this.isbn = isbn;
        this.title = title;
        this.author = author;
        this.borrowed = false;
        this.borrowBy = null;
    }

    //The Getter methods
    public long getIsbn(){
        return isbn;
    }

    public String getTitle(){
        return title;
    }

    public String getAuthor(){
        return author;
    }  

    public boolean isBorrowed(){
        return borrowed;
    }

    public String getBorrowBy(){
        return borrowBy;
    }

    //Marks book borrowed
    public void borrowBook(String userID){
            this.borrowed = true;
            this.borrowBy = userID;
    }

    //Marks book returned
    public void returnBook() {
            this.borrowed = false;
            this.borrowBy = null;
    }

    //Formats book details for display
    @Override
    public String toString() {
        return "ISBN: [" + isbn + "] Title: " + title + " by " + author + (borrowed ? " | (Borrowed by: " + borrowBy + ")" : " | (Available)");
    }
}
