package kth.books;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import kth.books.model.BooksDbImpl;
import kth.books.model.BooksDbInterface;
import kth.books.view.MainView;

/**
 * Entry point for the Library System application.
 * @author Majd & Majid
 * @version 1.0
 */
public class Main extends Application {

    /**
     * Starts the JavaFX application.
     *
     * @param primaryStage the primary stage for this application
     */
    @Override
    public void start(Stage primaryStage) {
        BooksDbInterface booksDb = new BooksDbImpl();
        MainView mainView = new MainView(booksDb);

        Scene scene = new Scene(mainView.getView(), 800, 600);
        primaryStage.setTitle("Library System");
        primaryStage.setScene(scene);

        primaryStage.setOnCloseRequest(event -> {
            try {
                booksDb.close();
                System.out.println("Database connection closed.");
            } catch (Exception e) {
                System.err.println("Error closing database connection: " + e.getMessage());
            }
        });

        primaryStage.show();
    }
}