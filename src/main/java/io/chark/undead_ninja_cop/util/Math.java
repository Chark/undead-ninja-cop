package io.chark.undead_ninja_cop.util;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;

public final class Math {

    /**
     * Get mouse position in the world based on provided camera projection.
     *
     * @param camera camera whose projection to use.
     * @return mouse position.
     */
    public static Vector2 getMousePosition(Camera camera) {
        Vector3 pos = camera.unproject(new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0));
        return new Vector2(pos.x, pos.y);
    }
}