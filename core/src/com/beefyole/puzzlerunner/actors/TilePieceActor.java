package com.beefyole.puzzlerunner.actors;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Buttons;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.beefyole.puzzlerunner.World;
import com.beefyole.puzzlerunner.GameConfig;
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
	int draw_width = 81;
	int draw_height = 81;
	
	boolean addedToWorld = false;
	boolean clickedDown = false;
	GameConfig config;
					
	public TilePieceActor(World world, Texture tex, int regionID, int startX, int startY){
		this.world = world;
		config = new GameConfig();

		setBounds(STARTX, STARTY, draw_width, draw_height);
		regionHelper = new TextureRegionHelper(tex, width, height, rows, cols);
		region = regionHelper.getRegionAt(regionID);
		setPosition(startX, startY);
		//setPosition(world.getPos().x + getX(), world.getPos().y + getY());
		addListener(new InputListener(){
			public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
				System.out.println("down");
				setPosition(- width / 2, - height/2); 
				//setPosition(x - width / 2, Gdx.graphics.getHeight() - y - height/2); 
				clickedDown = true;
				return true;
			}
			public void touchUp (InputEvent event, float x, float y, int pointer, int button) {
				clickedDown = false;
		        System.out.println("up");
		    }
		});
	}
	
	@Override 
	public void act(float dt){
		if(clickedDown){
			setPosition(Gdx.input.getX() - width / 2, 
					Gdx.graphics.getHeight() - Gdx.input.getY() - height / 2);
		} 
	}
	
	public boolean isClicked(){
		return clickedDown;
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
