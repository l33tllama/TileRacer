package com.beefyole.puzzlerunner;

import com.badlogic.gdx.utils.Array;

public class TileGrid{
	
	private Tile[][] tiles;
	
	private int width;
	private int height;
	private Tile startTile;
	
	public enum Directions { LEFT, RIGHT, UP, DOWN, NONE};
	
	public TileGrid(int width, int screenHeight, Tile startTile){
		this.width = width;
		this.height = screenHeight;
		this.startTile = startTile;
		
		tiles = new Tile[width][height];
		for(int x = 0; x < width; x++){
			for(int y = 0; y < height; y++){
				tiles[x][y] = new Tile(false, false, false, false, x, y);
			}
		}		
	}
	
	// Get an array of tiles with exits that are unconnected. Useful for finding new places to put pieces.
	public Array<Tile> findUnconnectedTiles(){
		Array<Tile> unconnectedTiles = new Array<Tile>(width * height);
		
		for(int x = 0; x < width; x++){
			for(int y = 0; y < height; y++){
				if(tiles[x][y].canConnectUp()){
					unconnectedTiles.add(tiles[x][y]);
				}
				if(tiles[x][y].canConnectDown()){
					unconnectedTiles.add(tiles[x][y]);
				}	
				if(tiles[x][y].canConnectLeft()){
					unconnectedTiles.add(tiles[x][y]);
				}
				if(tiles[x][y].canConnectRight()){
					unconnectedTiles.add(tiles[x][y]);
				}
			}
		}
		
		return unconnectedTiles;
	}
	
	// Once found open tiles, iterate over them and look at where exactly the open sides are using this function 
	public Array<Directions> getOpenDirections(Tile t){
		
		Array<Directions> dirs = new Array<Directions>(4);
		
		if(t.canConnectUp()){
			dirs.add(Directions.UP);
		}
		if(t.canConnectDown()){
			dirs.add(Directions.DOWN);
		}
		if(t.canConnectLeft()){
			dirs.add(Directions.LEFT);
		}
		if(t.canConnectRight()){
			dirs.add(Directions.RIGHT);
		}
		if(!t.canConnectRight() && !t.canConnectLeft() && !t.canConnectDown() && !t.canConnectUp()){
			dirs.add(Directions.NONE);
		}
		return dirs;
		
	}
	
	// Once found a new direction of a tile piece to add a new tile piece to, connect it!
	public boolean addTile(Tile existing_tile, Tile new_tile, Directions dir){
		switch(dir){
		case DOWN:
			return existing_tile.connectDown(new_tile);
		case LEFT:
			return existing_tile.connectLeft(new_tile);
		case NONE:
			return false;
		case RIGHT:
			return existing_tile.connectRight(new_tile);
		case UP:
			return existing_tile.connectUp(new_tile);
		default:
			return false;
		}
	}
	
	// Find a path for the robot to follow, if the height has increased
	public Array<Tile> findPath(Tile start, int highestPt, Directions from, Array<Tile> pathSoFar){
		if(start.isConnectedLeft() && from != Directions.RIGHT){
			pathSoFar.add(start.getLeft());
			findPath(start.getLeft(), highestPt, Directions.RIGHT, pathSoFar);
		}
		if(start.isConnectedRight() && from != Directions.LEFT){
			pathSoFar.add(start.getRight());
			findPath(start.getRight(), highestPt, Directions.LEFT, pathSoFar);
		}
		if(start.isConnectedDown() && from != Directions.UP){
			pathSoFar.add(start.getDown());
			findPath(start.getDown(), highestPt, Directions.DOWN, pathSoFar);
		}
		if(start.isConnectedUp() && from != Directions.DOWN){
			if(start.getUp().getHeight() > highestPt){
				pathSoFar.add(start.getUp());
				return pathSoFar;
			}
			findPath(start.getUp(), highestPt, Directions.UP, pathSoFar);
		}
		
		return pathSoFar;
	}
	
	// ?? 
	/*
	public void findHighestPath(Tile start, int highest){
		
		Array<Tile> connectedTiles = new Array<Tile>(4);
		
		if(start.isConnectedUp()){
			connectedTiles.add(start.getUp());
		}
		if(start.isConnectedDown()){
			connectedTiles.add(start.getDown());
		}
		
		for(Tile t : connectedTiles){
			findHighestPath(t);
		}
	} */

}
