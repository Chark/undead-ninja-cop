package io.chark.undead_ninja_cop.core.config;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * General settings holder class.
 */
public final class Settings {

    private final String textureDirectory;
    private final String levelDirectory;
    private final String musicDirectory;
    private final String fontDirectory;

    private final float musicVolume;

    private final int screenWidth;
    private final int screenHeight;

    private final boolean windowed;
    private final boolean debug;

    @JsonCreator
    private Settings(@JsonProperty("textureDirectory") String textureDirectory,
                     @JsonProperty("levelDirectory") String levelDirectory,
                     @JsonProperty("musicDirectory") String musicDirectory,
                     @JsonProperty("fontDirectory") String fontDirectory,
                     @JsonProperty("musicVolume") float musicVolume,
                     @JsonProperty("screenWidth") int screenWidth,
                     @JsonProperty("screenHeight") int screenHeight,
                     @JsonProperty("windowed") boolean windowed,
                     @JsonProperty("debug") boolean debug) {
        this.textureDirectory = textureDirectory;

        this.levelDirectory = levelDirectory;
        this.musicDirectory = musicDirectory;
        this.fontDirectory = fontDirectory;
        this.musicVolume = musicVolume;
        this.screenWidth = screenWidth;
        this.screenHeight = screenHeight;
        this.windowed = windowed;
        this.debug = debug;
    }

    public String getTextureDirectory() {
        return textureDirectory;
    }

    public String getLevelDirectory() {
        return levelDirectory;
    }

    public String getFontDirectory() {
        return fontDirectory;
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