package com.beefyole.puzzlerunner.actors;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.beefyole.puzzlerunner.World;
import com.beefyole.puzzlerunner.TextureRegionHelper;

public class TilePieceActor extends Actor{
	
	private final int STARTX = 0;
	private final int STARTY = 0;
	InputListener inputListener;
	TextureRegion region;
	World world;
	TextureRegionHelper regionHelper;
	int rows = 9;
	int cols = 9;
	int width = 64;
	int	height = 64;
	
	boolean addedToWorld = false;
					
	public TilePieceActor(World world, Texture tex, int regionID, int startX, int startY){
		this.world = world;

		setBounds(STARTX, STARTY, width, height);
		regionHelper = new TextureRegionHelper(tex, width, height, rows, cols);
		region = regionHelper.getRegionAt(regionID);
		setPosition(startX, startY);
	}
	
	@Override 
	public void act(float dt){
		
	}
	
	@Override
	public void draw(Batch batch, float parentAlpha){
		Color color = getColor();
		batch.setColor(color.r, color.g, color.b, color.a * parentAlpha);
		if(addedToWorld){
			batch.draw(region, world.getPos().x + getX(),world.getPos().y + getY(), 
					getOriginX(), getOriginY(), width, height, getScaleX(), 
					getScaleY(), getRotation());
		} else {
			batch.draw(region, world.getPos().x + getX(), getY(), 
					getOriginX(), getOriginY(), width, height, getScaleX(), 
					getScaleY(), getRotation());
		}
		
	}

}
