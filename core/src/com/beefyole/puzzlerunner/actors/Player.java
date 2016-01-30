package com.beefyole.puzzlerunner.actors;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.beefyole.puzzlerunner.World;

public class Player extends Actor{
	
	private final int STARTX = 0;
	private final int STARTY = 0;
	private int width, height;
	
	Texture tex;
	TextureRegion region;
	World world;
	
	public Player(World world, Vector2 startPos){
		tex = new Texture(Gdx.files.internal("sprites/robot_3Dblue.png"));
		width = tex.getWidth();
		height = tex.getHeight();
		region = new TextureRegion(tex);
		setBounds(STARTX, STARTY, width, height);
		rotateBy(90.0f);
		scaleBy(-0.3f);
		//region = new TextureRegion();
		setPosition(startPos.x, startPos.y);
		this.world = world;
	}
	
	@Override
	public void act(float dt){
		// logic goes here..
	}
	
	@Override
	public void draw(Batch batch, float parentAlpha){
		Color color = getColor();
		batch.setColor(color.r, color.g, color.b, color.a * parentAlpha);
		batch.draw(region, getX() + world.getPos().x, world.getPos().y + getY(), 
				getOriginX(), getOriginY(), getWidth(), getHeight(), getScaleX(), 
				getScaleY(), getRotation());
	}
	
	public void dispose(){
		tex.dispose();
	}

}
