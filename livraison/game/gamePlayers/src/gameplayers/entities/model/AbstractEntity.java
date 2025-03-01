package gameplayers.entities.model;

import gameplayers.util.geometry.*;

/**
 * Classe abstraite immplémentant Entity
 */
public abstract class AbstractEntity implements Entity {

    /**Position de l'entité */
    protected Position position;

    /**
     * Construit une entité à la position donnée
     * @param position la position de l'entité
     */
    public AbstractEntity (Position position) {
        this.position = position;
    }

    /**
     * Récupère la position de l'entité
     * @return la position de l'entité
     */
    @Override
    public Position getPosition () {
        return this.position;
    }

}