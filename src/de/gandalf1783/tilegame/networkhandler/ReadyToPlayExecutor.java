package de.gandalf1783.tilegame.networkhandler;

import de.gandalf1783.tilegame.Handler;
import de.gandalf1783.tilegame.objects.BasicResponse;
import de.gandalf1783.tilegame.states.MultiplayerGameState;

public class ReadyToPlayExecutor extends BasicResponseExecutor {

    public ReadyToPlayExecutor(String command) {
        super(command);
    }

    @Override
    public void execute(BasicResponse resp, Handler handler) {
        MultiplayerGameState.startNetworkingThread();
        MultiplayerGameState.getClient().updateReturnTripTime();
    }
}
