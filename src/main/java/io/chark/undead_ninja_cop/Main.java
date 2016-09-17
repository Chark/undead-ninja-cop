package io.chark.undead_ninja_cop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import io.chark.undead_ninja_cop.game.UndeadNinjaCop;

public class Main {

    public static void main(String[] arg) {
        new LwjglApplication(new UndeadNinjaCop(), new LwjglApplicationConfiguration());
    }
}