# Smart Library System

Smart Library System is a console based Java program for managing a small library catalogue. It supports librarian and student logins, book searching, borrowing and returning, borrowing history, fine management and undo actions for recent library operations.

## Project Structure

```text
.
|-- bin/        // Compiled class files
|-- src/        // Java source files
|-- .gitignore  // Ignore .class file       
|-- README.md   // Compile, run and user guide
```
## Compile

From the project root directory, run:

### Windows PowerShell

```powershell
javac -d bin src\*.java
```

### macOS/Linux

```bash
javac -d bin src/*.java
```

## Run

After compiling, start the program from the project root directory:

```bash
java -cp bin Main
```

## How to Use

> [!NOTE]
> Inside SmartLibrary.java, sample data is provided for testing and demo purposes. To enable it, remove the comment signs // before the sample data lines.


When the program starts, choose a role:

```text
1. Librarian
2. Student
3. Exit
```

Enter any non-empty user ID and password when prompted. If the ID does not exist yet, the program automatically registers it under the selected role with the entered password. If the ID already exists, the same role and correct password must be entered to log in.

One user ID can only have one role. For example, if `S001` is registered as a Librarian, `S001` cannot register again as a Student during the same program session.

### Password Login Guide

After choosing a role, the program asks for both ID and password:

```text
> Please enter your Librarian ID:
> Please enter your Password:
```

For students, the prompt is:

```text
> Please enter your Student ID:
> Please enter your Password:
```

Password rules:

- Password cannot be empty.
- New users are registered automatically with the entered password.
- Existing users must enter the same password used during registration.
- If the password is wrong, login is rejected.
- If the user ID already belongs to another role, login or registration is rejected.

---

### Librarian Guide

Enter `1` to choose `1. Librarian` from the main menu, then enter a Librarian ID and password.

Librarian options:

```text
1. Add Book
2. Remove Book
3. View All Borrowed Books
4. View All Registered Users
5. Fine Management
6. Undo Last Action
7. View All Books
8. Search Books
9. Borrow Book
10. Return Book
11. View My Borrowing History
12. Check Fine Status
13. Back to Main Menu
```
Options 1 - 6 are librarian admin options which cannot access by student.

Fine management options:

```text
1. View User Fines
2. Add Fine
3. Reduce Fine
4. Back
```

The fine is calculated as RM 1.00 per late day.


---


### Student Guide

Enter `2` to choose `2. Student` from the main menu, then enter a Student ID and password.

Student options:

```text
1. View All Books
2. Search Books
3. Borrow Book
4. Return Book
5. View My Borrowing History
6. Check Fine Status
7. Back to Main Menu
```


1. Use `View All Books` to see available books.
2. Use `Search Books` to find a book by ISBN, title, or author.
3. Select `Borrow Book` and enter the ISBN of an available book.
4. Select `Return Book` and enter the ISBN of a book borrowed by your user ID.
5. Use `View My Borrowing History` to see previously borrowed books.
6. Use `Check Fine Status` to view outstanding fines.
   
---

### Searching Books

Both librarians and students can search books by:

```text
1. ISBN
2. Title
3. Author
```

Searches by title or author can return multiple matching books.

---

### Undo Support

The librarian `Undo Last Action` option can reverse recent supported actions, including:

- Adding a book
- Removing a book
- Borrowing a book
- Returning a book
- Adding a fine
- Reducing a fine

Undo may fail if the current state no longer allows the reversal, such as trying to undo adding a book that has already been borrowed.

## Example Session

```text
> Welcome to the Smart Library!
1. Librarian
2. Student
3. Exit
> Please select an option: 1
> Please enter your Librarian ID: L001
> Please enter your Password: libpass

>> Librarian Menu:
1. Add Book
...
> Please select an option: 1
> Enter ISBN: 1001
> Enter Title of the book: Data Structures
> Enter Author: Alex Tan
> Book added successfully: ISBN: [1001] Title: Data Structures by Alex Tan | (Available)
```

> [!NOTE]
> The catalogue and users are stored in memory only. Data is reset when the program exits.

<br>
<div align="center">
  <strong>Made by OCC 9 Group 8</strong>
  <br>
  <strong><em>Universiti Malaya<em>
</div>
