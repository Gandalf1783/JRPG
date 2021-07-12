package de.gandalf1783.tilegame.tiles;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

public class Tile {
	
	//STATIC STUFF HERE
	
	public static Tile[] tiles = new Tile[256];
	public static Tile air0 = new Air(0);

	public static Tile dirtTile = new DirtTile(1);
	public static Tile rockTile = new RockTile(2);
	public static Tile sandTile = new SandTile(3);
	public static Tile cobbleTile = new CobbleStoneTile(4);
	public static Tile grassTile = new GrassTile(200);
	public static Tile waterTile = new WaterTile(6);
	public static Tile deepWaterTile = new DeepWaterTile(5);
	public static Tile water7 = new Water7(7);
	public static Tile water8 = new Water8(8);
	public static Tile water9 = new Water9(9);
	public static Tile water10 = new Water10(10);
	public static Tile water11 = new Water11(11);
	public static Tile water12 = new Water12(12);
	public static Tile water13 = new Water13(13);
	public static Tile water14 = new Water14(14);
	public static Tile water15 = new Water15(15);
	public static Tile water16 = new Water16(16);
	public static Tile water17 = new Water17(17);
	public static Tile water18 = new Water18(18);
	public static Tile waterfall19 = new Waterfall19(19);
	public static Tile waterfall20 = new Waterfall20(20);
	public static Tile waterfall21 = new Waterfall21(21);
	public static Tile sand22 = new Sand22(22);
	public static Tile sand23 = new Sand23(23);
	public static Tile sand24 = new Sand24(24);
	public static Tile sand25 = new Sand25(25);
	public static Tile sand26 = new Sand26(26);
	public static Tile sand27 = new Sand27(27);
	public static Tile sand28 = new Sand28(28);
	public static Tile sand29 = new Sand29(29);
	public static Tile sand30 = new Sand30(30);

	public static Tile sand32 = new Sand32(32);

	public static Tile sand35 = new Sand35(35);

	public static Tile sand37 = new Sand37(37);

	public static Tile dungeon110 = new Dungeon110(110);
	public static Tile dungeon111 = new Dungeon111(111);
	public static Tile dungeon112 = new Dungeon112(112);
	public static Tile dungeon113 = new Dungeon113(113);
	public static Tile dungeon114 = new Dungeon114(114);
	public static Tile dungeon115 = new Dungeon115(115);
	public static Tile dungeon116 = new Dungeon116(116);
	public static Tile dungeon117 = new Dungeon117(117);
	public static Tile dungeon118 = new Dungeon118(118);

	public static Tile sand44 = new Sand44(44);
	public static Tile sand45 = new Sand45(45);




	//CLASS
	
	public static final int TILEWIDTH = 53, TILEHEIGHT = 53;
	
	protected BufferedImage texture;
	protected final int id;
	protected boolean isSolid;
	
	public Tile(BufferedImage texture, int id){
		this.texture = texture;
		this.isSolid = false;
		this.id = id;
		
		tiles[id] = this;
	}
	
	public void tick(){

	}
	
	public void render(Graphics g, int x, int y){
		g.drawImage(texture, x, y, TILEWIDTH, TILEHEIGHT, null);
	}
	
	public boolean isSolid(){
		return this.isSolid;
	}
	public void setSolid(boolean solid) {
		this.isSolid = solid;
	}
	
	public int getId(){
		return id;
	}
	
}
