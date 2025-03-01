package fightgame.controller.cli;

import fightgame.model.battle.*;


/**Lance le jeu. D'abord la BattleConfigurator et quand la configuration est terminée lance la bataille (BattleSimulator) */
public class App {

    public static void main (String[] args) {

        Prompter prompt = new Prompter();

        BattleConfigurator configurator = new BattleConfigurator(prompt);
        configurator.launchBattleConfiguration();

        if (configurator.battleRequested()) {
            try {
                BattleSimulator simulator = new BattleSimulator(new Battle(configurator.getBattleSetup()), prompt);
                simulator.launchBattle();
            } catch (Exception e) {
                System.out.println("\nThe battle couldn't be launched -> " + e.getMessage() + "\n");
                //e.printStackTrace();
                System.exit(1);
            }
        } 

       prompt.close();

    }

}