import java.util.Scanner;


public class SmartLibrary implements LibraryADT {
    private BookBST catalogue;
    Scanner scanner = new Scanner(System.in);

    public void runMenu(boolean running){
        logInMenu(running);
    } //?


    public void logInMenu(boolean running){
        System.out.println("> Welcome to the Smart Library!");
        System.out.println("> Are you a Librarian or a Student? Please select: ");
        System.out.println("1. Librarian");
        System.out.println("2. Student");
        System.out.println("3. Exit");

        int role = scanner.nextInt();

        switch(role){
            case 1:
                librarianMenu();
                break;
            case 2:
                studentMenu();
                break;
            case 3:
                System.out.println("> Exiting...");
                running = false;
                break;
            default:
                System.out.println("> Invalid selection. Please try again.");
        }
    }

    public void librarianMenu(){
        System.out.println(">> Librarian Menu:");
        System.out.println(">> Admin Options:");
        System.out.println("1. Add Book");
        System.out.println("2. Remove Book");
        System.out.println("3. View All Borrowed Books");
        System.out.println("4. Fine Management");
        System.out.println("5. Undo Last Action");
        System.out.println();
        System.out.println(">> General Options:");
        System.out.println("6. View All Books");
        System.out.println("7. Search Books");
        System.out.println("8. Borrow Book");
        System.out.println("9. Return Book");
        System.out.println("10. View My Borrowing History");
        System.out.println("11. Check Fine Status");
        System.out.println("12. Exit");

        int librarianChoice = scanner.nextInt();

        switch(librarianChoice){
            case 1:
                break;
            case 2:
                break;
            case 3:
                break;
            case 4:
                break;
            case 5:
                break;
            case 6:
                break;
            case 7:
                break;
            case 8:
                break;
            case 9:
                break;
            case 10:
                break;
            case 11:
                break;
            case 12:
                break;
            default:
                System.out.println("> Invalid selection. Please try again.");
        }

    }

    public void studentMenu(){
        System.out.println(">> Student Menu:");
        System.out.println("1. View All Books");
        System.out.println("2. Search Books");
        System.out.println("3. Borrow Book");
        System.out.println("4. Return Book");
        System.out.println("5. View My Borrowing History");
        System.out.println("6. Check Fine Status");
        System.out.println("7. Exit");

        int studentChoice = scanner.nextInt();

        switch(studentChoice){
            case 1:
                break;
            case 2:
                break;
            case 3:
                break;
            case 4:
                break;
            case 5:
                break;
            case 6:
                break;
            case 7:
                break;
            default:
                System.out.println("> Invalid selection. Please try again.");
        }
    }

    @Override
    public boolean addBook(int isbn, String title, String author) {
        
    }

    @Override
    public boolean removeBook(int isbn) {
        
    }

    @Override
    public Book searchByIsbn(int isbn) {
        
    }

    @Override
    public void searchBooksByTitle(String title) {
        
    }

    @Override
    public void searchBooksByAuthor(String author) {
        
    }

    @Override
    public boolean borrowBook(int isbn, String studentID) {
        
    }

    @Override
    public boolean returnBook(int isbn, String studentID) {
        
    }

    @Override
    public void viewAllBooks() {
        
    }

    @Override
    public void viewBorrowedBooks(String studentID) {
        
    }
}
