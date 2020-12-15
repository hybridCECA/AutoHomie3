package com.autohomie3;

import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.events.message.guild.GuildMessageUpdateEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import javax.annotation.Nonnull;

/*
    This listener deletes messages in a specific channel if the message contains lowercase characters.
    Also deletes them if edited to contain lowercase characters.
 */
public class RoastingListener extends ListenerAdapter {
    private long roastingChannelId;

    public RoastingListener(long roastingChannelId) {
        this.roastingChannelId = roastingChannelId;
    }

    @Override
    public void onGuildMessageUpdate(@Nonnull GuildMessageUpdateEvent event) {
        onCheckMessage(event.getChannel(), event.getMessage());
    }

    @Override
    public void onGuildMessageReceived(@Nonnull GuildMessageReceivedEvent event) {
        onCheckMessage(event.getChannel(), event.getMessage());
    }

    private void onCheckMessage(TextChannel channel, Message message) {
        if (channel.getIdLong() == roastingChannelId) {
            if (hasLowercase(message.getContentDisplay())) {
                message.delete().queue();
            }
        }
    }

    private static boolean hasLowercase(String s) {
        return !s.equals(s.toUpperCase());
    }
}
