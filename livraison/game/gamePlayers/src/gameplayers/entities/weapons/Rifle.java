package gameplayers.entities.weapons;

import gameplayers.util.geometry.*;
import gameplayers.entities.players.*;
import gameplayers.config.*;


/**La classe Rifle représente un fusil */
public class Rifle extends AbstractWeapon {

    /**Le nombre de munition dont il est chargé */
    protected int ammo;
    /**Le nombre de munition maximal dont il peut être chargé */
    protected int ammoCapacity;
    /**La portée du fusil */
    protected int range;
    /**La direction dans laquelle il pointe lors du tir, 0 lorsqu'il n'est pas utilisé */
    protected int pointedDirection;

    /**
     * Construit une nouveau fusil pour le personnage donné avec les capacités données
     * @param owner le propriétaire
     * @param power la puissance
     * @param intinialAmmo le nombre de munition intial
     * @param range la portée
     */
    public Rifle (Personage owner, int power, int initialAmmo, int range) {
        super(owner, power);
        this.ammo = initialAmmo;
        this.ammoCapacity = initialAmmo;
        this.range = range;
        this.pointedDirection = 0;
    }

    /**
     * Construit un fusil avec la configuration par défaut
     * @param owner le propriétaire
     */
    public Rifle (Personage owner) {
        this(owner, DefaultConfig.getRiflePower(), DefaultConfig.getRifleAmmoCapacity(), DefaultConfig.getRifleRange());
    }

    /**
     * Charge le fusil avec un nombre de munition
     * @param loadedAmmoCount le nombre de munition
     */
    public void load (int loadedAmmoCount) {
        this.ammo += loadedAmmoCount;
        if (this.ammo > this.ammoCapacity) {
            this.ammo = this.ammoCapacity;
        }
    }

    /**
     * Décharge le fusil d'un nombre de munition
     * @param unloadedAmmoCount le nombre de munition
     */
    public void unload (int unloadedAmmoCount) {
        this.ammo -= unloadedAmmoCount;
        if (this.ammo < 0) {
            this.ammo = 0;
        }
    }

    public int getAmmo () {
        return this.ammo;
    }

    public int getAmmoCapacity () {
        return this.ammoCapacity;
    }

    /**
     * Indique que le fusil a été tiré
     * @param direction la direction de tir
     */
    public void triggerHasBeenPulled (int direction) {
        this.pointedDirection = direction;
        this.unload(1);
        this.destroy();
    }

    /**
     * Remplit la zone de destruction du fusil. Toutes les positions qui sont a portée et dans les limites de sa position sont affectées.
     */
    public void destroy () {
        if (this.pointedDirection != 0) {
            Position affectedPosition = new Position(this.position);
            int affectedPositionsCount = 0;
            while (!affectedPosition.equals(affectedPosition.getNeighbor(this.pointedDirection)) && affectedPositionsCount < this.range) {
                this.destroyedZone.add(affectedPosition.getNeighbor(this.pointedDirection));
                affectedPosition = affectedPosition.getNeighbor(this.pointedDirection);
                affectedPositionsCount += 1;
            }
            this.pointedDirection = 0;
        }
    }


    @Override
    public String toString () {
        return "Rifle of " + this.getOwner().getName() + ", ammo is " + this.ammo + ", range is " + this.range;
    }

}