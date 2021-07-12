package de.gandalf1783.tilegame.states;

import de.gandalf1783.tilegame.Handler;
import de.gandalf1783.tilegame.gfx.Assets;
import de.gandalf1783.tilegame.gfx.Text;

import java.awt.*;

public class LoadingState extends State {

    private Boolean reAuth = true;

    public LoadingState(Handler handler) {
        super(handler);
    }

    @Override
    public void tick() {
        System.out.println("REAUTH PROT");
        try {
            Thread.sleep(0);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        State.setState(new MultiplayerGameState(handler));
    }

    @Override
    public void render(Graphics g) {
        Text.drawString(g, "Please Wait. Map Loading...", 30,30,true, Color.WHITE, Assets.font15);
    }
}
