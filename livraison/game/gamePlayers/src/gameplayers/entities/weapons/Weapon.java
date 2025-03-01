package gameplayers.entities.weapons;

import java.util.*;
import gameplayers.util.geometry.*;
import gameplayers.entities.model.*;
import gameplayers.entities.players.*;


/**L'interface Weapon est implémentée par les armes offensives */
public interface Weapon extends Entity {

    /**Permet à l'arme de détruire. Dans les faits, remplit sa zone de destruction avec les positions affectées */
    public void destroy ();

    /**
     * Récupère la zone de destruction de l'arme
     * @return la zone de destruction
     */
    public List<Position> getDestroyedZone ();

    /**
     * Afflige des dégâts au personnage cible d'une valeur qui est sa puissance si ce dernier n'est pas son propriétaire
     * @param target le personnage cible
     */
    public void hit (Personage target);

    /**
     * Récupère le propriétaire de l'arme
     * @return le propriétaire de l'arme
     */
    public Personage getOwner ();

}