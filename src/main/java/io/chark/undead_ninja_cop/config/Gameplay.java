package io.chark.undead_ninja_cop.config;

/**
 * Gameplay settings holder class.
 */
public final class Gameplay {

    /**
     * Default gameplay settings.
     */
    static final Gameplay DEFAULTS =
            new Gameplay(60, 10);

    private final int maxFps;

    private final float ppm;
    private final float mpp;

    Gameplay(int maxFps, float ppm) {
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