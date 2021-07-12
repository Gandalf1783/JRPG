package de.gandalf1783.tilegame.networkhandler;

import de.gandalf1783.tilegame.Handler;
import de.gandalf1783.tilegame.objects.BasicResponse;

import java.util.HashMap;

public class BasicResponseHandler {

    private static Handler handler;

    private static final HashMap<String, BasicResponseExecutor> executorHashMap = new HashMap<>();

    public static void setHandler(Handler handler1) {
        handler = handler1;
    }

    public static void executeResponse(BasicResponse resp) {
        if(!executorHashMap.containsKey(resp.text)) {
            System.out.println("Response \""+resp.text+"\" is not known.");
            return;
        }
        executorHashMap.get(resp.text).execute(resp, handler);
    }

    public static void registerResponse(String command, BasicResponseExecutor executor) {
        executorHashMap.put(command, executor);
        System.out.println("Registered Response for \""+command+"\"");
    }

}
