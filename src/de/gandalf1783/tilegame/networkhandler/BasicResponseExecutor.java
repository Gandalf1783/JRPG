package de.gandalf1783.tilegame.networkhandler;

import de.gandalf1783.tilegame.Handler;
import de.gandalf1783.tilegame.objects.BasicResponse;

public class BasicResponseExecutor {


    public BasicResponseExecutor(String command) {
        BasicResponseHandler.registerResponse(command, this);
    }

    public void execute(BasicResponse resp, Handler handler) {
    }

}
