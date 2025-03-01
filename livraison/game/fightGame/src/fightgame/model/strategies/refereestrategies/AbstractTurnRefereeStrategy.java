package fightgame.model.strategies.refereestrategies;


/**Classe abstraite factorisant les éléments communs aux stratégies */
public abstract class AbstractTurnRefereeStrategy implements TurnRefereeStrategy {

    protected int fightersCount;

    public AbstractTurnRefereeStrategy (int fightersCount) {
        this.fightersCount = fightersCount;
    }

    @Override
    public int getFightersCount () {
        return this.fightersCount;
    }

    @Override
    public void setFightersCount (int newFightersCount) {
        this.fightersCount = newFightersCount;
    }

}