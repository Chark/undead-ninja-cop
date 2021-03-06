package io.chark.undead_ninja_cop.game.audio;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import io.chark.undead_ninja_cop.core.config.Configuration;
import io.chark.undead_ninja_cop.core.config.Settings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Helper class which simplifies playing of music and ensures that only one song is playing at any given time.
 */
public final class MusicPlayer {

    private static final Logger LOGGER = LoggerFactory.getLogger(MusicPlayer.class);

    private final Settings configuration = Configuration
            .getInstance()
            .getSettings();

    private static MusicPlayer instance;

    /**
     * Currently played music file.
     */
    private Music music;

    /**
     * Currently played song name.
     */
    private String name;

    private MusicPlayer() {
    }

    /**
     * Play song by name without looping it, current song will be stopped and disposed.
     *
     * @param name song name.
     */
    public void play(String name) {
        play(name, false);
    }

    /**
     * Play song by name, current song will be stopped and disposed.
     *
     * @param loop should the song loop.
     * @param name song name.
     */
    public void play(String name, boolean loop) {
        stop();

        this.music = load(name);
        this.name = name;

        music.setLooping(loop);
        music.setVolume(configuration.getMusicVolume());

        LOGGER.debug("Playing music, name: {}, loop: {}",
                name, loop);

        music.play();
    }

    /**
     * Stop current song and dispose it.
     */
    public void stop() {
        if (music == null) {
            return;
        }

        LOGGER.debug("Disposing current music, name: {}", name);
        music.dispose();
    }

    /**
     * Get currently played songs volume.
     *
     * @return currently played music volume.
     */
    public float getVolume() {
        if (music == null) {
            return 0;
        }
        return music.getVolume();
    }

    /**
     * Set current played songs volume.
     *
     * @param newVolume music volume.
     */
    public void changeVolume(float newVolume) {
        if (music == null) {
            return;
        }

        if (newVolume < 0) {
            newVolume = 0;
        }
        if (newVolume > 1) {
            newVolume = 1;
        }

        LOGGER.debug("Changing music volume to: {}", newVolume);
        music.setVolume(newVolume);
    }

    /**
     * Get music player instance.
     *
     * @return music player instance.
     */
    public static synchronized MusicPlayer getInstance() {
        if (instance == null) {
            instance = new MusicPlayer();
        }
        return instance;
    }

    /**
     * Load song by name.
     *
     * @param name music name.
     * @return loaded music.
     */
    private Music load(String name) {
        return Gdx.audio.newMusic(Gdx.files.internal(
                configuration.getMusicDirectory() + name));
    }
}