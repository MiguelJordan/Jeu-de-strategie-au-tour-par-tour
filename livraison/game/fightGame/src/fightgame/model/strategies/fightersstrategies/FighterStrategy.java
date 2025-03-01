package fightgame.model.strategies.fightersstrategies;

import gameplayers.entities.players.*;
import fightgame.model.battle.*;
import fightgame.model.actions.*;


/**Stratégie d'un joueur (utilisée par les robots) */
public interface FighterStrategy {

    /**
     * Détermine la meilleure action à exécuter pour le joueur, considérant la grille (le champ de de bataille)
     * @param fighter le joueur
     * @param battleField le champ de bataille
     */
    public Action computeBestMove (Personage fighter, Grid battleField);

}