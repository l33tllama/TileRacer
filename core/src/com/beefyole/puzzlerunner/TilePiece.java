package com.beefyole.puzzlerunner;

import com.badlogic.gdx.utils.Array;

public class TilePiece {
	
	public enum PieceName{
		UP, DOWN, LEFT, RIGHT, NONE
	}
	
	public String name = "default";
	
	static Array<TilePiece> allTiles = new Array<TilePiece>(100);
		
	boolean has_up, has_down, has_left, has_right;
	
	boolean up_connected, down_connected, left_connected, right_connected;
	TilePiece up_p, down_p, left_p, right_p;
	int height = 0;
	int width = 0;
	boolean marked = false;
	public boolean marked_up, marked_down, marked_left, marked_right;
	static TilePiece start;
	public PieceName connection;
	public int deltaX;
	public int deltaY;

	public TilePiece(boolean has_up, boolean has_down, boolean has_left, boolean has_right, int width, int height){
		this.has_up = has_up;
		this.has_down = has_down;
		this.has_left = has_left;
		this.has_right = has_right;
		this.height = height;
		this.width = width;
		up_connected = down_connected = left_connected = right_connected = false;
		marked_up = marked_down = marked_left = marked_right = false;
		allTiles.add(this);
	}
	
	public static TilePiece getStartPiece(){
		return start;
	}
	
	public static void setStartPiece(TilePiece start){
		TilePiece.start = start;
	}
	
	public boolean hasDown() {
		return has_down;
	}

	public boolean hasLeft() {
		return has_left;
	}

	public boolean hasRight() {
		return has_right;
	}

	public boolean hasUp() {
		return has_up;
	}
	
	public void setHeight(int height){
		this.height = height;
	}
	public void setWidth(int width){
		this.width = width;
	}
	
	public boolean getMarked(){
		return marked;
	}
	
	public void mark(){
		marked = true;
	}
	
	public void unMark(){
		marked = false;
	}
	
	public int getHeight(){
		return height;
	}
	
