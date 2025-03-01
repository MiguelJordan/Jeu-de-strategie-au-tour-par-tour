package gameplayers.entities.weapons;

import java.util.*;
import gameplayers.util.geometry.*;
import gameplayers.entities.players.*;
import gameplayers.config.*;


/**La classe Bomb repésente une bombe */
public class Bomb extends Explosive {

    /**Le timer de la bombe */
    private int timer;
    /**Le rayon de la bombe */
    private int radius;

    /**Construit une bombe avec les paramètre spécifiés */
    public Bomb (Personage owner, int power, int radius, int delay, boolean visibility) {
        super(owner, power, visibility);
        this.timer = delay;
        this.radius = radius;
    }

    /**Construit une bombe avec la configuration par défaut */
    public Bomb (Personage owner) {
        this(owner, DefaultConfig.getBombPower(), DefaultConfig.getBombRadius(), DefaultConfig.getBombTimer(), DefaultConfig.getBombVisibility());
    }


    public void setTimer (int newTimer) {
        if (newTimer > 0) {
            this.timer = newTimer;
        }
    }

    /**Fait ticker la bombe */
    public void tick () {
        if (this.hasBeenPlanted) {
            this.timer -= 1;
            if (this.timer == 0) {
                this.destroy();
            }
        }
    }

    /**
     * Détruit si elle a pu pièger le joueur cible ou si son timer est arrivé à 0
     * @param target le joeur cible
    */
    public boolean appetizeForDestruction (Personage target) {
        this.tick();
        if (this.timer <= 0) {
            return true;
        }
        return super.appetizeForDestruction(target);
    }

    /**Remplit sa zone de destruction. Ce sont sa position elle même et les positions voisnes dans un périmètre de son rayon */
    public void destroy () {
        this.destroyedZone = this.position.getNeighbors(this.radius);
        this.destroyedZone.add(new Position(this.position));
        this.owner.removeWeapon(this);
    }

    @Override
    public String toString() {
        return "Bomb" + super.toString();
    }

}