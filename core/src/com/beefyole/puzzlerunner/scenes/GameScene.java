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
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.beefyole.puzzlerunner.GameConfig;
import com.beefyole.puzzlerunner.TilePiece;
import com.beefyole.puzzlerunner.TilePiece.PieceName;
import com.beefyole.puzzlerunner.actors.Player;
import com.beefyole.puzzlerunner.actors.TilePieceActor;
import com.beefyole.puzzlerunner.actors.WorldBackgroundTile;
import com.beefyole.puzzlerunner.worlds.GameWorld;

public class GameScene extends Stage {
	
	boolean renderSideBackground = false;
	int currentTiles = 5;
	int activeTileIndex = 0;
	int tileHeight = 0;
	int lastMapHeight = 81;
	int selectedPieceIndex = 0;
	float centerX;
	float leftX;
	float viewportWidth;
	float viewportHeight;
	boolean shownNewPiece = false;
	Camera camera;
	Viewport viewport;
	Array<TilePieceActor> tiles;
	Array<TilePieceActor> exitOptionsActs;
	Texture roadTilesTex;
	TilePieceActor tmp;
	TilePieceActor startPieceAct;
	TilePieceActor selectedPiece;
	ShapeRenderer shapeRenderer;
	WorldBackgroundTile tmpTile;
	Player player;
	GameWorld world;
	GameConfig config;
	Array<TilePiece> selectablePieces;
	TilePieceActor lastPA;
	Array<TilePiece> exitOptions;
	
	public GameScene(){
		config = new GameConfig();
		viewportWidth = config.getScreenWidth();
		viewportHeight = config.getScreenHeight();
		
		world = new GameWorld(0, 0, config.getTallscreenWidth(), 99999);
		world.generate();
		addActor(world);
		
		switch(Gdx.app.getType()){
		case Desktop:
			renderSideBackground = true;
			centerX = viewportWidth / 2.0f - config.getTallscreenWidth() / 2.0f;
			leftX = viewportWidth / 2 - config.getTallscreenWidth();
			world.setPosition(centerX, 81);
		default:
			centerX = config.getTallscreenWidth() / 2.0f;
			break;
		}
		
		centerX = config.getTallscreenWidth() / 2.0f;
		
		selectablePieces = new Array<TilePiece>(6);	
		tiles = new Array<TilePieceActor>(6);
		exitOptionsActs = new Array<TilePieceActor>(16);
		exitOptions = new Array<TilePiece>(16);
		
		camera = new OrthographicCamera(viewportWidth, viewportHeight);
		viewport = new FitViewport(viewportWidth, viewportHeight);
		
		shapeRenderer = new ShapeRenderer();
		shapeRenderer.setProjectionMatrix(camera.combined);
		
		roadTilesTex = new Texture(Gdx.files.internal("sprites/road_spritesheet.png"));
		
		// create starting tiles
		for(int i = 0; i < currentTiles; i++){
			TilePiece tmpT = TilePiece.generateRndTilePiece();
			selectablePieces.insert(i, tmpT);
			tmp = world.createFromTile(roadTilesTex, tmpT);
			tmp.setPosition(viewportWidth / 2.0f - config.getTallscreenWidth() / 2.0f + i * tmp.getWidth(), 1.0f);
			tiles.add(tmp);
			addActor(tmp);			
		}
		
		// create background tiles
		for(int x = 0; x < 5; x++){
			for( int y = 0; y < 9; y++){
				tmpTile = new WorldBackgroundTile(roadTilesTex);
				tmpTile.setPosition(x * tmpTile.getWidth(), y * tmpTile.getHeight());
				world.addActor(tmpTile);
				tileHeight = y;
			}
		}
		
		// create player
		player = new Player(world, new Vector2(0.f, 0f));
		player.setPosition((81 * 2) + (81 / 2) - (player.getWidth() / 2), 0);
		world.addActor(player);
		
		// create starting piece
		TilePiece tmpT = TilePiece.createStartingPiece();
		startPieceAct = world.createFromTile(roadTilesTex, tmpT);
		startPieceAct.setPosition(81 * tmpT.getWidth(), 0);
		world.addActor(startPieceAct);
		
	}
	
	public void transformTileActor(TilePieceActor p, PieceName dir){
		if(dir == PieceName.UP){
			p.setY(p.getY() + 81);
		}
		if(dir == PieceName.DOWN){
			p.setY(p.getY() - 81);
		}
		if(dir == PieceName.LEFT){
			p.setX(p.getX() - 81);
		}
		if(dir == PieceName.RIGHT){
			p.setX(p.getX() + 81);
		}
	}
	
