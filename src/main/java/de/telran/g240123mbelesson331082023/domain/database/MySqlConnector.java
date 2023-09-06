package de.telran.g240123mbelesson331082023.domain.database;

import java.sql.Connection;
import java.sql.DriverManager;

import static de.telran.g240123mbelesson331082023.constants.Constants.*;

public class MySqlConnector {
    public static Connection getConnection() {
        try {
            Class.forName(DRIVER_PATH);
            // jdbc:mysql://localhost:3306/e-shop?user=root&password=6PGN6dTJvghvLM
            String dbUrl = String.format("%s%s?user=%s&password=%s", DB_ADDRESS, DB_NAME, USER_NAME, DB_PASSWORD);
            return DriverManager.getConnection(dbUrl);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
