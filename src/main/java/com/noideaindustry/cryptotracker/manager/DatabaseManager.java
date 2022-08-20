package com.noideaindustry.cryptotracker.manager;

import com.noideaindustry.cryptotracker.database.Database;
import com.noideaindustry.cryptotracker.utils.FileUtils;

import java.io.File;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.SQLException;

public class DatabaseManager {
    private final Database database;

    public DatabaseManager() {
        File file = FileUtils.getFile("data.sqlite");
        this.database = new Database(Paths.get(file.getPath()));
    }

    public void closeConnection() {
        try {
            this.database.closeConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Connection getConnection() {
        return database.getConnection();
    }
}
