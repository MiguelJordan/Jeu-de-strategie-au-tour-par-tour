package gameplayers.entities.players;

import java.util.*;
import gameplayers.entities.weapons.*;

/**La classe PlayerConfiguration représente une configuration du joueur */
public class PlayerConfiguration {

    /**Les points de vie initiaux */
    private int initialHealth;
    /**Les armes initiales */
    private Set<Weapon> initialWeapons;
    /**Le couclier ininitia */
    private Shield initialShield;

    /**
     * Construit une nouvelle configuration avec les points de vies, les armes et le bouclier spécifiés
     * @param health les points de vie
     * @param weapons les armes
     * @param shield le bouclier
     */
    public PlayerConfiguration (int health, Set<Weapon> weapons, Shield shield) {
        this.initialHealth = health;
        this.initialWeapons = weapons;
        this.initialShield = shield;
    }

    /**
     * Récupère les points de vie initiaux de la configuration
     * @return les points de vie inititiaux
     */
    public int getInitialHealth () {
        return this.initialHealth;
    }

    /**
     * Récupère les armes initiales de la configuration
     * @return les armes initiales
     */
    public Set<Weapon> getInitialWeapons () {
        return this.initialWeapons;
    }

    /**
     * Récupère le bouclier initial de la configuration
     * @return le bouclier inititial
     */
    public Shield getInitialShield () {
        return this.initialShield;
    }

}