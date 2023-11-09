package com.mbostic.gameObjects;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.mbostic.game.Assets;
import com.mbostic.game.CheckItMain;

public class Button {
    public Vector2 position;
    private TextureRegion regb;
    public Button (float x, float y){
        position = new Vector2(x, y);
        CheckItMain.buttonN++;
        regb = Assets.instance.button.b;
    }
    public boolean tap(float x, float y) {
        if (x > position.x && x < position.x + 100
                && y > position.y && y < position.y + 100){
            CheckItMain.button = new Button(CheckItMain.randx(), CheckItMain.randy());
            return true;
        }
        else return  false;
    }
    public void render (SpriteBatch batch) {
        batch.draw(regb.getTexture(), position.x, position.y, 0, 0, 80, 80, 1, 1, 0,
                regb.getRegionX(), regb.getRegionY(), regb.getRegionWidth(),
                regb.getRegionHeight(), false, false);
    }
}
