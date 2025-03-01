package fightgame.model.strategies.fightersstrategies;

import java.util.*;
import fightgame.model.battle.*;
import fightgame.model.actions.*;
import gameplayers.entities.players.*;
import gameplayers.entities.weapons.*;


/**La stratégie tactique d'un attaquant */
public class AttackerStrategy extends TacticalStrategy {

    /**Choisit un fusil chargé du joueur s'il en a */
    public Rifle chooseLoadedGun (Personage fighter) {
        List<Rifle> rifles = fighter.getRifles();
        for (Rifle rifle : rifles) {
            if (rifle.getAmmo() > 0) {
                return rifle;
            }
        }
        return rifles.get(0);
    }

    /**
     * Tire si le joueur a un ennemi dans une des quatre direction.
     * Plante un explosif avec un timer de 2 pour les bombes s'il y'a un ennemi dans les parages
     * Se repose sur la stratégie chaotique quand il est difficile de déterminer la meilleure action
     */
    @Override
    public Action computeBestMove (Personage fighter, Grid battleField) {
        for (int direction = 1; direction <= 4; direction += 1) {
            if (this.enemyInDirection(direction, fighter, battleField) && this.shootingActionIsApplicable(fighter)) {
                return new Shooting(fighter, this.chooseLoadedGun(fighter), direction);
            }
        }
        if (this.enemyInPerimeter(3, fighter, battleField) && this.plantingActionIsApplicable(fighter)) {
            Explosive explosive = fighter.getExplosives().get(0);
            if (explosive instanceof Bomb) {
                Bomb bomb = (Bomb) explosive;
                bomb.setTimer(2);
                return new Planting(fighter, bomb, 1+(new Random().nextInt(8)));
            }
            return new Planting(fighter, explosive, 1+(new Random().nextInt(8)));
        }
        return FALL_BACK_STRATEGY.computeBestMove(fighter, battleField);
    }

}