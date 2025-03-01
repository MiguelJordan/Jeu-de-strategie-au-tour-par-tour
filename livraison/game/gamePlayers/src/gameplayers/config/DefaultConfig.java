package gameplayers.config;

import gameplayers.util.configmanagers.*;


public class DefaultConfig {

    private static final String DEFAULT_CONFIG_FILE_PATH = "res/configfiles/default_config.xml";
    private static final ConfigManager CONFIG = new ConfigManager(DEFAULT_CONFIG_FILE_PATH);


    public static int getMaxHealth () {
        return CONFIG.getValue("maxhealth", "default");
    }

    public static int getShieldPower () {
        return CONFIG.getValue("shieldpower", "default");
    }

    public static int getRifleAmmoCapacity () {
        return CONFIG.getValue("rifleammocapacity", "default");
    }

    public static int getRifleRange () {
        return CONFIG.getValue("riflerange", "default");
    }

    public static int getRiflePower () {
        return CONFIG.getValue("riflepower", "default");
    }

    public static int getMinePower () {
        return CONFIG.getValue("minepower", "default");
    }

    public static boolean getMineVisibility () {
        return CONFIG.getValue("minevisibility", "default") == 0 ? false : true;
    }

    public static int getBombPower () {
        return CONFIG.getValue("bombpower", "default");
    }

    public static int getBombTimer () {
        return CONFIG.getValue("bombtimer", "default");
    }

    public static int getBombRadius () {
        return CONFIG.getValue("bombradius", "default");
    }

    public static boolean getBombVisibility () {
        return CONFIG.getValue("bombvisibility", "default") == 0 ? false : true;
    }

}