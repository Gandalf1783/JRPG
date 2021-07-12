package de.gandalf1783.tilegame.discord;

import net.arikia.dev.drpc.DiscordUser;
import net.arikia.dev.drpc.callbacks.ReadyCallback;

public class ReadyEvent implements ReadyCallback {
  @Override
  public void apply(DiscordUser discordUser) {
    System.out.println("Discord is Ready!!");
  }
}