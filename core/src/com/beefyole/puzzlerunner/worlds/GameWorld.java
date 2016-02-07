package com.beefyole.puzzlerunner.worlds;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.utils.Array;
import com.beefyole.puzzlerunner.TilePiece;
import com.beefyole.puzzlerunner.actors.TileActor;

public class GameWorld implements TileWorld {
			
	private Vector2 pos;
	private float vel = 1.0f;
	private static float MAX_VELOCITY = 10f;
	private float accel = 0.009f;
	private TileGrid tileGrid;	
	private int width, height;
	
	public GameWorld(int x, int y, int width, int height){
		pos = new Vector2(x, y);
		this.width = width;
		this.height = height;
		tileGrid = new TileGrid(width, height);
	}
	
	
	@Override
	public void update(float dt){
		
		// update world position
		if(vel < MAX_VELOCITY){
			vel += accel;
		}
		
		//pos.y -= vel * dt;
		//setY(getY() - vel * dt);
	}

	@Override
	public Vector2 getPos() {
		return pos;
	}
	
	@Override
	public void setPos(Vector2 pos) {
		this.pos = pos;
	}

	@Override
	public void generate() {
		
		
	}

}
