package com.beefyole.puzzlerunner.worlds;

import com.badlogic.gdx.math.Vector2;
import com.beefyole.puzzlerunner.World;

public class GameWorld implements World{
	
	private Vector2 pos;
	private float vel = 1.0f;
	private float accel = 0.01f;
	
	public GameWorld(){
		pos = new Vector2(0f, 0f);
	}
	
	public void generatePieces(){
		
	}
	
	@Override
	public void update(float dt){
		
		// update world position
		vel += accel;
		pos.y -= vel * dt;
	}

	public Vector2 getPos() {
		return pos;
	}

	public void setPos(Vector2 pos) {
		this.pos = pos;
	}

}
