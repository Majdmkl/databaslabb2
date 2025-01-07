package kth.books.model;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;


/**
 * Model class representing a Genre.
 * @author Majd & Majid
 * @version 1.0
 */
public class Genre {
    private final SimpleIntegerProperty id;
    private final SimpleStringProperty name;

    /**
     * Constructs a Genre with the specified ID and name.
     *
     * @param id the unique identifier for the genre
     * @param name the name of the genre
     */
    public Genre(int id, String name) {
        this.id = new SimpleIntegerProperty(id);
        this.name = new SimpleStringProperty(name);
    }

    /**
     * Retrieves the ID of the genre.
     *
     * @return the genre's ID
     */
    public int getId() {
        return id.get();
    }

    /**
     * Sets the ID of the genre.
     *
     * @param id the genre's new ID
     */
    public void setId(int id) {
        this.id.set(id);
    }


    /**
     * Retrieves the name of the genre.
     *
     * @return the genre's name
     */
    public String getName() {
        return name.get();
    }

    /**
     * Sets the name of the genre.
     *
     * @param name the genre's new name
     */
    public void setName(String name) {
        this.name.set(name);
    }

    /**
     * Returns a string representation of the genre.
     *
     * @return the name of the genre
     */
    @Override
    public String toString() {
        return name.get();
    }
}