	public int getWidth(){
		return width;
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
	
	public boolean testConnectUp(TilePiece o){
		return has_up && o.has_down && !up_connected;
	}
	
	public boolean testConnectDown(TilePiece o){
		return has_down && o.has_up && !down_connected;
	}
	
	public boolean testConnectLeft(TilePiece o){
		return has_left && o.has_right && !left_connected;
	}
	
	public boolean testConnectRight(TilePiece o){
		return has_right && o.has_left && !right_connected;
	}
	
	public static TilePiece createStartingPiece(){
		TilePiece startingPiece = new TilePiece(true, false, false, false, 2, 0);
		startingPiece.name = "world";
		TilePiece.setStartPiece(startingPiece);
		return startingPiece;
	}
	
	// test tile piece, particulalry with exits
	public PieceName testTilePiece(TilePiece s){
		//topPiece = TilePiece.findHighestPiece(startingPiece, 0);
		
		//TilePiece s = selectablePieces.get(selectedIndex);
		if(this.testConnectUp(s)){
			return PieceName.UP;
		}
		if(this.testConnectDown(s)){
			return PieceName.DOWN;
		}
		if(this.testConnectLeft(s)){
			return PieceName.LEFT;
		}
		if(this.testConnectRight(s)){
			return PieceName.RIGHT;
		}	
		
		return PieceName.NONE;
	}
	
	public void connectPiece(TilePiece s, PieceName dir){
		//TilePiece s = selectablePieces.get(selectedIndex);
		switch(dir){
		case DOWN:
			this.connectDown(s);
			break;
		case LEFT:
			this.connectLeft(s);
			break;
		case NONE:
			break;
		case RIGHT:
			this.connectRight(s);
			break;
		case UP:
			this.connectUp(s);
			break;
		default:
			break;
		}
	}
	
	public static TilePiece generateRndTilePiece(){
		boolean udlr[] = new boolean[4];
		for(int i = 0; i < 4; i++){
			double r = Math.random();
			udlr[i] = r > 0.5? true : false;
		}
		//selectablePieces.insert(index, );
		//int regionID = TilePiece.getTileIndex(udlr[0], udlr[1], udlr[2], udlr[3]);
		//System.out.println("Creating tile piece at index: " + regionID);
		return new TilePiece(udlr[0], udlr[1], udlr[2], udlr[3], 0, 0);
	}
	
	// magic algorithm by Al Goreithm
	public static TilePiece findHighestPiece(TilePiece start, int height){
		//int highestPiece = height;
		if(start.has_up && start.up_connected && !start.marked && start.height >= height){
			start.marked = true;
			findHighestPiece(start.up_p, height);
		}
		if(start.has_down && start.down_connected && !start.marked){
			start.marked = true;
			findHighestPiece(start.down_p, height);
		}
		if(start.has_left && start.left_connected && !start.marked){
			start.marked = true;
			findHighestPiece(start.left_p, height);
		}
		if(start.has_right && start.right_connected && !start.marked){
			start.marked = true;
			findHighestPiece(start.right_p, height);
		}
		return start;
	}
	
	public static void unMarkAllSides(){
		for(TilePiece t : allTiles){
			t.marked_up = false;
			t.marked_down = false;
			t.marked_left = false;
			t.marked_right = false;
		}
	}
	
	public static void unMarkAll(){
		for(TilePiece t : allTiles){
			t.unMark();
		}
	}
	
	public static boolean checkIfExitAndMark(TilePiece p){
		if(p.marked){
			return false;
		}
		if(p.has_up && !p.up_connected){
			p.mark();
			return true;
		}
		if(p.has_down && !p.down_connected){
			p.mark();
			return true;
		}
		if(p.has_left && !p.left_connected){
			p.mark();
			return true;
		}
		if(p.has_right && !p.right_connected){
			p.mark();
			return true;
		}
		return false;
	}
	
	public static int countExits(){
		int count = 0;
		for(TilePiece p : allTiles){
			if(p.has_up && !p.up_connected){
				count++;
			}
			if(p.has_down && !p.down_connected){
				count++;
			}
			if(p.has_left && !p.left_connected){
				count++;
			}
			if(p.has_right && !p.right_connected){
				count++;
			}
		}
		return count;
	}
	
	
	public static Array<TilePiece> getExitPieces(TilePiece start, Array<TilePiece> listSoFar){
		/*System.out.println("Testing " + start.name);
		System.out.println("has up: " + start.has_up);
		System.out.println("has dn: " + start.has_down);
		System.out.println("Up conn: " + start.up_connected);
		System.out.println("Dn conn: " + start.down_connected);
		System.out.println("marked: " + start.marked); */
		//int totalExits = countExits();
		//int totalMarked = 0;
		for(int i = 0; i < allTiles.size; i++){
			TilePiece tp = allTiles.get(i);
			if(tp.name == "world"){
				if(tp.has_up && !tp.up_connected){
					listSoFar.add(tp);
				}
				if(tp.has_down && !tp.down_connected){
					listSoFar.add(tp);
				}
				if(tp.has_left && !tp.left_connected){
					listSoFar.add(tp);
				}
				if(tp.has_right && !tp.right_connected){
					listSoFar.add(tp);
				}
			}
		}
		return listSoFar;
		
		/* if all outputs have not been marked
		if(start.has_up && !start.up_connected && !start.marked_up){
			//System.out.println("Testing " + start.name);
			start.marked_up = true;
			listSoFar.add(start);
			unMarkAll();
			getExitPieces(TilePiece.getStartPiece(), listSoFar);
		} else if (start.has_up && start.up_connected && !start.marked){
			System.out.println("Going up");
			start.up_p.mark();
			getExitPieces(start.up_p, listSoFar);
		} 
		
		if(start.has_left && !start.left_connected && !start.marked_left){
			start.marked_left = true;
			listSoFar.add(start);
			unMarkAll();
			getExitPieces(TilePiece.getStartPiece(), listSoFar);
		} else if (start.has_left && start.left_connected && !start.marked){
			System.out.println("Going left");
			start.left_p.mark();
			getExitPieces(start.left_p, listSoFar);
		}
		
		if(start.has_down && !start.down_connected && !start.marked_down){
			start.marked_down = true;
			listSoFar.add(start);
			unMarkAll();
			getExitPieces(TilePiece.getStartPiece(), listSoFar);
		} else if (start.has_down && start.down_connected && ! start.marked){
			start.down_p.mark();
			System.out.println("Going down");
			getExitPieces(start.down_p, listSoFar);
		}
		
		if(start.has_right && !start.right_connected && !start.marked_right){
			start.marked_right = true;
			listSoFar.add(start);
			unMarkAll();
			getExitPieces(TilePiece.getStartPiece(), listSoFar);
		} else if (start.has_right && start.right_connected && ! start.marked){
			System.out.println("Going right");
			start.right_p.mark();
			getExitPieces(start.right_p, listSoFar);
		}
		System.out.println("Finished looking..");
		// if has dir, and connected -> mark, follow connection
		// if has dir and not connected -> mark, restart loop
		
		start.mark();
				
		unMarkAllSides();
		//unMarkAll();
		return listSoFar; */
	}
	
	public void connectUp(TilePiece p){
		if(testConnectUp(p)){
			p.setHeight(height + 1);
			p.setWidth(width);
			this.up_p = p;
			p.down_p = this;
			this.up_connected = true;
			p.down_connected = true;
			this.marked = false;
			System.out.println("Connected up.. to: " + p.name);
		} else {
			System.out.println("Couldn't connect up!");
		}
	}
	
	public void connectDown(TilePiece p){
		if(testConnectDown(p)){
			p.setHeight(height - 1);
			p.setWidth(width);
			this.down_p = p;
			p.up_p = this;
			this.down_connected = true;
			p.up_connected = true;
			this.marked = false;

		}else {
			System.out.println("Couldn't connect dwon!");
		}
	}
	
	public void connectLeft(TilePiece p){
		if(testConnectLeft(p)){
			p.setHeight(height);
			p.setWidth(width - 1);
			this.left_p = p;
			p.right_p = this;
			this.left_connected = true;
			p.right_connected = true;
			this.marked = false;

		}else {
			System.out.println("Couldn't connect left!");
		}
	}
	
	public void connectRight(TilePiece p){
		if(testConnectRight(p)){
			p.setHeight(height);
			p.setWidth(width + 1);
			this.right_p = p;
			p.left_p = this;
			this.right_connected = true;
			p.left_connected = true;
			this.marked = false;

		}else {
			System.out.println("Couldn't connect right!");
		}
	}
}
