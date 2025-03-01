package gameplayers.entities.players;

import gameplayers.entities.model.*;
import gameplayers.entities.weapons.*;


/**La classe PlayerProxy représente un proxy configuré pour un joueur */
public class PlayerProxy {

    /**Le joueur pour lequel est configuré le proxy */
    private Personage player;

    /**
     * Crée un nouveau proxy pour le joueur donné
     * @param player le joueur
     */
    public PlayerProxy (Personage player) {
        this.player = player;
    }

    /**
     * Indique si le joueur (donc par le proxy) peut voir une entité du jeu
     * @param entity l'entité
     */
    public boolean canSee (Entity entity) {
        if (entity instanceof Explosive) {
            Explosive explosive = (Explosive) entity;
            return explosive.isVisible() || this.player.equals(explosive.getOwner()); // il ne peut pas voir les explosifs non visibles qui ne lui appartiennent pas
        }
        return true;
    }

    public Personage getPlayer () {
        return this.player;
    }

    
    @Override
    public String toString () {
        return "Proxy of " + this.player.toString();
    }

}