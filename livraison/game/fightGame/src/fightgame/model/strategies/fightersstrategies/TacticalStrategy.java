package fightgame.model.strategies.fightersstrategies;

import java.util.*;
import fightgame.model.battle.*;
import gameplayers.entities.players.*;
import gameplayers.util.geometry.*;


/**Représente les stratégies basées sur une tactique. */
public abstract class TacticalStrategy extends AbstractFighterStrategy {

    /**La stratégie qui s'applique lorsque la détermination de la tactique est impossible */
    protected static final FighterStrategy FALL_BACK_STRATEGY = new ChaoticStrategy();


    /**
     * Indique s'il y'a un ennemi dans la direction spécifiée pour le joueur donné
     */
    public boolean enemyInDirection (int direction, Personage fighter, Grid battleField) {
        if (direction == 1 || direction == 3) { 
            int dx = direction == 1 ? -1 : 1; // on change les x de 1 ou -1 pour les directions verticales
            for (int x = fighter.getPosition().getX()+dx; x >= 0 && x < battleField.getLinesCount(); x += dx) {
                if (battleField.get(x, fighter.getPosition().getY()) instanceof Player) {
                    return true;
                }
            }
        } else {
            int dy = direction == 2 ? 1 : -1; // on change les y de 1 ou -1 pour les directions horizontales
            for (int y = fighter.getPosition().getX()+dy; y >= 0 && y < battleField.getColumnsCount(); y += dy) {
                if (battleField.get(fighter.getPosition().getX(), y) instanceof Player) {
                    return true;
                }
            }
        }
        return false;
    }

    
    /**
     * Indique s'il y'a un un ennemi dans le périmètre du rayon donné. C'est le cas lorsqu'un voisin de la position dzns le périmètre est un joueur
     */
    public boolean enemyInPerimeter (int radius, Personage fighter, Grid battleField) {
        List<Position> possiblePositions = fighter.getPosition().getNeighbors(radius);
        for (Position position : possiblePositions) {
            if (battleField.get(position) instanceof Player) {
                return true;
            } 
        }
        return false;
    }

}