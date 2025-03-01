package fightgame.model.strategies.gridstrategies;

import java.util.*;
import fightgame.model.battle.*;
import fightgame.model.entities.blocks.*;
import fightgame.model.entities.collectables.*;
import gameplayers.util.geometry.*;


/**Stratégie de grille qui se base base sur le hasard pour gérer la grille. Mets des murs et des collectables en quantités déterminées sur la grile. */
public class RandomGridStrategy extends AbstractGridStrategy {

    private static final int WALLS_PROPORTION = 10; // donc 10%
    private static final int COLLECTABLESS_PROPORTION = 20; // donc 5%
    private static final int ENERGY_PELLET_MAX_VALUE = 10;
    private static final int AMMO_PACK_MAX_COUNT = 5;


    public void initializeGrid (Grid grid) {
        this.addWalls(grid);
        this.addCollectables(grid);
    }

    /**
     * La grille évolue lorsqu'un collectable a été collecté ; il y'a alors une chance sur 2 qu'un autre soit créé
     */
    public void evolveGrid (Grid grid) {
        int collectablesCount = (grid.getLinesCount() * grid.getColumnsCount()) / COLLECTABLESS_PROPORTION;
        int collectablesOnGridCount = this.getCollectablesOnGridCount(grid);
        if (collectablesOnGridCount == 0) {
            this.addCollectables(grid);
        } else if (collectablesOnGridCount < collectablesCount) {
            if (this.GENERATOR.nextBoolean()) {
                this.addCollectable(grid);
            }
        }
    }

    public void addWalls (Grid grid) {
        int obstacleCount = (grid.getLinesCount() * grid.getColumnsCount()) / WALLS_PROPORTION;
        for (int i = 0; i < obstacleCount; i++) {
            grid.set(new Block(this.getFreeCell(grid), false));
        }
    }

    public void addCollectables (Grid grid) {
        int collectablesCount = (grid.getLinesCount() * grid.getColumnsCount()) / COLLECTABLESS_PROPORTION;
        for (int i = 0; i < collectablesCount; i++) {
            this.addCollectable(grid);
        }
    }

    public void addCollectable (Grid grid) {
        if (this.GENERATOR.nextBoolean()) {
            grid.set(new EnergyPellet(this.getFreeCell(grid), 1+this.GENERATOR.nextInt(ENERGY_PELLET_MAX_VALUE)));
        } else {
            grid.set(new AmmoPack(this.getFreeCell(grid), 1+this.GENERATOR.nextInt(AMMO_PACK_MAX_COUNT)));
        }
    }

}