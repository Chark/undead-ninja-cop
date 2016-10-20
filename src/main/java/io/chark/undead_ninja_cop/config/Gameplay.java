package io.chark.undead_ninja_cop.config;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Gameplay settings holder class.
 */
public final class Gameplay {

    private final int maxFps;

    private final float ppm;
    private final float mpp;

    @JsonCreator
    private Gameplay(@JsonProperty("maxFps") int maxFps,
                     @JsonProperty("ppm") float ppm) {

        this.maxFps = maxFps;
        this.ppm = ppm;
        this.mpp = ppm == 0
                ? 0
                : 1 / ppm;
    }

    public int getMaxFps() {
        return maxFps;
    }

    /**
     * Get pixel per meter ratio.
     *
     * @return pixel per meter ratio.
     */
    public float getPpm() {
        return ppm;
    }

    /**
     * Get meter per pixel ratio.
     *
     * @return meter per pixel ratio.
     */
    public float getMpp() {
        return mpp;
    }
}