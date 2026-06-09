import java.util.InputMismatchException;
import java.util.Scanner;

public class Main {
    private static Scanner scanner = new Scanner(System.in);
    private static LibraryADT library = new SmartLibrary();

    public static void main(String[] args) {
        logInMenu();
    }

    //Shows login menu
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

    //Logs in librarian
    private static void logInAsLibrarian(){
        System.out.print("> Please enter your Librarian ID: ");
        String librarianID = readNonEmptyString();
        if(library.checkUserExists(librarianID)) {
            System.out.print("> Please enter your password to log in: ");
            
        } else {
            System.out.print("> Please enter a password to register:");
        }
        String password = readNonEmptyString();
        if (library.logIn(librarianID, "Librarian", password)) {
            librarianMenu(librarianID);
        }
    }

    //Logs in student
    private static void logInAsStudent(){
        System.out.print("> Please enter your Student ID: ");
        String studentID = readNonEmptyString();
        if(library.checkUserExists(studentID)) {
            System.out.print("> Please enter your password to log in: ");
        } else {
            System.out.print("> Please enter a password to register:");
        }
        String password = readNonEmptyString();
        if (library.logIn(studentID, "Student", password)) {
            studentMenu(studentID);
        }
    }

    //Shows librarian menu
    private static void librarianMenu(String userID){
        boolean runningLibrarian = true;

        while (runningLibrarian) {
            System.out.println();
            System.out.println(">> Librarian Menu:");
            System.out.println(">> Admin Options:");
            System.out.println("1. Add Book");
            System.out.println("2. Remove Book");
            System.out.println("3. View All Borrowed Books");
            System.out.println("4. View All Registered Users");
            System.out.println("5. Fine Management");
            System.out.println("6. Undo Last Action");
            System.out.println();
            System.out.println(">> General Options:");
            System.out.println("7. View All Books");
            System.out.println("8. Search Books");
            System.out.println("9. Borrow Book");
            System.out.println("10. Return Book");
            System.out.println("11. View My Borrowing History");
            System.out.println("12. Check Fine Status");
            System.out.println("13. Back to Main Menu");
            System.out.print("> Please select an option: ");

            int librarianChoice = readPositiveInt();
            System.out.println();

            switch(librarianChoice){
                case 1:
                    System.out.println("> Adding Book");
                    System.out.print("> Enter ISBN: ");
                    long addBookIsbn = readPositiveLong();
                    System.out.print("> Enter Title of the book: ");
                    String title = readNonEmptyString();
                    System.out.print("> Enter Author: ");
                    String author = readNonEmptyString();
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
                    library.viewRegisteredUsers();
                    break;
                case 5:
                    fineManagementMenu();
                    break;
                case 6:
                    library.undoLastAction();
                    break;
                case 7:
                    library.viewAllBooks();
                    break;
                case 8:
                    searchBooksMenu();
                    break;
                case 9:
                    borrowBookMenu(userID);
                    break;
                case 10:
                    returnBookMenu(userID);
                    break;
                case 11:
                    library.viewBorrowedBookHistory(userID);
                    break;
                case 12:
                    library.checkFineStatus(userID);
                    break;
                case 13:
                    System.out.println("> Back to main menu...");
                    runningLibrarian = false;
                    break;
                default:
                    System.out.println("> Invalid selection. Please try again.");
            }
        }
    }

