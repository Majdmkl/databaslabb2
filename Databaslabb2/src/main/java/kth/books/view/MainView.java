package kth.books.view;

import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import kth.books.controller.BookController;
import kth.books.controller.GenreController;
import kth.books.model.*;
import kth.books.controller.AuthorController;

import java.util.List;

/**
 * The main view of the application, including the menu, search functionality, and book table display.
 * @author Majd & Majid
 * @version 1.0
 */

public class MainView {

    private BorderPane root;
    private TableView<Book> bookTable;
    private ObservableList<Book> books;
    private final BooksDbInterface booksDb;

    /**
     * Constructs the MainView with the provided database interface.
     *
     * @param booksDb the database interface
     */
    public MainView(BooksDbInterface booksDb) {
        this.booksDb = booksDb;
        root = new BorderPane();

        MenuBar menuBar = new MenuBar();
        Menu fileMenu = new Menu("File");
        Menu helpMenu = new Menu("Help");
        menuBar.getMenus().addAll(fileMenu, helpMenu);

        TextField searchField = new TextField();
        searchField.setPromptText("Search...");
        Button searchButton = new Button("Search");
        searchButton.setOnAction(e -> handleSearch(searchField.getText()));

        HBox searchBox = new HBox(10, searchField, searchButton);
        searchBox.setPadding(new Insets(10));

        VBox topBox = new VBox(menuBar, searchBox);

        bookTable = new TableView<>();
        TableColumn<Book, Number> idColumn = new TableColumn<>("ID");
        idColumn.setCellValueFactory(cellData -> cellData.getValue().idProperty());

        TableColumn<Book, String> titleColumn = new TableColumn<>("Title");
        titleColumn.setCellValueFactory(cellData -> cellData.getValue().titleProperty());

        TableColumn<Book, String> isbnColumn = new TableColumn<>("ISBN");
        isbnColumn.setCellValueFactory(cellData -> cellData.getValue().isbnProperty());

        TableColumn<Book, String> publicationDateColumn = new TableColumn<>("Publication Date");
        publicationDateColumn.setCellValueFactory(cellData -> cellData.getValue().publicationDateProperty());

        TableColumn<Book, String> authorsColumn = new TableColumn<>("Authors");
        authorsColumn.setCellValueFactory(cellData -> {
            StringBuilder authors = new StringBuilder();
            for (Author author : cellData.getValue().getAuthors()) {
                authors.append(author.toString()).append(", ");
            }
            return new ReadOnlyStringWrapper(authors.toString().replaceAll(", $", ""));
        });

        TableColumn<Book, String> ratingColumn = new TableColumn<>("Rating");
        ratingColumn.setCellValueFactory(cellData -> cellData.getValue().ratingProperty());

        TableColumn<Book, String> genresColumn = new TableColumn<>("Genres");
        genresColumn.setCellValueFactory(cellData -> {
            StringBuilder genres = new StringBuilder();
            for (Genre genre : cellData.getValue().getGenres()) {
                genres.append(genre.getName()).append(", ");
            }
            return new ReadOnlyStringWrapper(genres.toString().replaceAll(", $", ""));
        });

        bookTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);


        bookTable.getColumns().addAll(idColumn, titleColumn, isbnColumn, publicationDateColumn, genresColumn, authorsColumn, ratingColumn);

        loadBooks();

        Button addBookButton = new Button("Add Book");
        addBookButton.setOnAction(e -> showAddBookDialog());

        Button deleteBookButton = new Button("Delete Book");
        deleteBookButton.setOnAction(e -> deleteSelectedBook());

