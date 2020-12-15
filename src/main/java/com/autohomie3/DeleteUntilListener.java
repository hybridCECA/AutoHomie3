package com.autohomie3;

import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.*;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import javax.annotation.Nonnull;

// This listener provides a mass deleting function
// Operated by sending "!delete until [message id]"
// If person A sent the message with [message id], then all messages sent by person A since that message are deleted
// Mostly used for deleting spam
public class DeleteUntilListener extends ListenerAdapter {
    final static int MESSAGE_LIMIT = 100;

    @Override
    public void onGuildMessageReceived(@Nonnull GuildMessageReceivedEvent event) {
        String content = event.getMessage().getContentDisplay();
        if (content.toLowerCase().contains("!delete until")) {
            // Test for manage message permissions
            TextChannel textChannel = event.getChannel();
            Member commandSender = event.getMember();
            if (!commandSender.hasPermission(textChannel, Permission.MESSAGE_MANAGE)) {
                return;
            }

            // Command succeeded, delete command
            event.getMessage().delete().queue();

            // Get message id from content
            String[] words = content.split(" ");
            long messageId = 0;
            for (int i = 0; i < words.length; i++) {
                if (words[i].equalsIgnoreCase("until")) {
                    try {
                        messageId = Long.parseLong(words[i + 1]);
                    } catch (ArrayIndexOutOfBoundsException e) {
                        return;
                    }
                }
            }

            // Get history since the spam message
            Message spamMessage = textChannel.retrieveMessageById(messageId).complete();
            long spamUserId = spamMessage.getAuthor().getIdLong();
            MessageHistory history = textChannel.getHistoryAfter(messageId, MESSAGE_LIMIT).complete();

            // Delete all messages by person A since that message
            for (Message message : history.getRetrievedHistory()) {
                if (message.getAuthor().getIdLong() == spamUserId) {
                    message.delete().queue();
                }
            }
            spamMessage.delete().queue();
        }
    }

}
