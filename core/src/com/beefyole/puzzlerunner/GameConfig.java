package com.beefyole.puzzlerunner;

import com.badlogic.gdx.Gdx;

public class GameConfig {
	static int widescreen_width;
	static int widescreen_height;
	static int tallscreen_width;
	static int tallscreen_height;
	
	public GameConfig(){
		//widescreen_width = 1280;
		//widescreen_height = 720;
	}
	
	public void setTallscreenResolution(int width, int height){
		tallscreen_width = width;
		tallscreen_height = height;
	}
	
	public void setWidescreenResolution(int width, int height){
		widescreen_width = width;
		widescreen_height = height;
	}
	
	public int getTallscreenWidth(){
		return tallscreen_width;
	}
	
	public int getTallscreenHeight(){
		return tallscreen_height;
	}
	
	public int getScreenWidth(){
		switch(Gdx.app.getType()){
		case Desktop:
			return widescreen_width;
		default:
			return tallscreen_width;
		}
	}
	
	public int getScreenHeight(){
		switch(Gdx.app.getType()){
		case Desktop:
			return widescreen_height;
		default:
			return tallscreen_height;
		}
	}
}
