package com.beefyole.puzzlerunner;

import java.util.HashMap;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.Array;

public abstract class Scene extends Stage {
	
	private Array<Actor> actors;
	private HashMap<Actor, Integer> actorMap;
	private static final int MAX_ACTORS = 2000;
	private static final int MAX_SPRITES = 4000;
	private static SpriteBatch batcher;
	
	public Scene(){
		setActors(new Array<Actor>(MAX_ACTORS));
		actorMap = new HashMap<Actor, Integer>();
		batcher = new SpriteBatch(MAX_SPRITES);
	}

	public Array<Actor> getActors() {
		return actors;
	}
	
	public void addActor(Actor actor){
		this.actors.add(actor);
	}
	
	public void removeActor(Actor actor){
		for(int i = 0; i < actors.size; i++){
			Actor tmpActor = actors.get(i);
			if(actor.equals(tmpActor)){
				actors.removeIndex(i);
			}
		}
	}

	private void setActors(Array<Actor> actors) {
		this.actors = actors;
	}
		
	public void destroy(){
		actors.clear();
		actorMap.clear();
		batcher.dispose();
	}

}
