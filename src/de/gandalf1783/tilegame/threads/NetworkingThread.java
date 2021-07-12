package de.gandalf1783.tilegame.threads;

import com.esotericsoftware.kryonet.Client;
import de.gandalf1783.tilegame.Handler;
import de.gandalf1783.tilegame.entities.creatures.Player;
import de.gandalf1783.tilegame.states.MultiplayerGameState;
import de.gandalf1783.tilegame.objects.Pos;


public class NetworkingThread implements Runnable {

    private Handler handler;
    private Pos pos = new Pos();
    private Client client;
    private boolean shouldRun = true;

    public NetworkingThread() {
    }

    @Override
    public void run() {
        while (shouldRun) {
            if(MultiplayerGameState.getHandler().getWorld().getEntityManager().getPlayer().isActive()) {
                if(pos == null)
                    continue;
                pos.setX(handler.getWorld().getEntityManager().getPlayer().getX());
                pos.setY(handler.getWorld().getEntityManager().getPlayer().getY());
                float xMove = handler.getWorld().getEntityManager().getPlayer().getxMove();
                float yMove = handler.getWorld().getEntityManager().getPlayer().getyMove();
                pos.setDirection(handler.getWorld().getEntityManager().getPlayer().getDirection(xMove, yMove));
                pos.setDimensionID(MultiplayerGameState.getDimensionID());
                client.sendUDP(pos);

                try {
                    Thread.sleep(5);
                } catch (InterruptedException e) {
                }
            } else {
                try {
                    Thread.sleep(30);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * Sets the Network Client
     * @param client Network Client (Kryo)
     */
    public void setClient(Client client) {
        this.client = client;
    }

    /**
     * Sets the Handler of the Game
     * @param handler Gamehandler Intance
     */
    public void setHandler(Handler handler) {
        this.handler = handler;
    }



    public void setShouldRun(boolean shouldRun) {
        this.shouldRun = shouldRun;
    }

    public boolean getShouldRun() {
        return shouldRun;
    }
}
