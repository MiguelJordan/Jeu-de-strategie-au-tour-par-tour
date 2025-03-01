package fightgame.model.entities.collectables;

import gameplayers.entities.players.*;


public interface Collectable {

    /**
     * 
     * @param collecter le personnage qui doit recuperer les objets des classes qui implemente cette interface
     */
    public void getsCollected (Personage collecter);

}