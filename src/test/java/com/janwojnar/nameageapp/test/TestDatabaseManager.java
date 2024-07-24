package com.janwojnar.nameageapp.test;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileWriter;
import java.io.IOException;

public class TestDatabaseManager {

    private static final Logger logger = LoggerFactory.getLogger(TestDatabaseManager.class);


    public static void emptyDatabase(String dbPath) throws IOException {
        logger.info("Dumping database.");
        try {
            FileWriter fileWriter = new FileWriter(dbPath, false);
            fileWriter.close();
        } catch (IOException e) {
            logger.error("An error occurred while emptying the file: " + e.getMessage());
        }
    }

    public static void populateDatabase(String dbPath){
        logger.info("Populating database.");
        try {
            FileWriter fileWriter = new FileWriter(dbPath, true);
            fileWriter.append("{\"name\":\"Ola\",\"age\":22}\n" +
                    "{\"name\":\"Trola\",\"age\":12}\n" +
                    "{\"name\":\"Zola\",\"age\":6}\n" +
                    "{\"name\":\"Kola\",\"age\":100}\n" +
                    "{\"name\":\"Dwola\",\"age\":35}\n" +
                    "{\"name\":\"Gola\",\"age\":12}\n" +
                    "{\"name\":\"Ola\",\"age\":45}\n" +
                    "{\"name\":\"Kasia\",\"age\":27}\n");
            fileWriter.close();
        } catch (IOException e) {
            logger.error("An error occurred while emptying the file: " + e.getMessage());
        }
    }
}
