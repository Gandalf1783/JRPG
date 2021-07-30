package de.gandalf1783.tilegame.world;


import de.gandalf1783.quadtree.Rectangle;
import de.gandalf1783.tilegame.Game;
import de.gandalf1783.tilegame.entities.Entity;
import de.gandalf1783.tilegame.gfx.Assets;
import de.gandalf1783.tilegame.gfx.Text;
import de.gandalf1783.tilegame.objects.BasicRequest;
import de.gandalf1783.tilegame.states.MultiplayerGameState;
import de.gandalf1783.tilegame.states.State;
import de.gandalf1783.tilegame.tiles.Tile;
import de.gandalf1783.tilegame.Handler;
import de.gandalf1783.tilegame.entities.EntityManager;
import de.gandalf1783.tilegame.entities.creatures.Player;
import de.gandalf1783.tilegame.items.ItemManager;

import java.awt.*;
import java.io.Serializable;
import java.util.*;

public class World implements Serializable {

    private Handler handler;
    private EntityManager entityManager;
    private ItemManager itemManager;

    private int worldChunkSize = 50;

    // Chunks are saved in x/y dimensions, no height
    // We have 50 Chunks in each direction
    // 25/25 is middle chunk then
    //            x y
    private Chunk[][] chunks = new Chunk[worldChunkSize][worldChunkSize];

    private final int spawnChunkX = 0, spawnChunkY = 0;

    private long seed;

    private Rectangle boundaries;

    public static ArrayList<String> requestedChunks = new ArrayList<>();

    public static int chunksRendered = 0;
    public static int playerChunkX, playerChunkY;

    public World(Handler handler) {
        this.boundaries = new Rectangle(50*16/2,50*16/2,50*16,50*16);
        this.handler = handler;
        Player p = new Player(handler, 25*16,25*16);
        entityManager = new EntityManager(handler, p);
        itemManager = new ItemManager(handler);
    }

    public void render(Graphics g) {
        chunksRendered = 0;

        int radiusInChunk = 3;

        playerChunkX = (int) entityManager.getPlayer().getX()/53/16;
        playerChunkY = (int) entityManager.getPlayer().getY()/53/16;

        for(int x = 0; x < worldChunkSize; x++) {
            for(int y = 0; y < worldChunkSize; y++) {
                int chunkX = x-(worldChunkSize/2);
                int chunkY = y-(worldChunkSize/2);

                if(chunks[x][y] == null) {
                        Chunk c = new Chunk();

                        c.setChunkX(x);
                        c.setChunkY(y);

                        if(World.requestedChunks.contains(chunkX+"#"+chunkY)) {
                            continue; // Go to next Chunk if this one has already been requested
                        }

                        if(chunkX > playerChunkX-radiusInChunk && chunkY > playerChunkY-radiusInChunk && chunkX < playerChunkX+radiusInChunk && chunkY < playerChunkY+radiusInChunk) {
                            if(State.getState() == handler.getGame().multiplayerGameState) {

                                new Thread(() -> { // request a chunk from the server
                                    BasicRequest request = new BasicRequest();
                                    request.text = "REQUEST_CHUNK";
                                    request.data = chunkX+"#"+chunkY;
                                    MultiplayerGameState.getClient().sendTCP(request);
                                    World.requestedChunks.add(chunkX+"#"+chunkY);
                                    System.out.println("Requested a chunk: "+chunkX+"|"+chunkY);
                                }).start();
                            } else {
                                this.getChunk(chunkX,chunkY); // Generate chunk from client
                            }
                        }

                    continue;
                }

                long calc1 = Math.abs(x)-playerChunkX-(handler.getWorld().getWorldChunkSize()/2);
                long calc2 = Math.abs(y)-playerChunkY-(handler.getWorld().getWorldChunkSize()/2);

                float playerChunkX = (handler.getWorld().getEntityManager().getPlayer().getX())/53/16 +(worldChunkSize/2);
                float playerChunkY = (handler.getWorld().getEntityManager().getPlayer().getY())/53/16+(worldChunkSize/2);


                if(x < playerChunkX+radiusInChunk && x > playerChunkX-radiusInChunk && y < playerChunkY+radiusInChunk && y > playerChunkY-radiusInChunk) {

                    Chunk c = chunks[x][y];

                    int[][][] tileMap = c.getBlocks();

                    for (int tileY = 0; tileY < tileMap[0].length; tileY++) {
                        for (int tileX = 0; tileX < 16; tileX++) {
                            for (int tileZ = 0; tileZ < 16; tileZ++) {

                                float tileRenderX = (c.getChunkX() * 16 * Tile.TILEWIDTH) + (tileX * Tile.TILEWIDTH) - handler.getGameCamera().getxOffset();

                                float tileRenderY = (c.getChunkY() * 16 * Tile.TILEHEIGHT) + (tileZ * Tile.TILEHEIGHT) - handler.getGameCamera().getyOffset();

                                if (tileY <= 1)
                                    Tile.tiles[tileMap[tileX][tileY][tileZ]].render(g, (int) tileRenderX, (int) tileRenderY);

                                if (MultiplayerGameState.getAdvancedDebug() && tileY >= 2) {
                                    g.setColor(Color.yellow);
                                    Text.drawString(g,
                                            tileMap[tileX][1][tileZ] + "",
                                            (int) tileRenderX + Tile.TILEWIDTH / 4,
                                            (int) tileRenderY + Tile.TILEWIDTH / 4, false, Color.RED, Assets.font15);
                                    g.drawRect((int) tileRenderX, (int) tileRenderY, Tile.TILEWIDTH, Tile.TILEHEIGHT);
                                }
                            }
                        }
                    }
                    chunksRendered++;
                }
            }
        }
        entityManager.render(g);
        itemManager.render(g);
    }

