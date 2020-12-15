package com.autohomie3;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;

import javax.security.auth.login.LoginException;

public class Main {
    public static void main(String[] args) throws LoginException {
        String token = "";
        long roastingChannelId = 0;
        try {
            token = args[0];
            roastingChannelId = Long.parseLong(args[1]);
        } catch (ArrayIndexOutOfBoundsException | NumberFormatException e) {
            System.err.println("Usage: [Executable] [Bot token] [Roasting Channel ID]");
            System.exit(1);
        }

        JDA jda = JDABuilder.createDefault(token).build();
        jda.addEventListener(new RoastingListener(roastingChannelId));
        jda.addEventListener(new DeleteUntilListener());
    }
}
