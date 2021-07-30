package de.gandalf1783.tilegame.gfx;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class Assets {
	
	private static final int width = 32, height = 32;
	
	public static Font font50, font28, font15, font23;

	public static ArrayList<BufferedImage> assets = new ArrayList<>();
	
	public static final String ASSETS_VERSION = "ASSETS_1.2.1";

	public static BufferedImage emptyImage;
	public static BufferedImage[] emptyImages;

	public static BufferedImage[] player_down, player_up, player_left, player_right;
	public static BufferedImage[] player_attackDown, player_attackUp, player_attackLeft, player_attackRight;
	public static BufferedImage[] remoteplayer_down, remoteplayer_up, remoteplayer_left, remoteplayer_right;
	public static BufferedImage[] remoteplayer_attackDown, remoteplayer_attackUp, remoteplayer_attackLeft, remoteplayer_attackRight;
	public static BufferedImage[] zombie_down, zombie_up, zombie_left, zombie_right;
	public static BufferedImage[] btn_start;
	public static BufferedImage[] water, water1, deepWater;
	public static BufferedImage[] saveAnim;
	public static BufferedImage[] cactus, stones1;
	public static BufferedImage player_stand, remoteplayer_stand;
	public static BufferedImage inventoryScreen, inventoryActiveTab, inventoryActiveItem;
	public static BufferedImage saveIcon;
	public static BufferedImage dirt, grass, grass1, stone, sand, tree, rock, cobblestone;
	public static BufferedImage sand38, sand39, sand40, sand41, sand42, sand43, sand44, sand45;

	public static BufferedImage wood;
	public static BufferedImage[] water7, water8, water9, water10, water11, water12, water13, water14, water15, water16, water17, water18, water19, water20, water21;
	public static BufferedImage[] sand22, sand23, sand24, sand25, sand26, sand27, sand28, sand29, sand30, sand31, sand32, sand33, sand34, sand35, sand36, sand37;

	public static BufferedImage grass46, grass47, grass48, grass49, grass50, grass51, grass52, grass53, grass61, grass62, grass63, grass64, grass65, grass66, grass67;
	public static BufferedImage brick70, brick71, brick72, brick73, brick74, brick75, brick76, brick77, brick78, brick79, brick80, brick81, brick82, brick83, brick84, brick85;
	public static BufferedImage plank91, plank92, plank93, plank94, plank95, plank96, plank97, plank98, plank99, plank100, plank101;
	public static BufferedImage sign201, sign202, sign203, sign204;
	public static BufferedImage[] loadingAnim, offlineAnim, shutdownAnim;

	public static Image mouse;

	// Cave
	public static BufferedImage[] lavaAnim301;
	public static BufferedImage dungeon110, dungeon111, dungeon112, dungeon113, dungeon114, dungeon115, dungeon116, dungeon117, dungeon118 ;

	public static void init(){

		SpriteSheet sheet = new SpriteSheet(ImageLoader.loadImage("/textures/sheet.png"));
		SpriteSheet ocean = new SpriteSheet(ImageLoader.loadImage("/textures/Ocean_SpriteSheet.png"));
		SpriteSheet saveicon = new SpriteSheet(ImageLoader.loadImage("/textures/saveicon.png"));
		SpriteSheet textures3 = new SpriteSheet(ImageLoader.loadImage("/textures/textures3.png"));
		SpriteSheet textures2 = new SpriteSheet(ImageLoader.loadImage("/textures/textures2.jpg"));
		SpriteSheet mainCharacter = new SpriteSheet(ImageLoader.loadImage("/textures/main_character.png"));
		SpriteSheet remoteCharacter = new SpriteSheet(ImageLoader.loadImage("/textures/remote_character.png"));
		SpriteSheet loadingAnimation = new SpriteSheet(ImageLoader.loadImage("/textures/loading.png"));
		SpriteSheet cave = new SpriteSheet(ImageLoader.loadImage("/textures/cave.png"));
		SpriteSheet offline = new SpriteSheet(ImageLoader.loadImage("/textures/offline.png"));
		SpriteSheet shutdown = new SpriteSheet(ImageLoader.loadImage("/textures/shutdown.png"));
		SpriteSheet ui = new SpriteSheet(ImageLoader.loadImage("/textures/ui_split.png"));

		font50 = FontLoader.loadFont("/fonts/slkscr.ttf", 50);
		font28 = FontLoader.loadFont("/fonts/slkscr.ttf", 28);
		font15 = FontLoader.loadFont("/fonts/slkscr.ttf", 15);
		font23 = FontLoader.loadFont("/fonts/slkscr.ttf", 23);

		emptyImage = loadingAnimation.crop(0,0,1,1);
		emptyImages = new BufferedImage[2];
		emptyImages[0] = emptyImage;
		emptyImages[1] = emptyImage;

		inventoryScreen = ui.crop(5,99, 120, 137);
		inventoryActiveTab = ui.crop(128,101,17,18);
		inventoryActiveItem = ui.crop(127,125,14,14);

		mouse = ui.crop(204,31,8,8);

		//Loading Image:
		loadingAnim = new BufferedImage[7];
		loadingAnim[0] = loadingAnimation.crop(102*0,102*0,102,102);
		loadingAnim[1] = loadingAnimation.crop(102*1,102*0,102,102);
		loadingAnim[2] = loadingAnimation.crop(102*2,102*0,102,102);
		loadingAnim[3] = loadingAnimation.crop(102*3,102*0,102,102);
		loadingAnim[4] = loadingAnimation.crop(102*4,102*0,102,102);
		loadingAnim[5] = loadingAnimation.crop(102*5,102*0,102,102);
		loadingAnim[6] = loadingAnimation.crop(102*6,102*0,102,102);

		//Offline Anim:
		offlineAnim = new BufferedImage[2];
		offlineAnim[0] = offline.crop(0,0,64,64);
		offlineAnim[1] = offline.crop(0,0,1,1);

		shutdownAnim = new BufferedImage[2];
		shutdownAnim[0] = shutdown.crop(0,0,96,96);
		shutdownAnim[1] = shutdown.crop(0,0,1,1);

		wood = sheet.crop(width, height, width, height);
		
		btn_start = new BufferedImage[2];
		btn_start[0] = sheet.crop(width * 6, height * 4, width * 2, height);
		btn_start[1] = sheet.crop(width * 6, height * 5, width * 2, height);
		
		player_down = new BufferedImage[8];
		player_up = new BufferedImage[8];
		player_left = new BufferedImage[8];
		player_right = new BufferedImage[8];
		player_attackDown = new BufferedImage[6];
		player_attackUp = new BufferedImage[6];
		player_attackLeft = new BufferedImage[6];
		player_attackRight = new BufferedImage[6];


		//PLAYER
		player_stand = mainCharacter.crop(64*0,64*10,64,64);
		player_up[0] = mainCharacter.crop(64*1,64*8,64,64);
		player_up[1] = mainCharacter.crop(64*2, 64*8, 64, 64);
		player_up[2] = mainCharacter.crop(64*3,64*8,64,64);
		player_up[3] = mainCharacter.crop(64*4, 64*8, 64, 64);
		player_up[4] = mainCharacter.crop(64*5,64*8,64,64);
		player_up[5] = mainCharacter.crop(64*6, 64*8, 64, 64);
		player_up[6] = mainCharacter.crop(64*7,64*8,64,64);
		player_up[7] = mainCharacter.crop(64*8, 64*8, 64, 64);

		player_left[0] = mainCharacter.crop(64*1,64*9,64,64);
		player_left[1] = mainCharacter.crop(64*2, 64*9, 64, 64);
		player_left[2] = mainCharacter.crop(64*3,64*9,64,64);
		player_left[3] = mainCharacter.crop(64*4, 64*9, 64, 64);
		player_left[4] = mainCharacter.crop(64*5,64*9,64,64);
		player_left[5] = mainCharacter.crop(64*6, 64*9, 64, 64);
		player_left[6] = mainCharacter.crop(64*7,64*9,64,64);
		player_left[7] = mainCharacter.crop(64*8, 64*9, 64, 64);
		
		player_down[0] = mainCharacter.crop(64*1,64*10,64,64);
		player_down[1] = mainCharacter.crop(64*2, 64*10, 64, 64);
		player_down[2] = mainCharacter.crop(64*3,64*10,64,64);
		player_down[3] = mainCharacter.crop(64*4, 64*10, 64, 64);
		player_down[4] = mainCharacter.crop(64*5,64*10,64,64);
		player_down[5] = mainCharacter.crop(64*6, 64*10, 64, 64);
		player_down[6] = mainCharacter.crop(64*7,64*10,64,64);
		player_down[7] = mainCharacter.crop(64*8, 64*10, 64, 64);

		player_right[0] = mainCharacter.crop(64*1,64*11,64,64);
		player_right[1] = mainCharacter.crop(64*2, 64*11, 64, 64);
		player_right[2] = mainCharacter.crop(64*3,64*11,64,64);
		player_right[3] = mainCharacter.crop(64*4, 64*11, 64, 64);
		player_right[4] = mainCharacter.crop(64*5,64*11,64,64);
		player_right[5] = mainCharacter.crop(64*6, 64*11, 64, 64);
		player_right[6] = mainCharacter.crop(64*7,64*11,64,64);
		player_right[7] = mainCharacter.crop(64*8, 64*11, 64, 64);

		player_attackUp[0] = mainCharacter.crop(64*0,64*12,64,64);
		player_attackUp[1] = mainCharacter.crop(64*1,64*12,64,64);
		player_attackUp[2] = mainCharacter.crop(64*2,64*12,64,64);
		player_attackUp[3] = mainCharacter.crop(64*3,64*12,64,64);
		player_attackUp[4] = mainCharacter.crop(64*4,64*12,64,64);
		player_attackUp[5] = mainCharacter.crop(64*5,64*12,64,64);

		player_attackLeft[0] = mainCharacter.crop(64*0,64*13,64,64);
		player_attackLeft[1] = mainCharacter.crop(64*1,64*13,64,64);
		player_attackLeft[2] = mainCharacter.crop(64*2,64*13,64,64);
		player_attackLeft[3] = mainCharacter.crop(64*3,64*13,64,64);
		player_attackLeft[4] = mainCharacter.crop(64*4,64*13,64,64);
		player_attackLeft[5] = mainCharacter.crop(64*5,64*13,64,64);

		player_attackDown[0] = mainCharacter.crop(64*0,64*14,64,64);
		player_attackDown[1] = mainCharacter.crop(64*1,64*14,64,64);
		player_attackDown[2] = mainCharacter.crop(64*2,64*14,64,64);
		player_attackDown[3] = mainCharacter.crop(64*3,64*14,64,64);
		player_attackDown[4] = mainCharacter.crop(64*4,64*14,64,64);
		player_attackDown[5] = mainCharacter.crop(64*5,64*14,64,64);

		player_attackRight[0] = mainCharacter.crop(64*0,64*15,64,64);
		player_attackRight[1] = mainCharacter.crop(64*1,64*15,64,64);
		player_attackRight[2] = mainCharacter.crop(64*2,64*15,64,64);
		player_attackRight[3] = mainCharacter.crop(64*3,64*15,64,64);
		player_attackRight[4] = mainCharacter.crop(64*4,64*15,64,64);
		player_attackRight[5] = mainCharacter.crop(64*5,64*15,64,64);
		
		
		// REMOTE PLAYER

		remoteplayer_down = new BufferedImage[8];
		remoteplayer_up = new BufferedImage[8];
		remoteplayer_left = new BufferedImage[8];
		remoteplayer_right = new BufferedImage[8];
		remoteplayer_attackDown = new BufferedImage[6];
		remoteplayer_attackUp = new BufferedImage[6];
		remoteplayer_attackLeft = new BufferedImage[6];
		remoteplayer_attackRight = new BufferedImage[6];


		//PLAYER
		remoteplayer_stand = remoteCharacter.crop(64*0,64*10,64,64);
		remoteplayer_up[0] = remoteCharacter.crop(64*1,64*8,64,64);
		remoteplayer_up[1] = remoteCharacter.crop(64*2, 64*8, 64, 64);
		remoteplayer_up[2] = remoteCharacter.crop(64*3,64*8,64,64);
		remoteplayer_up[3] = remoteCharacter.crop(64*4, 64*8, 64, 64);
		remoteplayer_up[4] = remoteCharacter.crop(64*5,64*8,64,64);
		remoteplayer_up[5] = remoteCharacter.crop(64*6, 64*8, 64, 64);
		remoteplayer_up[6] = remoteCharacter.crop(64*7,64*8,64,64);
		remoteplayer_up[7] = remoteCharacter.crop(64*8, 64*8, 64, 64);

		remoteplayer_left[0] = remoteCharacter.crop(64*1,64*9,64,64);
		remoteplayer_left[1] = remoteCharacter.crop(64*2, 64*9, 64, 64);
		remoteplayer_left[2] = remoteCharacter.crop(64*3,64*9,64,64);
		remoteplayer_left[3] = remoteCharacter.crop(64*4, 64*9, 64, 64);
		remoteplayer_left[4] = remoteCharacter.crop(64*5,64*9,64,64);
		remoteplayer_left[5] = remoteCharacter.crop(64*6, 64*9, 64, 64);
		remoteplayer_left[6] = remoteCharacter.crop(64*7,64*9,64,64);
		remoteplayer_left[7] = remoteCharacter.crop(64*8, 64*9, 64, 64);

		remoteplayer_down[0] = remoteCharacter.crop(64*1,64*10,64,64);
		remoteplayer_down[1] = remoteCharacter.crop(64*2, 64*10, 64, 64);
		remoteplayer_down[2] = remoteCharacter.crop(64*3,64*10,64,64);
		remoteplayer_down[3] = remoteCharacter.crop(64*4, 64*10, 64, 64);
		remoteplayer_down[4] = remoteCharacter.crop(64*5,64*10,64,64);
		remoteplayer_down[5] = remoteCharacter.crop(64*6, 64*10, 64, 64);
		remoteplayer_down[6] = remoteCharacter.crop(64*7,64*10,64,64);
		remoteplayer_down[7] = remoteCharacter.crop(64*8, 64*10, 64, 64);

		remoteplayer_right[0] = remoteCharacter.crop(64*1,64*11,64,64);
		remoteplayer_right[1] = remoteCharacter.crop(64*2, 64*11, 64, 64);
		remoteplayer_right[2] = remoteCharacter.crop(64*3,64*11,64,64);
		remoteplayer_right[3] = remoteCharacter.crop(64*4, 64*11, 64, 64);
		remoteplayer_right[4] = remoteCharacter.crop(64*5,64*11,64,64);
		remoteplayer_right[5] = remoteCharacter.crop(64*6, 64*11, 64, 64);
		remoteplayer_right[6] = remoteCharacter.crop(64*7,64*11,64,64);
		remoteplayer_right[7] = remoteCharacter.crop(64*8, 64*11, 64, 64);

		remoteplayer_attackUp[0] = remoteCharacter.crop(64*0,64*12,64,64);
		remoteplayer_attackUp[1] = remoteCharacter.crop(64*1,64*12,64,64);
		remoteplayer_attackUp[2] = remoteCharacter.crop(64*2,64*12,64,64);
		remoteplayer_attackUp[3] = remoteCharacter.crop(64*3,64*12,64,64);
		remoteplayer_attackUp[4] = remoteCharacter.crop(64*4,64*12,64,64);
		remoteplayer_attackUp[5] = remoteCharacter.crop(64*5,64*12,64,64);

		remoteplayer_attackLeft[0] = remoteCharacter.crop(64*0,64*13,64,64);
		remoteplayer_attackLeft[1] = remoteCharacter.crop(64*1,64*13,64,64);
		remoteplayer_attackLeft[2] = remoteCharacter.crop(64*2,64*13,64,64);
		remoteplayer_attackLeft[3] = remoteCharacter.crop(64*3,64*13,64,64);
		remoteplayer_attackLeft[4] = remoteCharacter.crop(64*4,64*13,64,64);
		remoteplayer_attackLeft[5] = remoteCharacter.crop(64*5,64*13,64,64);

		remoteplayer_attackDown[0] = remoteCharacter.crop(64*0,64*14,64,64);
		remoteplayer_attackDown[1] = remoteCharacter.crop(64*1,64*14,64,64);
		remoteplayer_attackDown[2] = remoteCharacter.crop(64*2,64*14,64,64);
		remoteplayer_attackDown[3] = remoteCharacter.crop(64*3,64*14,64,64);
		remoteplayer_attackDown[4] = remoteCharacter.crop(64*4,64*14,64,64);
		remoteplayer_attackDown[5] = remoteCharacter.crop(64*5,64*14,64,64);

		remoteplayer_attackRight[0] = remoteCharacter.crop(64*0,64*15,64,64);
		remoteplayer_attackRight[1] = remoteCharacter.crop(64*1,64*15,64,64);
		remoteplayer_attackRight[2] = remoteCharacter.crop(64*2,64*15,64,64);
		remoteplayer_attackRight[3] = remoteCharacter.crop(64*3,64*15,64,64);
		remoteplayer_attackRight[4] = remoteCharacter.crop(64*4,64*15,64,64);
		remoteplayer_attackRight[5] = remoteCharacter.crop(64*5,64*15,64,64);



		// MOBS

		zombie_down = new BufferedImage[2];
		zombie_up = new BufferedImage[2];
		zombie_left = new BufferedImage[2];
		zombie_right = new BufferedImage[2];

		zombie_down[0] = sheet.crop(width * 4, height * 2, width, height);
		zombie_down[1] = sheet.crop(width * 5, height * 2, width, height);
		zombie_up[0] = sheet.crop(width * 6, height * 2, width, height);
		zombie_up[1] = sheet.crop(width * 7, height * 2, width, height);
		zombie_right[0] = sheet.crop(width * 4, height * 3, width, height);
		zombie_right[1] = sheet.crop(width * 5, height * 3, width, height);
		zombie_left[0] = sheet.crop(width * 6, height * 3, width, height);
		zombie_left[1] = sheet.crop(width * 7, height * 3, width, height);

		// OTHER

		grass = textures3.crop(32*1,32*2,32,32);
		stone = textures2.crop(12,76,64,64);
		dirt = textures2.crop(140,12,64,64);
		sand = textures3.crop(4*32,6*32,32,32);
		cobblestone = textures2.crop(78,12,61,61);

		water = new BufferedImage[16];
		water[0] = ocean.crop(0,0,32,32);
		water[1] = ocean.crop(32,0,32,32);
		water[2] = ocean.crop(64,0,32,32);
		water[3] = ocean.crop(96,0,32,32);
		water[4] = ocean.crop(128,0,32,32);
		water[5] = ocean.crop(160,0,32,32);
		water[6] = ocean.crop(192,0,32,32);
		water[7] = ocean.crop(224,0,32,32);
		water[8] = ocean.crop(0,32,32,32);
		water[9] = ocean.crop(32,32,32,32);
		water[10] = ocean.crop(64,32,32,32);
		water[11] = ocean.crop(96,32,32,32);
		water[12] = ocean.crop(128,32,32,32);
		water[13] = ocean.crop(160,32,32,32);
		water[14] = ocean.crop(192,32,32,32);
		water[15] = ocean.crop(224,32,32,32);

		saveIcon = saveicon.crop(20,20,190,200);

		saveAnim = new BufferedImage[2];
		saveAnim[0] = saveicon.crop(20,20,190,220);
		saveAnim[1] = saveicon.crop(20,5,190,220);

		water1 = new BufferedImage[2];
		water1[0] = textures3.crop(96,672,32,32);
		water1[1] = textures3.crop(416,672,32,32);

		water7 = new BufferedImage[2];
		water8 = new BufferedImage[2];
		water9 = new BufferedImage[2];
		water10 = new BufferedImage[2];
		water11 = new BufferedImage[2];
		water12 = new BufferedImage[2];
		water13 = new BufferedImage[2];
		water14 = new BufferedImage[2];
		water15 = new BufferedImage[2];
		water16 = new BufferedImage[2];
		water17 = new BufferedImage[2];
		water18 = new BufferedImage[2];

		water7[0] = textures3.crop(0,512,32,32);
		water7[1] = textures3.crop(320,512,32,32);

		water8[0] = textures3.crop(32,512,32,32);
		water8[1] = textures3.crop(352,512,32,32);

		water9[0] = textures3.crop(64,512,32,32);
		water9[1] = textures3.crop(384,512,32,32);

		water10[0] = textures3.crop(96,512,32,32);
		water10[1] = textures3.crop(416,512,32,32);

		water11[0] = textures3.crop(192,512,32,32);
		water11[1] = textures3.crop(512,512,32,32);

		water12[0] = textures3.crop(224,512,32,32);
		water12[1] = textures3.crop(544,512,32,32);

		water13[0] = textures3.crop(256,512,32,32);
		water13[1] = textures3.crop(576,512,32,32);

		water14[0] = textures3.crop(192,544,32,32);
		water14[1] = textures3.crop(512,544,32,32);

		water15[0] = textures3.crop(256,544,32,32);
		water15[1] = textures3.crop(576,544,32,32);

		water16[0] = textures3.crop(192,576,32,32);
		water16[1] = textures3.crop(512,576,32,32);

		water17[0] = textures3.crop(224,576,32,32);
		water17[1] = textures3.crop(544,576,32,32);

		water18[0] = textures3.crop(256,576,32,32);
		water18[1] = textures3.crop(576,576,32,32);

		//Waterfall:
		water19 = new BufferedImage[2];
		water20 = new BufferedImage[2];
		water21 = new BufferedImage[2];

		water19[0] = textures3.crop(0,544,32,32);
		water19[1] = textures3.crop(320,544,32,32);

		water20[0] = textures3.crop(0,576, 32,32);
		water20[1] = textures3.crop(320,576,32,32);

		water21[0] = textures3.crop(0,608,32,32);
		water21[1] = textures3.crop(320,608,32,32);

		// Other Water
		deepWater = new BufferedImage[2];
		deepWater[0] = textures3.crop(160,672,32,32);
		deepWater[1] = textures3.crop(480,672,32,32);

		grass1 = textures3.crop(32,64,32,32);

		// Entities
		cactus = new BufferedImage[3];
		cactus[2] = textures3.crop(0,32*11,32,32);
		cactus[1] = textures3.crop(32*3,32*10,32,32);
		cactus[0] = textures3.crop(0,32*10,32,32);

		stones1 = new BufferedImage[5];
		stones1[4] = textures3.crop(0, 32*14,32,32);
		stones1[3] = textures3.crop(32,32*14,32,32);
		stones1[2] = textures3.crop(32*2,32*14,32,32);
		stones1[1] = textures3.crop(32*3,32*14,32,32);
		stones1[0] = textures3.crop(32*4,32*14,32,32);

		//Sand/Water Animation
		sand22 = new BufferedImage[2];
		sand23 = new BufferedImage[2];
		sand24 = new BufferedImage[2];
		sand25 = new BufferedImage[2];
		sand26 = new BufferedImage[2];
		sand27 = new BufferedImage[2];
		sand28 = new BufferedImage[2];
		sand29 = new BufferedImage[2];
		sand30 = new BufferedImage[2];
		sand31 = new BufferedImage[2];
		sand32 = new BufferedImage[2];
		sand33 = new BufferedImage[2];
		sand34 = new BufferedImage[2];
		sand35 = new BufferedImage[2];
		sand36 = new BufferedImage[2];
		sand37 = new BufferedImage[2];

		sand22[0] = textures3.crop(192,608,32,32);
		sand22[1] = textures3.crop(192+320,608,32,32);

		sand23[0] = textures3.crop(224,608,32,32);
		sand23[1] = textures3.crop(224+320,608,32,32);

		sand24[0] = textures3.crop(256,608,32,32);
		sand24[1] = textures3.crop(256+320,608,32,32);

		sand25[0] = textures3.crop(192,640,32,32);
		sand25[1] = textures3.crop(192+320,640,32,32);

		sand26[0] = textures3.crop(256,640,32,32);
		sand26[1] = textures3.crop(256+320,640,32,32);

		sand27[0] = textures3.crop(192,672,32,32);
		sand27[1] = textures3.crop(192+320,672,32,32);

		sand28[0] = textures3.crop(224,672,32,32);
		sand28[1] = textures3.crop(224+320,672,32,32);

		sand29[0] = textures3.crop(256, 672,32,32);
		sand29[1] = textures3.crop(256+320,672,32,32);

		sand30[0] = textures3.crop(97,704,32,32);
		sand30[1] = textures3.crop(97+320,704,32,32);

		sand32[0] = textures3.crop(160,704,32,32);
		sand32[1] = textures3.crop(160+320,704,32,32);

		sand35[0] = textures3.crop(96,768,32,32);
		sand35[1] = textures3.crop(96+320,768,32,32);

		sand37[0] = textures3.crop(160,768,32,32);
		sand37[1] = textures3.crop(160+320,768, 32,32);

		//Static Sand Tiles
		sand38 = textures3.crop(32*3,32*5,32,32);
		sand39 = textures3.crop(32*4,32*5,32,32);
		sand40 = textures3.crop(32*5,32*5,32,32);

		sand41 = textures3.crop(32*3,32*6,32,32);

		sand42 = textures3.crop(32*5,32*6,32,32);

		sand43 = textures3.crop(32*3,32*7,32,32);
		sand44 = textures3.crop(32*4,32*7,32,32);
		sand45 = textures3.crop(32*5,32*7,32,32);



		//Static Grass Tiles
		grass46 = textures3.crop(32*0,32*1,32,32);
		grass47 = textures3.crop(32*1,32*1,32,32);
		grass48 = textures3.crop(32*2,32*1,32,32);
		grass49 = textures3.crop(32*0,32*2,32,32);
		grass50 = textures3.crop(32*2,32*2,32,32);
		grass51 = textures3.crop(32*0,32*3,32,32);
		grass52 = textures3.crop(32*1,32*3,32,32);
		grass53 = textures3.crop(32*2,32*3,32,32);
		grass61 = textures3.crop(32*0,32*4,32,32);
		grass62 = textures3.crop(32*1,32*4,32,32);
		grass63 = textures3.crop(32*2,32*4,32,32);
		grass64 = textures3.crop(32*0,32*5,32,32);
		grass65 = textures3.crop(32*1,32*5,32,32);
		grass66 = textures3.crop(32*2,32*5,32,32);
		grass67 = textures3.crop(32*1,32*6,32,32);

		//Static Brick Tiles
		brick70 = textures3.crop(32*5,32*3,32,32);
		brick71 = textures3.crop(32*3,32*4,32,32);
		brick72 = textures3.crop(32*4,32*4,32,32);
		brick73 = textures3.crop(32*5,32*4,32,32);
		brick74 = textures3.crop(32*3,32*3,32,32);
		brick75 = textures3.crop(32*4,32*3,32,32);
		brick76 = textures3.crop(32*3,32*2,32,32);
		brick77 = textures3.crop(32*3,32*1,32,32);
		brick78 = textures3.crop(32*4,32*1,32,32);
		brick79 = textures3.crop(32*4,32*2,32,32);
		brick80 = textures3.crop(32*5,32*1,32,32);
		brick81 = textures3.crop(32*6,32*1,32,32);
		brick82 = textures3.crop(32*5,32*2,32,32);
		brick83 = textures3.crop(32*6,32*2,32,32);
		brick84 = textures3.crop(32*6,32*3,32,32);
		brick85 = textures3.crop(32*6,32*4,32,32);

		//Static Plank Tiles
		plank91 = textures3.crop(32*7,32*1,32,32);
		plank92 = textures3.crop(32*8,32*1,32,32);
		plank93 = textures3.crop(32*9,32*1,32,32);
		plank94 = textures3.crop(32*7,32*2,32,32);
		plank95 = textures3.crop(32*8,32*2,32,32);
		plank96 = textures3.crop(32*9,32*2,32,32);
		plank97 = textures3.crop(32*7,32*3,32,32);
		plank98 = textures3.crop(32*8,32*3,32,32);
		plank99 = textures3.crop(32*9,32*3,32,32);
		plank100 = textures3.crop(32*10,32*1,32,32);
		plank101 = textures3.crop(32*10,32*2,32,32);

		//Sign
		sign201 = textures3.crop(32*7,32*4,32,32);
		sign202 = textures3.crop(32*8,32*4,32,32);
		sign203 = textures3.crop(32*9,32*4,32,32);
		sign204 = textures3.crop(32*10,32*4,32,32);




		tree = sheet.crop(0, 0, width, height * 2);


		//Dungeon Tiles
		lavaAnim301 = new BufferedImage[5];
		lavaAnim301[0] = cave.crop(0,304,32,32);
		lavaAnim301[1] = cave.crop(32,304,32,32);
		lavaAnim301[2] = cave.crop(64,304,32,32);
		lavaAnim301[3] = cave.crop(32,304,32,32);
		lavaAnim301[4] = cave.crop(0,304,32,32);


		dungeon110 = cave.crop(0*16,0*16,16,16);
		dungeon111 = cave.crop(1*16,0*16,16,16);
		dungeon112 = cave.crop(2*16,0*16,16,16);
		dungeon113 = cave.crop(0*16,1*16,16,16);
		dungeon114 = cave.crop(1*16,1*16,16,16);
		dungeon115 = cave.crop(2*16,1*16,16,16);
		dungeon116 = cave.crop(0*16,2*16,16,16);
		dungeon117 = cave.crop(1*16,2*16,16,16);
		dungeon118 = cave.crop(2*16,2*16,16,16);




	}



}
