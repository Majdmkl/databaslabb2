package kth.books.model;


/**
 * Custom exception class for database-related errors in the Books application.
 * @author Majd & Majid
 * @version 1.0
 */
public class BooksDbException extends RuntimeException {

    /**
     * Constructs a new BooksDbException with the specified detail message and cause.
     *
     * @param message the detail message
     * @param cause the cause of the exception
     */
    public BooksDbException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Constructs a new BooksDbException with the specified detail message.
     *
     * @param message the detail message
     */
    public BooksDbException(String message) {
        super(message);
    }
}