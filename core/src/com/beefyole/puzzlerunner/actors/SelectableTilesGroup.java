package com.beefyole.puzzlerunner.actors;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Pool;
import com.beefyole.puzzlerunner.GameConfig;
import com.beefyole.puzzlerunner.TextureRegionHelper;
import com.beefyole.puzzlerunner.worlds.GameWorld;
import com.beefyole.puzzlerunner.worlds.Tile;

public class SelectableTilesGroup extends Group {
	
	private Pool<TileActor> tileActorsPool;
	private Array<TileActor> selectableTileActors;
	private Texture tex;
	private TileDirector tileDirector;
	private final int SELECTABLE_TILES = 5;
	private final int MAX_TILES = 25;
	private int tileWidth, tileHeight;
	private TextureRegionHelper regionHelper;
	private GameWorld world;
	private int selectedIndex;
	private ShapeRenderer shapeRenderer;
	private GameConfig config;	
	
	//TODO: move tileactorpool elsewhere..
	
	public SelectableTilesGroup(GameWorld world, final Texture tex, TileDirector tileDirector, int tileWidth, int tileHeight){
		this.tex = tex;
		this.tileDirector = tileDirector;
		this.tileWidth = tileWidth;
		this.tileHeight = tileHeight;
		this.world = world;
		selectableTileActors = new Array<TileActor>(SELECTABLE_TILES);
		this.regionHelper = new TextureRegionHelper(tex, tileWidth, tileHeight, 5, 9);
		selectedIndex = 0;
		shapeRenderer = new ShapeRenderer();
		tileActorsPool = new Pool<TileActor>(MAX_TILES){
			@Override
			protected TileActor newObject(){
				return new TileActor(new Tile(), tex, 33, 0, 0);
			}
		};
		addSelectableTiles();
		tileDirector.connectSelectableTiles(this);
		
	}
	
	void addSelectableTiles(){
		for(int i = 0; i < SELECTABLE_TILES; i++){
			TileActor tmp = tileActorsPool.obtain();
			boolean[] dirs = new boolean[4];
			for(int j = 0; j < 3; j++){
				dirs[j] = Math.random() > 0.5 ? true : false;
			}
			Tile selTmp = new Tile(dirs[0], dirs[1], dirs[2], dirs[3], 0, 0);
			int regionID = Tile.getTileIndex(dirs[0], dirs[1], dirs[2], dirs[3]);
			tmp.postInit(selTmp, tex, regionID, (int)world.getPos().x + i * tileWidth, 0);
			selectableTileActors.add(tmp);
			addActor(tmp);
		}
	}
	
	public TileActor getSelectedTile(){
		return selectableTileActors.get(selectedIndex);
	}
	
	@Override
	public void act(float dt){
		if(Gdx.input.isKeyJustPressed(Keys.LEFT)){
			selectedIndex = selectedIndex == 0 ? 4 : selectedIndex - 1;
		}
		if(Gdx.input.isKeyJustPressed(Keys.RIGHT)){
			selectedIndex = selectedIndex == 4 ? 0 : selectedIndex + 1;
		}
		// move to next tile (tab not work in HTML5..)
		if(Gdx.input.isKeyJustPressed(Keys.TAB)){
			
		}
		if(Gdx.input.isKeyJustPressed(Keys.ENTER)){
			
		}
	}
	@Override 
	public void draw(Batch batch, float parentAlpha){
		super.draw(batch, parentAlpha);
		shapeRenderer.begin(ShapeType.Line);
		shapeRenderer.setColor(Color.RED);		
		shapeRenderer.rect(world.getPos().x + selectedIndex * tileWidth, 0, tileWidth, tileHeight);
		shapeRenderer.end();
	}
}