    //Shows student menu
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
            System.out.println("7. Back to Main Menu");
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
                    library.viewBorrowedBookHistory(userID);
                    break;
                case 6:
                    library.checkFineStatus(userID);
                    break;
                case 7:
                    System.out.println("> Back to main menu...");
                    runningStudent = false;
                    break;
                default:
                    System.out.println("> Invalid selection. Please try again.");
            }
        }
    }

    //Shows fine management submenu
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
                    library.viewRegisteredUsers();
                    System.out.print("> Enter User ID to check fine status: ");
                    String userID = readNonEmptyString();
                    library.checkFineStatus(userID);
                    break;
                case 2:
                    library.viewRegisteredUsers();
                    System.out.print("> Enter User ID to add fine: ");
                    String addFineUserID = readNonEmptyString();
                    if (!library.viewBorrowedBooksByUser(addFineUserID)) {
                        break;
                    }
                    System.out.print("> Enter book ISBN to add fine: ");
                    long addFineIsbn = readPositiveLong();
                    if (!library.isBookBorrowedByUser(addFineUserID, addFineIsbn)) {
                        break;
                    }
                    System.out.print("> Enter number of late days: ");
                    int lateDays = readPositiveInt();
                    library.addFine(addFineUserID, addFineIsbn, lateDays);
                    break;
                case 3:
                    library.viewRegisteredUsers();
                    System.out.print("> Enter User ID to reduce fine: ");
                    String reduceFineUserID = readNonEmptyString();
                    if (!library.viewBorrowedBooksByUser(reduceFineUserID)) {
                        break;
                    }
                    System.out.print("> Enter book ISBN to reduce fine: ");
                    long reduceFineIsbn = readPositiveLong();
                    if (!library.isBookBorrowedByUser(reduceFineUserID, reduceFineIsbn)) {
                        break;
                    }
                    System.out.print("> Enter amount to reduce: ");
                    double amount = readPositiveDouble();
                    library.reduceFine(reduceFineUserID, reduceFineIsbn, amount);
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

    //Shows search submenu
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
                    String title = readNonEmptyString();
                    library.searchBooksByTitle(title);
                    break;
                case 3:
                    System.out.print("> Enter Author to search: ");
                    String author = readNonEmptyString();
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

    //Submenu for handling book borrowing
    private static void borrowBookMenu(String userID) {
        library.viewAllBooks();
        System.out.print("> Enter ISBN of the book to borrow: ");
        long isbn = readPositiveLong();
        library.borrowBook(isbn, userID);
    }

    //Submenu for handling book return
    private static void returnBookMenu(String userID) {
        System.out.print("> Enter ISBN of the book to return: ");
        long isbn = readPositiveLong();
        library.returnBook(isbn, userID);

    }

    //Reads positive integer input from user with validation specifically for menu selection and late days input
    private static int readPositiveInt() {
        while (true) {
            try {
                int value = scanner.nextInt();
                scanner.nextLine();
                if (value > 0) {
                    return value;
                } 
                
                else {
                    System.out.println(">Please enter a positive number.");
                }
            } catch (InputMismatchException e) {
                System.out.println("> Invalid input. Please enter a number.");
                scanner.nextLine();
            }
        }
    }

    //Reads positive long input from user with validation specifically for ISBN
    private static long readPositiveLong() {
        while (true) {
            try {
                long value = scanner.nextLong();
                scanner.nextLine();
                if (value > 0) {
                    return value;
                }
                else {
                    System.out.println("> Please enter a positive number.");
                }
            } catch (InputMismatchException e) {
                System.out.println("> Invalid input. Please enter a number.");
                scanner.nextLine();
            }
        }
    }

    //Reads positive decimal input from user with validation specifically for fine amount input
    private static double readPositiveDouble() {
        while (true) {
            try {
                double value = scanner.nextDouble();
                scanner.nextLine();
                if (value > 0) {
                    return value;
                }
                else {
                    System.out.println("> Please enter a positive number.");
                }
            } catch (InputMismatchException e) {
                System.out.println("> Invalid input. Please enter a number.");
                scanner.nextLine();
            }
        }
    }

    //Reads non-empty text input from user with validation specifically for string inputs like title, author and user ID.
    private static String readNonEmptyString() {
        while (true) {
            String input = scanner.nextLine().trim();
            if (!input.isEmpty()) {
                return input;
            } else {
                System.out.println("> Input cannot be empty. Please try again.");
            }
        }
    }
}

