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
		
		Tile emptyTile = new Tile(false, false, false, false);
		
	}
	
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
	
	public boolean addTile(Tile existing_tile, Tile new_tile, Directions dir){
		switch(dir){
		case DOWN:
			break;
		case LEFT:
			break;
		case NONE:
			break;
		case RIGHT:
			break;
		case UP:
			return existing_tile.connectUp(new_tile);
			break;
		default:
			break;
		
		}
		
			
		return false;
	}
	
	public Array<Tile> findPath(Tile start, int highestPt, Directions from, Array<Tile> pathSoFar){
		if(start.isConnectedLeft() && from != Directions.RIGHT){
			pathSoFar.add(start.getLeft());
			// if start == height, return path..
			findPath(start.getLeft(), highestPt, Directions.RIGHT, pathSoFar);
		}
		if(start.isConnectedRight() && from != Directions.LEFT){
			findPath(start.getRight(), highestPt, Directions.LEFT, pathSoFar);
		}
	}
	
	public void findHighestPath(Tile start, int highest){
		
		Array<Tile> connectedTiles = new Array<Tile>(4);
		
		if(start.isConnectedUp()){
			connectedTiles.add(start.getUp());
		}
		if(start.isConnectedDown()){
			connectedTiles.add(start.getDown());
		}
		
		for(Tile t : connectedTiles){
			findHighestPath(t)
		}
	}

}
