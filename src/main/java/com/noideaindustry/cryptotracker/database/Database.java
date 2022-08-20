package com.noideaindustry.cryptotracker.database;

import com.noideaindustry.cryptotracker.CryptoTracker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.nio.file.Path;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Database {
    private Connection conn = null;

    private final Path databasePath;

    public Database(Path databasePath) {
        this.databasePath = databasePath;
        this.initConnection();
    }

    private void initConnection() {
        try {
            conn = DriverManager.getConnection("jdbc:sqlite:" + databasePath.toString());
            System.out.println("Connection to SQLite has been established.");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void closeConnection() throws SQLException {
        if (this.conn != null) {
            if (this.conn.isClosed()) {
                this.conn.close();
                System.out.println("Connection to SQLite has been terminated!");
            }
        }
    }

    public Connection getConnection() {
        try {
            if (this.conn != null && !this.conn.isClosed())
                return this.conn;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        initConnection();
        return this.conn;
    }
}
