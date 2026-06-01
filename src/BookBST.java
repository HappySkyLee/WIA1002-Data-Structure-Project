import java.util.ArrayList;

//Stores books title and author indexed by ISBN in a binary search tree
public class BookBST {
    private BookNode root;

    //Tree node
    private static class BookNode {
        private Book book;
        private BookNode left;
        private BookNode right;

        //Constructor
        public BookNode(Book book) {
            this.book = book;
            this.left = null;
            this.right = null;
        }
    }

    //Constructor
    public BookBST() {
        this.root = null;
    }


    //Inserts a book
    public boolean insert(Book book) {
        if (searchByIsbn(book.getIsbn()) != null) {
            System.out.println("Book with ISBN " + book.getIsbn() + " already exists.");
            return false;
        }
        
        root = insertRec(root, book);
        return true;
    }

    //Inserts recursively
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


    //Searches by ISBN
    public Book searchByIsbn(long isbn) {
        return searchByIsbnRec(root, isbn);
    }

    //Searches recursively
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

    //Removes a book
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

    //Removes recursively
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
            //No children
            if (current.left == null && current.right == null) {
                return null;
            }
            
            //One child
            if (current.left == null) {
                return current.right;
            }
            if (current.right == null) {
                return current.left;
            }

            //Two children
            Book smallestBook = findSmallestBook(current.right);
            current.book = smallestBook;
            current.right = removeRec(current.right, smallestBook.getIsbn());
        }
        return current;
    }

    //Find the smallest book in right subtree to replace the removed node 
    private Book findSmallestBook(BookNode current) {
        while (current.left != null) {
            current = current.left;
        }
        return current.book;
    }


    //Displays all books
    public void displayAll() {
        if (root == null) {
            System.out.println("No books in the library.");
            return;
        }

        System.out.println("> All Books sorted by ISBN:");
        displayInOrderRec(root);
    }

    //Displays in order
    private void displayInOrderRec(BookNode current) {
        if (current != null) {
            displayInOrderRec(current.left);
            System.out.println(current.book);
            displayInOrderRec(current.right);
        }
    }

    //Displays borrowed books
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

    //Displays borrowed books recursively
    private void displayBorrowedBooksRec(BookNode current, ArrayList<Book> borrowedBooks) {
        if (current != null) {
            displayBorrowedBooksRec(current.left, borrowedBooks);

            if (current.book.isBorrowed()) {
                borrowedBooks.add(current.book);
            }

            displayBorrowedBooksRec(current.right, borrowedBooks);
        }
    }

    //Displays borrowed books by user
    public void displayBorrowedBooksByUser(String userID) {
        ArrayList<Book> borrowedBooks = new ArrayList<>();
        displayBorrowedBooksByUserRec(root, userID, borrowedBooks);

        if (borrowedBooks.isEmpty()) {
            System.out.println("> No books are currently borrowed by user " + userID + ".");
        }
        else {
            System.out.println("> Books currently borrowed by user " + userID + ":");
            for (Book book : borrowedBooks) {
                System.out.println(book.toString());
            }
        }
    }

    //Displays borrowed books by user recursively
    private void displayBorrowedBooksByUserRec(BookNode current, String userID, ArrayList<Book> borrowedBooks) {
        if (current != null) {
            displayBorrowedBooksByUserRec(current.left, userID, borrowedBooks);

            if (current.book.isBorrowed() && userID.equals(current.book.getBorrowBy())) {
                borrowedBooks.add(current.book);
            }

            displayBorrowedBooksByUserRec(current.right, userID, borrowedBooks);
        }
    }


    //Searches by author
    public ArrayList<Book> searchByAuthor(String author) {
        ArrayList<Book> booksByAuthor = new ArrayList<>();

        if(author == null || author.trim().isEmpty()) {
            return booksByAuthor;
        }

        String lowerCaseAuthor = author.trim().toLowerCase();

        searchByAuthorRec(root, lowerCaseAuthor, booksByAuthor);
        return booksByAuthor;
    }

    //Searches authors recursively
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
    
    //Searches by title
    public ArrayList<Book> searchByTitle(String title) {
        ArrayList<Book> booksByTitle = new ArrayList<>();

        if(title == null || title.trim().isEmpty()) {
            return booksByTitle;
        }

        String lowerCaseTitle = title.trim().toLowerCase();

        searchByTitleRec(root, lowerCaseTitle, booksByTitle);
        return booksByTitle;
    }

    //Searches titles recursively
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
