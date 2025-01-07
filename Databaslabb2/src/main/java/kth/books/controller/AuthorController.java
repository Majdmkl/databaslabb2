package kth.books.controller;

import javafx.application.Platform;
import kth.books.DbConnect;
import kth.books.model.Author;
import kth.books.model.BooksDbInterface;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Controller class for managing author-related operations.
 * @author Majd & Majid
 * @version 1.0
 */
public class AuthorController {

    private final BooksDbInterface booksDb;

    /**
     * Constructs an AuthorController with the provided database interface.
     *
     * @param booksDb the database interface
     */
    public AuthorController(BooksDbInterface booksDb) {
        this.booksDb = booksDb;
    }

    /**
     * Adds a new author to the database.
     *
     * @param author the author to add
     */
    public void addAuthor(Author author) {
        try {
            booksDb.addAuthor(author);
            System.out.println("Author added successfully!");
        } catch (Exception e) {
            System.err.println("Error adding author: " + e.getMessage());
        }
    }

    /**
     * Links an author to a book.
     *
     * @param bookId the ID of the book
     * @param authorId the ID of the author
     */
    public void linkAuthorToBook(int bookId, int authorId) {
        try {
            booksDb.linkAuthorToBook(bookId, authorId);
            System.out.println("Author linked to book successfully!");
        } catch (Exception e) {
            System.err.println("Error linking author to book: " + e.getMessage());
        }
    }

    /**
     * Retrieves authors associated with a specific book.
     *
     * @param bookId the ID of the book
     * @return a list of authors
     */
    public List<Author> getAuthorsForBook(int bookId) {
        try {
            return booksDb.getAuthorsForBook(bookId);
        } catch (Exception e) {
            System.err.println("Error fetching authors for book: " + e.getMessage());
            return new ArrayList<>();
        }
    }

    /**
     * Retrieves the ID of the last inserted author.
     *
     * @return the ID of the last inserted author
     */
    public int getLastInsertedAuthorId() {
        try {
            return booksDb.getLastInsertedAuthorId();
        } catch (Exception e) {
            System.err.println("Error fetching last inserted author ID: " + e.getMessage());
            return -1;
        }
    }

    /**
     * Retrieves authors for a specific book in the background thread and executes a callback on completion.
     *
     * @param bookId the ID of the book
     * @param onComplete the callback to execute upon completion
     */
    public void getAuthorsForBookInBackground(int bookId, Runnable onComplete) {
        Thread thread = new Thread(() -> {
            List<Author> authors = getAuthorsForBook(bookId);
            Platform.runLater(() -> {
                onComplete.run();
            });
        });
        thread.setDaemon(true);
        thread.start();
    }

    /**
     * Adds an author in the background thread and executes a callback on completion.
     *
     * @param author the author to add
     * @param onComplete the callback to execute upon completion
     */
    public void addAuthorInBackground(Author author, Runnable onComplete) {
        Thread thread = new Thread(() -> {
            addAuthor(author);
            Platform.runLater(onComplete);
        });
        thread.setDaemon(true);
        thread.start();
    }

    /**
     * Links an author to a book in the background thread and executes a callback on completion.
     *
     * @param bookId the ID of the book
     * @param authorId the ID of the author
     * @param onComplete the callback to execute upon completion
     */
    public void linkAuthorToBookInBackground(int bookId, int authorId, Runnable onComplete) {
        Thread thread = new Thread(() -> {
            linkAuthorToBook(bookId, authorId);
            Platform.runLater(onComplete);
        });
        thread.setDaemon(true);
        thread.start();
    }
}