package fightgame.controller;


import fightgame.model.battle.*;
import fightgame.model.actions.*;
import gameplayers.entities.players.Player;
import gameplayers.entities.weapons.*;


public class BattleController {
    private Battle battle;
 

    public BattleController(Battle battle) {
        this.battle = battle; 
    }

    public void handleMove(Player player,int direction) {
       
        Action moveAction = new Moving(player, direction);
        battle.next(moveAction);

       
    }

    public void handleAction(Action action){
        battle.next(action);
    }

    public void handleShoot(Player player, int direction,Rifle rifle) {
        
        Action shootAction = new Shooting(player, rifle,direction);
        battle.next(shootAction);

    }

    public void handleShieldUp(Player player){
        Action shieldAction = new Shielding(player);
        battle.next(shieldAction);
    }

    public void handlePlant(Player player,int position,Explosive explosive) {
       
        
        Action plantAction = new Planting(player, explosive,position);
        battle.next(plantAction);   
    }


    public void handleRest(Player player){
        Action resAction = new Resting(player);
        battle.next(resAction);
    }

    public Battle getBattle() {
        return battle;
    }

  
}
