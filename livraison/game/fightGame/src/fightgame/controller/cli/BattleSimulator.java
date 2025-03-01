package fightgame.controller.cli;

import java.util.*;
import fightgame.model.battle.*;
import fightgame.model.actions.*;
import fightgame.model.entities.robotfighters.*;
import fightgame.view.cli.*;
import gameplayers.entities.players.*;
import gameplayers.entities.weapons.*;


/**Contrôleur permettant de lancer la simulation de battle */
public class BattleSimulator {

    private Battle battle;
    private BattleDisplayer displayer;
    private Prompter prompter;

    public BattleSimulator (Battle battle, Prompter prompter) {
        this.battle = battle;
        this.displayer = new BattleDisplayer(battle);
        this.prompter = prompter;
    }

    
    public Action getActiveFighterAction () {
        Personage activeFighter = this.battle.getActiveFighter();
        if (activeFighter instanceof RobotFighter) {
            return this.getRobotAction((RobotFighter) activeFighter);
        }
        return this.getHumanAction(activeFighter);
    }

    public Action getRobotAction (RobotFighter robot) {
        int choice = this.prompter.getChoice();
        if (choice == 0) {
            this.launchBattle();
            return null;
        }
        return robot.getNextAction(this.battle.getBattleField());
    }

    public Action getHumanAction (Personage human) {
        List<Object> action = this.prompter.getChoices(0);
        if ((int)action.get(0) == 0) {
            this.launchBattle();
            return null;
        }
        if (action.size() > 0) {
            switch ((int) action.get(0)) {
                case 2:
                    return new Shielding(human);
                case 3:
                    return this.getHumanMovingAction(human, action);
                case 4:
                    return this.getHumanShootingAction(human, action);
                case 5:
                    return this.getHumanPlantingAction(human, action);
            }
            return new Resting(human);
        }
        return null;
    }

    public Action getHumanMovingAction (Personage human, List<Object> action) {
        if (action.size() >= 2) {
            int direction = (int) action.get(1);
            if (1<=direction && direction<=4) {
                return new Moving(human, direction);
            }
            return new Resting(human);
        }
        return null;
    }

    public Action getHumanShootingAction (Personage human, List<Object> action) {
        if (action.size() >= 3) {
            int direction = (int) action.get(1);
            int weapon = (int) action.get(2);
            List<Rifle> rifles = human.getRifles();
            if (1<=direction && direction<=4 && 0<=weapon && weapon<rifles.size()) {
                return new Shooting(human, rifles.get(weapon), direction);
            }
            return new Resting(human);
        }
        return null;
    }

    public Action getHumanPlantingAction (Personage human, List<Object> action) {
        if (action.size() >= 3) {
            int direction = (int) action.get(1);
            int weapon = (int) action.get(2);
            List<Explosive> explosives = human.getExplosives();
            Explosive explosive = explosives.get(weapon);
            if (1<=direction && direction<=8 && 0<=weapon && weapon<explosives.size()) {
                if (action.size() >= 4 && explosive instanceof Bomb) {
                    ((Bomb) explosive).setTimer((int) action.get(3));
                }
                return new Planting(human, explosive, direction);
            }
            return new Resting(human);
        }
        return null;
    }


    /**Lance la bataille */
    public void launchBattle () {
        this.displayer.displayBattleSituation();
        int choice = this.prompter.getChoice();
        if (choice == 0) {
            this.displayer.displayQuittingMessage();
            System.exit(0);
        } else {
            this.simulateBattle();
        }
    }

    /**Simule la bataille */
    public void simulateBattle () {
        this.displayer.displayActiveFighterView();
        while (!this.battle.isOver()) {
            Action action = this.getActiveFighterAction();
            if (action != null) {
                this.battle.next(action);
            }
        }
        this.displayer.displayEndedBattleSituation();
        
    }

}