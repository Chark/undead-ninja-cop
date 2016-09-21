package io.chark.undead_ninja_cop.game.resource;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.Sprite;

public interface ResourceManager {

    Sprite getSprite(String name);

    Sound getSound(String name);
}