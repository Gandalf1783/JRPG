package de.gandalf1783.tilegame.networkhandler;

import de.gandalf1783.tilegame.Handler;
import de.gandalf1783.tilegame.objects.BasicResponse;

public class UUIDExecutor extends BasicResponseExecutor {

    public UUIDExecutor(String command) {
        super(command);
    }

    @Override
    public void execute(BasicResponse resp, Handler handler) {
        if(resp.data.equalsIgnoreCase("IN_USE")) {
            System.out.println("UUID already connected to the Server. Please use an other computer for this.");
            System.exit(4);
        }
    }
}
