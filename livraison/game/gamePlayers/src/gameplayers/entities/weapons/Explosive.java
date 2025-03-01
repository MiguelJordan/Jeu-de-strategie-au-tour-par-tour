package gameplayers.entities.weapons;

import gameplayers.util.geometry.*;
import gameplayers.entities.players.*;


/**La classe Explosive représente une explosif */
public abstract class Explosive extends AbstractWeapon {

    /**Indique s'il a été planté */
    protected boolean hasBeenPlanted;
    /**Indique s'il est visible */
    protected boolean visible;

    public Explosive (Personage owner, int power, boolean visibility) {
        super(owner, power);
        this.hasBeenPlanted = false;
        this.visible = visibility;
    }

    /**
     * Plante l'explosif à la position donnée
     * @param position la position
     */
    public void getsPlanted (Position position) {
        this.position = new Position(position);
        this.hasBeenPlanted = true;
    }

    public boolean isVisible () {
        return this.visible;
    }

    public boolean hasExploded () {
        return this.destroyedZone.size() > 0;
    }

    /**
     * Détruit si elle a pu pièger le joueur cible
     * @param target le joeur cible
    */
    public boolean appetizeForDestruction (Personage target) {
        if (this.hasBeenPlanted && this.position.equals(target.getPosition()) && !target.equals(this.owner)) {
            this.destroy();
            return true;
        }
        return false;
    }

    
    @Override
    public String toString() {
        return " of " + this.owner.getName() + ", " + (this.hasBeenPlanted ? "planted at " + this.getPosition() : "unplanted") + (this.visible ? ", visible" : ", unvisible");
    }

}