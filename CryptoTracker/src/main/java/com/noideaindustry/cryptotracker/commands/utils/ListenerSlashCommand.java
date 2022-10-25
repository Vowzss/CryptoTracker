package com.noideaindustry.cryptotracker.commands.utils;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.events.interaction.SlashCommandEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;

public class ListenerSlashCommand extends ListenerAdapter {
    private final HandlerSlashCommand handlerSlashCommand;

    public ListenerSlashCommand(JDA jda) {
        handlerSlashCommand = new HandlerSlashCommand();
        handlerSlashCommand.registerSlashCommands(jda);
    }

    @Override
    public void onSlashCommand(@NotNull SlashCommandEvent event) {
        if (event.getUser().isBot()) return;
        handlerSlashCommand.handleSlashCommand(event);
    }
}
