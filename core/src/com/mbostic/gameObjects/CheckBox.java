package com.mbostic.gameObjects;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.mbostic.game.Assets;

public class CheckBox {
    private TextureRegion regcbuc;
    public boolean checked;
    public Vector2 position;
    public CheckBox (float x, float y) {
        position = new Vector2(x, y);
        checked = false;
        regcbuc = Assets.instance.checkBox.cbuc;
    }
    public void render (SpriteBatch batch) {
        TextureRegion reg = null;
        reg = regcbuc;
        batch.draw(reg.getTexture(), position.x, position.y,0,0,40f,40f,1,1,0,reg.getRegionX(),reg.getRegionY(),reg.getRegionWidth(),reg.getRegionHeight(),false,false);
    }
}
