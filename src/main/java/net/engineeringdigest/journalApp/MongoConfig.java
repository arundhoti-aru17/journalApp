package net.engineeringdigest.journalApp; // Use your actual package

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.MongoTemplate;

@Configuration
public class MongoConfig {

    // REPLACE THIS STRING WITH YOUR ACTUAL CONNECTION STRING
    private static final String CONNECTION_STRING = "mongodb+srv://journaluser:journal123@cluster0.wu0plpl.mongodb.net/journaldb?retryWrites=true&w=majority&appName=Cluster0";

    @Bean
    public MongoClient mongoClient() {
        return MongoClients.create(CONNECTION_STRING);
    }

    @Bean
    public MongoTemplate mongoTemplate(MongoClient mongoClient) {
        return new MongoTemplate(mongoClient, "journaldb");
    }
}
