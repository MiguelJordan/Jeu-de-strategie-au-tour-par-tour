package fightgame.model.battle;

import java.util.*;
import gameplayers.entities.players.*;
import gameplayers.util.geometry.*;
import fightgame.model.strategies.refereestrategies.*;
import fightgame.model.strategies.gridstrategies.*;
import fightgame.model.strategies.fighterspositionningstrategies.*;


/**
 * Cette classe permet la mise en place d'une battle
 */
public class BattleSetup {

    private List<Personage> initialFighters;
    private Grid initialBattleField;
    private TurnRefereeStrategy turnReferee;
    private GridStrategy gridStrategy;
    private FightersPositioningStrategy positionningStrategy;

    public BattleSetup () {
        this.initialFighters = new ArrayList<>();
        this.turnReferee = new OrderedTurnsReferee(0);
        this.gridStrategy = new RandomGridStrategy();
        this.positionningStrategy = new RandomPositioning();
    }

    
    public void setInitialFighters (List<Personage> fighters) {
        this.initialFighters = new ArrayList<>(fighters);
    }
    public void addFighter (Personage fighter) {
        this.initialFighters.add(fighter);
    }

    public void setInitialBattleField (Grid field) {
        this.initialBattleField = field;
    }

    public void setTurnRefereeStrategy (TurnRefereeStrategy referee) {
        this.turnReferee = referee;
    }

    public void setGridStrategy (GridStrategy strategy) {
        this.gridStrategy = strategy;
    }

    public void setPositionningStrategy (FightersPositioningStrategy strategy) {
        this.positionningStrategy = strategy;
    }

    
    /**
     * Toutes les positions sont valides lorsqu'elles respectent les dimensions de la grille
     */
    public boolean allPositionsValid () {
        for (Personage fighter : this.initialFighters) {
            Position fighterPosition = fighter.getPosition();
            if (
                fighterPosition.getX()<0 || fighterPosition.getX()>=this.initialBattleField.getLinesCount() ||
                fighterPosition.getY()<0 || fighterPosition.getY()>=this.initialBattleField.getColumnsCount() ||
                fighterPosition.getXLimit() >= this.initialBattleField.getLinesCount() || 
                fighterPosition.getYLimit() >= this.initialBattleField.getColumnsCount()
            ) {
                return false;
            }
        }
        return true;
    }

    /**
     * Il y'a position de conflit quant deux joueurs ont la même position
     */
    public boolean noPositionConflict () {
        Set<Position> seenPositons = new HashSet<>();
        for (Personage fighter : this.initialFighters) {
            if (seenPositons.contains(fighter.getPosition())) {
                return false;
            }
            seenPositons.add(fighter.getPosition());
        } 
        return true;
    }

    /**
     * Positionne les joueurs sur la grille selon la stratégie de positionnement
     */
    private void setFighters () {
        List<Position> positions = this.positionningStrategy.getPositions(this.initialFighters.size(), this.initialBattleField.getLinesCount(), this.initialBattleField.getColumnsCount());
        for (int i = 0; i < positions.size(); i += 1) {
            Personage fighter = this.initialFighters.get(i);
            fighter.getPosition().set(positions.get(i));
            this.initialBattleField.set(fighter);
        }
    }

    /**
     * Finalise la setup. Vérifie d'abors qu'elle est valide et initialise la grille
     */
    public void finalizeSetup () throws IllegalArgumentException {
        if (this.initialFighters.size() < 2) {
            throw new IllegalArgumentException("A battle must oppose at least 2 fighters");
        }
        if (this.initialBattleField == null) {
            throw new IllegalArgumentException("A battle nust have a battle field");
        }
        if (this.initialFighters.size() > this.initialBattleField.getLinesCount()*this.initialBattleField.getColumnsCount()) {
            throw new IllegalArgumentException("More fighters than the battle field can contains");
        }
        this.setFighters();
        this.gridStrategy.initializeGrid(this.initialBattleField);
        this.turnReferee.setFightersCount(this.initialFighters.size());
        if (!this.allPositionsValid()) {
            throw new IllegalArgumentException("All fighters must have a valid position considering the battlefield");
        }
        if (!this.noPositionConflict()) {
            throw new IllegalArgumentException("All fighters must be at a different position");
        }
    }


    public List<Personage> getInitialFighters () {
        return this.initialFighters;
    }

    public Grid getInitialBattleField () {
        return this.initialBattleField;
    }

    public TurnRefereeStrategy getTurnRefereeStrategy () {
        return this.turnReferee;
    }

    public GridStrategy getGridStrategy () {
        return this.gridStrategy;
    }

    public FightersPositioningStrategy getPositioningStrategy () {
        return this.positionningStrategy;
    }

}