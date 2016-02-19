package com.beefyole.puzzlerunner.actors;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Pool;
import com.beefyole.puzzlerunner.worlds.GameWorld;
import com.beefyole.puzzlerunner.worlds.Tile;
import com.beefyole.puzzlerunner.worlds.TileGrid;

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
	private TileGrid tileGrid;
	private int tileWidth, tileHeight;
	
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
		
		TileActor firstTile = tileActorsPool.obtain();
		Tile startTile = new Tile(true, false, false, false, 2, 0);
		firstTile.postInit(startTile, tex, 31, 0, 0);
		tileActors.add(firstTile);
		
		addActor(firstTile);
		//addSelectableTiles();
		
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
			selectableTileActors.add(tmp);
			addActor(tmp);
		}
	}
	
	@Override
	public void act(float dt){
		for (TileActor t : tileActors){
			t.setPosition(t.tile.getGridX() * tileWidth, t.tile.getGridY() * tileHeight);
		}
	}
	
	@Override
	public void draw(Batch batch, float parentAlpha) {
		// TODO Auto-generated method stub
		super.draw(batch, parentAlpha);
		/*for(TileActor i : selectableTileActors){
			i.draw(batch, 0.5f);
		}*/
		
		
	}

}
