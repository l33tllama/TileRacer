package com.beefyole.puzzlerunner.scenes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.Array;
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
	float leftX;
	Array<TilePieceActor> tiles;
	Texture roadTilesTex;
	TilePieceActor tmp;
	//Group worldObjects;
	ShapeRenderer shapeRenderer;
	
	public GameScene(){
		config = new GameConfig();
		float viewportWidth = config.getScreenWidth();
		float viewportHeight = config.getScreenHeight();
		roadTilesTex = new Texture(Gdx.files.internal("sprites/road_spritesheet.png"));
		tiles = new Array<TilePieceActor>(6);
		
		shapeRenderer = new ShapeRenderer();
		
		
		world = new GameWorld(0, 0, config.getTallscreenWidth(), 99999);
		world.generate();
		addActor(((Group)world));
		
		
		switch(Gdx.app.getType()){
		case Desktop:
			renderSideBackground = true;
			centerX = viewportWidth / 2.0f - config.getTallscreenWidth() / 2.0f;
			leftX = viewportWidth / 2 - config.getTallscreenWidth();
			((Group)world).setPosition(centerX, 0);
		default:
			centerX = config.getTallscreenWidth() / 2.0f;
			break;
		}
		centerX = config.getTallscreenWidth() / 2.0f;
		
		camera = new OrthographicCamera(viewportWidth, viewportHeight);
		viewport = new FitViewport(viewportWidth, viewportHeight);
		shapeRenderer.setProjectionMatrix(camera.combined);
		player = new Player(world, new Vector2(0.f, 0f));
		player.setPosition(centerX - player.getWidth() / 2.0f, 0f);
		((Group)world).addActor(player);
		
		for(int i = 0; i < 3; i++){
			tmp = ((GameWorld)world).addTilePiece(roadTilesTex);
			tmp.setPosition(viewportWidth / 2.0f - config.getTallscreenWidth() / 2.0f + i * tmp.getWidth() + 16, 1.0f);
			tiles.add(tmp);
			addActor(tmp);
			//((Group)world).addActor(tmp);
		}
	}
	
	@Override 
	public void act(float dt){
		world.update(dt);
		player.moveBy(0.01f * dt, 20f * dt);
		for(TilePieceActor t : tiles){
			t.act(dt);
			if(t.isClicked()){
				tmp = t;
			}
		}
	}
	
	@Override 
	public void draw(){
		
		shapeRenderer.begin(ShapeType.Line);
		shapeRenderer.setColor(Color.RED);
		//shapeRenderer.rect(tmp.getX() - 203, tmp.getY() - 361, tmp.getWidth() + 2, tmp.getHeight() + 2);
		shapeRenderer.rect(tmp.getX() - (config.getScreenWidth() / 2) - 1, tmp.getY() - (config.getScreenHeight() / 2) - 1, tmp.getWidth() + 2, tmp.getHeight() + 2);
		shapeRenderer.end();
		
		
		super.draw();
		
	} 
	@Override 
	public void dispose(){
		player.dispose();
	}
}
