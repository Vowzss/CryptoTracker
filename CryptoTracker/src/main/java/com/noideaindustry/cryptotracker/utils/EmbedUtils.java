package com.noideaindustry.cryptotracker.utils;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.JDA;

import java.awt.*;
import java.time.Instant;
import java.util.Random;

public class EmbedUtils {
    public static EmbedBuilder createEmbed(JDA jda) {
        return new EmbedBuilder().setFooter(jda.getSelfUser().getAsTag(), jda.getSelfUser().getAvatarUrl()).setColor(new Color(new Random().nextFloat(), new Random().nextFloat(), new Random().nextFloat())).setTimestamp(Instant.now());
    }

    public static EmbedBuilder createErrorEmbed(JDA jda, String text) {
        return createEmbed(jda).setColor(Color.RED).setTitle("Error!").setDescription(text);
    }

    public static EmbedBuilder createSuccessEmbed(JDA jda, String text) {
        return createEmbed(jda).setColor(Color.GREEN).setTitle("Success!").setDescription(text);
    }
}
