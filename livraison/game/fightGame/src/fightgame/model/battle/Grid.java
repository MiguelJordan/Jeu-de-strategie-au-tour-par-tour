package fightgame.model.battle;

import gameplayers.entities.model.*;
import gameplayers.entities.players.*;
import gameplayers.util.geometry.*;
import fightgame.model.entities.blocks.*;


public class Grid {

    private Entity[][] grid;
    private int linesCount;
    private int columnsCount;

    public Grid (int linesCount, int columnsCount) {
        this.linesCount = linesCount;
        this.columnsCount = columnsCount;
        this.grid = new Entity[linesCount][columnsCount];
        this.init();
    }

    public void init () {
        for (int line = 0; line < this.linesCount; line += 1) {
            for (int column = 0; column < this.columnsCount; column += 1) {
                this.grid[line][column] = new Block(new Position(line, column, this.linesCount-1, this.columnsCount-1));
            }
        }
    }


    public int getLinesCount () {
        return this.linesCount;
    }
    public int getColumnsCount () {
        return this.columnsCount;
    }

    public Entity get (int line, int column) {
        return this.grid[line][column];
    }
    
    public Entity get (Position position) {
        return this.get(position.getX(), position.getY());
    }

    public void set (Entity entity, int line, int column) {
        this.grid[line][column] = entity;
    }

    public void set (Entity entity) {
        this.set(entity, entity.getPosition().getX(), entity.getPosition().getY());
    }


    /**
     * 
     * @param position position ou on doit remplacer ce qui s'y trouve par un block traversable  
     */
    public void clearPosition (Position position) {
        this.set(new Block(new Position(position)));
    }

    /**
     * 
     * @param position une position
     * @return False s'il y'a un block non traversable ou un joueur et True sinon 
     */
    public boolean isCellFree (Position position) {
        Entity entityThere = this.get(position);
        if (entityThere instanceof Player) {
            return false;
        }
        if (entityThere instanceof Block) {
            Block entityThereAsBlock = (Block) entityThere;
            return entityThereAsBlock.isCrossable();
        }
        return true;
    }


    @Override
    public String toString () {
        String representation = "";
        for (int line = 0; line < this.linesCount; line += 1) {
            for (int column = 0; column < this.columnsCount; column += 1) {
                representation += this.grid[line][column] + " ";
            }
            representation += "\n";
        }
        return representation.trim();
    }

}