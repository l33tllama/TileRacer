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
			return 11;
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
	
	/* Un-needed?
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
	} */
	
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
