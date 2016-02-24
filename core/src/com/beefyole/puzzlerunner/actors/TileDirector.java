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
	private final int MAX_TILES = 100;
	private final int SELECTABLE_TILES = 5;
	private int selectedIndex;
	private TileGrid tileGrid;
	private int tileWidth, tileHeight;
	private SelectableTilesGroup selectableTilesGroup;
	
	
	public TileDirector(final Texture tex, GameWorld world, int tileWidth, int tileHeight){
		this.tex = tex;
		this.world = world;
		this.tileGrid = world.getTileGrid();
		this.tileWidth = tileWidth;
		this.tileHeight = tileHeight;
		tileActors = new Array<TileActor>(MAX_TILES);
		selectableTileActors = new Array<TileActor>(SELECTABLE_TILES);		
		
		// TODO: initialise tiles in separate function (not constructor - see link above)
		tileActorsPool = new Pool<TileActor>(MAX_TILES){
			@Override
			protected TileActor newObject(){
				return new TileActor(new Tile() , tex, 33, 0, 0);
			}
		};
		
		TileActor ftA = tileActorsPool.obtain();
		Tile startTile = new Tile(true, false, false, false, 2, 1);
		ftA.postInit(startTile, tex, Tile.getTileIndex(true, false, false, false), 0, 0);
		startTile.setTileActor(ftA);
		world.getTileGrid().createFirstTile(startTile);
		tileActors.add(ftA);
		
		addActor(ftA);
		
	}
	
	public void connectSelectableTiles(SelectableTilesGroup g){
		selectableTilesGroup = g;
	}
	
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
	}
	
	@Override
	public void act(float dt){
		for (TileActor t : tileActors){
			t.setPosition(t.tile.getGridX() * tileWidth, t.tile.getGridY() * tileHeight);
		}
		TileActor ta = selectableTilesGroup.getSelectedTile();
		Tile t = ta.tile;
		TileGrid tG = world.getTileGrid();
		boolean canConnect = false;
		
		for(Tile ut : tG.findUnconnectedTiles()){
			Color thisColor = ut.getTileActor().getColor();
			
			for(Directions d : tG.getOpenDirections(ut)){
				if(d == Directions.UP && t.canConnectDown()){
					System.out.println("Tile can connect up!");
					canConnect = true;
				}
				else if(d == Directions.DOWN && t.canConnectUp()){
					System.out.println("Tile can connect down!");
					canConnect = true;
				}
			}
			
			if(canConnect && t.canConnectDown()){
				thisColor.a = 0.5f;
				ut.getTileActor().setColor(thisColor);
			} else {
				thisColor.a = 1.0f;
				ut.getTileActor().setColor(thisColor);
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
