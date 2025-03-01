package fightgame.model.factories;

import java.util.*;
import gameplayers.entities.players.*;
import gameplayers.entities.weapons.*;
import gameplayers.util.configmanagers.*;
import gameplayers.util.geometry.*;


/**Une factory pour créer de joueurs de différents types  */
public class FighterFactory {

    private static final String FACTORY_DEFAULT_CONFIG_FILE_PATH = "res/configfiles/config1.xml";

    public static final String DEMOLISHER = "demolisher";
    public static final String SNIPER = "sniper";
    public static final String TACTICIAN = "tactician";
    public static final String DEFENDER = "defender";
    public static final String TANKER = "tanker";

    private ConfigManager config;

    public FighterFactory (ConfigManager config) {
        this.config = config;
    }

    public FighterFactory (String configFilePath) {
        this(new ConfigManager(configFilePath));
    }

    /**Utilise le fichier par défaut */
    public FighterFactory () {
        this(FACTORY_DEFAULT_CONFIG_FILE_PATH);
    }
    
    /**
     * Créé un joueur du type donné. Prends en paramètre le joueur lorsque ce dont on a besoin en juste la configuration du joueur.
     * On fait entrer le joueur dans l'usine et il ressort configuré avec son type
     */
    public Personage createFighter (Personage fighter, String fighterType) {
        fighter.init(
            new PlayerConfigurationBuilder(fighter)
                .withLife(this.getFighterHealth(fighterType))
                .withWeapons(this.getFighterWeapons(fighter, fighterType))
                .withShield(this.getFighterShield(fighter, fighterType))
                .build()
        );
        return fighter;
    }

    public Personage createFighter (String name, Position position, String fighterType) {
        return this.createFighter(new Player(name, position), fighterType);
    }

    public int getFighterHealth(String fighterType) {
        return this.config.getValue("maxhealth", fighterType);
    }

    public Shield getFighterShield (Personage fighter, String fighterType) {
        return new Shield(fighter, this.config.getValue("shieldpower", fighterType));
    }

    public Set<Weapon> getFighterWeapons (Personage fighter, String fighterType) {
        Set<Weapon> fighterWeapons = new HashSet<>();
        fighterWeapons.addAll(this.getFighterRifles(fighter, fighterType));
        fighterWeapons.addAll(this.getFighterMines(fighter, fighterType));
        fighterWeapons.addAll(this.getFighterBombs(fighter, fighterType));
        return fighterWeapons;
    }

    public Set<Weapon> getFighterRifles (Personage fighter, String fighterType) {
        List<Integer> riflesAmmoCapacities = this.config.getValues("rifleammocapacity", fighterType);
        List<Integer> riflesRanges = this.config.getValues("riflerange", fighterType);
        List<Integer> riflesPowers = this.config.getValues("riflepower", fighterType);
        int riflesCount = Math.min(riflesAmmoCapacities.size(), Math.min(riflesRanges.size(), riflesPowers.size()));
        Set<Weapon> rifles = new HashSet<>();
        for (int i = 0; i < riflesCount; i += 1) {
            rifles.add(new Rifle(fighter, riflesAmmoCapacities.get(i), riflesRanges.get(i), riflesPowers.get(i)));
        }
        return rifles;
    }

    public Set<Weapon> getFighterMines (Personage fighter, String fighterType) {
        List<Integer> minesPowers = this.config.getValues("minepower", fighterType);
        List<Integer> minesVisibilities = this.config.getValues("minevisibility", fighterType);
        int minesCount = Math.min(minesPowers.size(), minesVisibilities.size());
        Set<Weapon> mines = new HashSet<>();
        for (int i = 0; i < minesCount; i += 1) {
            boolean mineVisibility = minesVisibilities.get(i) == 0 ? false : true;
            mines.add(new Mine(fighter, minesPowers.get(i), mineVisibility));
        }
        return mines;
    }

    public Set<Weapon> getFighterBombs (Personage fighter, String fighterType) {
        List<Integer> bombsPowers = this.config.getValues("bombpower", fighterType);
        List<Integer> bombsRadiuses = this.config.getValues("bombradius", fighterType);
        List<Integer> bombsVisibilities = this.config.getValues("bombvisibility", fighterType);
        int bombTimer = this.config.getValue("bombtimer", "default");
        int bombsCount = Math.min(bombsVisibilities.size(), Math.min(bombsPowers.size(), bombsRadiuses.size()));
        Set<Weapon> bombs = new HashSet<>();
        for (int i = 0; i < bombsCount; i += 1) {
            boolean bombVisibility = bombsVisibilities.get(i) == 0 ? false : true;
            bombs.add(new Bomb(fighter, bombsPowers.get(i), bombsRadiuses.get(i), bombTimer, bombVisibility));
        }
        return bombs;
    }

    
}