package com.noideaindustry.cryptotracker.commands;

import com.noideaindustry.cryptotracker.commands.utils.interfaces.ISlashCommand;
import com.noideaindustry.cryptotracker.utils.EmbedUtils;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.interaction.SlashCommandEvent;

import java.awt.*;

public class StatsSlashCommand implements ISlashCommand {
    @Override
    public String getDescription() {
        return "Get stats of the bot";
    }

    @Override
    public void handle(SlashCommandEvent event) {
        EmbedBuilder embed = EmbedUtils.createEmbed(event.getJDA());
        embed.setTitle("Some stats of the bot :")
                .setColor(Color.getColor("#0099ff"))
                .addField("Total Servers", event.getJDA().getGuilds().size() + "", true)
                .addField("Total Users", event.getJDA().getUsers().size() + "", true)
                .addField("Ram usage", Runtime.getRuntime().totalMemory() / 1024 / 1024 + "Mo", true)
                .addField("Need Support ? Follow the news ?", "insert server url here ;)", false)
                .setFooter("CryptoTracker", "insert icon url here ;)");
        event.replyEmbeds(embed.build()).queue();
    }
}
