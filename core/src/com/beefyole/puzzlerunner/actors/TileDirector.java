package com.beefyole.puzzlerunner.actors;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Pool;
import com.beefyole.puzzlerunner.GameConfig;
import com.beefyole.puzzlerunner.worlds.GameWorld;
import com.beefyole.puzzlerunner.worlds.Tile;
import com.beefyole.puzzlerunner.worlds.TileGrid;
import com.beefyole.puzzlerunner.worlds.TileGrid.Directions;

import sun.reflect.LangReflectAccess;

/*
 * TileDirector
 * 
 * Renders tiles on the screen, acquired from the GameWorld class
 * 
 * https://github.com/libgdx/libgdx/wiki/Memory-management
 */
public class TileDirector extends Group{
	
	private GameWorld world;
	private Texture tex;
	private Pool<TileActor> tileActorsPool;
	private Array<TileActor> tileActors; 
	private Array<TileActor> selectableTileActors;
	private final int REGION_GRASS = 33;
	private final int MAX_TILES = 100;
	private final int SELECTABLE_TILES = 5;
	private int sel_new_tile_i;
	private TileGrid tileGrid;
	private int tileWidth, tileHeight;
	private SelectableTilesGroup selectableTilesGroup;
	private TileActor tmpTAToAdd;
	
	public TileDirector(final Texture tex, GameWorld world, int tileWidth, int tileHeight){
		this.tex = tex;
		this.world = world;
		this.tileGrid = world.getTileGrid();
		this.tileWidth = tileWidth;
		this.tileHeight = tileHeight;
		tileActors = new Array<TileActor>(MAX_TILES);
		selectableTileActors = new Array<TileActor>(SELECTABLE_TILES);	
		sel_new_tile_i = 0;
		
		// TODO: initialise tiles in separate function (not constructor - see link above)
		tileActorsPool = new Pool<TileActor>(MAX_TILES){
			@Override
			protected TileActor newObject(){
				return new TileActor(new Tile() , tex, 33, 0, 0);
			}
		};
		
		// create first tile (clean this up?)
		TileActor ftA = tileActorsPool.obtain();
		Tile startTile = new Tile(true, false, false, false, 2, 1);
		ftA.postInit(startTile, tex, Tile.getTileIndex(true, false, false, false), 0, 0);
		startTile.setTileActor(ftA);
		world.getTileGrid().createFirstTile(startTile);
		tileActors.add(ftA);
		
		addActor(ftA);
		
		// create temporary tile that could be added and draw off-screen
		tmpTAToAdd = tileActorsPool.obtain();
		tmpTAToAdd.postInit(new Tile(), tex, REGION_GRASS, -tileWidth, -tileHeight);
		addActor(tmpTAToAdd);
		
	}
	
	public void connectSelectableTiles(SelectableTilesGroup g){
		selectableTilesGroup = g;
	}
	/*
	void addSelectableTiles(){
		for(int i = 0; i < 5; i++){
			TileActor tmp = tileActorsPool.obtain();
			boolean[] dirs = new boolean[4];
			for(int j = 0; j < 3; j++){
				dirs[j] = Math.random() > 0.5 ? true : false;
			}
			Tile selTmp = new Tile(dirs[0], dirs[1], dirs[2], dirs[3], 0, 0);
			int regionID = Tile.getTileIndex(dirs[0], dirs[1], dirs[2], dirs[3]);
			tmp.postInit(selTmp, tex, regionID, i * tileWidth, 0);
			selTmp.setTileActor(tmp);
			selectableTileActors.add(tmp);
			addActor(tmp);
		}
	} */
	
	void updateTilePositions(){
		// update all tile positions
		for (TileActor t : tileActors){
			t.setPosition(t.tile.getGridX() * tileWidth, t.tile.getGridY() * tileHeight);
		}
	}
	
