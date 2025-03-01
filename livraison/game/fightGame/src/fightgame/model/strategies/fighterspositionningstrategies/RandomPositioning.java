package fightgame.model.strategies.fighterspositionningstrategies;

import java.util.*;
import gameplayers.util.geometry.*;


/**Stratégie de positionnement qui positionne les joueurs de manière aléatoire */
public class RandomPositioning implements FightersPositioningStrategy {

    /**Le générateur de hasard */
    private static final Random generator = new Random();


    @Override
    public List<Position> getPositions (int fightersCount, int gridLinesCount, int gridColumnsCount) {
        List<Position> positions = new ArrayList<>();
        if (fightersCount <= gridLinesCount*gridColumnsCount) {
            int xLimit = gridLinesCount-1;
            int yLimit = gridColumnsCount-1;
            for (int fighter = 1; fighter <= fightersCount; fighter += 1) {
                Position position;
                do {
                    int randomX = generator.nextInt(gridLinesCount);
                    int randomY = generator.nextInt(gridColumnsCount);
                    position = new Position(randomX, randomY, xLimit, yLimit);
                } while (positions.contains(position));
                positions.add(position);
            }
        }
        return positions;
    }

}