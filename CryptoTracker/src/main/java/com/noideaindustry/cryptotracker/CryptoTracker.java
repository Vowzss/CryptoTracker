package com.noideaindustry.cryptotracker;

import com.noideaindustry.cryptotracker.commands.utils.ListenerSlashCommand;
import com.noideaindustry.cryptotracker.database.Requests;
import com.noideaindustry.cryptotracker.manager.DatabaseManager;
import com.noideaindustry.cryptotracker.utils.FileUtils;
import kong.unirest.JsonNode;
import kong.unirest.Unirest;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.requests.GatewayIntent;
import net.dv8tion.jda.api.utils.cache.CacheFlag;
import org.simpleyaml.configuration.file.YamlFile;

import java.text.DecimalFormat;
import java.util.Collections;
import java.util.Timer;
import java.util.TimerTask;

public class CryptoTracker {
    private static JDA jda;
    private static Double oldPrice = (double) 0;
    private static YamlFile config;
    private static DatabaseManager databaseManager;

    public static void main(String[] args) {
        try {
            config = FileUtils.getYamlFile("config.yml");
            if (config == null) {
                System.out.println("Config file not found");
                System.out.println("Stopping program...");
                System.exit(1);
            }

            databaseManager = new DatabaseManager();
            Requests.createGuildTable();
            JDABuilder builder = JDABuilder.create(config.getString("token", "config"), GatewayIntent.getIntents(GatewayIntent.DEFAULT));
            builder.disableCache(CacheFlag.ACTIVITY, CacheFlag.CLIENT_STATUS, CacheFlag.ONLINE_STATUS);
            jda = builder.build().awaitReady();
            jda.addEventListener(new ListenerSlashCommand(jda));

        } catch (Exception e) {
            System.out.println("Error while starting program " + e);
            System.exit(1);
        }
        updateStatus();
    }

    private static void updateStatus() {
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                Unirest.get("https://api.binance.com/api/v3/avgPrice")
                        .queryString("symbol", config.getString("symbol"))
                        .asJsonAsync(response -> {
                            String crypto = config.getString("crypto");
                            int code = response.getStatus();
                            JsonNode body = response.getBody();
                            if (code == 200) {
                                Double price = body.getObject().getDouble("price");
                                jda.getPresence().setActivity(
                                        Activity.watching((price > oldPrice ? " 🟩 " : " 🟥 ") + " " + crypto + ": $"
                                                + new DecimalFormat("#.#" + String.join("", Collections.nCopies(Math.max(0, config.getInt("decimal", 4) - 1), "#"))).format(price))
                                );
                                oldPrice = price;
                            }
                        });

            }
        };

        Timer timer = new Timer("status");
        timer.scheduleAtFixedRate(task, 0, 5000);
    }

    public static YamlFile getConfig() {
        return config;
    }

    public static DatabaseManager getDatabaseManager() {
        return databaseManager;
    }
}
