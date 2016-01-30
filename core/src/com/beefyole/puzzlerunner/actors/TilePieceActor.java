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
	
	InputListener inputListener;
	TextureRegion region;
	World world;
	TextureRegionHelper regionHelper;
	
			
	public TilePieceActor(World world, Texture tex, int rows, int cols){
		this.world = world;
		regionHelper = new TextureRegionHelper(tex, tex.getWidth(), 
				tex.getHeight(), rows, cols);
	}
	
	@Override
	public void draw(Batch batch, float parentAlpha){
		Color color = getColor();
		batch.setColor(color.r, color.g, color.b, color.a * parentAlpha);
		batch.draw(region, getX() + world.getPos().x, world.getPos().y + getY(), 
				getOriginX(), getOriginY(), getWidth(), getHeight(), getScaleX(), 
				getScaleY(), getRotation());
	}

}
