package kth.books.model;

import javafx.beans.property.*;

/**
 * Model class representing an Author.
 * * @author Majd & Majid
 *  * @version 1.0
 */
public class Author {
    private IntegerProperty id;
    private StringProperty firstName;
    private StringProperty lastName;

    /**
     * Constructs an Author with the specified ID, first name, and last name.
     *
     * @param id the unique identifier for the author
     * @param firstName the first name of the author
     * @param lastName the last name of the author
     */
    public Author(int id, String firstName, String lastName) {
        this.id = new SimpleIntegerProperty(id);
        this.firstName = new SimpleStringProperty(firstName);
        this.lastName = new SimpleStringProperty(lastName);
    }

    /**
     * Retrieves the ID of the author.
     *
     * @return the author's ID
     */
    public int getId() {
        return id.get();
    }

    /**
     * Sets the ID of the author.
     *
     * @param id the author's new ID
     */
    public void setId(int id) {
        this.id.set(id);
    }

    /**
     * Provides the property object for the author's ID.
     *
     * @return the property object for the author's ID
     */
    public IntegerProperty idProperty() {
        return id;
    }

    /**
     * Retrieves the first name of the author.
     *
     * @return the author's first name
     */
    public String getFirstName() {
        return firstName.get();
    }

    /**
     * Sets the first name of the author.
     *
     * @param firstName the author's new first name
     */
    public void setFirstName(String firstName) {
        this.firstName.set(firstName);
    }


    /**
     * Provides the property object for the author's first name.
     *
     * @return the property object for the author's first name
     */
    public StringProperty firstNameProperty() {
        return firstName;
    }

    /**
     * Retrieves the last name of the author.
     *
     * @return the author's last name
     */
    public String getLastName() {
        return lastName.get();
    }

    /**
     * Sets the last name of the author.
     *
     * @param lastName the author's new last name
     */
    public void setLastName(String lastName) {
        this.lastName.set(lastName);
    }

    /**
     * Provides the property object for the author's last name.
     *
     * @return the property object for the author's last name
     */
    public StringProperty lastNameProperty() {
        return lastName;
    }

    /**
     * Returns a string representation of the author.
     *
     * @return the author's full name in the format "FirstName LastName"
     */
    @Override
    public String toString() {
        return firstName.get() + " " + lastName.get();
    }
}