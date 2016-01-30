package com.beefyole.puzzlerunner.scenes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.beefyole.puzzlerunner.actors.Player;
import com.beefyole.puzzlerunner.worlds.GameWorld;


public class GameScene extends Stage {
	private Camera camera;
	boolean renderSideBackground = false;
	Player player;
	World world;
	
	public GameScene(){
		float viewportWidth = 405;
		float viewportHeight = 720;
		
		world = new GameWorld();
		
		switch(Gdx.app.getType()){
		case Desktop:
			renderSideBackground = true;
			viewportHeight = 720;
			viewportWidth = 1280;
		default:
			break;
		}
		
		camera = new OrthographicCamera(viewportWidth, viewportHeight);
		
		player = new Player(new Vector2(0f, 0f));
		addActor(player);
				
	}
	
	@Override 
	public void act(float dt){
		world.update(dt);
	}
	
	@Override 
	public void draw(){
		
	}
}