    public void tick() {
        try {
            Iterator it = MultiplayerGameState.getEntityHashMap().entrySet().iterator();
            while (it.hasNext()) {
                Map.Entry pair = (Map.Entry) it.next();
                UUID uuid = UUID.fromString((String) pair.getKey());
                Entity e = (Entity) pair.getValue();
                if (e.equals(this)) continue;
                if (!entityManager.getEntities().contains(e)) {
                    entityManager.addEntity(e);
                }
            }
        } catch (ConcurrentModificationException e) {
        }


        for(int x = 0; x < worldChunkSize; x++) {
            for(int y = 0; y < worldChunkSize; y++) {
                if(chunks[x][y] == null)
                    continue;

                Chunk c = chunks[x][y];

                int[][][] tileMap = c.getBlocks();

                for(int tileY = 0; tileY < tileMap[0].length; tileY++) {
                    for(int tileX = 0; tileX < 16; tileX++) {
                        for(int tileZ = 0; tileZ < 16; tileZ++) {
                            Tile.tiles[tileMap[tileX][tileY][tileZ]].tick();
                        }
                    }
                }
            }
        }
        entityManager.tick();
        itemManager.tick();
    }

    public static int[][] generateMap(long seed,int iterations) {
        Generation.SEED = seed;
        Generation.OCEAN_SEED = seed+1;

        double[][] landNoiseMap = Generation.generateNoiseMap(130,iterations);
        double[][] oceanNoiseMap = Generation.generateNoiseMap(120,iterations);

        int[][] landTileMap = Generation.convertValuesToLandWaterMap(landNoiseMap); // Step 1
        int[][] oceanOverlayMap = Generation.convertValuesToLandOceanMap(oceanNoiseMap); // Step 2

        int[][] finalArray = Generation.overlayAllSteps(landTileMap, oceanOverlayMap);

        return finalArray;
    }


    public void getNewMap() {
       System.out.println("Creating a new Map... ");
        long start = System.currentTimeMillis();

        seed = 564981656646512130L; // TODO: Temporary SEED!

        int[][] finalArray = generateMap(this.seed, this.worldChunkSize*16);

        long end = System.currentTimeMillis();
        System.out.println("Took "+(end-start)+" ms to generate!");
    }

    public Chunk getChunk(int x, int y, int[][] tileMapFromNoiseGenerator) {

        int actualChunkX = x+(worldChunkSize/2), actualChunkY = y+(worldChunkSize/2);

        if(!((actualChunkX >= 0 ) && (actualChunkX < worldChunkSize) && (actualChunkY >= 0) && (actualChunkY < worldChunkSize))) {
            return null;
        }

        if(chunks[actualChunkX][actualChunkY] != null) { // If the Chunk exists, return it. Otherwise, generate a new Chunk!
            return chunks[actualChunkX][actualChunkY];
        } else {
            return generateChunk(x, y, tileMapFromNoiseGenerator);
        }
    }

    public Chunk getChunk(int x, int y) {
        int actualChunkX = x+(worldChunkSize/2), actualChunkY = y+(worldChunkSize/2);

        if(!((actualChunkX >= 0 ) && (actualChunkX < worldChunkSize) && (actualChunkY >= 0) && (actualChunkY < worldChunkSize))) {
            return null;
        }

        if(chunks[actualChunkX][actualChunkY] != null) { // If the Chunk exists, return it. Otherwise, generate a new Chunk!
            return chunks[actualChunkX][actualChunkY];
        } else {
            return generateChunk(x, y);
        }
    }

