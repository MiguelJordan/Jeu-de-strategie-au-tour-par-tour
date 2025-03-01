package fightgame.model.entities.collectables;

import java.util.*;
import gameplayers.entities.model.*;
import gameplayers.entities.players.*;
import gameplayers.entities.weapons.*;
import gameplayers.util.geometry.*;


public class AmmoPack extends AbstractEntity implements Collectable {

    private int ammoCount;

    public AmmoPack (Position position, int ammoCount) {
        super(position);
        this.ammoCount = ammoCount;
    }

    /**
     * 
     * @param rifles liste des fusils du joueur
     * @return le fusil qui contient le moin de munition
     */
    public Rifle emptiestRifle (List<Rifle> rifles) {
        int remainingAmmoCapacity = Integer.MAX_VALUE;
        Rifle emptiest = rifles.get(0);
        for (Rifle rifle : rifles) {
            int remainingCapacity = rifle.getAmmoCapacity()-rifle.getAmmo();
            if (remainingCapacity < remainingAmmoCapacity) {
                remainingAmmoCapacity = remainingCapacity;
                emptiest = rifle;
            }
        }
        return emptiest;
    } 

    @Override
    public void getsCollected (Personage collecter) {
        List<Rifle> collecterRifles = collecter.getRifles();
        if (collecterRifles.size() > 0) {
            this.emptiestRifle(collecterRifles).load(this.ammoCount);
        }
    }

}