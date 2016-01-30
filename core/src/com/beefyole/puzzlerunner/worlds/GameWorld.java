package com.beefyole.puzzlerunner.worlds;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.utils.Array;
import com.beefyole.puzzlerunner.TilePiece;
import com.beefyole.puzzlerunner.World;
import com.beefyole.puzzlerunner.actors.TilePieceActor;

public class GameWorld extends Group implements World {
	
	private Vector2 pos;
	private float vel = 1.0f;
	private static float MAX_VELOCITY = 10f;
	private float accel = 0.001f;
	Array<TilePiece> selectablePieces;
	
	public GameWorld(int x, int y, int height, int width){
		setPosition(x, y);
		setWidth(width);
		setHeight(height);
		pos = new Vector2(getX(), getY());
		selectablePieces = new Array<TilePiece>(6);
		
	}
	
	public TilePieceActor addTilePiece(Texture tex){
		boolean udlr[] = new boolean[4];
		for(int i = 0; i < 4; i++){
			double r = Math.random();
			udlr[i] = r > 0.5? true : false;
		}
		
		selectablePieces.add(new TilePiece(udlr[0], udlr[1], udlr[2], udlr[3]));
		int regionID = TilePiece.getTileIndex(udlr[0], udlr[1], udlr[2], udlr[3]);
		System.out.print("Creating tile piece at index: " + regionID);
		return new TilePieceActor(this, tex, regionID, 0, 0);
	}
	
	@Override
	public void update(float dt){
		
		// update world position
		if(vel < MAX_VELOCITY){
			vel += accel;
		}
		
		//pos.y -= vel * dt;
		setY(getY() - vel * dt);
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
