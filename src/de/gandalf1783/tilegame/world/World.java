package de.gandalf1783.tilegame.world;

import de.gandalf1783.quadtree.Rectangle;
import de.gandalf1783.tilegame.entities.Entity;
import de.gandalf1783.tilegame.gfx.Assets;
import de.gandalf1783.tilegame.gfx.Text;
import de.gandalf1783.tilegame.objects.BasicRequest;
import de.gandalf1783.tilegame.objects.Pos;
import de.gandalf1783.tilegame.states.MultiplayerGameState;
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

    public World(Handler handler) {
        this.boundaries = new Rectangle(50*16/2,50*16/2,50*16,50*16);
        this.handler = handler;
        Player p = new Player(handler, 25*16,25*16);
        entityManager = new EntityManager(handler, p);
        itemManager = new ItemManager(handler);
    }

    public void render(Graphics g) {
        int playerChunkX, playerChunkY;

        playerChunkX = (int) entityManager.getPlayer().getX()/53/16;
        playerChunkY = (int) entityManager.getPlayer().getY()/53/16;




        for(int x = 0; x < worldChunkSize; x++) {
            for(int y = 0; y < worldChunkSize; y++) {




                if(chunks[x][y] == null) {

                    int chunkX = x-(worldChunkSize/2);
                    int chunkY = y-(worldChunkSize/2);

                        Chunk c = new Chunk();

                        c.setChunkX(x);
                        c.setChunkY(y);

                        if(World.requestedChunks.contains(chunkX+"#"+chunkY)) {
                            continue; // Go to next Chunk if this ones unavailable
                        }

                        if(chunkX > playerChunkX-3 && chunkY > playerChunkY-3 && chunkX < playerChunkX+3 && chunkY < playerChunkY+3) {
                            new Thread(() -> { // request a chunk from the server
                                BasicRequest request = new BasicRequest();
                                request.text = "REQUEST_CHUNK";
                                request.data = chunkX+"#"+chunkY;
                                MultiplayerGameState.getClient().sendTCP(request);
                                World.requestedChunks.add(chunkX+"#"+chunkY);
                            }).start();
                        }

                    continue;
                }

                Chunk c = chunks[x][y];

                int[][][] tileMap = c.getBlocks();

                for(int tileY = 0; tileY < tileMap[0].length; tileY++) {
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

                if(!this.getEntityManager().getPlayer().getRect().intersects(c.getRect()))
                    continue;

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

    private int[][] generateMap() {
        Generation.SEED = seed;
        Generation.OCEAN_SEED = seed+1;
        double[][] landNoiseMap = Generation.generateNoiseMap(130,700);
        double[][] oceanNoiseMap = Generation.generateNoiseMap(120,900);

        int[][] landTileMap = Generation.convertValuesToLandWaterMap(landNoiseMap);
        int[][] oceanOverlayMap = Generation.convertValuesToLandOceanMap(oceanNoiseMap);

        int[][] finalArray = Generation.overlayAllSteps(landTileMap, oceanOverlayMap);

        return finalArray;
    }

    public void getNewMap() {
       System.out.println("Creating a new Map... ");
        long start = System.currentTimeMillis();

        seed = 564981656646512130L; // TODO: Temporary SEED!

        int[][] finalArray = generateMap();

        long end = System.currentTimeMillis();
    }

    public Chunk getChunk(int x, int y) {

        int xIndex = x+worldChunkSize/2;
        int yIndex = y+worldChunkSize/2;

        if(!((xIndex >= 0 ) && (xIndex < worldChunkSize) && (yIndex >= 0) && (yIndex < worldChunkSize))) {
            Chunk c = new Chunk();
            c.setChunkY(0);
            c.setChunkX(0);
            return c;
        }

        if(chunks[xIndex][yIndex] != null) { // If the Chunk exists, return it. Otherwise, generate a new Chunk!
            return chunks[xIndex][yIndex];
        } else {
            return generateChunk(x, y);
        }
    }

    public Chunk generateChunk(int x, int y) {
        // TODO: Generate Chunk and return then!
        // TODO: ATTENTION: DO THIS IN A NEW THREAD!!!!

        int[][] array = generateMap();

        Chunk c = new Chunk();

        c.setChunkX(x);
        c.setChunkY(y);

        int actualChunkX = x+(worldChunkSize/2), actualChunkY = y+(worldChunkSize/2);

        byte chunkTileX = 0, chunkTileZ = 0;
        for(int tileX = (x+actualChunkX)*16; tileX < (x+1+actualChunkX)*16; tileX++) {
            chunkTileX = 0;
            for(int tileY = (y+actualChunkY)*16; tileY < (y+1+actualChunkY)*16; tileY++) {

                //System.out.println("Going for Tile "+tileX+"|"+tileY+" - "+chunkTileX+"|"+chunkTileZ);

                c.setBlock(chunkTileX, 0, chunkTileZ, 2); // Set Underlaying Structure as Rock
                c.setBlock(chunkTileX, 1, chunkTileZ, array[tileX][tileY]); // The one the player sees if isnt air!
                chunkTileX++;
            }
            chunkTileZ++;
        }
        chunks[x+actualChunkX][y+actualChunkY] = c;
        return c;
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

    public void setChunk(int x, int y, Chunk chunk) {
        this.chunks[x+worldChunkSize/2][y+worldChunkSize/2] = chunk;
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
