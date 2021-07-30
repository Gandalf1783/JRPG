package de.gandalf1783.tilegame.networkhandler;

import de.gandalf1783.tilegame.Handler;
import de.gandalf1783.tilegame.entities.Entity;
import de.gandalf1783.tilegame.entities.statics.Sign201;
import de.gandalf1783.tilegame.entities.statics.StaticEntity;
import de.gandalf1783.tilegame.objects.BasicResponse;
import de.gandalf1783.tilegame.states.MultiplayerGameState;

public class EntityAddExecutor extends BasicResponseExecutor{

    public EntityAddExecutor(String command) {
        super(command);
    }

    @Override
    public void execute(BasicResponse resp, Handler handler) {

    }
}
