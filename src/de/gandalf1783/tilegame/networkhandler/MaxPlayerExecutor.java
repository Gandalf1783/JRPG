package de.gandalf1783.tilegame.networkhandler;

import de.gandalf1783.tilegame.Handler;
import de.gandalf1783.tilegame.objects.BasicResponse;
import de.gandalf1783.tilegame.states.MultiplayerGameState;
import de.gandalf1783.tilegame.discord.DiscordPresence;

public class MaxPlayerExecutor extends BasicResponseExecutor {


    public MaxPlayerExecutor(String command) {
        super(command);
    }

    @Override
    public void execute(BasicResponse resp, Handler handler) {
        int max_players = Integer.parseInt(resp.data);
        System.out.println("MAX_PLAYERS: "+max_players);
        MultiplayerGameState.maxPlayers = max_players;
        DiscordPresence.updateMultiplayerNumbers(MultiplayerGameState.currentPlayers, MultiplayerGameState.maxPlayers);
    }
}
