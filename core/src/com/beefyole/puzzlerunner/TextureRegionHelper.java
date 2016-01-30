package com.beefyole.puzzlerunner;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class TextureRegionHelper {
	
	int width = 0;
	int height = 0;
	int rows = 0;
	int cols = 0;
	Texture tex;
	
	public TextureRegionHelper(Texture tex, int width, int height, int rows, int cols){
		this.width = width;
		this.height = height;
		this.rows = rows;
		this.cols = cols;
		this.tex = tex;
	}
			
	public TextureRegion getRegionAt(int index){
		int xPos = (index % cols)  * width;
		int yPos = (index / rows) * height;
		return new TextureRegion(tex, xPos, yPos, width, height);
		
	}
}
