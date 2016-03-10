package com.mbostic.gameObjects;


import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.mbostic.game.Assets;

/**
 * Created by Metod on 6.3.2016.
 */
public class Icon {

    public static TextureRegion playIcon = Assets.instance.icons.play;
    public static TextureRegion settingsIcon = Assets.instance.icons.settings;
    public static TextureRegion replayIcon = Assets.instance.icons.replay;
    public static TextureRegion exitIcon = Assets.instance.icons.exit;
    public static TextureRegion homeIcon = Assets.instance.icons.home;

    public TextureRegion reg;
    public Vector2 position;
    public float size;
    public Icon (float posX, float posY, float size, String iconName){
        position = new Vector2(posX, posY);
        this.size = size;
        if (iconName.equals("play")) this.reg = playIcon;
        else if (iconName.equals("settings")) this.reg = settingsIcon;
        else if (iconName.equals("replay")) this.reg = replayIcon;
        else if (iconName.equals("exit")) this.reg = exitIcon;
        else if (iconName.equals("home")) this.reg = homeIcon;
    }
    public boolean tap(float x, float y) {
        return (x > position.x && x < position.x + size
                && y > position.y && y < position.y + size);
    }
    public void render (SpriteBatch batch) {
        batch.draw(reg.getTexture(), position.x, position.y, 0, 0, size, size, 1, 1, 0,
                reg.getRegionX(), reg.getRegionY(), reg.getRegionWidth(),
                reg.getRegionHeight(), false, false);
    }
}
