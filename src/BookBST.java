import java.util.ArrayList;

public class BookBST {
    private BookNode root;

    private static class BookNode {
        private Book book;
        private BookNode left;
        private BookNode right;

        public BookNode(Book book) {
            this.book = book;
            this.left = null;
            this.right = null;
        }
    }

    public BookBST() {
        this.root = null;
    }


    public boolean insert(Book book) {
        if (searchByIsbn(book.getIsbn()) != null) {
            System.out.println("Book with ISBN " + book.getIsbn() + " already exists.");
            return false;
        }
        
        root = insertRec(root, book);
        return true;
    }

    private BookNode insertRec(BookNode current, Book book) {
        if (current == null) {
            return new BookNode(book);
        }

        if (book.getIsbn() < current.book.getIsbn()) {
            current.left = insertRec(current.left, book);
        } 
        else if (book.getIsbn() > current.book.getIsbn()) {
            current.right = insertRec(current.right, book);
        }

        return current;
    }


    public Book searchByIsbn(long isbn) {
        return searchByIsbnRec(root, isbn);
    }

    private Book searchByIsbnRec(BookNode current, long isbn) {
        if (current == null) {
            return null;
        }

        if (isbn == current.book.getIsbn()) {
            return current.book;
        } 
        else if (isbn < current.book.getIsbn()) {
            return searchByIsbnRec(current.left, isbn);
        } 
        else {
            return searchByIsbnRec(current.right, isbn);
        }
    }

    public Book remove(long isbn) {
        Book targetBook = searchByIsbn(isbn);

        if (targetBook == null) {
            System.out.println("Book with ISBN " + isbn + " not found.");
            return null;
        }

        Book removedBook = new Book(targetBook.getIsbn(),targetBook.getTitle(),targetBook.getAuthor());
        root = removeRec(root, isbn);
        return removedBook;

    }

    private BookNode removeRec(BookNode current, long isbn) {
        if (current == null) {
            return null;
        }
        if (isbn < current.book.getIsbn()) {
            current.left = removeRec(current.left, isbn);
        } 
        else if (isbn > current.book.getIsbn()) {
            current.right = removeRec(current.right, isbn);
        } 
        else {
            
            if (current.left == null && current.right == null) {
                return null;
            }
            
            if (current.left == null) {
                return current.right;
            }

            if (current.right == null) {
                return current.left;
            }

            Book smallestBook = findSmallestBook(current.right);
            current.book = smallestBook;
            current.right = removeRec(current.right, smallestBook.getIsbn());
        }
        return current;
    }

    private Book findSmallestBook(BookNode current) {
        while (current.left != null) {
            current = current.left;
        }
        return current.book;
    }


    public void displayAll() {
        if (root == null) {
            System.out.println("No books in the library.");
            return;
        }

        System.out.println("> All Books sorted by ISBN:");
        displayInOrderRec(root);
    }

    private void displayInOrderRec(BookNode current) {
        if (current != null) {
            displayInOrderRec(current.left);
            System.out.println(current.book);
            displayInOrderRec(current.right);
        }
    }


    public void displayBorrowedBooks() {
        ArrayList<Book> borrowedBooks = new ArrayList<>();
        displayBorrowedBooksRec(root, borrowedBooks);

        if (borrowedBooks.isEmpty()) {
            System.out.println("No books are currently borrowed.");
        } 
        else {
            System.out.println("> Borrowed Books:");
            for (Book book : borrowedBooks) {
                System.out.println(book.toString()); 
            }
        }
    }

    private void displayBorrowedBooksRec(BookNode current, ArrayList<Book> borrowedBooks) {
        if (current != null) {
            displayBorrowedBooksRec(current.left, borrowedBooks);

            if (current.book.isBorrowed()) {
                borrowedBooks.add(current.book);
            }

            displayBorrowedBooksRec(current.right, borrowedBooks);
        }
    }


    public ArrayList<Book> searchByAuthor(String author) {
        ArrayList<Book> booksByAuthor = new ArrayList<>();

        if(author == null || author.trim().isEmpty()) {
            return booksByAuthor;
        }

        String lowerCaseAuthor = author.trim().toLowerCase();

        searchByAuthorRec(root, lowerCaseAuthor, booksByAuthor);
        return booksByAuthor;
    }

    private void searchByAuthorRec(BookNode current, String author, ArrayList<Book> booksByAuthor) {
        if (current != null) {
            searchByAuthorRec(current.left, author, booksByAuthor);

            String currentAuthor = current.book.getAuthor().toLowerCase();

            if (currentAuthor.contains(author)) {
                booksByAuthor.add(current.book);
            }

            searchByAuthorRec(current.right, author, booksByAuthor);
        }
    }
    
    public ArrayList<Book> searchByTitle(String title) {
        ArrayList<Book> booksByTitle = new ArrayList<>();

        if(title == null || title.trim().isEmpty()) {
            return booksByTitle;
        }

        String lowerCaseTitle = title.trim().toLowerCase();

        searchByTitleRec(root, lowerCaseTitle, booksByTitle);
        return booksByTitle;
    }

    private void searchByTitleRec(BookNode current, String title, ArrayList<Book> booksByTitle) {
        if (current != null) {
            searchByTitleRec(current.left, title, booksByTitle);

            String currentTitle = current.book.getTitle().toLowerCase();
            
            if (currentTitle.contains(title)) {
                booksByTitle.add(current.book);
            }

            searchByTitleRec(current.right, title, booksByTitle);
        }
    }
}
