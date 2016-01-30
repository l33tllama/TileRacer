package com.beefyole.puzzlerunner;

public class TilePiece {
	
	boolean has_up, has_down, has_left, has_right;
	boolean  up_connected, down_connected, left_connected, right_connected;
	TilePiece up_p, down_p, left_p, right_p;

	public TilePiece(boolean has_up, boolean has_down, boolean has_left, boolean has_right){
		this.has_up = has_up;
		this.has_down = has_down;
		this.has_left = has_left;
		this.has_right = has_right;
	}
	
	public boolean hasUp(){
		return has_up;
	}
	
	public boolean hasDown(){
		return has_down;
	}
	
	public boolean hasLeft(){
		return has_left;
	}
	
	public boolean hasRight(){
		return has_right;
	}
	
	boolean testConnectUp(){
		return has_up && !up_connected;
	}
	
	boolean testConnectDown(){
		return has_up && !up_connected;
	}
	
	boolean testConnectLeft(){
		return has_up && !up_connected;
	}
	
	boolean testConnectRight(){
		return has_up && !up_connected;
	}
	
	void connectUp(TilePiece p){
		if(testConnectUp()){
			this.up_p = p;
		}
	}
	
	void connectDown(TilePiece p){
		if(testConnectDown()){
			this.up_p = p;
		}
	}
	
	void connectLeft(TilePiece p){
		if(testConnectLeft()){
			this.up_p = p;
		}
	}
	
	void connectRight(TilePiece p){
		if(testConnectRight()){
			this.up_p = p;
		}
	}
	
	

}
