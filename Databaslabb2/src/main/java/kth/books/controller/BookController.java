package kth.books.controller;

import javafx.application.Platform;
import kth.books.DbConnect;
import kth.books.model.Book;
import kth.books.model.BookRating;
import kth.books.model.BooksDbInterface;

import java.util.List;

/**
 * Controller class for managing book-related operations.
 * @author Majd & Majid
 * @version 1.0
 */
public class BookController  {

    private final BooksDbInterface booksDb;

    /**
     * Constructs a BookController with the provided database interface.
     *
     * @param booksDb the database interface
     */
    public BookController(BooksDbInterface booksDb) {
        this.booksDb = booksDb;
    }

    /**
     * Adds a new book to the database.
     *
     * @param book the book to add
     */
    public void addBook(Book book) {
        try {
            booksDb.addBook(book);
            System.out.println("Book added successfully!");
        } catch (Exception e) {
            System.err.println("Error adding book: " + e.getMessage());
        }
    }

    /**
     * Deletes a book from the database.
     *
     * @param bookId the ID of the book to delete
     */
    public void deleteBook(int bookId) {
        try {
            booksDb.deleteBook(bookId);
            System.out.println("Book deleted successfully!");
        } catch (Exception e) {
            System.err.println("Error deleting book: " + e.getMessage());
        }
    }

    /**
     * Retrieves all books from the database.
     *
     * @return a list of books
     */
    public List<Book> getAllBooks() {
        try {
            return booksDb.getAllBooks();
        } catch (Exception e) {
            System.err.println("Error fetching books: " + e.getMessage());
            return null;
        }
    }

    /**
     * Retrieves the ID of the last inserted book.
     *
     * @return the ID of the last inserted book
     */
    public int getLastInsertedBookId() {
        try {
            return booksDb.getLastInsertedBookId();
        } catch (Exception e) {
            System.err.println("Error fetching last inserted book ID: " + e.getMessage());
            return -1;
        }
    }

    /**
     * Adds a book in the background thread and executes a callback on completion.
     *
     * @param book the book to add
     * @param onComplete the callback to execute upon completion
     */
    public void addBookInBackground(Book book, Runnable onComplete) {
        Thread thread = new Thread(() -> {
            addBook(book);
            Platform.runLater(onComplete);
        });
        thread.setDaemon(true);
        thread.start();
    }

    /**
     * Deletes a book in the background thread and executes a callback on completion.
     *
     * @param bookId the ID of the book to delete
     * @param onComplete the callback to execute upon completion
     */
    public void deleteBookInBackground(int bookId, Runnable onComplete) {
        Thread thread = new Thread(() -> {
            deleteBook(bookId);
            Platform.runLater(onComplete);
        });
        thread.setDaemon(true);
        thread.start();
    }

    /**
     * Retrieves all books in the background thread and updates the output list, then executes a callback.
     *
     * @param onComplete the callback to execute upon completion
     * @param outputBooks the list to update with the retrieved books
     */
    public void getAllBooksInBackground(Runnable onComplete, List<Book> outputBooks) {
        Thread thread = new Thread(() -> {
            List<Book> books = getAllBooks();
            Platform.runLater(() -> {
                outputBooks.clear();
                outputBooks.addAll(books);
                onComplete.run();
            });
        });
        thread.setDaemon(true);
        thread.start();
    }

    /*// Add rating to a book
    public void addRating(int bookId, int value) {
        String insertRatingSql = "INSERT INTO BookRating (value, rating_date) VALUES (?, NOW())";
        String linkRatingSql = "INSERT INTO AssignRating (book_id, rating_id) VALUES (?, LAST_INSERT_ID())";

        try (Connection connection = DbConnect.getConnection()) {
            // Insert into BookRating
            try (PreparedStatement ratingStmt = connection.prepareStatement(insertRatingSql)) {
                ratingStmt.setInt(1, value);
                ratingStmt.executeUpdate();
            }

            // Link the rating to the book
            try (PreparedStatement assignStmt = connection.prepareStatement(linkRatingSql)) {
                assignStmt.setInt(1, bookId);
                assignStmt.executeUpdate();
            }

        } catch (SQLException e) {
            System.err.println("Error adding rating: " + e.getMessage());
        }
    }*/

    /**
     * Adds a rating to a specific book.
     *
     * @param bookId the ID of the book
     * @param ratingValue the value of the rating
     */
    public void addRatingToBook(int bookId, int ratingValue) {
        try {
            booksDb.addRatingToBook(bookId, ratingValue);
            System.out.println("Rating added successfully to book!");
        } catch (Exception e) {
            System.err.println("Error adding rating to book: " + e.getMessage());
        }
    }

    /**
     * Retrieves ratings for a specific book.
     *
     * @param bookId the ID of the book
     * @return a list of ratings
     */
    public List<BookRating> getRatingsForBook(int bookId) {
        try {
            return booksDb.getRatingsForBook(bookId);
        } catch (Exception e) {
            System.err.println("Error fetching ratings for book: " + e.getMessage());
            return null;
        }
    }
}