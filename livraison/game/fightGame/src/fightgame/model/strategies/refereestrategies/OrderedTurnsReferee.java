package fightgame.model.strategies.refereestrategies;


/**Stratégie des tours ordonnés. Choisit le joueur actic en suivant l'ordre des passages. */
public class OrderedTurnsReferee extends AbstractTurnRefereeStrategy {

    public OrderedTurnsReferee (int fightersCount) {
        super(fightersCount);
    }
    
    @Override
    public int getFirstActiveFighterIndex () {
        return 0;
    }
    
    @Override
    public int getNextActiveFighterIndex (int activeFighterIndex) {
        return (activeFighterIndex+1) % this.fightersCount;
    } 

}