package com.beefyole.puzzlerunner.client;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.backends.gwt.GwtApplication;
import com.badlogic.gdx.backends.gwt.GwtApplicationConfiguration;
import com.beefyole.puzzlerunner.PuzzleRunner;

public class HtmlLauncher extends GwtApplication {

        @Override
        public GwtApplicationConfiguration getConfig () {
        		int width = 405;
        		int height = 720;
        	
                return new GwtApplicationConfiguration(width, height);
        }

        @Override
        public ApplicationListener getApplicationListener () {
                return new PuzzleRunner();
        }
}