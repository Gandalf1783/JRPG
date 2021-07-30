package de.gandalf1783.tilegame.networkhandler;

import de.gandalf1783.tilegame.Handler;
import de.gandalf1783.tilegame.entities.creatures.RemotePlayer;
import de.gandalf1783.tilegame.objects.BasicResponse;
import de.gandalf1783.tilegame.states.MultiplayerGameState;
import de.gandalf1783.tilegame.discord.DiscordPresence;

import java.util.UUID;

public class PlayerAddExecutor extends BasicResponseExecutor{

    public PlayerAddExecutor(String command) {
        super(command);
    }

    @Override
    public void execute(BasicResponse resp, Handler handler) {
        System.out.println("Creating new Player....");

        String data = resp.data;
        String[] dataArray = data.split("#");
        System.out.println(data);
        System.out.println(dataArray.length);
        if(dataArray.length != 6)
            return;
        String xPos = dataArray[0];
        String yPos = dataArray[1];
        String health = dataArray[2];
        UUID uuid = UUID.fromString(dataArray[3]);
        int dimensionID = Integer.parseInt(dataArray[4]);
        String name = dataArray[5];
        System.out.println("NAME: "+name);

        RemotePlayer p = new RemotePlayer(handler, Float.parseFloat(xPos), Float.parseFloat(yPos));
        p.setName(name);
        p.setHealth(Integer.parseInt(health));
        p.setUUID(uuid);

        MultiplayerGameState.getEntityHashMap().put(uuid.toString(), p);
        DiscordPresence.updateMultiplayerNumbers(MultiplayerGameState.currentPlayers, MultiplayerGameState.maxPlayers);
    }
}
