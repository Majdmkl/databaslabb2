module kth.databaslabb2 {
    requires javafx.controls;
    requires javafx.fxml;
    requires org.mongodb.driver.sync.client;
    requires org.mongodb.driver.core;
    requires java.desktop;
    requires org.mongodb.bson;
    requires java.sql;

    opens kth.databaslabb2 to javafx.fxml;
    exports kth.books;
}