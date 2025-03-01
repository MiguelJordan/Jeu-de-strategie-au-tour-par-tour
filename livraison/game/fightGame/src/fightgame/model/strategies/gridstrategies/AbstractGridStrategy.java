package fightgame.model.strategies.gridstrategies;

import java.util.*;
import fightgame.model.battle.*;
import fightgame.model.entities.blocks.*;
import fightgame.model.entities.collectables.*;
import gameplayers.util.geometry.*;


/**Classe abstraite héritée par les stratégies de grille. Contient les méthodes utilisables par toute stratégie */
public abstract class AbstractGridStrategy implements GridStrategy {

    /**Le générateur aléatoire utilisé par les stratégies */
    protected static final Random GENERATOR = new Random();

    /**
     * Donne une position de case vide au hasard dans la grille
     */
    public Position getFreeCell (Grid grid) {
        int x, y;
        do {
            x = GENERATOR.nextInt(grid.getLinesCount());
            y = GENERATOR.nextInt(grid.getColumnsCount());
        } while (!grid.isCellFree(new Position(x,y)));
        return new Position(x,y,grid.getLinesCount()-1,grid.getColumnsCount()-1);
    }

    /**
     * Donne le nombre de collectables actuellement sur la grille
     */
    public int getCollectablesOnGridCount (Grid grid) {
        int collectablesOnGridCount = 0;
        for (int i = 0; i < grid.getLinesCount(); i += 1) {
            for (int j = 0; j < grid.getColumnsCount(); j += 1) {
                if (grid.get(i,j) instanceof Collectable) {
                    collectablesOnGridCount += 1;
                }
            }
        }
        return collectablesOnGridCount;
    }

}