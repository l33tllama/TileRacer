package com.beefyole.puzzlerunner.scenes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.beefyole.puzzlerunner.GameConfig;
import com.beefyole.puzzlerunner.World;
import com.beefyole.puzzlerunner.actors.Player;
import com.beefyole.puzzlerunner.actors.TilePieceActor;
import com.beefyole.puzzlerunner.actors.WorldBackgroundTile;
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
	ShapeRenderer shapeRenderer;
	WorldBackgroundTile tmpTile;
	int currentTiles = 5;
	int activeTileIndex = 0;
	int tileHeight = 0;
	int lastMapHeight = 0;
	
	public GameScene(){
		config = new GameConfig();
		float viewportWidth = config.getScreenWidth();
		float viewportHeight = config.getScreenHeight();
		camera = new OrthographicCamera(viewportWidth, viewportHeight);
		viewport = new FitViewport(viewportWidth, viewportHeight);
				
		roadTilesTex = new Texture(Gdx.files.internal("sprites/road_spritesheet.png"));
		tiles = new Array<TilePieceActor>(6);
		shapeRenderer = new ShapeRenderer();
		shapeRenderer.setProjectionMatrix(camera.combined);
		world = new GameWorld(0, 0, config.getTallscreenWidth(), 99999);
		world.generate();
		addActor(((Group)world));
		
		switch(Gdx.app.getType()){
		case Desktop:
			renderSideBackground = true;
			centerX = viewportWidth / 2.0f - config.getTallscreenWidth() / 2.0f;
			leftX = viewportWidth / 2 - config.getTallscreenWidth();
			((Group)world).setPosition(centerX, 81);
		default:
			centerX = config.getTallscreenWidth() / 2.0f;
			break;
		}
		centerX = config.getTallscreenWidth() / 2.0f;
		
		
		
		for(int i = 0; i < currentTiles; i++){
			tmp = ((GameWorld)world).addTilePiece(roadTilesTex);
			tmp.setPosition(viewportWidth / 2.0f - config.getTallscreenWidth() / 2.0f + i * tmp.getWidth(), 1.0f);
			tiles.add(tmp);
			addActor(tmp);
			//((Group)world).addActor(tmp);
		}
		// 16 / 5 : 9 / 5
		// x : 5
		for(int x = 0; x < 5; x++){
			for( int y = 0; y < 9; y++){
				tmpTile = new WorldBackgroundTile(roadTilesTex);
				tmpTile.setPosition(x * tmpTile.getWidth(), y * tmpTile.getHeight());
				((Group)world).addActor(tmpTile);
				tileHeight = y;
			}
		}
		
		player = new Player(world, new Vector2(0.f, 0f));
		player.setPosition((3 * 81) - 40, 0);
		((Group)world).addActor(player);
		
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
		if (((Group)world).getY() < lastMapHeight){
			int y = tileHeight; 
			for(int x = 0; x < 5; x++){
				tmpTile = new WorldBackgroundTile(roadTilesTex);
				tmpTile.setPosition(x * tmpTile.getWidth(), y * tmpTile.getHeight());
				((Group)world).addActor(tmpTile);
			}
			tileHeight++;
			lastMapHeight -= 81;
			player.toFront();
		} 
		
		if(Gdx.input.isKeyJustPressed(Keys.LEFT)){
			activeTileIndex--;
			if(activeTileIndex < 0){
				activeTileIndex = currentTiles - 1;
			}
		}
		if(Gdx.input.isKeyJustPressed(Keys.RIGHT)){
			activeTileIndex = (activeTileIndex + 1) % (currentTiles);
		}
		tmp = tiles.get(activeTileIndex);
	}
	
	@Override 
	public void draw(){	
		super.draw();
		shapeRenderer.begin(ShapeType.Line);
		shapeRenderer.setColor(Color.RED);
		//shapeRenderer.rect(tmp.getX() - 203, tmp.getY() - 361, tmp.getWidth() + 2, tmp.getHeight() + 2);
		shapeRenderer.rect(tmp.getX() - (config.getScreenWidth() / 2), 
				tmp.getY() - (config.getScreenHeight() / 2), tmp.getWidth() + 2, tmp.getHeight() + 2);
		shapeRenderer.end();
	} 
	@Override 
	public void dispose(){
		player.dispose();
	}
}
