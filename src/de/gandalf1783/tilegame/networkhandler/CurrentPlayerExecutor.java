package de.gandalf1783.tilegame.networkhandler;

import de.gandalf1783.tilegame.Handler;
import de.gandalf1783.tilegame.objects.BasicResponse;
import de.gandalf1783.tilegame.states.MultiplayerGameState;
import de.gandalf1783.tilegame.discord.DiscordPresence;

public class CurrentPlayerExecutor extends BasicResponseExecutor {


    public CurrentPlayerExecutor(String command) {
        super(command);
    }

    @Override
    public void execute(BasicResponse resp, Handler handler) {
        int current_players = Integer.parseInt(resp.data);
        System.out.println("CURRENT PLAYERS: "+current_players);
        MultiplayerGameState.currentPlayers = current_players;
        DiscordPresence.updateMultiplayerNumbers(MultiplayerGameState.currentPlayers, MultiplayerGameState.maxPlayers);
    }
}
