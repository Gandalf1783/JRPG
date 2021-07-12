package de.gandalf1783.tilegame.states;

import de.gandalf1783.tilegame.Handler;
import de.gandalf1783.tilegame.gfx.Assets;
import de.gandalf1783.tilegame.gfx.Text;

import java.awt.*;

public class ErrorState extends State {

    private String ERR_CODE;
    private String ERR_STRING;

    public ErrorState(Handler handler, String ERR_CODE, String ERR_STRING) {
        super(handler);
        this.ERR_STRING = ERR_STRING;
        this.ERR_CODE = ERR_CODE;
    }

    @Override
    public void tick() {

    }

    @Override
    public void render(Graphics g) {
        Text.drawString(g, "An error occured.", 10,30, false, Color.BLACK, Assets.font23);
        Text.drawString(g, ERR_STRING, 10,90, false, Color.BLACK, Assets.font23);
        Text.drawString(g, "Code: "+ERR_CODE, 10,130, false, Color.BLACK, Assets.font23);
        Text.drawString(g, "Please restart the game and try again.", 10, 250, false, Color.BLACK, Assets.font23);
    }
}
