package com.beefyole.puzzlerunner;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.beefyole.puzzlerunner.scenes.GameScene;

public class PuzzleRunner extends ApplicationAdapter {
	SpriteBatch batch;
	Texture img;
	Stage currentStage;
	GameConfig config;
	
	@Override
	public void create () {
		//batch = new SpriteBatch();
		//img = new Texture("badlogic.jpg");
		config = new GameConfig();
		config.setTallscreenResolution(405, 720);
		config.setWidescreenResolution(1280, 720);
		currentStage = new GameScene();
		Gdx.input.setInputProcessor(currentStage);
	}

	@Override
	public void render () {
		currentStage.act(Gdx.graphics.getDeltaTime());
		Gdx.gl.glClearColor(0.0f, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		currentStage.draw();
		//batch.begin();
		//batch.draw(img, 0, 0);
		//batch.end();
	}
}
