import java.util.InputMismatchException;
import java.util.Scanner;

public class Main {
    private static Scanner scanner = new Scanner(System.in);
    private static LibraryADT library = new SmartLibrary();

    public static void main(String[] args) {
        logInMenu();
    }

    private static void logInMenu(){
        boolean runningLogIn = true;

        while (runningLogIn) {
            System.out.println();
            System.out.println("> Welcome to the Smart Library!");
            System.out.println("1. Librarian");
            System.out.println("2. Student");
            System.out.println("3. Exit");
            System.out.print("> Please select an option: ");

            int role = readPositiveInt();
            System.out.println();

            switch(role){
                case 1:
                    logInAsLibrarian();
                    break;
                case 2:
                    logInAsStudent();
                    break;
                case 3:
                    System.out.println("> Exiting...");
                    runningLogIn = false;
                    break;
                default:
                    System.out.println("> Invalid selection. Please try again.");
            }
        }    
    }

    private static void logInAsLibrarian(){
        System.out.print("> Please enter your Librarian ID: ");
        String librarianID = scanner.nextLine();
        if (library.logIn(librarianID, "Librarian")) {
            librarianMenu(librarianID);
        }
    }

    private static void logInAsStudent(){
        System.out.print("> Please enter your Student ID: ");
        String studentID = scanner.nextLine();
        if (library.logIn(studentID, "Student")) {
            studentMenu(studentID);
        }
    }

    private static void librarianMenu(String userID){
        boolean runningLibrarian = true;

        while (runningLibrarian) {
            System.out.println();
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
            System.out.println("12. Back");
            System.out.print("> Please select an option: ");

            int librarianChoice = readPositiveInt();
            System.out.println();

            switch(librarianChoice){
                case 1:
                    System.out.println("> Adding Book");
                    System.out.print("> Enter ISBN: ");
                    long addBookIsbn = readPositiveLong();
                    System.out.print("> Enter Title of the book: ");
                    String title = scanner.nextLine();
                    System.out.print("> Enter Author: ");
                    String author = scanner.nextLine();
                    library.addBook(addBookIsbn, title, author);
                    break;
                case 2:
                    System.out.println("> Removing Book");
                    library.viewAllBooks();
                    System.out.print("> Enter ISBN: ");
                    long removeBookIsbn = readPositiveLong();
                    library.removeBook(removeBookIsbn);
                    break;
                case 3:
                    library.viewAllBorrowedBooks();
                    break;
                case 4:
                    fineManagementMenu();
                    break;
                case 5:
                    library.undoLastAction();
                    break;
                case 6:
                    library.viewAllBooks();
                    break;
                case 7:
                    searchBooksMenu();
                    break;
                case 8:
                    borrowBookMenu(userID);
                    break;
                case 9:
                    returnBookMenu(userID);
                    break;
                case 10:
                    library.viewOwnBorrowedBooks(userID);
                    break;
                case 11:
                    library.checkFineStatus(userID);
                    break;
                case 12:
                    System.out.println("> Exiting...");
                    runningLibrarian = false;
                    break;
                default:
                    System.out.println("> Invalid selection. Please try again.");
            }
        }
    }

    private static void studentMenu(String userID){
        boolean runningStudent = true;

        while (runningStudent) {
            System.out.println();
            System.out.println(">> Student Menu:");
            System.out.println("1. View All Books");
            System.out.println("2. Search Books");
            System.out.println("3. Borrow Book");
            System.out.println("4. Return Book");
            System.out.println("5. View My Borrowing History");
            System.out.println("6. Check Fine Status");
            System.out.println("7. Exit");
            System.out.print("> Please select an option: ");

            int studentChoice = readPositiveInt();
            System.out.println();

            switch(studentChoice){
                case 1:
                    library.viewAllBooks();
                    break;
                case 2:
                    searchBooksMenu();
                    break;
                case 3:
                    borrowBookMenu(userID);
                    break;
                case 4:
                    returnBookMenu(userID);
                    break;
                case 5:
                    library.viewOwnBorrowedBooks(userID);
                    break;
                case 6:
                    library.checkFineStatus(userID);
                    break;
                case 7:
                    System.out.println("> Exiting...");
                    runningStudent = false;
                    break;
                default:
                    System.out.println("> Invalid selection. Please try again.");
            }
        }
    }