	public void showTileOptions(){
		// todo: build an array of temp tiles to show on screen
		// then show them, then remove them
		// test if can connect current tile
		//PieceName tName = world.testTilePiece(activeTileIndex);
		TilePiece.unMarkAll();
		TilePiece.unMarkAllSides();
		Array<TilePiece> exits = world.getExitPieces();
		TilePiece selectedTile = selectablePieces.get(activeTileIndex);
		if(exits.size > 0){
			for(TilePiece e : exits){
				//System.out.println("possible options: " + e.name);
				//int height = e.getHeight();
				//int width = e.getWidth();
				
				if(e.hasUp() && e.testTilePiece(selectedTile) == PieceName.UP){
					//System.out.println("Height: "+ e.getHeight() + " width: " + e.getWidth());
					System.out.println("UP");
					e.deltaY = 1;
					e.deltaX = 0;
					e.connection =  PieceName.UP;
					exitOptions.add(e);
					
				} else if(e.hasDown() && e.testTilePiece(selectedTile) == PieceName.DOWN){
					//System.out.println("Height: "+ e.getHeight() + " width: " + e.getWidth());
					System.out.println("DOWN");
					e.deltaY = -1;
					e.deltaX = 0;
					e.connection = PieceName.DOWN;
					exitOptions.add(e);
					
				} else if(e.hasLeft() && e.testTilePiece(selectedTile) == PieceName.LEFT){
					//System.out.println("Height: "+ e.getHeight() + " width: " + e.getWidth());
					System.out.println("LEFT");
					e.deltaX = -1;
					e.deltaY = 0;
					e.connection = PieceName.LEFT;
					exitOptions.add(e);
					
				} else if(e.hasRight() && e.testTilePiece(selectedTile) == PieceName.RIGHT){
					//System.out.println("Height: "+ e.getHeight() + " width: " + e.getWidth());
					System.out.println("RIGHT");
					e.deltaX = 1;
					e.deltaY = 0;
					e.connection = PieceName.RIGHT;
					exitOptions.add(e);
				}
			}
		}
	}
		
