package de.gandalf1783.tilegame.networkhandler;

import de.gandalf1783.tilegame.Handler;
import de.gandalf1783.tilegame.objects.BasicResponse;
import de.gandalf1783.tilegame.states.MultiplayerGameState;

import java.util.UUID;

public class HealthExecutor extends BasicResponseExecutor {


    public HealthExecutor(String command) {
        super(command);
    }

    @Override
    public void execute(BasicResponse resp, Handler handler) {
        String[] data = resp.data.split("#");
        if(MultiplayerGameState.getUUID().equals(UUID.fromString(data[0]))) {
            handler.getWorld().getEntityManager().getPlayer().setHealth(Integer.parseInt(data[1]));
        } else {
            if(MultiplayerGameState.getEntityHashMap().get(data[0]) == null) return;
            MultiplayerGameState.getEntityHashMap().get(data[0]).setHealth(Integer.parseInt(data[1]));
        }
    }
}