    private static void fineManagementMenu(){
        boolean runningFineManagement = true;

        while (runningFineManagement) {
            System.out.println();
            System.out.println(">> Fine Management Menu:");
            System.out.println("1. View User Fines");
            System.out.println("2. Add Fine");
            System.out.println("3. Reduce Fine");
            System.out.println("4. Back");
            System.out.print("> Please select an option: ");

            int fineChoice = readPositiveInt();
            System.out.println();

            switch(fineChoice){
                case 1:
                    System.out.print("> Enter User ID to check fine status: ");
                    String userID = scanner.nextLine();
                    library.checkFineStatus(userID);
                    break;
                case 2:
                    System.out.print("> Enter User ID to add fine: ");
                    userID = scanner.nextLine();
                    System.out.print("> Enter number of late days: ");
                    int lateDays = readPositiveInt();
                    library.addFine(userID, lateDays);
                    break;
                case 3:
                    System.out.print("> Enter User ID to reduce fine: ");
                    userID = scanner.nextLine();
                    System.out.print("> Enter amount to reduce: ");
                    int amount = readPositiveInt();
                    library.reduceFine(userID, amount);
                    break;
                case 4:
                    System.out.println("> Returning to previous menu...");
                    runningFineManagement = false;
                    break;
                default:
                    System.out.println("> Invalid selection. Please try again.");
            }
        }
    }

    private static void searchBooksMenu(){
        boolean runningSearch = true;

        while (runningSearch) {
            System.out.println();
            System.out.println(">> Search Books Menu:");
            System.out.println("1. Search by ISBN");
            System.out.println("2. Search by Title");
            System.out.println("3. Search by Author");
            System.out.println("4. Back");
            System.out.print("> Please select an option: ");

            int searchChoice = readPositiveInt();
            System.out.println();

            switch(searchChoice){
                case 1:
                    System.out.print("> Enter ISBN to search: ");
                    long isbn = readPositiveLong();
                    library.searchByIsbn(isbn);
                    break;
                case 2:
                    System.out.print("> Enter Title to search: ");
                    String title = scanner.nextLine();//input vadiation?
                    library.searchBooksByTitle(title);
                    break;
                case 3:
                    System.out.print("> Enter Author to search: ");
                    String author = scanner.nextLine();
                    library.searchBooksByAuthor(author);
                    break;
                case 4:
                    System.out.println("> Returning to previous menu...");
                    runningSearch = false;
                    break;
                default:
                    System.out.println("> Invalid selection. Please try again.");
            }
        }
    }

    private static void borrowBookMenu(String userID) {
        System.out.print("> Enter ISBN of the book to borrow: ");
        long isbn = readPositiveLong();
        library.borrowBook(isbn,userID);
    }

    private static void returnBookMenu(String userID) {
        System.out.print("> Enter ISBN of the book to return: ");
        long isbn = readPositiveLong();
        library.returnBook(isbn,userID);

    }

    public static int readPositiveInt() {
        while (true) {
            try {
                int value = scanner.nextInt();
                scanner.nextLine();
                if (value > 0) {
                    return value;
                } else {
                    System.out.println("Please enter a positive number.");
                }
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a number.");
                scanner.nextLine();
            }
        }
    }

    public static long readPositiveLong() {
        while (true) {
            try {
                long value = scanner.nextLong();
                scanner.nextLine();
                if (value > 0) {
                    return value;
                } else {
                    System.out.println("Please enter a positive number.");
                }
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a number.");
                scanner.nextLine();
            }
        }
    }
}

