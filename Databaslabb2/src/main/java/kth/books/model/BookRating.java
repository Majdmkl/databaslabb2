package kth.books.model;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;

import java.time.LocalDate;


/**
 * Model class representing a BookRating.
 * @author Majd & Majid
 * @version 1.0
 */
public class BookRating {

    private final SimpleIntegerProperty ratingId = new SimpleIntegerProperty();
    private final SimpleIntegerProperty bookId = new SimpleIntegerProperty();
    private final SimpleIntegerProperty value = new SimpleIntegerProperty();
    private final SimpleObjectProperty<LocalDate> ratingDate = new SimpleObjectProperty<>();

    /**
     * Constructs a BookRating with the specified ID, book ID, value, and rating date.
     *
     * @param ratingId the unique identifier for the rating
     * @param bookId the ID of the book associated with the rating
     * @param value the value of the rating
     * @param ratingDate the date the rating was given
     */
    public BookRating(int ratingId, int bookId, int value, LocalDate ratingDate) {
        this.ratingId.set(ratingId);
        this.bookId.set(bookId);
        this.value.set(value);
        this.ratingDate.set(ratingDate);
    }


    /**
     * Retrieves the ID of the rating.
     *
     * @return the rating's ID
     */
    public int getRatingId() {
        return ratingId.get();
    }

    /**
     * Provides the property object for the rating's ID.
     *
     * @return the property object for the rating's ID
     */
    public SimpleIntegerProperty ratingIdProperty() {
        return ratingId;
    }

    /**
     * Sets the ID of the rating.
     *
     * @param ratingId the new ID for the rating
     */
    public void setRatingId(int ratingId) {
        this.ratingId.set(ratingId);
    }

    /**
     * Retrieves the ID of the book associated with the rating.
     *
     * @return the book's ID
     */
    public int getBookId() {
        return bookId.get();
    }

    /**
     * Provides the property object for the book's ID.
     *
     * @return the property object for the book's ID
     */
    public SimpleIntegerProperty bookIdProperty() {
        return bookId;
    }

    /**
     * Sets the ID of the book associated with the rating.
     *
     * @param bookId the new book ID
     */
    public void setBookId(int bookId) {
        this.bookId.set(bookId);
    }

    /**
     * Retrieves the value of the rating.
     *
     * @return the rating value
     */
    public int getValue() {
        return value.get();
    }

    /**
     * Provides the property object for the rating value.
     *
     * @return the property object for the rating value
     */
    public SimpleIntegerProperty valueProperty() {
        return value;
    }

    /**
     * Sets the value of the rating.
     *
     * @param value the new rating value
     */
    public void setValue(int value) {
        this.value.set(value);
    }

    /**
     * Retrieves the date the rating was given.
     *
     * @return the rating date
     */
    public LocalDate getRatingDate() {
        return ratingDate.get();
    }

    /**
     * Provides the property object for the rating date.
     *
     * @return the property object for the rating date
     */
    public SimpleObjectProperty<LocalDate> ratingDateProperty() {
        return ratingDate;
    }

    /**
     * Sets the date the rating was given.
     *
     * @param ratingDate the new rating date
     */
    public void setRatingDate(LocalDate ratingDate) {
        this.ratingDate.set(ratingDate);
    }

    /**
     * Returns a string representation of the rating.
     *
     * @return a string in the format "Rating: [value] on [ratingDate]"
     */
    @Override
    public String toString() {
        return "Rating: " + value.get() + " on " + ratingDate.get();
    }
}