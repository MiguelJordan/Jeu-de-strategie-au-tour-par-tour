package gameplayers.entities.weapons;

import gameplayers.entities.model.*;
import gameplayers.entities.players.*;
import gameplayers.config.*;


public class Shield extends AbstractEntity {

    private Personage owner;
    private int power;
    private boolean isActivated;

    public Shield (Personage owner, int power) {
        super(owner.getPosition());
        this.owner = owner;
        this.power = power;
        this.isActivated = false;
    }

    public Shield (Personage owner) {
        this(owner, DefaultConfig.getShieldPower());
    }


    public void activate() {
        if(this.power > 0){
            this.isActivated = true;
        }
    }

    public void desactivate () {
        this.isActivated = false;
    }

    public int absorbDamage(int weaponDamage){

        if(this.isActivated){
            int absorbedMinDamage = Math.min(power, weaponDamage);
            int remainingDamage = weaponDamage - absorbedMinDamage;
            power -= absorbedMinDamage;

            if (power <= 0){
                this.isActivated = false;
            }
            return remainingDamage;
        }    
        return weaponDamage;
        

    }
    
    public boolean isActivated(){
        return this.isActivated;
    }

    public int getPower() {
        return this.power;
    }

    
    @Override
    public String toString () {
        return "Shield of " + this.owner.getName() + ", power is " + this.power + ", " + (this.isActivated ? "isActivated" : "unactivated");
    }

}