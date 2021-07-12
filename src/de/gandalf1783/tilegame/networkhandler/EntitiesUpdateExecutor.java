package de.gandalf1783.tilegame.networkhandler;

import de.gandalf1783.tilegame.Handler;
import de.gandalf1783.tilegame.objects.BasicRequest;
import de.gandalf1783.tilegame.objects.BasicResponse;
import de.gandalf1783.tilegame.states.MultiplayerGameState;

public class EntitiesUpdateExecutor extends BasicResponseExecutor {

    public EntitiesUpdateExecutor(String command) {
        super(command);
    }

    @Override
    public void execute(BasicResponse resp, Handler handler) {
        BasicRequest request = new BasicRequest();
        request.text = "ENTITIES?";
        request.data = "";
        MultiplayerGameState.getClient().sendTCP(request);
    }
}
