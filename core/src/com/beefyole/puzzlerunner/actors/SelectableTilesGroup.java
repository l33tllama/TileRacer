package com.beefyole.puzzlerunner.actors;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Pool;
import com.beefyole.puzzlerunner.TextureRegionHelper;
import com.beefyole.puzzlerunner.worlds.GameWorld;
import com.beefyole.puzzlerunner.worlds.Tile;
import com.sun.prism.paint.Color;

public class SelectableTilesGroup extends Group {
	
	private Pool<TileActor> tileActorsPool;
	private Array<TileActor> selectableTileActors;
	private Texture tex;
	private TileDirector tileDirector;
	private final int SELECTABLE_TILES = 5;
	private final int MAX_TILES = 25;
	private int tileWidth, tileHeight;
	private TextureRegionHelper regionHelper;
	private GameWorld world;
	
	//TODO: move tileactorpool elsewhere..
	
	public SelectableTilesGroup(GameWorld world, final Texture tex, TileDirector tileDirector, int tileWidth, int tileHeight){
		this.tex = tex;
		this.tileDirector = tileDirector;
		this.tileWidth = tileWidth;
		this.tileHeight = tileHeight;
		this.world = world;
		selectableTileActors = new Array<TileActor>(SELECTABLE_TILES);
		this.regionHelper = new TextureRegionHelper(tex, tileWidth, tileHeight, 5, 9);
		tileActorsPool = new Pool<TileActor>(MAX_TILES){
			@Override
			protected TileActor newObject(){
				return new TileActor(new Tile(), tex, 33, 0, 0);
			}
		};
		addSelectableTiles();
		
	}
	
	void addSelectableTiles(){
		for(int i = 0; i < SELECTABLE_TILES; i++){
			TileActor tmp = tileActorsPool.obtain();
			boolean[] dirs = new boolean[4];
			for(int j = 0; j < 3; j++){
				dirs[j] = Math.random() > 0.5 ? true : false;
			}
			Tile selTmp = new Tile(dirs[0], dirs[1], dirs[2], dirs[3], 0, 0);
			int regionID = Tile.getTileIndex(dirs[0], dirs[1], dirs[2], dirs[3]);
			tmp.postInit(selTmp, tex, regionID, (int)world.getPos().x + i * tileWidth, 0);
			selectableTileActors.add(tmp);
			addActor(tmp);
		}
	}
	/*
	@Override
	public void act(float dt){
		for(int i = 0; i < MAX_TILES; i++){
			TileActor t = selectableTileActors.get(i);
			t.setPosition(i * tileWidth, 0);
		}
	}*/
}
