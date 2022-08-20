package com.noideaindustry.cryptotracker.commands.utils.interfaces;

import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.events.interaction.SlashCommandEvent;

import java.util.Collection;
import java.util.Collections;

public interface ISlashCommand {
    default String getName() {
        return this.getClass().getSimpleName().toLowerCase().replaceAll("slashcommand", "");
    }

    String getDescription();

    default String getUsage() {
        return "";
    }

    void handle(SlashCommandEvent event);

    default Collection<Permission> permissionsNeeded() {
        return Collections.emptyList();
    }
}
