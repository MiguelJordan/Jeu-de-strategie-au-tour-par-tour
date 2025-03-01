package fightgame.model.strategies.fighterspositionningstrategies;

import java.util.*;
import gameplayers.util.geometry.*;

/**Stratégie de positionnement des joueurs */
public interface FightersPositioningStrategy {

    /**
     * Trouve des positions pour chaque joueurs étant donné les dimensions de la grille
     */
    public List<Position> getPositions (int fightersCount, int gridLinesCount, int gridColumnsCount);

}