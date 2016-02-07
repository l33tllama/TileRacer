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
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.AddAction;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.beefyole.puzzlerunner.GameConfig;
import com.beefyole.puzzlerunner.TextureRegionHelper;
import com.beefyole.puzzlerunner.TilePiece;
import com.beefyole.puzzlerunner.TilePiece.PieceName;
import com.beefyole.puzzlerunner.actors.PlayerActor;
import com.beefyole.puzzlerunner.actors.TileActor;
import com.beefyole.puzzlerunner.actors.TileDirector;
import com.beefyole.puzzlerunner.actors.WorldBackgroundActor;
import com.beefyole.puzzlerunner.actors.StaticBackgroundTileActor;
import com.beefyole.puzzlerunner.worlds.GameWorld;
import com.beefyole.puzzlerunner.worlds.TileGrid;

public class GameScene extends Stage {
	
	private boolean renderSideBackground = false;
	//int currentTiles = 5;
	//int activeTileIndex = 0;
	//int tileHeight = 0;
	//int lastMapHeight = 81;
	//int selectedPieceIndex = 0;
	private int tileSize = 81;
	private float centerX;
	private float leftX;
	private float viewportWidth;
	private float viewportHeight;
	//boolean shownNewPiece = false;
	private Camera camera;
	private Viewport viewport;
	//Array<TilePieceActor> tiles;
	//Array<TilePieceActor> exitOptionsActs;
	private Texture roadTilesTex;
	private TileGrid tileGrid;
	//TilePieceActor tmp;
	//TilePieceActor startPieceAct;
	//TilePieceActor selectedPiece;
	private ShapeRenderer shapeRenderer;
	private StaticBackgroundTileActor tmpTile;
	private PlayerActor player;
	private GameWorld world;
	private GameConfig config;
	private WorldBackgroundActor worldActor;
	private TextureRegionHelper regionHelper;
	
	//Array<TilePiece> selectablePieces;
	//TilePieceActor lastPA;
	//Array<TilePiece> exitOptions;
	
	public GameScene(){
		System.out.println("Creating game scene..");
		config = new GameConfig();
		viewportWidth = config.getScreenWidth();
		viewportHeight = config.getScreenHeight();
		
		roadTilesTex = new Texture(Gdx.files.internal("sprites/road_spritesheet.png"));
		regionHelper = new TextureRegionHelper(roadTilesTex, tileSize, tileSize , 9, 9);
		System.out.println("Creating world..");
		world = new GameWorld(0, 0, 5, 9);
		
		worldActor = new WorldBackgroundActor(world, roadTilesTex, null, 5, 9, tileSize);
		addActor(worldActor);
		System.out.println("Done.");
		
		switch(Gdx.app.getType()){
		case Desktop:
			renderSideBackground = true;
			centerX = viewportWidth / 2.0f - config.getTallscreenWidth() / 2.0f;
			leftX = viewportWidth / 2 - config.getTallscreenWidth();
		default:
			centerX = config.getTallscreenWidth() / 2.0f;
			break;
		}
		
		centerX = config.getTallscreenWidth() / 2.0f;
		
		camera = new OrthographicCamera(viewportWidth, viewportHeight);
		viewport = new FitViewport(viewportWidth, viewportHeight);
		
		shapeRenderer = new ShapeRenderer();
		shapeRenderer.setProjectionMatrix(camera.combined);

	}
	
		
	@Override 
	public void act(float dt){
		
		
		world.update(dt);
		
		//player.moveBy(0.01f * dt, 20f * dt);
		
		// select tiles when keys pressed
		if(Gdx.input.isKeyJustPressed(Keys.LEFT)){
		}
		if(Gdx.input.isKeyJustPressed(Keys.RIGHT)){
		}
		if(Gdx.input.isKeyJustPressed(Keys.ESCAPE)){
			System.out.println("Goodbye");
			Gdx.app.exit();
		}
		
		// move to next tile (tab not work in HTML5..)
		if(Gdx.input.isKeyJustPressed(Keys.TAB)){
			
		}
		if(Gdx.input.isKeyJustPressed(Keys.ENTER)){
			
		}
	}
	
	@Override 
	public void draw(){	
		super.draw();
		//shapeRenderer.begin(ShapeType.Line);
		//shapeRenderer.setColor(Color.RED);		
		//shapeRenderer.rect(tmp.getX() - (config.getScreenWidth() / 2), 
			//	tmp.getY() - (config.getScreenHeight() / 2), tmp.getWidth() + 2, tmp.getHeight() + 2);
		//shapeRenderer.end();
	} 
	@Override 
	public void dispose(){
		player.dispose();
	}
}