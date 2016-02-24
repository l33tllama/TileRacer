package com.beefyole.puzzlerunner.worlds;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.beefyole.puzzlerunner.actors.TileActor;
import com.beefyole.puzzlerunner.old.GameObject2D;

public class Tile{
	
	private boolean has_up, has_down, has_left, has_right;
	private boolean up_connected, down_connected, left_connected, right_connected;
	private Tile up_t, down_t, left_t, right_t;
	private boolean is_empty = false;
	private int yIndex, xIndex;
	private Texture tex;
	private TextureRegion region;
	private TileActor tileActor;
	
	public Tile(){
		has_up = has_down = has_left = has_right = false;
		xIndex = yIndex = 0;
		up_connected = down_connected = left_connected = right_connected = false;
		is_empty = true;
	}
	
	public Tile(boolean has_up, boolean has_down, boolean has_left, boolean has_right, int xIndex, int yIndex){
		this.has_up = has_up;
		this.has_down = has_down;
		this.has_left = has_left;
		this.has_right = has_right;
		this.xIndex = xIndex;
		this.yIndex = yIndex;
		up_connected = down_connected = left_connected = right_connected = false;
		if(!has_up && !has_down && !has_left && !has_right){
			is_empty = true;
		}
	}
	
	// post init when used in memory pooling
	public void postInit(TileActor tileActor, boolean has_up, boolean has_down, boolean has_left, boolean has_right, int xIndex, int yIndex){
		this.has_up = has_up;
		this.has_down = has_down;
		this.has_left = has_left;
		this.has_right = has_right;
		this.xIndex = xIndex;
		this.yIndex = yIndex;
		up_connected = down_connected = left_connected = right_connected = false;
		if(!has_up && !has_down && !has_left && !has_right){
			is_empty = true;
		}
		this.tileActor = tileActor;
	}
	
	public void setTileActor(TileActor tileActor){
		this.tileActor = tileActor;
	}
	
	public TileActor getTileActor(){
		return this.tileActor;
	}
	
	public void createTexture(Texture tex){
		this.tex = tex;
	}
	
	// Get x and y positions
	public int getGridY(){
		return yIndex;
	}
	// might not need this..?
	public int getGridX(){
		return xIndex;
	}
	
	// check if tile has edge (read only)
	public boolean hasUp(){
		return has_up;
	}
	public boolean hasDown(){
		return has_up;
	}
	public boolean hasLeft(){
		return has_up;
	}
	public boolean hasRight(){
		return has_up;
	}
	
	// check if tile connected (read only)
	public boolean isConnectedUp(){
		return up_connected;
	}
	public boolean isConnectedDown(){
		return up_connected;
	}
	public boolean isConnectedLeft(){
		return up_connected;
	}
	public boolean isConnectedRight(){
		return up_connected;
	}
	
	// check if this tile can connect to something (used in finding tiles to connect to)
	public boolean canConnectUp(){
		return has_up && !up_connected;
	}
	public boolean canConnectDown(){
		return has_down && !down_connected;
	}
	public boolean canConnectLeft(){
		return has_left && !left_connected;
	}
	public boolean canConnectRight(){
		return has_right && !right_connected;
	}
	
	public boolean isEmpty(){
		return is_empty;
	}
	
	// test if tile can connect (for displaying options)
	public boolean testConnectUp(Tile up_t){
		return up_t.hasDown() && !up_t.isConnectedDown();
	}
	
	public boolean testConnectDown(Tile down_t){
		return down_t.hasUp() && !down_t.isConnectedUp();
	}
	public boolean testConnectRight(Tile right_t){
		return right_t.hasLeft() && !right_t.isConnectedLeft();
	}
	public boolean testConnectLeft(Tile left_t){
		return left_t.hasRight() && !left_t.isConnectedRight();
	}
	
	// get the surrounding tiles. If empty, create empty tile
	public Tile getUpTile(){
		if(has_up && up_connected){
			return up_t;
		}
		return new Tile(false, false, false, false, getGridX(), getGridY() + 1);
	}
	public Tile getDownTile(){
		if(has_down && down_connected){
			return up_t;
		}
		return new Tile(false, false, false, false, getGridX(), getGridY() - 1);
	}
	public Tile getLeftTile(){
		if(has_left && left_connected){
			return up_t;
		}
		return new Tile(false, false, false, false, getGridX() - 1, getGridY());
	}
	public Tile getRightTile(){
		if(has_right && right_connected){
			return up_t;
		}
		return new Tile(false, false, false, false, getGridX() + 1, getGridY());
	}
	
	// Connect the tiles. Two way.
	public boolean connectUp(Tile up_t){
		if(testConnectUp(up_t)){
			up_connected = true;
			this.up_t = up_t;
			if(up_t.testConnectDown(this)){
				// will return false because it will try to connect this back up again, but it's already connected..
				up_t.connectDown(this);				
				return true;
			} else {
				return false;
			}
		} else {
			return false;
		}
	}
	
	public boolean connectLeft(Tile left_t){
		if(testConnectLeft(left_t)){
			right_connected = true;
			this.left_t = left_t;
			if(left_t.testConnectRight(this)){
				left_t.connectRight(this);
				return true;
			} else {
				return false;
			}
		} else {
			return false;
		}
	} 
	
	public boolean connectRight(Tile right_t){
		if(testConnectRight(right_t)){
			right_connected = true;
			this.right_t = right_t;
			if(right_t.testConnectLeft(this)){
				right_t.connectLeft(this);
				return true;
			} else {
				return false;
			}
		} else {
			return false;
		}
	}
	
	public boolean connectDown(Tile down_t){
		if(testConnectDown(up_t)){
			down_connected = true;
			this.down_t = down_t;
			if(down_t.testConnectUp(this)){
				// ignore false return (already tested above and connected..)
				down_t.connectUp(this);
				return true;
			} else {
				return false;
			}
		} else {
			return false;
		}
	}
	public static int getTileIndex(boolean up, boolean down, boolean left, boolean right){
		if(!up && !down && !left && right){	// 0 0 0 1 - 1
			return 5;
		}
		if(!up && !down && left && !right){	// 0 0 1 0 - 2
			return 13;
		}
		if(!up && !down && left && right){	// 0 0 1 1 - 3
			return 31;
		}
		if(!up && down && !left && !right){	// 0 1 0 0 - 4
			return 19;
		}
		if(!up && down && !left && right){	// 0 1 0 1 - 5
			return 0;
		}
		if(!up && down && left && !right){	// 0 1 1 0 - 6
			return 20;
		}
		if(!up && down && left && right){	// 0 1 1 1 - 7 
			return 1;
		}
		if(up && !down && !left && !right){	// 1 0 0 0 - 8
			return 27;
		}
		if(up && !down && !left && right){	// 1 0 0 1 - 9
			return 15; 
		}
		if(up && !down && left && !right){	// 1 0 1 0 - 10
			return 7;
		}
		if(up && !down && left && right){	// 1 0 1 1 - 11
			return 16;
		}
		if(up && down && !left && !right){	// 1 1 0 0 - 12
			return 6;
		}
		if(up && down && !left && right){	// 1 1 0 1 - 13
			return 14;
		}
		if(up && down && left && !right){	// 1 1 1 0 - 14
			return 22;
		}
		if(up && down && left && right){	// 1 1 1 1 - 15
			return 23;
		}
		
		return 33;
	}
	public void dispose(){
		tex.dispose();
	}
}
