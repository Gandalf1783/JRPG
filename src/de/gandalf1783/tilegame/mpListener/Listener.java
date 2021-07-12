package de.gandalf1783.tilegame.mpListener;

import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.FrameworkMessage;
import de.gandalf1783.tilegame.Handler;
import de.gandalf1783.tilegame.entities.statics.Cactus;
import de.gandalf1783.tilegame.entities.statics.Rock;
import de.gandalf1783.tilegame.networkhandler.*;
import de.gandalf1783.tilegame.objects.BasicRequest;
import de.gandalf1783.tilegame.objects.BasicResponse;
import de.gandalf1783.tilegame.objects.EntityPKG;
import de.gandalf1783.tilegame.objects.UUIDPos;
import de.gandalf1783.tilegame.states.MultiplayerGameState;
import de.gandalf1783.tilegame.world.Chunk;
import de.gandalf1783.tilegame.discord.DiscordPresence;

import java.util.Map;
import java.util.UUID;

@SuppressWarnings("ALL")
public class Listener extends com.esotericsoftware.kryonet.Listener {

    private Handler handler;

    public Listener(Handler handler) {
        this.handler = handler;

        BasicResponseHandler.setHandler(handler);

        // Register all Commands
        // (Classes register themselves in BasicResponseHandler via method !!!)
        new PlayerAddExecutor("PLAYERADD");
        new WorldExecutor("WORLD");
        new CurrentPlayerExecutor("CURRENT_PLAYERS");
        new MaxPlayerExecutor("MAX_PLAYERS");
        new HealthExecutor("HEALTH");
        new UUIDExecutor("UUID");

        new EntitiesUpdateExecutor("ENTITY_UPDATE");

        new LoadExecutor("LOAD");
        new SaveExecutor("SAVE");
        new ShutdownExecutor("SHUTDOWN");

        new ItemPickupExecutor("ITEM_PICKUP");
        new ItemGroundExecutor("ITEM_GROUND");

        new ReadyToPlayExecutor("RTP");

        new PositionContinueExecutor("POS_SEND_CONTINUE");
        new PositionSendingPauseExecutor("POS_SEND_PAUSE");

    }

    @Override
    public void received(Connection connection, Object o) {
        if(o instanceof Chunk) {
            Chunk c = (Chunk) o;

            MultiplayerGameState.getWorld().setChunk(c.getChunkX(), c.getChunkY(), c);
        } else if(o instanceof BasicResponse) {
            BasicResponse response = (BasicResponse) o;
            BasicRequest request = new BasicRequest();

             // Execute the corresponding action to this command (If Available)
            BasicResponseHandler.executeResponse(response);

            if(response.text.equalsIgnoreCase("NEXT")) {
                if(response.data.equals("1")) {
                    request.text = "ENTITIES?";
                    connection.sendTCP(request);
                    return;
                }
            }

            if(response.text.equalsIgnoreCase("DISCONNECT")) {
                System.out.println("Player "+response.data+" disconnected.");
                MultiplayerGameState.currentPlayers -= 1;
                removeEntity(response.data, handler);
            }

            if(response.text.equalsIgnoreCase("DIE")) {
                System.out.println("Entity "+response.data +" died!");
                removeEntity(response.data, handler);
            }

            if(response.text.equalsIgnoreCase("HIT")) {
                String[] data = response.data.split("#");
                if(MultiplayerGameState.getUUID().equals(UUID.fromString(data[0]))) {
                    handler.getWorld().getEntityManager().getPlayer().hurt(Integer.parseInt(data[1]));
                    return;
                }
                try {
                    MultiplayerGameState.getEntityHashMap().get(getKeyFromValue(MultiplayerGameState.getEntityHashMap(), data[0])).hurt(Integer.parseInt(data[1]));
                } catch (NullPointerException e) {
                }
            }

            // TODO: Add Item Spawning on Map!

        } else if(o instanceof UUIDPos) {
            UUIDPos uPos = (UUIDPos) o;

            if(MultiplayerGameState.getUUID().toString().equalsIgnoreCase(uPos.uuid)) {
                MultiplayerGameState.getWorld().getEntityManager().getPlayer().setX(uPos.p.getX());
                MultiplayerGameState.getWorld().getEntityManager().getPlayer().setY(uPos.p.getY());
                return;
            }

            if(MultiplayerGameState.getEntityHashMap().get(uPos.uuid) == null) {
                if(MultiplayerGameState.getDimensionID() != uPos.p.getDimensionID()) {
                    MultiplayerGameState.setDimensionID(uPos.p.getDimensionID());
                }
                return;
            }

            if(MultiplayerGameState.getDimensionID() != uPos.p.getDimensionID()) return;

            MultiplayerGameState.getEntityHashMap().get(uPos.uuid).setX(uPos.p.getX());
            MultiplayerGameState.getEntityHashMap().get(uPos.uuid).setY(uPos.p.getY());
            MultiplayerGameState.getEntityHashMap().get(uPos.uuid).setDirection(uPos.p.getDirection());
        } else if(o instanceof EntityPKG) {
            EntityPKG e = (EntityPKG) o;
            if(e.type == null)
                return;
            if(e.type.equalsIgnoreCase("CACTUS")) {
                Cactus c = new Cactus(handler, UUID.fromString(e.uuid), e.x, e.y, 64,64);
                c.setHealth(e.health);
                MultiplayerGameState.getEntityHashMap().put(e.uuid, c);
            }
            if(e.type.equalsIgnoreCase("ROCK")) {
                Rock r = new Rock(handler, UUID.fromString(e.uuid), e.x, e.y, true);
                r.setHealth(e.health);
                MultiplayerGameState.getEntityHashMap().put(e.uuid, r);
            }

            // TODO: Add Items that are on the Ground

        } else {
            System.out.println("Received a Package with class "+o.getClass()+" | "+o.getClass().getName());

        }


    }

    @Override
    public void disconnected(Connection connection) {
        MultiplayerGameState.triggerOffline();
    }

    public static Object getKeyFromValue(Map hm, Object value) {
        for (Object o : hm.keySet()) {
            if (hm.get(o).equals(value)) {
                return o;
            }
        }
        return null;
    }

    private static void removeEntity(String uuid, Handler handler) {
        if(MultiplayerGameState.getUUID().equals(UUID.fromString(uuid))) {
            handler.getWorld().getEntityManager().getPlayer().die();
            return;
        }
        try {
            MultiplayerGameState.getEntityHashMap().get(uuid).die();
            MultiplayerGameState.getEntityHashMap().get(uuid).setActive(false);
            MultiplayerGameState.getEntityHashMap().remove(uuid);
        } catch (NullPointerException e) {
        }

    }

    public static void updateDiscordRPC() {
        DiscordPresence.updateMultiplayerNumbers(MultiplayerGameState.currentPlayers, MultiplayerGameState.maxPlayers);
    }
}
