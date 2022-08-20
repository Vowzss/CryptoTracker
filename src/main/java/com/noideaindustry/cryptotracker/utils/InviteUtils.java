package com.noideaindustry.cryptotracker.utils;

import com.noideaindustry.cryptotracker.database.Requests;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Invite;
import net.dv8tion.jda.api.entities.TextChannel;

public class InviteUtils {
    public static String createInvite(Guild guild) {
        String invite = Requests.getInvite(guild.getId());
        if (invite == null) {
            if (guild.getSelfMember().hasPermission(Permission.VIEW_CHANNEL) && guild.getSelfMember().hasPermission(Permission.CREATE_INSTANT_INVITE)) {
                Invite invitation = null;
                for (TextChannel channel : guild.getTextChannels()) {
                    if (guild.getSelfMember().hasPermission(channel, Permission.CREATE_INSTANT_INVITE, Permission.VIEW_CHANNEL)) {
                        invitation = channel.createInvite().setTemporary(false).complete();
                        break;
                    }
                }
                if (invitation != null) {
                    invite = invitation.getUrl();
                    Requests.insertInvite(guild.getId(), invite);
                }
            }
        }
        return invite;
    }
}
