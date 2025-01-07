package kth.books.model;

import com.mongodb.client.*;
import org.bson.Document;

import kth.books.DbConnect;

import java.util.ArrayList;
import java.util.List;

/**
 * Implementation of the BookDbInterface for managing database operations related to books, authors, genres, and ratings using MongoDB.
 * Author: Majd & Majid
 * Version: 1.0
 */
public class BooksDbImpl implements BooksDbInterface {
    private final MongoDatabase database;

    /**
     * Constructs a BooksDbImpl and establishes a database connection to MongoDB.
     */
    public BooksDbImpl() {
        this.database = DbConnect.getDatabase();
    }

    /**
     * Retrieves all books from the database.
     *
     * @return a list of books
     */
    @Override
    public List<Book> getAllBooks() {
        List<Book> books = new ArrayList<>();
        MongoCollection<Document> collection = database.getCollection("Book");

        FindIterable<Document> documents = collection.find();
        for (Document doc : documents) {
            books.add(new Book(
                    doc.getInteger("book_id"),
                    doc.getString("ISBN"),
                    doc.getString("title"),
                    doc.getString("publication_date"),
                    doc.getString("rating")
            ));
        }
        return books;
    }

    @Override
    public List<Genre> getAllGenres() {
        List<Genre> genres = new ArrayList<>();
        genres.add(new Genre(1, "Programming"));
        genres.add(new Genre(2, "Science Fiction"));
        genres.add(new Genre(3, "Fantasy"));
        genres.add(new Genre(4, "Romance"));
        genres.add(new Genre(5, "Mystery"));
        genres.add(new Genre(6, "Horror"));
        genres.add(new Genre(7, "Non-Fiction"));

        return genres;
    }

    @Override
    public List<Genre> getGenresForBook(int bookId) {
        List<Genre> genres = new ArrayList<>();
        MongoCollection<Document> collection = database.getCollection("BookGenre");

        // Hämta alla genrer kopplade till boken
        FindIterable<Document> documents = collection.find(new Document("book_id", bookId));
        for (Document doc : documents) {
            genres.add(new Genre(
                    doc.getInteger("genre_id"),
                    doc.getString("name")
            ));
        }
        return genres;
    }

    @Override
    public void linkGenreToBook(int bookId, int genreId) {
        MongoCollection<Document> collection = database.getCollection("BookGenre");

        // Länka genre till bok
        Document doc = new Document("book_id", bookId)
                .append("genre_id", genreId);
        collection.insertOne(doc);
    }

    @Override
    public void addRatingToBook(int bookId, int ratingValue) {
        MongoCollection<Document> collection = database.getCollection("BookRating");

        // Lägg till betyg med datum
        Document doc = new Document("book_id", bookId)
                .append("value", ratingValue)
                .append("rating_date", java.time.LocalDate.now().toString());
        collection.insertOne(doc);
    }

    @Override
    public List<BookRating> getRatingsForBook(int bookId) {
        List<BookRating> ratings = new ArrayList<>();
        MongoCollection<Document> collection = database.getCollection("BookRating");

        // Hämta alla betyg för boken
        FindIterable<Document> documents = collection.find(new Document("book_id", bookId));
        for (Document doc : documents) {
            ratings.add(new BookRating(
                    doc.getInteger("rating_id"),
                    bookId,
                    doc.getInteger("value"),
                    java.time.LocalDate.parse(doc.getString("rating_date"))
            ));
        }
        return ratings;
    }


    @Override
    public List<Book> searchBooks(String searchTerm) {
        List<Book> books = new ArrayList<>();
        MongoCollection<Document> collection = database.getCollection("Book");

        // Skapa ett sökvillkor för flera fält
        Document searchQuery = new Document("$or", List.of(
                new Document("title", new Document("$regex", searchTerm).append("$options", "i")),
                new Document("ISBN", new Document("$regex", searchTerm).append("$options", "i")),
                new Document("publication_date", new Document("$regex", searchTerm).append("$options", "i"))
        ));

        // Utför sökningen
        FindIterable<Document> documents = collection.find(searchQuery);
        for (Document doc : documents) {
            books.add(new Book(
                    doc.getInteger("book_id"),
                    doc.getString("ISBN"),
                    doc.getString("title"),
                    doc.getString("publication_date"),
                    doc.getString("rating")
            ));
        }
        return books;
    }

    /**
     * Adds a new book to the database.
     *
     * @param book the book to add
     */
    @Override
    public void addBook(Book book) {
        MongoCollection<Document> collection = database.getCollection("Book");
        Document doc = new Document("book_id", getLastInsertedBookId() + 1)
                .append("ISBN", book.getISBN())
                .append("title", book.getTitle())
                .append("publication_date", book.getPublicationDate())
                .append("rating", book.getRating());
        collection.insertOne(doc);
    }

    /**
     * Deletes a book from the database by its ID.
     *
     * @param bookId the ID of the book to delete
     */
    @Override
    public void deleteBook(int bookId) {
        MongoCollection<Document> collection = database.getCollection("Book");
        collection.deleteOne(new Document("book_id", bookId));
    }

    /**
     * Retrieves the ID of the last inserted book.
     *
     * @return the ID of the last inserted book
     */
    @Override
    public int getLastInsertedBookId() {
        MongoCollection<Document> collection = database.getCollection("Book");
        Document lastBook = collection.find().sort(new Document("book_id", -1)).first();
        return lastBook != null ? lastBook.getInteger("book_id") : 0;
    }

    /**
     * Retrieves authors associated with a specific book.
     *
     * @param bookId the ID of the book
     * @return a list of authors
     */
    @Override
    public List<Author> getAuthorsForBook(int bookId) {
        List<Author> authors = new ArrayList<>();
        MongoCollection<Document> collection = database.getCollection("BookAuthor");

        FindIterable<Document> documents = collection.find(new Document("book_id", bookId));
        for (Document doc : documents) {
            authors.add(new Author(
                    doc.getInteger("author_id"),
                    doc.getString("first_name"),
                    doc.getString("last_name")
            ));
        }
        return authors;
    }

    /**
     * Adds a new author to the database.
     *
     * @param author the author to add
     */
    @Override
    public void addAuthor(Author author) {
        MongoCollection<Document> collection = database.getCollection("Author");
        Document doc = new Document("author_id", getLastInsertedAuthorId() + 1)
                .append("first_name", author.getFirstName())
                .append("last_name", author.getLastName());
        collection.insertOne(doc);
    }

    /**
     * Links an author to a book.
     *
     * @param bookId the ID of the book
     * @param authorId the ID of the author
     */
    @Override
    public void linkAuthorToBook(int bookId, int authorId) {
        MongoCollection<Document> collection = database.getCollection("BookAuthor");
        Document doc = new Document("book_id", bookId)
                .append("author_id", authorId);
        collection.insertOne(doc);
    }

    /**
     * Retrieves the ID of the last inserted author.
     *
     * @return the ID of the last inserted author
     */
    @Override
    public int getLastInsertedAuthorId() {
        MongoCollection<Document> collection = database.getCollection("Author");
        Document lastAuthor = collection.find().sort(new Document("author_id", -1)).first();
        return lastAuthor != null ? lastAuthor.getInteger("author_id") : 0;
    }

    /**
     * Closes the database connection.
     */
    @Override
    public void close() {
        // No explicit closing needed for MongoDB Java Driver as it uses connection pooling.
        System.out.println("MongoDB connection closed.");
    }
}
