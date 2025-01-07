package kth.books.model;

import java.util.List;

/**
 * Interface representing the database operations for the Books application.
 * @author Majd & Majid
 * @version 1.0
 */

public interface BooksDbInterface {
    /**
     * Retrieves all books from the database.
     *
     * @return a list of books
     */
    List<Book> getAllBooks();

    /**
     * Adds a new book to the database.
     *
     * @param book the book to add
     */
    void addBook(Book book);

    /**
     * Deletes a book from the database by its ID.
     *
     * @param bookId the ID of the book to delete
     */
    void deleteBook(int bookId);

    /**
     * Retrieves the ID of the last inserted book.
     *
     * @return the ID of the last inserted book
     */
    int getLastInsertedBookId();

    /**
     * Retrieves authors associated with a specific book.
     *
     * @param bookId the ID of the book
     * @return a list of authors
     */
    List<Author> getAuthorsForBook(int bookId);

    /**
     * Adds a new author to the database.
     *
     * @param author the author to add
     */
    void addAuthor(Author author);

    /**
     * Links an author to a book.
     *
     * @param bookId the ID of the book
     * @param authorId the ID of the author
     */
    void linkAuthorToBook(int bookId, int authorId);

    /**
     * Retrieves the ID of the last inserted author.
     *
     * @return the ID of the last inserted author
     */
    int getLastInsertedAuthorId();

    /**
     * Retrieves all genres from the database.
     *
     * @return a list of genres
     */
    List<Genre> getAllGenres();

    /**
     * Retrieves genres associated with a specific book.
     *
     * @param bookId the ID of the book
     * @return a list of genres
     */
    List<Genre> getGenresForBook(int bookId);

    /**
     * Links a genre to a book.
     *
     * @param bookId the ID of the book
     * @param genreId the ID of the genre
     */
    void linkGenreToBook(int bookId, int genreId);

    /**
     * Adds a rating to a book.
     *
     * @param bookId the ID of the book
     * @param ratingValue the value of the rating
     */
    void addRatingToBook(int bookId, int ratingValue);

    /**
     * Retrieves ratings for a specific book.
     *
     * @param bookId the ID of the book
     * @return a list of ratings
     */
    List<BookRating> getRatingsForBook(int bookId);

    /**
     * Searches for books matching a given search term.
     *
     * @param searchTerm the term to search for
     * @return a list of matching books
     */
    List<Book> searchBooks(String searchTerm);

    /**
     * Closes the database connection.
     */
    void close();


}
