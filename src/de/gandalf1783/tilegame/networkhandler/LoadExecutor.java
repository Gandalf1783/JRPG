package de.gandalf1783.tilegame.networkhandler;

import de.gandalf1783.tilegame.Handler;
import de.gandalf1783.tilegame.objects.BasicResponse;
import de.gandalf1783.tilegame.states.MultiplayerGameState;

public class LoadExecutor extends BasicResponseExecutor {
    public LoadExecutor(String command) {
        super(command);
    }

    @Override
    public void execute(BasicResponse resp, Handler handler) {
        MultiplayerGameState.triggerLoad();
    }
}
