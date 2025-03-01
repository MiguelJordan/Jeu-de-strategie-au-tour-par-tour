package fightgame.model.strategies.fightersstrategies;

import java.util.*;
import fightgame.model.battle.*;
import gameplayers.entities.players.*;
import gameplayers.entities.weapons.*;
import gameplayers.util.geometry.*;


/**Classe abstraite héritée par nos stratégies. Contient des méthodes indiquant si des actions sont applicables */
public abstract class AbstractFighterStrategy implements FighterStrategy {


    /**
     * Indique si le joueur peut tirer. Oui s'il a au moins un fusil chargé
     */
    public boolean shootingActionIsApplicable (Personage fighter) {
        List<Rifle> rifles = fighter.getRifles();
        for (Rifle rifle : rifles) {
            if (rifle.getAmmo() > 0) {
                return true;
            }
        }
        return false;
    }

    /**
     * Indique si le joueur peut planter un explosif. Oui s'il en a au moins un
     */
    public boolean plantingActionIsApplicable (Personage fighter) {
        return fighter.getExplosives().size() > 0;
    }

    /**
     * Indique si le joueur peut se déplacer dans la direction donnée. Oui si la case de destination est libre
     */
    public boolean movingActionIsApplicable (Personage fighter, Grid battleField, int direction) {
        Position destination = new Position(fighter.getPosition());
        destination.change(direction);
        return battleField.isCellFree(destination);
    }
    

}