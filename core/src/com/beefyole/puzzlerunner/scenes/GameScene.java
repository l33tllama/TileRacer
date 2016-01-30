package com.beefyole.puzzlerunner.scenes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.beefyole.puzzlerunner.GameConfig;
import com.beefyole.puzzlerunner.World;
import com.beefyole.puzzlerunner.actors.Player;
import com.beefyole.puzzlerunner.actors.TilePieceActor;
import com.beefyole.puzzlerunner.worlds.GameWorld;


public class GameScene extends Stage {
	private Camera camera;
	private Viewport viewport;
	boolean renderSideBackground = false;
	Player player;
	World world;
	GameConfig config;
	float centerX;
	float botLeftX;
	TilePieceActor[] tiles;
	
	public GameScene(){
		config = new GameConfig();
		float viewportWidth = config.getScreenWidth();
		float viewportHeight = config.getScreenHeight();
		
		world = new GameWorld();
		world.generate();
		
		switch(Gdx.app.getType()){
		case Desktop:
			renderSideBackground = true;
			centerX = viewportWidth / 2.0f - config.getTallscreenWidth() / 2.0f;
			botLeftX = viewportWidth - config.getTallscreenWidth();
			world.setPos(new Vector2(centerX, 0));
		default:
			centerX = config.getTallscreenWidth() / 2.0f;
			break;
		}
		
		camera = new OrthographicCamera(viewportWidth, viewportHeight);
		viewport = new FitViewport(viewportWidth, viewportHeight);
		
		player = new Player(world, new Vector2(0.f, 0f));
		player.setPosition(centerX + player.getWidth() / 2.0f, 0f);
		addActor(player);
				
	}
	
	@Override 
	public void act(float dt){
		world.update(dt);
	}
	/*
	@Override 
	public void draw(){
		
	} */
	@Override 
	public void dispose(){
		player.dispose();
	}
}
