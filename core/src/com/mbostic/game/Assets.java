package com.mbostic.game;

import com.badlogic.gdx.assets.AssetErrorListener;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.badlogic.gdx.utils.Disposable;


public class Assets implements Disposable {

    public AssetCheckBox checkBox;

    public static final Assets instance = new Assets();
    private AssetManager assetManager;
    private Assets () {}
    public void init (AssetManager assetManager) {
        this.assetManager = assetManager;
// set asset manager error handler
        //assetManager.setErrorListener(this);
// load texture atlas
        assetManager.load("images/igrca.pack",
                TextureAtlas.class);
// start loading assets and wait until finished
        assetManager.finishLoading();

        TextureAtlas atlas
                = assetManager.get("images/igrca.pack");
        checkBox = new AssetCheckBox(atlas);
    }
    public class AssetCheckBox {
        public final AtlasRegion cbuc;
        public final AtlasRegion cbc;
        public AssetCheckBox (TextureAtlas atlas) {
            cbuc = atlas.findRegion("cbuc");
            cbc = atlas.findRegion("cbc");
        }
    }
    @Override
    public void dispose () {

    }
}
