package fightgame.model.strategies.fightersstrategies;

import java.util.*;
import fightgame.model.battle.*;
import fightgame.model.actions.*;
import gameplayers.entities.players.*;
import gameplayers.entities.weapons.*;


/**Stratégie qui choisit l'action a exécuter au hasard */
public class ChaoticStrategy extends AbstractFighterStrategy {

    protected static final int RESTING = 0;
    protected static final int SHIELDING = 1;
    protected static final int MOVING = 2;
    protected static final int SHOOTING = 3;
    protected static final int PLANTING = 4;
    protected static final int ACTIONS_COUNT = 5;

    private Random chaoticGuid;

    public ChaoticStrategy () {
        this.chaoticGuid = new Random();
    }

    /**
     * Choisit une action au hasard, si elle est applicable, elle la renvoie sinon elle renvoie une action de repos
     */
    @Override
    public Action computeBestMove (Personage fighter, Grid battleField) {
        int moveChoice = chaoticGuid.nextInt(ACTIONS_COUNT); 
        switch (moveChoice) {
            case PLANTING:
                if (this.plantingActionIsApplicable(fighter)) {
                    List<Explosive> explosives = fighter.getExplosives();
                    Explosive explosive = explosives.get(chaoticGuid.nextInt(explosives.size()));
                    int direction = 1 + chaoticGuid.nextInt(8);
                    return new Planting(fighter, explosive, direction);
                }
            case SHOOTING:
                if (this.shootingActionIsApplicable(fighter)) {
                    List<Rifle> rifles = fighter.getRifles();
                    Rifle rifle = rifles.get(chaoticGuid.nextInt(rifles.size()));
                    int direction = 1 + chaoticGuid.nextInt(4);
                    return new Shooting(fighter, rifle, direction);
                }
            case MOVING:
                int direction = 1 + chaoticGuid.nextInt(4);
                if (this.movingActionIsApplicable(fighter, battleField, direction)) {
                    return new Moving(fighter, direction);
                }
            case SHIELDING:
                return new Shielding(fighter);
        }
        return new Resting(fighter);
    }

}