        HBox buttonBox = new HBox(10, addBookButton, deleteBookButton);
        buttonBox.setStyle("-fx-padding: 10;");
        root.setTop(topBox);
        root.setCenter(bookTable);
        root.setBottom(buttonBox);
    }

    /**
     * Loads the list of books into the table.
     */
    private void loadBooks() {
        BookController bookController = new BookController(booksDb);
        books = FXCollections.observableArrayList();

        bookController.getAllBooksInBackground(() -> {
            bookTable.setItems(books);

            AuthorController authorController = new AuthorController(booksDb);
            GenreController genreController = new GenreController(booksDb);

            for (Book book : books) {
                authorController.getAuthorsForBookInBackground(book.getId(), () -> {
                    ObservableList<Author> authors = FXCollections.observableArrayList(authorController.getAuthorsForBook(book.getId()));
                    javafx.application.Platform.runLater(() -> {
                        book.setAuthors(authors);
                        bookTable.refresh();
                    });
                });

                genreController.getGenresForBookInBackground(book.getId(), book.getGenres(), () -> {
                    javafx.application.Platform.runLater(() -> {
                        bookTable.refresh();
                    });
                });

                new Thread(() -> {
                    List<BookRating> ratings = bookController.getRatingsForBook(book.getId());
                    javafx.application.Platform.runLater(() -> {
                        if (!ratings.isEmpty()) {
                            book.setRating(String.valueOf(ratings.get(0).getValue()));
                        } else {
                            book.setRating("N/A");
                        }
                        bookTable.refresh();
                    });
                }).start();
            }
        }, books);
    }

    /**
     * Handles search functionality.
     *
     * @param searchTerm the term to search for
     */
    private void handleSearch(String searchTerm) {
        Search search = new Search(booksDb);

        new Thread(() -> {
            List<Book> searchResults = search.searchBooks(searchTerm);

            javafx.application.Platform.runLater(() -> {
                books.clear();
                books.addAll(searchResults);

                AuthorController authorController = new AuthorController(booksDb);
                GenreController genreController = new GenreController(booksDb);

                for (Book book : books) {
                    new Thread(() -> {
                        List<Author> authors = authorController.getAuthorsForBook(book.getId());
                        javafx.application.Platform.runLater(() -> {
                            book.setAuthors(FXCollections.observableArrayList(authors));
                            bookTable.refresh();
                        });
                    }).start();

                    new Thread(() -> {
                        List<Genre> genres = genreController.getGenresForBook(book.getId());
                        javafx.application.Platform.runLater(() -> {
                            book.setGenres(FXCollections.observableArrayList(genres));
                            bookTable.refresh();
                        });
                    }).start();
                }
            });
        }).start();
    }

    /**
     * Displays a dialog for adding a new book.
     */
    private void showAddBookDialog() {
        Stage dialog = new Stage();
        dialog.setTitle("Add Book");

        // Layout för dialogrutan
        GridPane gridPane = new GridPane();
        gridPane.setHgap(10);
        gridPane.setVgap(10);
        gridPane.setPadding(new Insets(10));

        // Fält för bokinformation
        TextField titleField = new TextField();
        TextField isbnField = new TextField();
        TextField publicationDateField = new TextField();
        TextField authorFirstNameField = new TextField();
        TextField authorLastNameField = new TextField();
        TextField ratingField = new TextField();

        // Lägg till fält i grid
        gridPane.add(new Label("Title:"), 0, 0);
        gridPane.add(titleField, 1, 0);
        gridPane.add(new Label("ISBN:"), 0, 1);
        gridPane.add(isbnField, 1, 1);
        gridPane.add(new Label("Publication Date:"), 0, 2);
        gridPane.add(publicationDateField, 1, 2);
        gridPane.add(new Label("Author First Name:"), 0, 3);
        gridPane.add(authorFirstNameField, 1, 3);
        gridPane.add(new Label("Author Last Name:"), 0, 4);
        gridPane.add(authorLastNameField, 1, 4);
        gridPane.add(new Label("Rating:"), 0, 5);
        gridPane.add(ratingField, 1, 5);

        // Lista för genrer
        ListView<Genre> genreListView = new ListView<>();
        genreListView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

        // Hämta genrer från databasen eller använd fallback
        GenreController genreController = new GenreController(booksDb);
        List<Genre> genres = genreController.getAllGenres();

        if (genres.isEmpty()) {
            System.out.println("No genres found in MongoDB. Using default genres.");
            genres = List.of(
                    new Genre(1, "Programming"),
                    new Genre(2, "Science Fiction"),
                    new Genre(3, "Fantasy"),
                    new Genre(4, "Romance"),
                    new Genre(5, "Mystery"),
                    new Genre(6, "Horror"),
                    new Genre(7, "Non-Fiction")
            );
        }

        // Sätt genrer i ListView
        genreListView.setItems(FXCollections.observableArrayList(genres));

        // Lägg till ListView i grid
        gridPane.add(new Label("Genres:"), 0, 6);
        gridPane.add(genreListView, 1, 6);

        // Spara och avbryt-knappar
        Button saveButton = new Button("Save");
        Button cancelButton = new Button("Cancel");
        HBox buttonBox = new HBox(10, saveButton, cancelButton);
        buttonBox.setAlignment(Pos.CENTER_RIGHT);
        gridPane.add(buttonBox, 1, 7);

        // Spara-knappens funktionalitet
        saveButton.setOnAction(e -> {
            new Thread(() -> {
                String title = titleField.getText();
                String isbn = isbnField.getText();
                String publicationDate = publicationDateField.getText();
                String authorFirstName = authorFirstNameField.getText();
                String authorLastName = authorLastNameField.getText();
                String ratingValue = ratingField.getText();
                ObservableList<Genre> selectedGenres = genreListView.getSelectionModel().getSelectedItems();

                if (!title.isEmpty() && !isbn.isEmpty() && !publicationDate.isEmpty() &&
                        !authorFirstName.isEmpty() && !authorLastName.isEmpty() && !selectedGenres.isEmpty() && !ratingValue.isEmpty()) {

                    try {
                        int rating = Integer.parseInt(ratingValue);
                        if (rating < 1 || rating > 5) throw new NumberFormatException();

                        BookController bookController = new BookController(booksDb);
                        AuthorController authorController = new AuthorController(booksDb);

                        Book book = new Book(0, isbn, title, publicationDate, ratingValue);
                        bookController.addBookInBackground(book, () -> {
                            int bookId = bookController.getLastInsertedBookId();

                            Author author = new Author(0, authorFirstName, authorLastName);
                            authorController.addAuthorInBackground(author, () -> {
                                int authorId = authorController.getLastInsertedAuthorId();

                                authorController.linkAuthorToBookInBackground(bookId, authorId, () -> {

                                    for (Genre genre : selectedGenres) {
                                        genreController.linkGenreToBookInBackground(bookId, genre.getId(), () -> {

                                            bookController.addRatingToBook(bookId, rating);

                                            javafx.application.Platform.runLater(() -> {
                                                loadBooks();
                                                dialog.close();
                                            });
                                        });
                                    }
                                });
                            });
                        });

                    } catch (NumberFormatException ex) {
                        javafx.application.Platform.runLater(() -> {
                            Alert alert = new Alert(Alert.AlertType.ERROR);
                            alert.setContentText("Please enter a valid rating between 1 and 5.");
                            alert.show();
                        });
                    }
                } else {
                    javafx.application.Platform.runLater(() -> {
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setContentText("All fields and at least one genre must be selected!");
                        alert.show();
                    });
                }
            }).start();
        });

        // Avbryt-knappens funktionalitet
        cancelButton.setOnAction(e -> dialog.close());

        // Skapa och visa dialogen
        Scene scene = new Scene(gridPane, 500, 400);
        dialog.setScene(scene);
        dialog.show();
    }


    /**
     * Deletes the selected book from the database.
     */
    private void deleteSelectedBook() {
        Book selectedBook = bookTable.getSelectionModel().getSelectedItem();

        if (selectedBook != null) {
            BookController bookController = new BookController(booksDb);

            bookController.deleteBookInBackground(selectedBook.getId(), () -> {
                books.remove(selectedBook);
            });
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setContentText("Please select a book to delete.");
            alert.show();
        }
    }

    /**
     * Retrieves the root BorderPane for the MainView.
     *
     * @return the BorderPane
     */
    public BorderPane getView() {
        return root;
    }
}