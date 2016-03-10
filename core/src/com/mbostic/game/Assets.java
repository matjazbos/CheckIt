package com.mbostic.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.assets.AssetErrorListener;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.graphics.g2d.BitmapFont;


public class Assets implements Disposable {

    public AssetCheckBox checkBox;
    public AssetRadioButton radioButton;
    public AssetButton button;
    public AssetIcons icons;
    private static Preferences prefs;

    public static final Assets instance = new Assets();
   // private AssetManager assetManager;
    FreeTypeFontGenerator generator;
    public BitmapFont roboto;
    public BitmapFont robotoBig;
    private Assets () {}
    public void init (AssetManager assetManager) {
       // this.assetManager = assetManager;
// set asset manager error handler
        //assetManager.setErrorListener(this);
// load texture atlas
        assetManager.load("images/game.atlas",
                TextureAtlas.class);
// start loading assets and wait until finished
        assetManager.finishLoading();
        generator = new FreeTypeFontGenerator(Gdx.files.internal("fonts/Roboto-Regular.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        //roboto
        parameter.size = 64;
        roboto = generator.generateFont(parameter);
        roboto.getRegion().getTexture().setFilter(
                Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        roboto.setColor(0,0,0,1);
        //roboto big
        parameter.size = 100;
        robotoBig = generator.generateFont(parameter);
        robotoBig.setColor(0, 0, 0, 1);
        generator.dispose();

        TextureAtlas atlas = assetManager.get("images/game.atlas");
        checkBox = new AssetCheckBox(atlas);
        radioButton = new AssetRadioButton(atlas);
        button = new AssetButton(atlas);
        icons = new AssetIcons(atlas);
        prefs = Gdx.app.getPreferences("prefs");
        if (!prefs.contains("bestTime")) {
            prefs.putFloat("bestTime", 300);
        }
    }
    public class AssetCheckBox {
        public final AtlasRegion cbuc;
        public final AtlasRegion cbc;
        public AssetCheckBox (TextureAtlas atlas) {
            cbuc = atlas.findRegion("cbuc");
            cbc = atlas.findRegion("cbc");
        }
    }
    public class AssetIcons {
        public final AtlasRegion play;
        public final AtlasRegion exit;
        public final AtlasRegion home;
        public final AtlasRegion settings;
        public final AtlasRegion replay;
        public AssetIcons (TextureAtlas atlas) {
            play = atlas.findRegion("play-icon");
            exit = atlas.findRegion("exit-icon");
            home = atlas.findRegion("home-icon");
            settings = atlas.findRegion("settings-icon");
            replay = atlas.findRegion("replay-icon");
        }
    }
    public class AssetButton {
        public final AtlasRegion b;
        public AssetButton (TextureAtlas atlas){
            b = atlas.findRegion("b");
        }
    }
    public class AssetRadioButton {
        public final AtlasRegion rbuc;
        public final AtlasRegion rbc;
        public AssetRadioButton (TextureAtlas atlas){
            rbuc = atlas.findRegion("rbuc");
            rbc = atlas.findRegion("rbc");
        }
    }
    public static void setBestTime(float val) {
        prefs.putFloat("bestTime", val);
        prefs.flush();
    }

    public static float getBestTime() {
        return prefs.getFloat("bestTime");
    }
    @Override
    public void dispose () {
        roboto.dispose();
        robotoBig.dispose();
    }
}
