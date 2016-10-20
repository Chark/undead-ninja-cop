package io.chark.undead_ninja_cop.config;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * General settings holder class.
 */
public final class Settings {

    private final String levelsDirectory;
    private final String musicDirectory;

    private final float musicVolume;

    private final int screenWidth;
    private final int screenHeight;

    private final boolean windowed;
    private final boolean debug;

    @JsonCreator
    private Settings(@JsonProperty("levelDirectory") String levelDirectory,
                     @JsonProperty("musicDirectory") String musicDirectory,
                     @JsonProperty("musicVolume") float musicVolume,
                     @JsonProperty("screenWidth") int screenWidth,
                     @JsonProperty("screenHeight") int screenHeight,
                     @JsonProperty("windowed") boolean windowed,
                     @JsonProperty("debug") boolean debug) {

        this.levelsDirectory = levelDirectory;
        this.musicDirectory = musicDirectory;
        this.musicVolume = musicVolume;
        this.screenWidth = screenWidth;
        this.screenHeight = screenHeight;
        this.windowed = windowed;
        this.debug = debug;
    }

    public String getLevelDirectory() {
        return levelsDirectory;
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
        return !isWindowed();
    }

    public boolean isDebug() {
        return debug;
    }
}