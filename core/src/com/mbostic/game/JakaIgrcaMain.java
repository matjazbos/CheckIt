package com.mbostic.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mbostic.gameObjects.CheckBox;

public class JakaIgrcaMain extends ApplicationAdapter {
	SpriteBatch batch;
	CheckBox checkBox;
	
	@Override
	public void create () {
		batch = new SpriteBatch();
		Assets.instance.init(new AssetManager());
		checkBox  = new CheckBox(50,50);

	}

	@Override
	public void render () {
		Gdx.gl.glClearColor(1, 1, 1, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		batch.begin();
		checkBox.render(batch);
		batch.end();
	}
}
