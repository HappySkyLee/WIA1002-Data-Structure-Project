public class Book {
    private final int isbn;
    private final String title;
    private final String author;
    private boolean borrowed;
    private String borrowBy;

    public Book(int isbn, String title, String author){
        this.isbn = isbn;
        this.title = title;
        this.author = author;
        this.borrowed = false;
        this.borrowBy = null;
    }

    public int getIsbn(){
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

    public void borrowBook(String StudentID){
            this.borrowed = true;
            this.borrowBy = StudentID;
    }

    public void returnBook() {
            this.borrowed = false;
            this.borrowBy = null;
    }

    @Override
    public String toString() {
        return "ISBN: [" + isbn + "] Title: " + title + " by " + author + (borrowed ? " | (Borrowed by: " + borrowBy + ")" : " (Available)");
    }
}
