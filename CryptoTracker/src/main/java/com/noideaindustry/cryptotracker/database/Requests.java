package com.noideaindustry.cryptotracker.database;

import com.noideaindustry.cryptotracker.CryptoTracker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;

public class Requests {
    private static final Connection conn = CryptoTracker.getDatabaseManager().getConnection();

    public static void createGuildTable() {
        try {
            Statement statement = conn.createStatement();
            statement.execute("CREATE TABLE IF NOT EXISTS `invites` ( `guildId` VARCHAR(255) NOT NULL , `inviteUrl` VARCHAR(255) NULL, PRIMARY KEY (`guildId`));");
            System.out.println("Guild table created!");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static String getInvite(String guildId) {
        try {
            PreparedStatement statement = conn.prepareStatement("SELECT * FROM invites WHERE guildId = ?");
            statement.setString(1, guildId);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) return resultSet.getString("inviteUrl");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void insertInvite(String guildId, String inviteUrl) {
        try {
            PreparedStatement statement = conn.prepareStatement("INSERT INTO invites (guildId, inviteUrl) VALUES (?, ?)");
            statement.setString(1, guildId);
            statement.setString(2, inviteUrl);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
