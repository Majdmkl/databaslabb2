package kth.books.controller;

import javafx.application.Platform;
import kth.books.model.BooksDbInterface;
import kth.books.model.Genre;
import kth.books.DbConnect;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Controller class for managing genre-related operations.
 * @author Majd & Majid
 * @version 1.0
 */
public class GenreController {
    private final BooksDbInterface booksDb;

    /**
     * Constructs a GenreController with the provided database interface.
     *
     * @param booksDb the database interface
     */
    public GenreController(BooksDbInterface booksDb) {
        this.booksDb = booksDb;
    }

    /**
     * Retrieves all genres from the database.
     *
     * @return a list of genres
     */
    public List<Genre> getAllGenres() {
        try {
            return booksDb.getAllGenres();
        } catch (Exception e) {
            System.err.println("Error fetching genres: " + e.getMessage());
            return null;
        }
    }

    /**
     * Retrieves genres associated with a specific book from the database.
     *
     * @param bookId the ID of the book
     * @return a list of genres
     */
    public List<Genre> getGenresForBook(int bookId) {
        try {
            return booksDb.getGenresForBook(bookId);
        } catch (Exception e) {
            System.err.println("Error fetching genres for book: " + e.getMessage());
            return null;
        }
    }

    /**
     * Links a genre to a specific book in the database.
     *
     * @param bookId the ID of the book
     * @param genreId the ID of the genre
     */
    public void linkGenreToBook(int bookId, int genreId) {
        try {
            booksDb.linkGenreToBook(bookId, genreId);
            System.out.println("Genre linked to book successfully!");
        } catch (Exception e) {
            System.err.println("Error linking genre to book: " + e.getMessage());
        }
    }

    /**
     * Retrieves all genres in a background thread and updates the output list, then executes a callback.
     *
     * @param outputGenres the list to update with the retrieved genres
     * @param onComplete the callback to execute upon completion
     */
    public void getAllGenresInBackground(List<Genre> outputGenres, Runnable onComplete) {
        Thread thread = new Thread(() -> {
            List<Genre> genres = getAllGenres();
            Platform.runLater(() -> {
                outputGenres.clear();
                outputGenres.addAll(genres);
                onComplete.run();
            });
        });
        thread.setDaemon(true);
        thread.start();
    }

    /**
     * Retrieves genres associated with a specific book in a background thread and updates the output list, then executes a callback.
     *
     * @param bookId the ID of the book
     * @param outputGenres the list to update with the retrieved genres
     * @param onComplete the callback to execute upon completion
     */
    public void getGenresForBookInBackground(int bookId, List<Genre> outputGenres, Runnable onComplete) {
        Thread thread = new Thread(() -> {
            List<Genre> genres = getGenresForBook(bookId);
            Platform.runLater(() -> {
                outputGenres.clear();
                outputGenres.addAll(genres);
                onComplete.run();
            });
        });
        thread.setDaemon(true);
        thread.start();
    }

    /**
     * Links a genre to a specific book in a background thread and executes a callback upon completion.
     *
     * @param bookId the ID of the book
     * @param genreId the ID of the genre
     * @param onComplete the callback to execute upon completion
     */
    public void linkGenreToBookInBackground(int bookId, int genreId, Runnable onComplete) {
        Thread thread = new Thread(() -> {
            linkGenreToBook(bookId, genreId);
            Platform.runLater(onComplete);
        });
        thread.setDaemon(true);
        thread.start();
    }
}