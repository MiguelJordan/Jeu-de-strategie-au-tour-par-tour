package gameplayers.entities.model;

import gameplayers.util.geometry.*;

/**
 * L'interface Entity représente une entité, dans un jeu donné. Une entité devra avoir une position. 
 */
public interface Entity {

    /**
     * Récupère la position de l'entité
     * @return la position de l'entité
     */
    public Position getPosition ();

}