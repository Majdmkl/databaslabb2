package kth.books.model;

import kth.books.DbConnect;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Provides functionality to search for books in the database.
 * @author Majd & Majid
 * @version 1.0
 */
public class Search {
    private final BooksDbInterface booksDb;

    /**
     * Constructs a Search instance with the specified database interface.
     *
     * @param booksDb the database interface for accessing book data
     */
    public Search(BooksDbInterface booksDb) {
        this.booksDb = booksDb;
    }

    /**
     * Searches for books in the database that match the given search term.
     *
     * @param searchTerm the term to search for
     * @return a list of books matching the search term
     */
    public List<Book> searchBooks(String searchTerm) {
        try {
            return booksDb.searchBooks(searchTerm);
        } catch (BooksDbException e) {
            System.err.println("Error searching books: " + e.getMessage());
            return List.of();
        }
    }
}