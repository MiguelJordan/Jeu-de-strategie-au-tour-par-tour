package fightgame.model.strategies.refereestrategies;


/**Stratégie de décision des tours de jeu */
public interface TurnRefereeStrategy {

    /**Détermine le premier joueur à jouer */
    public int getFirstActiveFighterIndex ();
    
    /**Détermine le prochain joueur à jouer */
    public int getNextActiveFighterIndex (int currentFighterIndex);

    public int getFightersCount ();

    public void setFightersCount (int newFightersCount);

}