package com.noideaindustry.cryptotracker.commands.utils;

import com.noideaindustry.cryptotracker.commands.StatsSlashCommand;
import com.noideaindustry.cryptotracker.commands.utils.interfaces.ISlashCommand;
import com.noideaindustry.cryptotracker.utils.ConstantsUtils;
import com.noideaindustry.cryptotracker.utils.EmbedUtils;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.events.interaction.SlashCommandEvent;

import java.util.HashMap;
import java.util.Map;

public class HandlerSlashCommand {
    private final Map<String, ISlashCommand> slashCommands = new HashMap<>();

    public HandlerSlashCommand() {
        addSlashCommand(new StatsSlashCommand());
    }

    private void addSlashCommand(ISlashCommand slashCommand) {
        if (!slashCommands.containsKey(slashCommand.getName())) {
            slashCommands.put(slashCommand.getName(), slashCommand);
        }
    }

    public void handleSlashCommand(SlashCommandEvent event) {
        final String invoke = event.getName();

        if (slashCommands.containsKey(invoke)) {
            ISlashCommand cmd = getSlashCommand(invoke);
            if (event.getMember() != null) {
                if (!event.getMember().hasPermission(cmd.permissionsNeeded()) && !event.getUser().getId().equals(ConstantsUtils.OWNER_ID)) {
                    event.reply("You don't have permission to do that!").queue();
                    return;
                }
            }

            try {
                cmd.handle(event);
            } catch (Exception e) {
                event.replyEmbeds(EmbedUtils.createErrorEmbed(event.getJDA(), "An error occurred when executing this command!\n" + e.getMessage()).build()).queue();
            }
        }
    }

    public ISlashCommand getSlashCommand(String name) {
        return slashCommands.get(name);
    }

    public Map<String, ISlashCommand> getSlashCommands() {
        return slashCommands;
    }

    public void registerSlashCommands(JDA jda) {
        getSlashCommands().forEach((name, slashCommand) -> jda.upsertCommand(slashCommand.getName(), slashCommand.getDescription()).queue());
    }
}
