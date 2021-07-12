package de.gandalf1783.tilegame.states;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryonet.Client;
import de.gandalf1783.tilegame.Game;
import de.gandalf1783.tilegame.Handler;
import de.gandalf1783.tilegame.gfx.Animation;
import de.gandalf1783.tilegame.gfx.Assets;
import de.gandalf1783.tilegame.gfx.Text;
import de.gandalf1783.tilegame.items.Item;
import de.gandalf1783.tilegame.objects.*;
import de.gandalf1783.tilegame.tiles.Tile;
import de.gandalf1783.tilegame.world.Chunk;
import de.gandalf1783.tilegame.world.World;
import de.gandalf1783.tilegame.discord.DiscordPresence;
import de.gandalf1783.tilegame.entities.Entity;
import de.gandalf1783.tilegame.mpListener.Listener;
import de.gandalf1783.tilegame.threads.NetworkingThread;

import java.awt.*;
import java.beans.XMLDecoder;
import java.beans.XMLEncoder;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.UUID;

public class MultiplayerGameState extends State {

    private static World world;
    private static Client client;
    private Kryo kryo;
    private static String worldData;
    private static NetworkingThread networking = new NetworkingThread();
    private static Thread networkThread ;
    private static Boolean displayDebug = false;
    private static Boolean displayAdvancedDebug = false;
    private static UUID uuid;
    private static Handler handler;
    private static HashMap<String, Entity> entityHashMap = new HashMap<>();
    private static Boolean worldChanged = false;
    private Properties p;
    private static boolean save = false, load = false, offline = false, shutdown = false;
    private Animation saveIcon, loadIcon, offlineIcon, shutdownIcon;
    private int saveCount = 0, loadCount = 0;
    private static int dimensionID;

    private static String IP_ADDR = "localhost";

    public static int currentPlayers, maxPlayers;

    public static final String VERSION = "DEV - 2.4.6";

    private void init() {
        try {
            loadUUID();
            if(p == null) {
                uuid = UUID.randomUUID();
                p = new Properties();
                p.setUuid(uuid.toString());
                saveUUID();
            }

            maxPlayers = 10;
            currentPlayers = 1;

            client = new Client(65536, 32768);
            client.start();
            kryo = client.getKryo();

            //Register classes for kryonet
            kryo.register(BasicRequest.class);
            kryo.register(BasicResponse.class);
            kryo.register(Pos.class);
            kryo.register(UUIDPos.class);
            kryo.register(EntityPKG.class);
            kryo.register(de.gandalf1783.quadtree.Rectangle.class);
            kryo.register(World.class);
            kryo.register(Chunk.class);
            kryo.register(int[][][].class);
            kryo.register(int[][].class);
            kryo.register(int[].class);


            client.connect(5000, IP_ADDR, 54555, 54777);
            client.addListener(new Listener(handler));
            DiscordPresence.init();

            DiscordPresence.updatePresence("Developing new Features...", "Multiplayer on "+IP_ADDR);
            DiscordPresence.updateMultiplayerNumbers(currentPlayers, maxPlayers);


            BasicRequest request = new BasicRequest();

            request.text = "LOGIN?";
            request.data = uuid.toString();
            client.sendTCP(request);

            System.out.println("[INFO] Loading World");
            saveIcon = new Animation(350, Assets.saveAnim, true);
            loadIcon = new Animation(100, Assets.loadingAnim, true);
            offlineIcon = new Animation(1000, Assets.offlineAnim, true);
            shutdownIcon = new Animation(1000, Assets.shutdownAnim, true);
        } catch (IOException e) {
            System.out.println("Could not connect to a Server");
            handler.getGame().resetGame();
        }

    }

    public MultiplayerGameState(Handler handler) {
        super(handler);
        this.handler = handler;
        world = new World(handler);
        handler.setWorld(world);


        init(); // Initiates the networking and handles some basic Setup


    }

