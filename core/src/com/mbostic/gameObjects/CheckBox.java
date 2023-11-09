package com.mbostic.gameObjects;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.mbostic.game.Assets;
import com.mbostic.game.CheckItMain;

public class CheckBox extends AbstractObject {
    public Vector2 speed;
    public int index;
    public CheckBox (float x, float y){
        CheckItMain.cBN++;
        index = CheckItMain.cBN;
        speed = new Vector2();
        position = new Vector2(x, y);
        reguc = Assets.instance.checkBox.cbuc;
        regc = Assets.instance.checkBox.cbc;
        speed.x = (float) (Math.random()*40+20)* (MathUtils.randomBoolean() ? 1 : -1) ; //hitrost (px/s)
        speed.y = (float) (Math.random()*60+30)* (MathUtils.randomBoolean() ? 1 : -1) ;
    }
}
