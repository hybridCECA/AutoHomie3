package com.autohomie3;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;

import javax.security.auth.login.LoginException;

public class Main {
    public static void main(String[] args) throws LoginException {
        String token = "";
        try {
            token = args[0];
        } catch (ArrayIndexOutOfBoundsException e) {
            System.err.println("Usage: [Executable] [Bot token]");
            System.exit(1);
        }

        JDA jda = JDABuilder.createDefault(token).build();
        jda.addEventListener(new RoastingListener(720797181258498209L));
        jda.addEventListener(new DeleteUntilListener());
    }
}