    @Override
    public void tick() {

        //ticking World Entities
        world.tick();
        //ticking Server Entities

        Iterator it = entityHashMap.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry pair = (Map.Entry)it.next();
            Entity e = (Entity) pair.getValue();
            e.tick();
        }

        if(handler.getKeyManager().debug) {
            try {
                displayDebug = !displayDebug;
                Thread.sleep(250);
            } catch (InterruptedException e) {
            }
        }
        if(handler.getKeyManager().advancedDebug) {
            try {
                displayAdvancedDebug = !displayAdvancedDebug;
                Thread.sleep(250);
            } catch (InterruptedException e) {
            }
        }
        if(save){
            saveIcon.tick();
            saveCount++;
        }
        if(saveCount > 120) {
            saveCount = 0;
            save = false;
        }
        if(load) {
            loadIcon.tick();
            loadCount++;
        }
        if(loadCount > 200) {
            loadCount = 0;
            load = false;
        }
        if(offline) {
            offlineIcon.tick();
        }
        if(shutdown) {
            shutdownIcon.tick();
        }

    }

    @Override
    public void render(Graphics g) {
            //Rendering World Entities
            world.render(g);

            if(displayDebug) {
                Iterator it = entityHashMap.entrySet().iterator();
                while (it.hasNext()) {
                Map.Entry pair = (Map.Entry) it.next();
                Entity e = (Entity) pair.getValue();
                    Rectangle r = e.getBounds();
                    Color c = g.getColor();
                    g.setColor(Color.GREEN);
                    g.drawRect((int) r.getX()  - (int) world.getHandler().getGameCamera().getxOffset(), (int) r.getY() - (int) world.getHandler().getGameCamera().getyOffset(), r.width, r.height );
                    g.setColor(c);
                }
            }

            if(displayDebug) {

                int x = (int) world.getEntityManager().getPlayer().getX();
                int y = (int) world.getEntityManager().getPlayer().getY();
                int width = world.getEntityManager().getPlayer().getWidth();
                int height = world.getEntityManager().getPlayer().getHeight();
                int chunkX = (int) Math.ceil(x/53/16)+ handler.getWorld().getWorldChunkSize()/2;
                int chunkY = (int) Math.ceil(y/53/16)+ handler.getWorld().getWorldChunkSize()/2;

                int NETWORK_PING = 0;
                String NETWORK_IP = "UNKNOWN";

                if(MultiplayerGameState.getClient().getRemoteAddressTCP() != null) {
                    NETWORK_PING = MultiplayerGameState.getClient().getReturnTripTime();
                    NETWORK_IP = MultiplayerGameState.getClient().getRemoteAddressTCP().toString().split(":")[0].replace("/", "");
                }

                Text.drawString(g, "X/Y:  "+x+" | "+y, 10, 12, false, Color.WHITE, Assets.font15);
                Text.drawString(g, "Block: "+ x/ Tile.TILEWIDTH+" | "+y/Tile.TILEHEIGHT  , 10, 24, false, Color.WHITE, Assets.font15);
                Text.drawString(g, "HP: " +world.getEntityManager().getPlayer().getHealth(), 10, 36, false, Color.WHITE, Assets.font15);
                Text.drawString(g, "COLIDE E: "+world.getEntityManager().getPlayer().checkEntityCollisions(world.getEntityManager().getPlayer().getxMove(), world.getEntityManager().getPlayer().getyMove()), 10, 48, false, Color.WHITE, Assets.font15);
                Text.drawString(g, "COLIDE T: "+world.getEntityManager().getPlayer().collisionWithTile(x+ (int) world.getEntityManager().getPlayer().getxMove(),  y+ (int) world.getEntityManager().getPlayer().getyMove()), 10, 60, false, Color.WHITE, Assets.font15);

                if(uuid != null) Text.drawString(g, "UUID: "+uuid.toString(), 10, 72, false, Color.WHITE, Assets.font15);

                Text.drawString(g, "FPS :"+ Game.getFPS()+"/60", 10, 84,false, Color.white, Assets.font15);
                Text.drawString(g, "Dimension: "+getDimensionID(), 10,96,false, Color.white, Assets.font15);
                Text.drawString(g, "Connected: "+(!offline), 10,108,false, Color.white, Assets.font15);
                Text.drawString(g, "Selected Inv. Slot: "+world.getEntityManager().getPlayer().getInventory().getSelectedIndex(), 10, 120, false, Color.white, Assets.font15);

                if(world.getEntityManager().getPlayer().getInventory().getInvSize() != 0) {
                    String item = world.getEntityManager().getPlayer().getInventory().getSelectedItem().getName();
                    Text.drawString(g, "Selected Item: "+item, 10, 132, false, Color.WHITE, Assets.font15);
                }

                Text.drawString(g, "Number of Items: "+world.getItemManager().getItems().size(), 10,144, false, Color.white, Assets.font15);
                Text.drawString(g, "Number of Entities: "+world.getEntityManager().getEntities().size(), 10, 156, false, Color.WHITE, Assets.font15);
                Text.drawString(g, "Server / Ping: "+NETWORK_IP+" @ "+NETWORK_PING+"ms", 10, 168, false, Color.WHITE, Assets.font15);

                Text.drawString(g, "Chunk: "+chunkX+" / "+chunkY, 10, 192, false, Color.WHITE, Assets.font15);

                Color c = g.getColor();
                g.setColor(Color.CYAN);
                g.drawRect(x  - (int) world.getHandler().getGameCamera().getxOffset(), y - (int) world.getHandler().getGameCamera().getyOffset(), width, height );
                g.setColor(Color.red);
                g.drawRect(x  - (int) world.getHandler().getGameCamera().getxOffset() + world.getEntityManager().getPlayer().getBounds().x, y - (int) world.getHandler().getGameCamera().getyOffset() + world.getEntityManager().getPlayer().getBounds().y, world.getEntityManager().getPlayer().getBounds().width, world.getEntityManager().getPlayer().getBounds().height);

                Iterator it = MultiplayerGameState.getEntityHashMap().entrySet().iterator();
                while (it.hasNext()) {
                    Map.Entry pair = (Map.Entry)it.next();
                    Entity e = (Entity) pair.getValue();
                    Graphics2D g2 = (Graphics2D) g;
                    g2.setColor(Color.CYAN);
                    g2.drawRect((int) e.getX()  - (int) world.getHandler().getGameCamera().getxOffset(), (int) e.getY() - (int) world.getHandler().getGameCamera().getyOffset(), e.getWidth(), e.getHeight());
                    g2.setColor(Color.green);
                    if(e.isSolid()) g2.setColor(Color.red);
                    g2.drawRect((int) e.getX()  - (int) world.getHandler().getGameCamera().getxOffset() + e.getBounds().x, (int) e.getY() - (int) world.getHandler().getGameCamera().getyOffset() + e.getBounds().y, e.getBounds().width, e.getBounds().height);
                }
                for(Item i : MultiplayerGameState.getWorld().getItemManager().getItems()) {
                    Graphics2D g2 = (Graphics2D) g;
                    g2.setColor(Color.PINK);
                    g2.drawRect(i.getX()  - (int) world.getHandler().getGameCamera().getxOffset(), i.getY()  - (int) world.getHandler().getGameCamera().getyOffset(), i.getBounds().width ,i.getBounds().height);
                    Text.drawString(g2, i.getCount()+"" ,i.getX()  - (int) world.getHandler().getGameCamera().getxOffset(), i.getY()  - (int) world.getHandler().getGameCamera().getyOffset(), false, Color.blue, Assets.font15);
                }
                g.setColor(c);

            }
        if(save) {
            g.drawImage(saveIcon.getCurrentFrame(), 685,10, 35, 37,null);
        }
        if(load) {
            g.drawImage(loadIcon.getCurrentFrame(), 685,10, 35, 37,null);
        }
        if(offline) {
            if(shutdown) {
                g.drawImage(shutdownIcon.getCurrentFrame(), 685,10,45,45,null);
            } else {
                g.drawImage(offlineIcon.getCurrentFrame(), 685, 10, 45, 45, null);
            }
        }
    }

    public static void setWorldData(String worldData1) {
        System.out.println("SETTING WORLD DATA");
        if(worldData != null) {
            worldData = worldData1;
            System.out.println("World should be loaded");
            return;
        }
        worldData = worldData1;

    }

    private void loadUUID() {
        try {
            String path = System.getProperty("user.dir");
            path = path + "\\properties.dat";
            FileInputStream fis = null;
            fis = new FileInputStream(path);
            XMLDecoder xml = new XMLDecoder(fis);
            System.out.println("Loading...: " + path);
            Properties p1 = (Properties) xml.readObject();
            p = p1;
            uuid = UUID.fromString(p.getUuid());
            xml.close();
            fis.close();
            System.out.println("Loaded.");
        } catch (FileNotFoundException e) {
            System.out.println("EXCEPTION: " + e.getMessage());
            System.out.println("This should only occur once.");
        } catch (IOException e) {
            System.out.println("EXCEPTION: " + e.getMessage());
        }
    }

    private void saveUUID() {
        try {
            String path = System.getProperty("user.dir");
            FileOutputStream fos = new FileOutputStream(path + "\\" + "properties" + ".dat");
            XMLEncoder xml = new XMLEncoder(fos);
            xml.writeObject(p);
            xml.close();
            fos.close();
            System.out.println("Multiplayer Properties saved.");
        } catch (IOException e) {
            System.out.println("An Error occured while saving Multiplayer Properties.");
        }
        MultiplayerGameState.setUUID(UUID.randomUUID());
    }


    public static void startNetworkingThread() {
        networking.setClient(client);
        networking.setHandler(handler);
        networkThread = new Thread(networking);
        networkThread.start();
    }

    public void handleEntityHit(String uuid, int amount) {
        BasicRequest request = new BasicRequest();
        request.text = "HIT";
        request.data = uuid+"#"+amount;
        client.sendTCP(request);
    }

    public static void handlePlayerDead() {
        YouDiedState diedState = new YouDiedState(handler, displayDebug, entityHashMap, uuid);
        setState(diedState);
    }
    public static HashMap<String, Entity> getEntityHashMap() {
        return entityHashMap;
    }
    public static void setUUID(UUID uuid1) {
        uuid = uuid1;
    }
    public static UUID getUUID() {
        return uuid;
    }
    public static Client getClient() {
        return client;
    }
    public static void triggerSave() {
        save = true;
    }
    public static void triggerLoad() {
        load = true;
    }
    public static void triggerOffline() {
        offline = true;
    }
    public static void triggerShutdown() {
        shutdown = true;
    }
    public static Boolean getAdvancedDebug() {
        return displayAdvancedDebug;
    }
    public static int getDimensionID() {
        return dimensionID;
    }
    public static void setDimensionID(int dimension) {
        dimensionID = dimension;
    }
    public static Handler getHandler() {
        return handler;
    }
    public static Boolean isConnected() {
        return !offline;
    }

    public static boolean isServerAvailable() {
        try {
            client = new Client();
            client.start();
            client.connect(1000, IP_ADDR, 54555, 54777);
            client.close();
            return true;
        } catch (IOException e) {
            System.out.println("No Server available");
            return false;
        }
    }

    public static World getWorld() {
        return world;
    }

    public static NetworkingThread getNetworking() {
        return networking;
    }

    public static void restartNetworking() {
        networking = new NetworkingThread();
        networking.setHandler(handler);
        networking.setClient(client);
        networkThread = new Thread(networking);
        networkThread.start();
    }
}
