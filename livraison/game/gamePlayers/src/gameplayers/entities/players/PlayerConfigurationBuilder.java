package gameplayers.entities.players;

import java.util.*;
import gameplayers.entities.weapons.*;
import gameplayers.config.*;


/**La classe PlayerConfigurationBuilder est un builder de PlayerConfiguration. */
public class PlayerConfigurationBuilder {
    
    /**Les points de vie de la configuration */
    private int health;
    /**Les armes de la configuration */
    private Set<Weapon> weapons;
    /**Le bouclier de la configuration */
    private Shield shield;

    /**
     * Construit une configuration pour le joueur donné. Elle s'initialise avec la configuration par défaut
     * @param player le joueur
     */
    public PlayerConfigurationBuilder (Personage player) {
        this.health = DefaultConfig.getMaxHealth();
        this.weapons = new HashSet<>();
        this.shield = new Shield(player, DefaultConfig.getShieldPower());
    }

    /**
     * Construit une configuration avec les points de vie spécifiés
     * @param initialLife les points de vie
     */
    public PlayerConfigurationBuilder withLife (int initialLife) {
        this.health = initialLife;
        return this;
    }

    /**
     * Construit une configuration avec les armes spécifiées
     * @param initialWeapons les armes
     */
    public PlayerConfigurationBuilder withWeapons (Set<Weapon> initialWeapons) {
        this.weapons = initialWeapons;
        return this;
    }

    /**
     * Construit une configuration avec le bouclier donné
     * @param initialShield le bouclier
     */
    public PlayerConfigurationBuilder withShield (Shield initialShield) {
        this.shield = initialShield;
        return this;
    }

    /**
     * Construit la configuration jusqu'à lors instanciée
     * @return la configuration construite 
     */
    public PlayerConfiguration build () {
        return new PlayerConfiguration(this.health, this.weapons, this.shield);
    }

}