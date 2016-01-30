package com.beefyole.puzzlerunner.actors;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class Player extends Actor{
	
	private final int STARTX = 0;
	private final int STARTY = 0;
	private final int WIDTH = 100;
	private final int HEIGHT = 100;
	
	Texture tex;
	TextureRegion region;
	
	Vector2 pos;
	
	public Player(Vector2 startPos){
		setBounds(STARTX, STARTY, WIDTH, HEIGHT);
		region = new TextureRegion();
		pos = startPos;
	}
	
	@Override
	public void draw(Batch batch, float parentAlpha){
		Color color = getColor();
		batch.setColor(color.r, color.g, color.b, color.a * parentAlpha);
		batch.draw(region, getX(), getY(), getOriginX(), getOriginY(), getWidth(), getHeight(), getScaleX(), getScaleY(), getRotation());
	}

}
