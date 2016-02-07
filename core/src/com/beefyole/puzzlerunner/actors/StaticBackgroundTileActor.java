package com.beefyole.puzzlerunner.actors;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.beefyole.puzzlerunner.TextureRegionHelper;

public class StaticBackgroundTileActor extends Actor {
	
	Texture tex;
	TextureRegion region;
	TextureRegionHelper regionHelper;
	int rows = 14;
	int cols = 20;
	int width = 64;
	int	height = 64;
	int draw_width = 81;
	int draw_height = 81;
	
	public StaticBackgroundTileActor(Texture tex){
		//this.tex = tex;
		tex = new Texture(Gdx.files.internal("sprites/RPGpack_sheet.png"));
		regionHelper = new TextureRegionHelper(tex, width, height, rows, cols);
		region = regionHelper.getRegionAt(21);
		setWidth(draw_width);
		setHeight(draw_height);
	}
	
	@Override
	public void draw(Batch batch, float parentAlpha){
		Color color = getColor();
		batch.setColor(color.r, color.g, color.b, color.a * parentAlpha);
		batch.draw(region, getX(), + getY(), 
				getOriginX(), getOriginY(), draw_width, draw_height, getScaleX(), 
				getScaleY(), getRotation());
		
	}

}
