package com.beefyole.puzzlerunner.worlds;

import com.badlogic.gdx.math.Vector2;
import com.beefyole.puzzlerunner.World;
import com.beefyole.puzzlerunner.actors.TilePieceActor;

public class GameWorld implements World{
	
	private Vector2 pos;
	private float vel = 1.0f;
	private float accel = 0.01f;
	
	public GameWorld(){
		this.pos = new Vector2(0f, 0f);
	}
	
	public TilePieceActor addTilePiece(){
		return null;
	}
	
	@Override
	public void update(float dt){
		
		// update world position
		vel += accel;
		pos.y -= vel * dt;
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
