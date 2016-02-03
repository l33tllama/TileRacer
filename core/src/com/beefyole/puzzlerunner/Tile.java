package com.beefyole.puzzlerunner;

public class Tile {
	
	private boolean has_up, has_down, has_left, has_right;
	private boolean up_connected, down_connected, left_connected, right_connected;
	private Tile up_t, down_t, left_t, right_t;
	private boolean is_empty = false;
	private int yPos, xPos;
	
	public Tile(boolean has_up, boolean has_down, boolean has_left, boolean has_right, int xPos, int yPos){
		this.has_up = has_up;
		this.has_down = has_down;
		this.has_left = has_left;
		this.has_right = has_right;
		this.xPos = xPos;
		this.yPos = yPos;
		up_connected = down_connected = left_connected = right_connected = false;
		if(!has_up && !has_down && !has_left && !has_right){
			is_empty = true;
		}
	}
	
	// Get x and y positions
	public int getHeight(){
		return yPos;
	}
	// might not need this..?
	public int getX(){
		return xPos;
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
	public Tile getUp(){
		if(has_up && up_connected){
			return up_t;
		}
		return new Tile(false, false, false, false, getX(), getHeight() + 1);
	}
	public Tile getDown(){
		if(has_down && down_connected){
			return up_t;
		}
		return new Tile(false, false, false, false, getX(), getHeight() - 1);
	}
	public Tile getLeft(){
		if(has_left && left_connected){
			return up_t;
		}
		return new Tile(false, false, false, false, getX() - 1, getHeight());
	}
	public Tile getRight(){
		if(has_right && right_connected){
			return up_t;
		}
		return new Tile(false, false, false, false, getX() + 1, getHeight());
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
}
