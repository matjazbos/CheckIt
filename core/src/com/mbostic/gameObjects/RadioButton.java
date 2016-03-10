package com.mbostic.gameObjects;

import com.badlogic.gdx.math.Vector2;
import com.mbostic.game.Assets;
import com.mbostic.game.JakaIgrcaMain;

public class RadioButton extends AbstractObject {
public int id;
    public RadioButton (float x, float y){
        id = JakaIgrcaMain.rBN;
        JakaIgrcaMain.rBN++;
        position = new Vector2(x, y);
        reguc = Assets.instance.radioButton.rbuc;
        regc = Assets.instance.radioButton.rbc;
    }
}
