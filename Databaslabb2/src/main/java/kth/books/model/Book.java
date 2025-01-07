package kth.books.model;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import kth.books.model.BookRating;

import java.util.List;


/**
 * Model class representing a Book
 * @author Majd & Majid
 * @version 1.0
 */
public class Book {
    private IntegerProperty id;
    private StringProperty ISBN;
    private StringProperty title;
    private StringProperty publicationDate;
    private ObservableList<Author> authors;
    private StringProperty rating;
    private final ObservableList<Genre> genres = FXCollections.observableArrayList();
    private ObservableList<BookRating> ratings = FXCollections.observableArrayList();

    /**
     * Constructs a Book with the specified ID, ISBN, title, publication date, and rating.
     *
     * @param id the unique identifier for the book
     * @param ISBN the ISBN of the book
     * @param title the title of the book
     * @param publicationDate the publication date of the book
     * @param rating the rating of the book
     */
    public Book(int id, String ISBN, String title, String publicationDate, String rating) {
        this.id = new SimpleIntegerProperty(id);
        this.ISBN = new SimpleStringProperty(ISBN);
        this.title = new SimpleStringProperty(title);
        this.publicationDate = new SimpleStringProperty(publicationDate);
        this.authors = FXCollections.observableArrayList();
        this.rating = new SimpleStringProperty(rating);

    }

    /**
     * Retrieves the ID of the book.
     *
     * @return the book's ID
     */
    public int getId() {
        return id.get();
    }

    /**
     * Sets the ID of the book.
     *
     * @param id the book's new ID
     */
    public void setId(int id) {
        this.id.set(id);
    }

    /**
     * Provides the property object for the book's ID.
     *
     * @return the property object for the book's ID
     */
    public IntegerProperty idProperty() {
        return id;
    }

    /**
     * Retrieves the ISBN of the book.
     *
     * @return the book's ISBN
     */
    public String getISBN() {
        return ISBN.get();
    }

    /**
     * Sets the ISBN of the book.
     *
     * @param ISBN the book's new ISBN
     */
    public void setISBN(String ISBN) {
        this.ISBN.set(ISBN);
    }

    /**
     * Provides the property object for the book's ISBN.
     *
     * @return the property object for the book's ISBN
     */
    public StringProperty isbnProperty() {
        return ISBN;
    }

    /**
     * Retrieves the title of the book.
     *
     * @return the book's title
     */
    public String getTitle() {
        return title.get();
    }

    /**
     * Sets the title of the book.
     *
     * @param title the book's new title
     */
    public void setTitle(String title) {
        this.title.set(title);
    }

    /**
     * Provides the property object for the book's title.
     *
     * @return the property object for the book's title
     */
    public StringProperty titleProperty() {
        return title;
    }

    /**
     * Retrieves the publication date of the book.
     *
     * @return the book's publication date
     */
    public String getPublicationDate() {
        return publicationDate.get();
    }

    /**
     * Sets the publication date of the book.
     *
     * @param publicationDate the book's new publication date
     */
    public void setPublicationDate(String publicationDate) {
        this.publicationDate.set(publicationDate);
    }

    /**
     * Provides the property object for the book's publication date.
     *
     * @return the property object for the book's publication date
     */
    public StringProperty publicationDateProperty() {
        return publicationDate;
    }

    /**
     * Retrieves the list of authors associated with the book.
     *
     * @return the list of authors
     */
    public ObservableList<Author> getAuthors() {
        return authors;
    }

    /**
     * Sets the authors associated with the book.
     *
     * @param authors the new list of authors
     */
    public void setAuthors(ObservableList<Author> authors) {
        this.authors = authors;
    }

    /**
     * Adds an author to the book's list of authors.
     *
     * @param author the author to add
     */
    public void addAuthor(Author author) {
        this.authors.add(author);
    }

    /**
     * Removes an author from the book's list of authors.
     *
     * @param author the author to remove
     */
    public void removeAuthor(Author author) {
        this.authors.remove(author);
    }

    /**
     * Retrieves the list of genres associated with the book.
     *
     * @return the list of genres
     */
    public ObservableList<Genre> getGenres() {
        return genres;
    }

    /**
     * Sets the genres associated with the book.
     *
     * @param genres the new list of genres
     */
    public void setGenres(List<Genre> genres) {
        this.genres.setAll(genres);
    }

    /**
     * Retrieves the rating of the book.
     *
     * @return the book's rating
     */
    public String getRating() {
        return rating.get();
    }

    /**
     * Sets the rating of the book.
     *
     * @param rating the book's new rating
     */
    public void setRating(String rating) {
        this.rating.set(rating);
    }

    /**
     * Provides the property object for the book's rating.
     *
     * @return the property object for the book's rating
     */
    public StringProperty ratingProperty() {
        return rating;
    }

    /**
     * Retrieves the list of ratings associated with the book.
     *
     * @return the list of ratings
     */
    public ObservableList<BookRating> getRatings() {
        return ratings;
    }

    /**
     * Sets the ratings associated with the book.
     *
     * @param ratings the new list of ratings
     */
    public void setRatings(ObservableList<BookRating> ratings) {
        this.ratings = ratings;
    }
}


