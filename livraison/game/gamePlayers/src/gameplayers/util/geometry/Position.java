package gameplayers.util.geometry;

import java.util.*;


/**
 * La classe Position représente une position. Une position a un abscisse, une ordonnée, mais aussi une limite en X et une limite en Y
 * qu'elle ne dépasse jamais
 * Une position est donc une position dans un espace donné
 * Les direction de changement et de voisin seront 1 -> north, 2 -> east, 3 -> south, 4 -> west, 5 -> north-east, 6 -> south-east, 7 -> south-west, 8 -> north-west
*/
public class Position {

    /**Les valeurs de translation */
    private static final int[][] DELTAS = {{-1,0}, {0,1}, {1,0}, {0,-1}, {-1,1}, {1,1}, {1,-1}, {-1,-1}};

    private int x;
    private int y;
    private int xLimit;
    private int yLimit;

    public Position (int x, int y, int xLimit, int yLimit) {
        this.x = x;
        this.y = y;
        this.xLimit = xLimit;
        this.yLimit = yLimit;
    }

    /**Dans ce cas les limite sont infinies */
    public Position (int x, int y) {
        this(x, y, Integer.MAX_VALUE, Integer.MAX_VALUE);
    }

    /**Copie la position */
    public Position (Position position) {
        this(position.getX(), position.getY(), position.getXLimit(), position.getYLimit());
    }

    public int getX () {
        return this.x;
    }

    public int getY () {
        return this.y;
    }

    public int getXLimit () {
        return this.xLimit;
    }

    public int getYLimit () {
        return this.yLimit;
    }


    public void setX (int newX) {
        this.x = newX;
    }
    
    public void setY (int newY) {
        this.y = newY;
    }

    public void set (Position other) {
        this.x = other.getX();
        this.y = other.getY();
        this.xLimit = other.getXLimit();
        this.yLimit = other.getYLimit();
    }

    public void translate (int deltaX, int deltaY) {
        if (this.isXValid(this.x+deltaX) && this.isYValid(this.y+deltaY)) {
            this.x += deltaX;
            this.y += deltaY;
        }
    }


    public boolean isXValid (int testedX) {
        return testedX >= 0 && testedX <= this.xLimit;
    }

    public boolean isYValid (int testedY) {
        return testedY >= 0 && testedY <= this.yLimit;
    }

    /**
     * Permet à la position de changer en se translatant dans une direction donnée
     * @param direction la direction
     */
    public void change (int direction) {
        if (1 <= direction && direction <= 8) {
            this.translate(DELTAS[direction-1][0], DELTAS[direction-1][1]);
        }
    }

    /**RFécupère le voisin de la position */
    public Position getNeighbor (int neighbor) {
        Position neighborPosition = new Position(this);
        if (1 <= neighbor && neighbor <= 8) {
            neighborPosition.change(neighbor);
        }
        return neighborPosition;
    }

    /**Récupère les voisins dans un rayon donné */
    public List<Position> getNeighbors(int radius) {
        List<Position> neighbors = new ArrayList<>();
        for (int dx = -radius; dx <= radius; dx++) {
            for (int dy = -radius; dy <= radius; dy++) {
                if (dx == 0 && dy == 0) continue;
                int newX = this.x + dx;
                int newY = this.y + dy;
                if (isXValid(newX) && isYValid(newY)) {
                    neighbors.add(new Position(newX, newY, this.xLimit, this.yLimit));
                }
            }
        }
        return neighbors;
    }

    
    public String toString () {
        return "(" + this.x + "," + this.y + ")";
    }


    @Override
    public boolean equals (Object other) {
        if (other instanceof Position) {
            Position otherAsPosition = (Position) other;
            return this.x == otherAsPosition.getX() && this.y == otherAsPosition.getY();
        }
        return false;
    }

    @Override
    public int hashCode () {
        return Objects.hash(this.x, this.y);
    }


}