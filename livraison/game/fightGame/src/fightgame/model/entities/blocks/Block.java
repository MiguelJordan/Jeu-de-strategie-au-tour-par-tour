package fightgame.model.entities.blocks;

import gameplayers.util.geometry.*;
import gameplayers.entities.model.*;


public class Block extends AbstractEntity {

    private boolean crossable;

    public Block (Position position, boolean crossable) {
        super(position);
        this.crossable = crossable;
    }

    public Block (Position position) {
        this(position, true);
    }


    public boolean isCrossable () {
        return this.crossable;
    }


    @Override
    public String toString () {
        return "Block " + (this.crossable ? "crossable" : "uncrossable");
    }

}