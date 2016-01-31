package com.beefyole.puzzlerunner.worlds;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.utils.Array;
import com.beefyole.puzzlerunner.TilePiece;
import com.beefyole.puzzlerunner.World;
import com.beefyole.puzzlerunner.actors.TilePieceActor;

public class GameWorld extends Group implements World {
			
	private Vector2 pos;
	private float vel = 1.0f;
	private static float MAX_VELOCITY = 10f;
	private float accel = 0.009f;
	
	
	//TilePiece topPiece;
	
	public GameWorld(int x, int y, int height, int width){
		setPosition(x, y);
		setWidth(width);
		setHeight(height);
		pos = new Vector2(getX(), getY());
		
	}
	
	public TilePieceActor createFromTile(Texture tex, TilePiece t){
		int regionID = TilePiece.getTileIndex(t.hasUp(), t.hasDown(), t.hasLeft(), t.hasRight());
		return new TilePieceActor(this, tex, regionID, 0, 0);
	}
		
	public Array<TilePiece> getExitPieces(){
		Array<TilePiece> list = new Array<TilePiece>(20);
		Array<TilePiece> out = TilePiece.getExitPieces(TilePiece.getStartPiece(), list);
		TilePiece.unMarkAllSides();
		return out;
	}
	
	public void addTileToWorld(TilePieceActor tpa){
		addActor(tpa);
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
