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
	private int draw_width, draw_height;
	
	Texture tex;
	TextureRegion region;
	World world;
	
	public Player(World world, Vector2 startPos){
		tex = new Texture(Gdx.files.internal("sprites/robot_3Dblue.png"));
		width = tex.getWidth();
		height = tex.getHeight();
		region = new TextureRegion(tex);
		setBounds(STARTX, STARTY, width, height);
		setPosition(startPos.x, startPos.y);
		draw_width = 64;
		draw_height = 64;
		setWidth(draw_width);
		setHeight(draw_height);
		setOrigin(draw_width / 2, draw_height / 2);
		rotateBy(90.0f);		
		this.world = world;
	}
	
	@Override
	public void act(float dt){
		// logic goes here..
		/*setPosition(getX() - world.getPos().x, getY() - world.getPos().y);
		float oldPosX = getX();
		float oldPosY = getY();
		setPosition(world.getPos().x + oldPosX, world.getPos().y + oldPosY);*/
		
	}
	
	@Override
	public void draw(Batch batch, float parentAlpha){
		Color color = getColor();
		batch.setColor(color.r, color.g, color.b, color.a * parentAlpha);
		batch.draw(region, getX(), getY(), 
				getOriginX(), getOriginY(), getWidth(), getHeight(), getScaleX(), 
				getScaleY(), getRotation());
	}
	
	public void dispose(){
		tex.dispose();
	}

}
