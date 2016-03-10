package com.mbostic.gameObjects;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

public class AbstractObject {
    public TextureRegion reguc;
    public TextureRegion regc;
    public boolean checked;
    public Vector2 position;
    public AbstractObject (){
        checked = false;
    }

    public boolean tap(float x, float y) {
        if (x > position.x - 8 && x < position.x + 48
                && y > position.y - 8 && y < position.y + 48){
            this.checked = !this.checked; return true;}
        else return false;
    }
    public void render (SpriteBatch batch) {
        TextureRegion reg;
        reg = this.checked ? regc : reguc;
        batch.draw(reg.getTexture(), position.x, position.y, 0, 0, 40f, 40f, 1, 1, 0,
                reg.getRegionX(), reg.getRegionY(), reg.getRegionWidth(),
                reg.getRegionHeight(), false, false);
    }
}
