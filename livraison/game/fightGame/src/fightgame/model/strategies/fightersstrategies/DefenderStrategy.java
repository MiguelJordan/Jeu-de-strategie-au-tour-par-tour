package fightgame.model.strategies.fightersstrategies;

import fightgame.model.battle.*;
import fightgame.model.actions.*;
import gameplayers.entities.players.*;


public class DefenderStrategy extends TacticalStrategy {

    /**
     * Se déplace pour éviter les tirs s'il y a un ennemi dans une des quatre direction.
     * Active le bouclier s'il y'a un ennemi dans les parages
     * Se repose sur la stratégie chaotique quand il est difficile de déterminer la meilleure action
     */
    @Override
    public Action computeBestMove (Personage fighter, Grid battleField) {
        for (int direction = 1; direction <= 4; direction += 1) {
            if (this.enemyInDirection(direction, fighter, battleField)) {
                int firstSafeDirection;
                int secondSafeDirection;
                if (direction % 2 == 0) {
                    firstSafeDirection = 1;
                    secondSafeDirection = 3;
                } else {
                    firstSafeDirection = 2;
                    secondSafeDirection = 4;
                }
                if (this.movingActionIsApplicable(fighter, battleField, firstSafeDirection)) {
                    return new Moving(fighter, firstSafeDirection);
                }
                if (this.movingActionIsApplicable(fighter, battleField, secondSafeDirection)) {
                    return new Moving(fighter, secondSafeDirection);
                }
            }
        }
        if (this.enemyInPerimeter(2, fighter, battleField)) {
            return new Shielding(fighter);
        }
        return FALL_BACK_STRATEGY.computeBestMove(fighter, battleField);
    }

}