package com.beefyole.puzzlerunner.actors;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.utils.Array;
import com.beefyole.puzzlerunner.TextureRegionHelper;
import com.beefyole.puzzlerunner.worlds.GameWorld;

public class WorldBackgroundGroup extends Group{
	
	private TileDirector tileDirector;
	private GameWorld world;
	private Array<TileActor> backgroundTiles;
	private TextureRegion backgroundRegion;
	private TextureRegionHelper regionHelper;
	private int rows, cols;
	private int tileSize;
	private final int GRASS_REGION = 33;
	
	public WorldBackgroundGroup(GameWorld world, Texture tex, TextureRegionHelper regionHelper, int cols, int rows, int tileSize){
		this.world = world;
		this.rows = rows;
		this.cols = cols;
		this.tileSize = tileSize;
		this.regionHelper = regionHelper;
		tileDirector = new TileDirector(tex, world, tileSize, tileSize);
		backgroundTiles = new Array<TileActor>(rows * cols);
		regionHelper = new TextureRegionHelper(tex, tileSize, tileSize, rows, cols);
		//backgroundRegion = regionHelper.getRegionAt(GRASS_REGION);
		int i = 0;
		
		for(int x = 0; x < cols; x++){
			for(int y = 0; y < rows; y++){
				System.out.println("Adding a tile at " + x + ", " + y + ".");
				//TODO: get regionHelper from here..?
				TileActor tmpTA = new TileActor(null, tex, (int)Math.floor(Math.random()* 33), x * tileSize, y * tileSize);
				addActor(tmpTA);
			}
		}
		
		addActor(tileDirector);		
	}
	
	public TileDirector getTileDirector(){
		return tileDirector;
	}
	
	@Override
	public void act(float delta) {	
		// update pos as world pos updates
		this.setPosition(world.getPos().x, world.getPos().y);
		tileDirector.act(delta);
	}

}
