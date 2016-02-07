package com.beefyole.puzzlerunner.actors;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.utils.Array;
import com.beefyole.puzzlerunner.worlds.GameWorld;

/*
 * TileDirector
 * 
 * Renders tiles on the screen, acquired from the GameWorld class
 * 
 */
public class TileDirector extends Group{
	
	private GameWorld world;
	private Texture tex;
	private Array<TileActor> tileActors;
	private final int MAX_TILES = 100;
	
	public TileDirector(Texture tex, GameWorld world){
		this.tex = tex;
		this.world = world;
		
		tileActors = new Array<TileActor>(MAX_TILES);
	}
	
	@Override
	public void act(float dt){
		
	}
	
	@Override
	public void draw(Batch batch, float parentAlpha) {
		// TODO Auto-generated method stub
		super.draw(batch, parentAlpha);
	}

}
