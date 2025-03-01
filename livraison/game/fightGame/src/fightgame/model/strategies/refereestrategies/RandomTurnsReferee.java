package fightgame.model.strategies.refereestrategies;

import java.util.*;


/**Stratégie des tours aléatoires. Choisit le joueur actif au hasard */
public class RandomTurnsReferee extends AbstractTurnRefereeStrategy {

    /**Le générateur de hasard */
    private Random selector;

    public RandomTurnsReferee (int fightersCount) {
        super(fightersCount);
        this.selector = new Random();
    }

    @Override
    public int getFirstActiveFighterIndex () {
        return this.selector.nextInt(this.fightersCount);
    }

    @Override
    public int getNextActiveFighterIndex (int activeFighterIndex) {
        return this.selector.nextInt(this.fightersCount);
    } 

}