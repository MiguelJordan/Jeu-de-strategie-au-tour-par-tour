package fightgame.model.strategies.fighterspositionningstrategies;

import java.util.*;
import gameplayers.util.geometry.*;


/**Stratégie de positionnement qui place les joueurs le plus espacement possible, et pas sur les mêmes lignes et colonnes. Ainsi ils ne sont pas vulnérables dès le début. */
public class SafePositioning implements FightersPositioningStrategy {

    /**Positionnement aléatoire quant le safepositionning est impossible */
    private static final RandomPositioning fallbackStrategy = new RandomPositioning();


    @Override
    public List<Position> getPositions(int fightersCount, int gridLinesCount, int gridColumnsCount) {
        
        // Vérifier si les contraintes de ligne/colonne uniques sont réalisables
        if (fightersCount > Math.min(gridLinesCount, gridColumnsCount)) {
            return fallbackStrategy.getPositions(fightersCount, gridLinesCount, gridColumnsCount);
        }

        int xLimit = gridLinesCount-1;
        int yLimit = gridColumnsCount-1;

        // Liste pour stocker les positions
        List<Position> positions = new ArrayList<>();
        Set<Integer> usedLines = new HashSet<>();
        Set<Integer> usedColumns = new HashSet<>();

        // Calcul des intervalles pour maximiser les distances
        int rowStep = gridLinesCount / fightersCount;
        int columnStep = gridColumnsCount / fightersCount;

        for (int i = 0; i < fightersCount; i++) {
            int safeX = i * rowStep;
            int safeY = i * columnStep;

            // Ajuster les positions pour éviter les répétitions
            while (usedLines.contains(safeX)) {
                safeX = (safeX + 1) % gridLinesCount;
            }
            while (usedColumns.contains(safeY)) {
                safeY = (safeY + 1) % gridColumnsCount;
            }

            positions.add(new Position(safeX, safeY, xLimit, yLimit));
            usedLines.add(safeX);
            usedColumns.add(safeY);
        }

        return positions;
    }
}