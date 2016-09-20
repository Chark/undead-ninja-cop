package io.chark.undead_ninja_cop.config;

public class Response {

    private GeneralSettings general;

    private GameSettings game;

    public GeneralSettings getGeneral() {
        return general;
    }

    public GameSettings getGame() {
        return game;
    }

    public static class GeneralSettings {
        private String musicDirectory;
        private float musicVolume;
        private String screenWidth;
        private String screenHeight;
        private boolean windowed;

        public String getMusicDirectory() {
            return musicDirectory;
        }

        public float getMusicVolume() {
            return musicVolume;
        }

        public String getScreenWidth() {
            return screenWidth;
        }

        public String getScreenHeight() {
            return screenHeight;
        }

        public boolean isWindowed() {
            return windowed;
        }
    }

    public static class GameSettings {
        private int maxFps;
        private int ppm;

        public int getMaxFps() {
            return maxFps;
        }

        public int getPpm() {
            return ppm;
        }
    }
}
