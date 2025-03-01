package fightgame.model.strategies.gridstrategies;

import fightgame.model.battle.*;


/**Stratégie de remplissage et évolution des grilles */
public interface GridStrategy {

    /**Initialise la grille selon la stratégie */
    public void initializeGrid (Grid grid);

    /**Fait évoluer la grille selon la stratégie */
    public void evolveGrid (Grid grid);

}