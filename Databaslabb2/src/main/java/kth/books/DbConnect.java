package kth.books;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

/**
 * Utility class for establishing a connection to the database.
 * @author Majd & Majid
 * @version 1.0
 */

public class DbConnect {
    private static final String URL = "mongodb://localhost:27017";
    private static final String DATABASE_NAME = "Books";

    public static MongoDatabase getDatabase(){
        MongoClient mongoClient = MongoClients.create(URL);
        return mongoClient.getDatabase(DATABASE_NAME);
    }
}
