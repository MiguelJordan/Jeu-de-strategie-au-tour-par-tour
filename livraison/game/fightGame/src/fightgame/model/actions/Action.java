package fightgame.model.actions;

import gameplayers.entities.players.*;


public interface Action {

    public Personage getPerformer ();

    /**
     * Permet d'executer une action
     */
    public void getsExecuted();

}