	@Override
	public void act(float dt){
		
		updateTilePositions();
		
		// get the current tile selected from the selectable tiles group
		TileActor sel_ta = selectableTilesGroup.getSelectedTile();
		Tile sel_t = sel_ta.tile;
		
		// booleans used to determine whether a tile can connect in which direction
		boolean canConnectUp, canConnectDown, canConnectLeft, canConnectRight;
		canConnectDown = canConnectLeft = canConnectRight = canConnectUp = false;
		
		// booleans used for getting the sprite region
		boolean up = sel_t.canConnectUp();
		boolean down = sel_t.canConnectDown();
		boolean left = sel_t.canConnectLeft();
		boolean right = sel_t.canConnectRight();
		
		// white
		Color thisColor = new Color(1.0f, 1.0f, 1.0f, 1.0f);
		
		// Find all unconnected tiles and add them to an array
		Tile lastConnectableTile = new Tile();
		Array<Tile> connectableTiles = new Array<Tile>();
		TileGrid tG = world.getTileGrid();
		for(Tile ut : tG.findUnconnectedTiles()){
			
			// Look at all the open directions for each tile and see if the selected tile can connect (opposite side also open)
			for(Directions d : tG.getOpenDirections(ut)){
				int pieceDX = 0;
				int pieceDY = 0;
				
				// if the tile has an opening on the top, and the selected tile has an opening on the bottom (etc)
				if(d == Directions.UP && sel_t.canConnectDown()){
					connectableTiles.add(ut);
				} else if(d == Directions.DOWN && sel_t.canConnectUp()){
					connectableTiles.add(ut);
				} else if(d == Directions.LEFT && sel_t.canConnectRight()){
					connectableTiles.add(ut);
				} else if(d == Directions.RIGHT && sel_t.canConnectLeft()){
					connectableTiles.add(ut);
				}
			}
		}
		
		// iterate all possible tile options when tab key is pressed
		if(Gdx.input.isKeyJustPressed(Keys.TAB)){
			sel_new_tile_i = sel_new_tile_i == connectableTiles.size - 1 ? 0 : sel_new_tile_i + 1; 
		}
		
		// if there are any tiles to connect to..
		if(connectableTiles.size > 0){
			
			// Get the tile on the grid to connect to at the selected index
			Tile sel_grid_t = connectableTiles.get(sel_new_tile_i);
			
			// if the selected tile on the grid can connect down, and the selected tile in the bottom box can connect up (and etc)
			if(sel_grid_t.canConnectDown() && sel_t.canConnectUp()){
				canConnectDown = true;
			} else if(sel_grid_t.canConnectUp() && sel_t.canConnectDown()){
				canConnectUp = true;
			} else if(sel_grid_t.canConnectLeft() && sel_t.canConnectRight()){
				canConnectLeft = true;
			} else if(sel_grid_t.canConnectRight() && sel_t.canConnectLeft()){
				canConnectRight = true;
			}
						
			int pieceDX = 0;
			int pieceDY = 0;
			// if there are any connections, prepare new tile to show on screen (add alpha, set region)
			if(canConnectUp || canConnectRight || canConnectLeft || canConnectDown){
				thisColor.a = 0.5f;
				tmpTAToAdd.setRegion(Tile.getTileIndex(up, down, left, right));
				tmpTAToAdd.setColor(thisColor);
				
				// if it can connect, create a new tile to show at the position where it can go
				if(canConnectUp){
					pieceDY = 1;
				} else if (canConnectLeft){
					pieceDX = -1;
				} else if (canConnectRight){
					pieceDX = 1;
				} else if (canConnectDown){
					pieceDY = -1;
				}
				System.out.println("Can connect");
				tmpTAToAdd.setX((sel_grid_t.getGridX() + pieceDX) * tileWidth);
				tmpTAToAdd.setY((sel_grid_t.getGridY() + pieceDY) * tileHeight);
			} else {
				thisColor.a = 1.0f;
				tmpTAToAdd.setX(-tileWidth);
				tmpTAToAdd.setY(-tileHeight);
				tmpTAToAdd.setColor(thisColor);
			}
		}
		
		
		// When player presses enter, add the tile to the grid	
		if(Gdx.input.isKeyJustPressed(Keys.ENTER)){
			if(canConnectDown || canConnectLeft || canConnectRight || canConnectUp){
				TileActor newPieceActor = tileActorsPool.obtain();
				int newGridX = sel_t.getGridX();
				int newGridY = sel_t.getGridY();
				if(canConnectDown){
					newGridY += 1;
					//newPieceActor.postInit(tile, tex, regionID, startX, startY);
				} else if (canConnectUp){
					
				} else if (canConnectLeft){
					
				} else if (canConnectRight){
					
				}
				
				
			}
			
			
		}
		
	}
	
	@Override
	public void draw(Batch batch, float parentAlpha) {
		// TODO Auto-generated method stub
		super.draw(batch, parentAlpha);
		//int leftX = config.getScreenWidth() / 2 - config.getTallscreenWidth();
		
	}

}
