package fightgame.model.entities.robotfighters;

import gameplayers.entities.players.*;
import gameplayers.util.geometry.*;
import fightgame.model.strategies.fightersstrategies.*;
import fightgame.model.actions.*;
import fightgame.model.battle.*;


public class RobotFighter extends Player {

    private FighterStrategy strategy;

    public RobotFighter (String name, Position position, PlayerConfiguration intitialConfiguration, FighterStrategy strategy) {
        super(name, position, intitialConfiguration);
        this.strategy = strategy;
    }

    public RobotFighter (String name, Position position, FighterStrategy strategy) {
        super(name, position);
        this.strategy = strategy;
    }

    /**
     * 
     * @param battleField la grille
     * @return la prochaine action du robot en fonction de sa strategie
     */
    public Action getNextAction (Grid battleField) {
        return this.strategy.computeBestMove(this, battleField);
    }

}