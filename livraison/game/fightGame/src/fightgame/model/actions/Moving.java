package fightgame.model.actions;

import gameplayers.entities.players.*;
import gameplayers.util.geometry.*;


public class Moving extends AbstractAction {

    private int direction;
    private Position previousPosition;

    public Moving (Personage performer, int direction) {
        super(performer);
        this.direction = direction;
        this.previousPosition = new Position(this.performer.getPosition());
    }

    public int getDirection () {
        return this.direction;
    }

    public Position getPreviousPosition () {
        return this.previousPosition;
    }

    @Override
    public void getsExecuted () {
        this.performer.move(this.direction);
    }

}