package fightgame.view.cli;

import fightgame.model.battle.*;
import fightgame.model.entities.robotfighters.*;
import fightgame.model.strategies.refereestrategies.*;
import fightgame.model.strategies.fighterspositionningstrategies.*;
import gameplayers.entities.players.*;


/**Afficheur du menu de configuration */
public class MenuDisplayer extends Displayer {


    public void displayHeader () {
        System.out.println();
        System.out.println(SEPARATOR);
        System.out.println("BATTLE GAME");
        System.out.println(SEPARATOR);
        System.out.println();
    }

    public void displayMainMenu () {
        System.out.println("3 -> Configurate battle");
        System.out.println("2 -> Add fighter");
        System.out.println("1 -> Go to battle");
        System.out.println("0 -> Quit");
        System.out.println();
    }

    public void displayBattleSettingsMenu () {
        this.clear();
        this.displayHeader();
        System.out.println("BATTLE SETTING\n");
        System.out.println("0 -> Go back\n");
        System.out.println("Choose in order (separate with simple spaces) :\n\tBattlefield height\n\tBattlefield width");
        System.out.println("\tFighters positioning\n\t\t0 -> Random Positionning\n\t\t1 -> Safe Positionning");
        System.out.println("\tTurn Referee\n\t\t0 -> Random turns\n\t\t1 -> Ordered turns");
        System.out.println("\nFor example :\n\t25 20 1 0\nsets a battlefield of height 25, width 20 ; fighters initialy positionned safely, and playing on random turns\n");
    }

    public void displayNewFighterMenu () {
        this.clear();
        this.displayHeader();
        System.out.println("ADDING NEW FIGHTER\n");
        System.out.println("0 -> Go back\n");
        System.out.println("Choose in order (separate with simple spaces) :\n\tFighter name");
        System.out.println("\tFighter Controller\n\t\t0 -> Human\n\t\t1 -> Robot (Chaotic (random) strategy)\n\t\t2 -> Robot (Attacker strategy)\n\t\t3 -> Robot (Defender strategy)");
        System.out.println("\tFightertype\n\t\t0 -> Tactician\n\t\t1 -> Defender\n\t\t2 -> Tanker\n\t\t3 -> Sniper\n\t\t4 -> Demolisher");
        System.out.println("\nFor example :\n\tMegalo 1 4\ncreates a sniper controlled by robot, named Megalo\n");
    }

    public void displayConfiguration (BattleSetup setup) {
        this.clear();
        this.displayHeader();
        System.out.println("CURRENT CONFIGURATION");
        System.out.println(SEPARATOR);
        System.out.println("Battle field size : " + setup.getInitialBattleField().getLinesCount() + " " + setup.getInitialBattleField().getColumnsCount());
        System.out.println("Turns strategy : " + (setup.getTurnRefereeStrategy() instanceof OrderedTurnsReferee ? "ordered turns" : "random turns"));
        System.out.println("Positionning strategy : " + (setup.getPositioningStrategy() instanceof SafePositioning ? "safe positionning" : "random positionning"));
        System.out.println("Fighters");
        if (setup.getInitialFighters().size() == 0) {
            System.out.println("\tNo fighters");
        } else {
            for (Personage fighter : setup.getInitialFighters()) {
                System.out.println("\t" + fighter.getName() + (fighter instanceof RobotFighter ? " (robot)" : " (human)"));
            }
        }
        System.out.println(SEPARATOR);
        System.out.println();
    }

}