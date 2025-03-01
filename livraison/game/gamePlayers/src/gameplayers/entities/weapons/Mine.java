package gameplayers.entities.weapons;

import java.util.*;
import gameplayers.util.geometry.*;
import gameplayers.entities.players.*;
import gameplayers.config.*;


/**La classe Mine représente une mine */
public class Mine extends Explosive {

    public Mine (Personage owner, int power, boolean visibility) {
        super(owner, power, visibility);
    }

    public Mine (Personage owner) {
        this(owner, DefaultConfig.getMinePower(), DefaultConfig.getMineVisibility());
    }

    /**La zone de destruction est seulement la position de la mine */
    public void destroy () {
        this.destroyedZone.add(new Position(this.position));
        this.owner.removeWeapon(this);
    }

    @Override
    public String toString() {
        return "Mine" + super.toString();
    }
}