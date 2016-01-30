package com.beefyole.puzzlerunner;

import com.badlogic.gdx.math.Vector2;

public interface World {
	
	public void generate();
	
	public void update(float dt);
	
	public Vector2 getPos();
	
	public void setPos(Vector2 pos);

}
