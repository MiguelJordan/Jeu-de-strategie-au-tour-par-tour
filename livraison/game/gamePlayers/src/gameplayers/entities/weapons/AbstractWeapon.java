package gameplayers.entities.weapons;

import java.util.*;
import gameplayers.util.geometry.*;
import gameplayers.entities.model.*;
import gameplayers.entities.players.*;


/**La classe abstraite AbstractWeapon est héritée par toutes les armes offensives. Elle implémente Weapon */
public abstract class AbstractWeapon extends AbstractEntity implements Weapon {

    /**Le propriétaire de l'arme */
    protected Personage owner;
    /**La zone de destruction */
    protected List<Position> destroyedZone;
    /**La puissance de l'arme */
    protected int power;

    /**
     * Construit une nouvelle arme pour le propriétaire donnée, ayant la puissance indiquée
     * @param owner le propriéaire
     * @param power la puissance de l'arme
     */
    public AbstractWeapon (Personage owner, int power) {
        super(owner.getPosition());
        this.owner = owner;
        this.power = power;
        this.destroyedZone = new ArrayList<>();
        this.owner.addWeapon(this);
    }

    /**
     * Récupère le propriétaire de l'arme
     * @return le propriétaire de l'arme
     */
    @Override
    public Personage getOwner () {
        return this.owner;
    }

    /**
     * Récupère la zone de destruction de l'arme
     * @return la zone de destruction
     */
    @Override
    public List<Position> getDestroyedZone () {
        return this.destroyedZone;
    }

    /**
     * Afflige des dégâts au personnage cible d'une valeur qui est sa puissance si ce dernier n'est pas son propriétaire
     * @param target le personnage cible
     */
    @Override
    public void hit (Personage target) {
        if (!target.equals(this.owner)) { // une arme n'affecte pas son popriétaire
            target.takeDamage(this.power);
        }
    }   

}