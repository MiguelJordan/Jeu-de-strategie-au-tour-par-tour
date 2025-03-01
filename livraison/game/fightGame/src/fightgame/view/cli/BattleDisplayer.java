package fightgame.view.cli;

import java.util.*;
import fightgame.model.battle.*;
import fightgame.model.entities.blocks.*;
import fightgame.model.entities.collectables.*;
import fightgame.model.entities.robotfighters.*;
import util.mvc.*;
import gameplayers.entities.players.*;
import gameplayers.entities.model.*;
import gameplayers.entities.weapons.*;


/**Afficheur de la bataille */
public class BattleDisplayer extends Displayer implements Listener {

    private static final int HEALTH_BAR_WIDTH = 25;
    /*🌱🪤💥💼🎛️🥋🦾🪖🔋 */
    private static final Map<String,String> REPRESENTATIONS = Map.of(
        "space", "  ", "wall", "🚫", "bomb", "💣", "mine", "🧨", "ammo", "💼", "energy", "💊", "enemy", "👹", "fighter", "🤺", "robot", "👾"
    );
    /*private static final Map<String,String> REPRESENTATIONS = Map.of(
        "space", " ", "wall", "-", "bomb", "O", "mine", "i", "ammo", "u", "energy", "+", "enemy", "E", "fighter", "F", "robot", "R"
    );*/

    private Battle battle;

    public BattleDisplayer (Battle battle) {
        this.battle = battle;
        this.battle.addListener(this);
    }

    public String getFighterHealthBar (Personage fighter) {
        double healthPercentage = (double) fighter.getHealth() / fighter.getMaxHealth();
        int filledLength = (int) (HEALTH_BAR_WIDTH * healthPercentage);
        String healthBar = "";
        for (int i = 0; i < HEALTH_BAR_WIDTH; i++) {
            if (i < filledLength) {
                healthBar += "💙";
            } else {
                healthBar += "🖤";
            }
        }
        healthBar += String.format(" %d/%d", fighter.getHealth(), fighter.getMaxHealth());
        return healthBar;
    }

    public String getRepresentation (Entity entity) {
        if (entity instanceof RobotFighter) {
            return REPRESENTATIONS.get("robot");
        }
        if (entity instanceof Block) {
            return ((Block)entity).isCrossable() ? REPRESENTATIONS.get("space") : REPRESENTATIONS.get("wall");
        }
        if (entity instanceof EnergyPellet) {
            return REPRESENTATIONS.get("energy");
        }
        if (entity instanceof AmmoPack) {
            return REPRESENTATIONS.get("ammo");
        }
        if (entity instanceof Bomb) {
            return REPRESENTATIONS.get("bomb");
        }
        if (entity instanceof Mine) {
            return REPRESENTATIONS.get("mine");
        }
        return REPRESENTATIONS.get("fighter");
    }

    public String getBattleFieldSeparator () {
        String separator = "+";
        for (int i = 1; i <= this.battle.getBattleField().getColumnsCount(); i += 1) {
            separator += "----+";
        }
        return separator;
    }


    public void displayBattleField (PlayerProxy proxy) {
        Grid battleField = this.battle.getBattleField();
        String separator = this.getBattleFieldSeparator();
        System.out.println(separator);
        for (int i = 0; i < battleField.getLinesCount(); i += 1) {
            System.out.print("|");
            for (int j = 0; j < battleField.getColumnsCount(); j += 1) {
                Entity entityThere = battleField.get(i,j);
                String representation = this.getRepresentation(entityThere);
                if (proxy != null) {
                    if (!proxy.canSee(entityThere)) {
                        representation = REPRESENTATIONS.get("space");
                    }
                    if (entityThere instanceof Player && !proxy.getPlayer().equals(((Player)entityThere))) {
                        representation = REPRESENTATIONS.get("enemy");
                    }
                }
                System.out.print(" " + representation + " |");
            }
            System.out.println("\n" + separator);
        }
    }
    public void displayBattleField () {
        this.displayBattleField(null);
    }

    public void displayFighterCommandBoard (Personage fighter) {
        if (fighter instanceof RobotFighter) {
            System.out.println("You are a robot, " + fighter.getName() + " " + this.getRepresentation(fighter));
            System.out.println("Follow your strategy : 1 ");
        } else {
            System.out.println("What's your next move, " + fighter.getName() + " ? " + this.getRepresentation(fighter));
            System.out.println("Rest       : 1");
            System.out.println("Shield up  : 2");
            System.out.println("Move       : 3 <direction>");
            System.out.println("Shoot      : 4 <direction> <gun>");
            System.out.println("Plant      : 5 <direction> <explosive> <timer_if_bomb> (timer is non-required, a default one will be used)");
            System.out.println("[Coordinate your move well, this is war ; faux pas have costs ...]");
        }
        System.out.println("\n0 -> Go to whole battle situation (only for admins), don't cheat 👀\n");
    }

    public void displayFighterWeapons (Personage fighter) {
        System.out.println("Shield " + fighter.getShield());
        System.out.println("Weapons");
        List<Rifle> rifles = fighter.getRifles();
        for (int i = 0; i < rifles.size(); i+= 1) {
            System.out.println("\t" + i + ". " + rifles.get(i));
        }
        List<Explosive> explosives = fighter.getExplosives();
        for (int i = 0; i < explosives.size(); i+= 1) {
            System.out.println("\t" + i + ". " + explosives.get(i));
        }
    }

    public void displayBattleSituation () {
        this.clear();
        System.out.println();
        System.out.println(SEPARATOR);
        System.out.println("\nBATTLE SITUATION\n");
        System.out.println(SEPARATOR);
        System.out.println();
        this.displayBattleField(null);
        System.out.println();
        System.out.println(SEPARATOR);
        System.out.println("FIGHTERS");
        System.out.println(SEPARATOR);
        this.displayFighters();
        System.out.println();
        this.displayBattleMenu();
        System.out.println();
    }

    public void displayFighters () {
        for (Personage fighter : this.battle.getFighters()) {
            this.displayFighter(fighter);
            System.out.println(SEPARATOR);
        }
    }

    public void displayFighter (Personage fighter) {
        System.out.println(fighter.getName() + " " + this.getRepresentation(fighter));
        System.out.println(this.getFighterHealthBar(fighter) + " (health)");
        System.out.println("Current position : " + fighter.getPosition());
    }

    public void displayBattleMenu () {
        System.out.println("0 -> Quit battle");
        System.out.println("1 -> Go to battle");
    }

    public void displayActiveFighterView () {
        this.clear();
        System.out.println("\nThere is some war out there ...\n");
        System.out.println(SEPARATOR + "\n");
        this.displayBattleField(new PlayerProxy(this.battle.getActiveFighter()));
        System.out.println("\n" + SEPARATOR + "\n");
        this.displayFighter(this.battle.getActiveFighter());
        System.out.println();
        this.displayFighterWeapons(this.battle.getActiveFighter());
        System.out.println("\n" + SEPARATOR + "\n");
        this.displayFighterCommandBoard(this.battle.getActiveFighter());
    }

    public void displayEndedBattleSituation () {
        this.displayBattleSituation();
        System.out.println(SEPARATOR);
        System.out.println("END OF BATTLE");
        System.out.println(SEPARATOR);
        if (this.battle.getFighters().size() > 0 ){
            System.out.println("\nWe have a winner : " + this.battle.getFighters().get(0).getName() + "\n");
        } else {
            System.out.println("\nAll losers\n");
        }
        System.out.println(SEPARATOR);
    }
    

    @Override
    public void updatedModel (Object source) {
        this.displayActiveFighterView();
    }

}