    public Chunk generateChunk(int x, int y, int[][] tileMapFromNoiseGenerator) {
        // TODO: Generate Chunk and return then!
        // TODO: ATTENTION: DO THIS IN A NEW THREAD!!!!
        int actualChunkX = x+(worldChunkSize/2), actualChunkY = y+(worldChunkSize/2);

        Chunk c = new Chunk();

        c.setChunkX(x);
        c.setChunkY(y);

        byte chunkTileX = 0, chunkTileZ = 0; // Only counts to 16, nothing more

        for(int tileX = (actualChunkX)*16; tileX < (1+actualChunkX)*16; tileX++) {
            chunkTileX = 0;
            for(int tileY = (actualChunkY)*16; tileY < (1+actualChunkY)*16; tileY++) {

                c.setBlock(chunkTileX, 0, chunkTileZ, 2); // Set Underlaying Structure as Rock
                c.setBlock(chunkTileX, 1, chunkTileZ, tileMapFromNoiseGenerator[tileX][tileY]); // The one the player sees if isnt air!

                chunkTileX++;
            }

            chunkTileZ++;
        }

        chunks[actualChunkX][actualChunkY] = c; // Saving the chunk as regular Chunk of World here, so it can be loaded later on

        return c;
    }

    public Chunk generateChunk(int x, int y) {
        // TODO: Generate Chunk and return then!
        // TODO: ATTENTION: DO THIS IN A NEW THREAD!!!!
        int actualChunkX = x+(worldChunkSize/2), actualChunkY = y+(worldChunkSize/2);


        int[][] tileMapFromNoiseGenerator = generateMap(this.seed, this.worldChunkSize*16);

        Chunk c = new Chunk();

        c.setChunkX(x);
        c.setChunkY(y);

        for(byte tileInChunkZ = 0; tileInChunkZ < 16; tileInChunkZ++) {
            for(byte tileInChunkX = 0; tileInChunkX < 16; tileInChunkX++) {
                int tile = tileMapFromNoiseGenerator[(actualChunkX*16) + tileInChunkX][(actualChunkY*16) + tileInChunkZ];
                c.setBlock(tileInChunkX, 0, tileInChunkZ, 2);
                c.setBlock(tileInChunkX, 1, tileInChunkZ, tile);
            }
        }

        chunks[actualChunkX][actualChunkY] = c; // Saving the chunk as regular Chunk of World here, so it can be loaded later on
        return c;
    }

    public Chunk[] getSpawnChunks() {
        int radius = 3;
        int i = 0;

        int xIndexOffset = worldChunkSize/2;
        int yIndexOffset = worldChunkSize/2;

        Chunk[] spawnchunks = new Chunk[radius*radius*4];

        for(int x = spawnChunkX-radius; x < spawnChunkX+radius; x++) {
            for(int y = spawnChunkY-radius; y < spawnChunkY+radius; y++) {
                spawnchunks[i] = getChunk(x,y);
                i++;
            }
        }

        return spawnchunks;
    }

    public boolean isChunkGenerated(int chunkX, int chunkY) {

        int indexOffset = worldChunkSize/2;
        if(chunkX+indexOffset < worldChunkSize || chunkY+indexOffset < worldChunkSize || chunkX+indexOffset > worldChunkSize || chunkY+indexOffset > worldChunkSize) {
            return true;
        }
        return (this.chunks[chunkX+indexOffset][chunkY+indexOffset] != null);
    }

    public int getWorldChunkSize() {
        return worldChunkSize;
    }

    public void setWorldChunkSize(int worldChunkSize) {
        this.worldChunkSize = worldChunkSize;
    }

    public Chunk[][] getChunks() {
        return chunks;
    }

    public void setChunks(Chunk[][] chunks) {
        this.chunks = chunks;
    }

    public int getSpawnChunkX() {
        return spawnChunkX;
    }

    public int getSpawnChunkY() {
        return spawnChunkY;
    }

    public long getSeed() {
        return seed;
    }

    public void setSeed(long seed) {
        this.seed = seed;
    }

    public Rectangle getBoundaries() {
        return boundaries;
    }

    public void setBoundaries(Rectangle boundaries) {
        this.boundaries = boundaries;
    }

    public void setChunk(int x, int y, Chunk c) {
        int chunkOffset = worldChunkSize/2;
        this.chunks[x+chunkOffset][y+chunkOffset] = c;
    }

    public Handler getHandler() {
        return handler;
    }

    public void setHandler(Handler handler) {
        this.handler = handler;
    }

    public EntityManager getEntityManager() {
        return entityManager;
    }

    public void setEntityManager(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public ItemManager getItemManager() {
        return itemManager;
    }

    public void setItemManager(ItemManager itemManager) {
        this.itemManager = itemManager;
    }


}
