package de.gandalf1783.tilegame.discord;

import net.arikia.dev.drpc.DiscordEventHandlers;
import net.arikia.dev.drpc.DiscordRPC;
import net.arikia.dev.drpc.DiscordRichPresence;
import net.arikia.dev.drpc.DiscordUser;

import java.util.Random;

public class DiscordPresence {

    private static DiscordEventHandlers handlers;
    private static DiscordRichPresence presence;

    public static void init() {
         handlers = new DiscordEventHandlers.Builder().setReadyEventHandler((user) -> {
            System.out.println("Welcome " + user.username + "#" + user.discriminator + "!");
        })


                 .setJoinGameEventHandler((String str ) -> {
                     System.out.println("Game Event: "+str);
                 })
                 .setJoinRequestEventHandler((DiscordUser user) -> {
                     System.out.println("JOINREQUEST!");
                     System.out.println("Player wanna join! "+user.username+" | "+user.userId);
                 })
                 .build();

        DiscordRPC.discordInitialize("854465969397432380", handlers,true);

        presence = new DiscordRichPresence.Builder("Initialising...").build();
        presence.largeImageKey = "background";
        DiscordRPC.discordUpdatePresence(presence);

        new Thread(() -> { // Lambda Expression
            while (true) {
                DiscordRPC.discordRunCallbacks();
                try {
                    Thread.sleep(500);
                } catch(Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }



    public static void updatePresence(String str1, String str2) {
        presence.state = str1;
        presence.details = str2;
        DiscordRPC.discordUpdatePresence(presence);
    }

    public static void updateMultiplayerNumbers(int current, int max)  {
        presence.partySize = current;
        presence.partyMax = max;
        DiscordRPC.discordUpdatePresence(presence);
    }

    public static void addMultiplayerKey(String str1, String str2) {
        presence.partyId=generateRandomString(10, "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz");
    }

    private static String generateRandomString(int length, String seedChars) {
        StringBuilder sb = new StringBuilder();
        int i = 0;
        Random rand = new Random();
        while (i < length) {
            sb.append(seedChars.charAt(rand.nextInt(seedChars.length())));
            i++;
        }
        return sb.toString();
    }
}
