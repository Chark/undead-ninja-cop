package io.chark.undead_ninja_cop.config;

/**
 * General settings holder class.
 */
public final class Settings {

    /**
     * Default general settings.
     */
    static final Settings DEFAULTS =
            new Settings("music/", 1, 1024, 768, true, false);

    private final String musicDirectory;
    private final float musicVolume;

    private final int screenWidth;
    private final int screenHeight;

    private final boolean windowed;
    private final boolean debug;

    Settings(String musicDirectory,
             float musicVolume,
             int screenWidth,
             int screenHeight,
             boolean windowed,
             boolean debug) {

        this.musicDirectory = musicDirectory;
        this.musicVolume = musicVolume;
        this.screenWidth = screenWidth;
        this.screenHeight = screenHeight;
        this.windowed = windowed;
        this.debug = debug;
    }

    public String getMusicDirectory() {
        return musicDirectory;
    }

    public float getMusicVolume() {
        return musicVolume;
    }

    public int getScreenWidth() {
        return screenWidth;
    }

    public int getScreenHeight() {
        return screenHeight;
    }

    public boolean isWindowed() {
        return windowed;
    }

    public boolean isFullScreen() {
        return !windowed;
    }

    public boolean isDebug() {
        return debug;
    }
}