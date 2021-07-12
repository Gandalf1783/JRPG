package de.gandalf1783.tilegame.networkhandler;

import com.sun.org.apache.xpath.internal.operations.Mult;
import de.gandalf1783.tilegame.Handler;
import de.gandalf1783.tilegame.objects.BasicResponse;
import de.gandalf1783.tilegame.states.MultiplayerGameState;

public class PositionSendingPauseExecutor extends BasicResponseExecutor {

    public PositionSendingPauseExecutor(String command) {
        super(command);
    }

    @Override
    public void execute(BasicResponse resp, Handler handler) {
        MultiplayerGameState.getNetworking().setShouldRun(false);
    }
}