	@Override 
	public void act(float dt){
		// remove temp tiles
		for(TilePieceActor tpa : exitOptionsActs){
			tpa.clear();
			tpa.remove();
		}
		exitOptions.clear();
		exitOptionsActs.clear();
		
		world.update(dt);
		
		player.moveBy(0.01f * dt, 20f * dt);

		// check if new tile is clicked
		for(int i = 0; i < tiles.size; i++){
			TilePieceActor t = tiles.get(i);
			t.act(dt);
			if(t.isClicked()){
				System.out.println("New tile clicked: " + i);
				activeTileIndex = i;
				for(int j = 0; j < tiles.size; j++){
					tiles.get(j).setUnclicked();
				}
			}
		}		
		
		// generate new background tiles above the world
		if (world.getY() < lastMapHeight){
			int y = tileHeight; 
			for(int x = 0; x < 5; x++){
				tmpTile = new WorldBackgroundTile(roadTilesTex);
				tmpTile.setPosition(x * tmpTile.getWidth(), y * tmpTile.getHeight());
				world.addActor(tmpTile);
			}
			tileHeight++;
			lastMapHeight -= 81;
			player.toFront();
		} 
		
		// select tiles when keys pressed
		if(Gdx.input.isKeyJustPressed(Keys.LEFT)){
			activeTileIndex--;
			if(activeTileIndex < 0){
				activeTileIndex = currentTiles - 1;
			}
		}
		if(Gdx.input.isKeyJustPressed(Keys.RIGHT)){
			activeTileIndex = (activeTileIndex + 1) % (currentTiles);
		}
		if(Gdx.input.isKeyJustPressed(Keys.ESCAPE)){
			System.out.println("Goodbye");
			Gdx.app.exit();
		}
		
		// display tiles that could be added onscreen
		showTileOptions();
		
		// set selected tile
		tmp = tiles.get(activeTileIndex);
		
		TilePiece tmpT = selectablePieces.get(activeTileIndex);
		if(tmpT.hasUp()){
			System.out.println("selected has up");
		}
		if(tmpT.hasDown()){
			System.out.println("selected has down");
		}
		if(tmpT.hasLeft()){
			System.out.println("selected has left");
		}
		if(tmpT.hasRight()){
			System.out.println("selected has right");
		}
		
		if(Gdx.input.isKeyJustPressed(Keys.TAB)){
			if(exitOptions.size > 0){
				System.out.println("Trying next exit: " + selectedPieceIndex+1 + "/" + exitOptions.size );
				selectedPieceIndex = (selectedPieceIndex + 1) % (exitOptions.size);
				System.out.println("Trying next exit: " + selectedPieceIndex+1 + "/" + exitOptions.size );
			}
		}
		
		// create sprites for selectable pieces (same tile at different positions)
		for(int i = 0; i < exitOptions.size; i++){
			TilePiece tpEx = exitOptions.get(i);
			TilePiece tpSel = selectablePieces.get(activeTileIndex);
			TilePieceActor ta = world.createFromTile(roadTilesTex, tpSel);
			ta.setPosition((tpEx.getWidth() + tpEx.deltaX) * 81,
					(tpEx.getHeight() + tpEx.deltaY) * 81);
			ta.setColor(1.0f, 1.0f, 1.0f, 0.5f);
			exitOptionsActs.insert(i, ta);
		}
		
		if(exitOptions.size > 0 && selectedPieceIndex < exitOptions.size){
			selectedPiece = exitOptionsActs.get(selectedPieceIndex);
			world.addActor(selectedPiece);
			shownNewPiece = true;
			System.out.println("Trying next exit: " + selectedPieceIndex + "/" + exitOptions.size );
		} else if (exitOptions.size == 0){
			if(shownNewPiece){
				selectedPiece.clear();
				selectedPiece.remove();
				shownNewPiece = false;
			}
			
			System.out.println("No exits!");
		}
		
		if(Gdx.input.isKeyJustPressed(Keys.ENTER)){
			
			if(exitOptions.size > 0){
				System.out.println("Adding a piece..");
				TilePiece selectedTPiece = exitOptions.get(selectedPieceIndex);
				
				// position of piece to add to ground
				int selectedX = (int)selectedPiece.getX();
				int selectedY = (int)selectedPiece.getY();
				
				// new tile to add to world
				TilePieceActor tileToAdd = new TilePieceActor(world, roadTilesTex, 
						selectedPiece.getRegionID(), selectedX, selectedY);
				world.addActor(tileToAdd);
				
				// tile and direction of the selected piece
				PieceName exitDir = selectedTPiece.connection;
				TilePiece selectedExitTile = selectedTPiece;
				
				//System.out.println("Connecting an exit: " + lastExit.name + " dir: " + exitDir.toString());
				TilePiece toConnect = selectablePieces.get(activeTileIndex);
				toConnect.name = "world";
				selectedExitTile.connectPiece(toConnect, exitDir);
				
				// remove selected piece and delete it from the tiles index
				selectedPiece.remove();
				selectedPiece.clear();
				tiles.removeIndex(activeTileIndex);
				selectablePieces.removeIndex(activeTileIndex);
				
				// generate new random tile to add
				TilePiece newSelectableTilePiece = TilePiece.generateRndTilePiece();
				TilePieceActor newSelectableTile = world.createFromTile(roadTilesTex, newSelectableTilePiece);
				newSelectableTile.setPosition(viewportWidth / 2.0f - config.getTallscreenWidth() / 2.0f + activeTileIndex * tileToAdd.getWidth(), 
						1.0f);
				
				// insert into selectable visual tiles
				tiles.insert(activeTileIndex, newSelectableTile);
				selectablePieces.insert(activeTileIndex, newSelectableTilePiece);
				addActor(newSelectableTile);
				
				player.toFront();
			} else {
				System.out.println("Can't add that piece..");
			}
		}
		if(exitOptions.size > 0){
			selectedPiece.clear();
		}
		
	}
	
	@Override 
	public void draw(){	
		super.draw();
		shapeRenderer.begin(ShapeType.Line);
		if(exitOptions.size > 0){
			shapeRenderer.setColor(Color.GREEN);
		} else {
			shapeRenderer.setColor(Color.RED);
		}
		
		shapeRenderer.rect(tmp.getX() - (config.getScreenWidth() / 2), 
				tmp.getY() - (config.getScreenHeight() / 2), tmp.getWidth() + 2, tmp.getHeight() + 2);
		shapeRenderer.end();
	} 
	@Override 
	public void dispose(){
		player.dispose();
	}
}