package com.beefyole.puzzlerunner.worlds;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.utils.Array;
import com.beefyole.puzzlerunner.actors.TileActor;
import com.beefyole.puzzlerunner.old.TilePiece;

public class GameWorld implements TileWorld {
			
	private Vector2 pos;
	private float vel = 1.0f;
	private static float MAX_VELOCITY = 10f;
	private float accel = 0.002f;
	private TileGrid tileGrid;	
	private int width, height;
	private boolean moving = false;
	
	public GameWorld(int x, int y, int width, int height){
		pos = new Vector2(x, y);
		this.width = width;
		this.height = height;
		tileGrid = new TileGrid(width, height);
	}
	
	public TileGrid getTileGrid(){
		return tileGrid;
	}
	
	public void startMoving(){
		moving = true;
	}
	
	public void pauseMoving(){
		moving = false;
	}
	
	@Override
	public void update(float dt){
		
		// update world position
		if(vel < MAX_VELOCITY){
			vel += accel;
		}
		if(moving){
			pos.y -= vel * dt;
		}
		
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
