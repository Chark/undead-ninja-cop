package io.chark.undead_ninja_cop.core;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator.FreeTypeFontParameter;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.utils.Disposable;
import io.chark.undead_ninja_cop.core.config.Configuration;
import io.chark.undead_ninja_cop.core.config.Settings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

public class ResourceLoader implements Disposable {

    private static final Logger LOGGER = LoggerFactory.getLogger(ResourceLoader.class);
    private static final String FONT_KONG_TEXT = "kong_text.ttf";
    private static final int FONT_DEFAULT_SIZE = 12;

    public static final String TEST_TEXTURE = "test.png";

    private final Settings settings = Configuration
            .getInstance()
            .getSettings();

    private final Map<String, Texture> textures = new HashMap<>();
    private final Map<String, BitmapFont> fonts = new HashMap<>();

    private final TmxMapLoader tmxMapLoader = new TmxMapLoader();

    /**
     * Get default game font.
     *
     * @return font.
     */
    public BitmapFont getDefaultFont() {
        return getFont(FONT_KONG_TEXT, FONT_DEFAULT_SIZE);
    }

    /**
     * Get font by name and size.
     *
     * @param name font name.
     * @param size desired font size.
     * @return font.
     */
    public BitmapFont getFont(String name, int size) {
        String nameWithSize = String.format("%d_%s", size, name);

        BitmapFont font = fonts.get(nameWithSize);
        if (font == null) {
            LOGGER.debug("Loading Font: {}", nameWithSize);

            FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx
                    .files.internal(settings.getFontDirectory() + name));

            FreeTypeFontParameter parameter = new FreeTypeFontParameter();
            parameter.size = size;

            font = generator.generateFont(parameter);
            fonts.put(nameWithSize, font);
        }
        return font;
    }

    /**
     * Get texture by name.
     *
     * @param name texture name.
     * @return texture.
     */
    public Texture getTexture(String name) {
        Texture texture = textures.get(name);
        if (texture == null) {
            LOGGER.debug("Loading Texture: {}", name);

            texture = new Texture(settings.getTextureDirectory() + name);
            textures.put(name, texture);
        }
        return texture;
    }

    /**
     * Get tiled map by name.
     *
     * @param name tiled map name.
     * @return tiled map.
     */
    public TiledMap getTiledMap(String name) {
        return tmxMapLoader.load(settings.getLevelDirectory() + name);
    }

    /**
     * Cleanup all resources.
     */
    @Override
    public void dispose() {
        List<Disposable> disposables = new ArrayList<>();
        disposables.addAll(textures.values());
        disposables.addAll(fonts.values());
        disposables.forEach(Disposable::dispose);
    }
}