package fightgame.model.battle;

import java.util.*;
import gameplayers.util.geometry.*;
import gameplayers.entities.model.*;
import gameplayers.entities.players.*;
import gameplayers.entities.weapons.*;
import fightgame.model.entities.collectables.*;
import fightgame.model.strategies.refereestrategies.*;
import fightgame.model.strategies.gridstrategies.*;
import fightgame.model.actions.*;
import util.mvc.*;


public class Battle extends AbstractListenable {

    private static final int ACTION_FATIGUE = 2;

    private Grid battleField;
    private List<Personage> fighters;
    private Set<Explosive> explosives;
    private int activeFighterIndex;
    private TurnRefereeStrategy turnReferee;
    private GridStrategy battleFieldStrategy;

    public Battle (BattleSetup setup) throws IllegalArgumentException {
        try {
            setup.finalizeSetup();
            this.battleField = setup.getInitialBattleField();
            this.fighters = setup.getInitialFighters();
            this.turnReferee = setup.getTurnRefereeStrategy();
            this.battleFieldStrategy = setup.getGridStrategy();
            this.explosives = new HashSet<>();
            this.activeFighterIndex = this.turnReferee.getFirstActiveFighterIndex();
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Invalid battle setup : " + e.getMessage());
        }
    }


    /**
     * 
     * @return tous les les joueurs active dans la bataille 
     */
    public List<Personage> getFighters () {
        return this.fighters;
    }
 /**
  * 
  * @return les explosives posés dans la grille
  */
    public Set<Explosive> getExplosives() {
        return this.explosives;
    }

    /**
     * 
     * @return le Personnage courrant (Celui qui doit jouer)
     */
    public Personage getActiveFighter () {
        return this.fighters.get(this.activeFighterIndex);
    }

    /**
     * Pour passer la main au prochain joueur
     */
    public void changeActiveFighter () {
        this.activeFighterIndex = this.turnReferee.getNextActiveFighterIndex(this.activeFighterIndex);
    }

    /**
     * 
     * @return recuperer la grille
     */
    public Grid getBattleField () {
        return this.battleField;
    }



    /**
     * 
     * @return vrai si la bataille est terminée et Faux sionn
     */
    public boolean isOver () {
        return this.fighters.size() <= 1;
    }

    /**
     * 
     * @param action l'action qui doit etre executé
     */
    public void next (Action action) {
        action.getsExecuted();
        this.handleAction(action);
        this.checkExplosives(this.getActiveFighter());
        this.checkFighters();
        this.battleFieldStrategy.evolveGrid(this.battleField);
        this.changeActiveFighter();
        this.fireChanges();    
    }

    /**
     * 
     * @param action l'action qui doit etre executé
     */
    public void handleAction (Action action) {
        if (action instanceof Moving) {
            this.handleMoving((Moving) action);
        } else if (action instanceof Shooting) {
            this.handleShooting((Shooting) action);
        } else if (action instanceof Planting) {            
            this.handlePlanting((Planting) action);
        } else if (action instanceof Shielding) {
            System.out.println("Shielding has been handled");
        } else{
            System.out.println("Resting has been handled");
        }
        if (!(action instanceof Resting)) {
            action.getPerformer().takeDamage(ACTION_FATIGUE);
        }
    }

    /**
     * 
     * @param movingAction l'action pour se deplacer
     */
    public void handleMoving (Moving movingAction) {
        Personage performer = movingAction.getPerformer();
        if (this.battleField.isCellFree(performer.getPosition())) {
            this.battleField.clearPosition(movingAction.getPreviousPosition());
            if (this.battleField.get(performer.getPosition()) instanceof Collectable) {
                Collectable collectable = (Collectable) this.battleField.get(performer.getPosition());
                collectable.getsCollected(performer);
            }
            this.battleField.set(performer);
        } else if (!performer.getPosition().equals(movingAction.getPreviousPosition())) {
            int movingDirection = movingAction.getDirection();
            int rewindedDirection = movingDirection != 2 ? (movingDirection+2) % 4 : 4; 
            performer.move(rewindedDirection);
        }
        System.out.println("Moving has been handled.");
    }

    /**
     * 
     * @param shootingAction l'action pour shooter
     */
    public void handleShooting (Shooting shootingAction) {
        Rifle usedGun = shootingAction.getUsedGun();
        List<Position> trajectory = usedGun.getDestroyedZone();
        int i = 0;
        while (i < trajectory.size() && this.battleField.isCellFree(trajectory.get(i))) {
            i += 1;
        }
        if (i < trajectory.size()) {
            Entity endPoint = this.battleField.get(trajectory.get(i));
            if (endPoint instanceof Player) {
                usedGun.hit((Personage) endPoint);
            }
        }
        System.out.println("Shooting has been handled.");
    }

    /**
     * 
     * @param plantingAction l'action pour poser un explosive
     */
    public void handlePlanting (Planting plantingAction) {
        Explosive explosive = plantingAction.getPlantedExplosive();
        if (this.battleField.isCellFree(explosive.getPosition())) {
            this.battleField.set(explosive);
            this.explosives.add(explosive);
        }
        System.out.println("Planting has been handled.");
    }


    /**
     * Permet de retirer tous les joueur qui sont deja mort
     */
    public void checkFighters () {
        Set<Personage> defeated = new HashSet<>();
        for (Personage fighter : this.fighters) {
            if (!fighter.isAlive()) {
                defeated.add(fighter);
                this.battleField.clearPosition(fighter.getPosition());
                System.out.println("That nigga dead -> " + fighter.getName());
            }
        }
        this.fighters.removeAll(defeated);
        this.turnReferee.setFightersCount(this.fighters.size());
    }

    /**
     * 
     * @param fighter le Personnage (Humain,Robot)
     * Permet d'enlever les explosives qui ont deja explosé dans la grille 
     */
    public void checkExplosives (Personage fighter) {
        Set<Explosive> exploded = new HashSet<>();
        for (Explosive explosive : this.explosives) {
            if (explosive.appetizeForDestruction(fighter)) {
                this.handleExplosion(explosive);
                if (this.battleField.get(explosive.getPosition()) instanceof Explosive) {
                    this.battleField.clearPosition(explosive.getPosition());
                }
                exploded.add(explosive);
            }
        }
        this.explosives.removeAll(exploded);
    }

    /**
     * 
     * @param exploded l'explosive
     */
    public void handleExplosion (Explosive exploded) {
        for (Position hitPosition : exploded.getDestroyedZone()) {
            Entity entityThere = this.battleField.get(hitPosition);
            if (entityThere instanceof Player) {
                exploded.hit((Player) entityThere);
            }
        }
    }


    /**
     * Afficher l'état courrant de la bataille
     */
    public void showBattleField () {
        System.out.println();
        System.out.println(this.battleField);
        System.out.println();
    }

}