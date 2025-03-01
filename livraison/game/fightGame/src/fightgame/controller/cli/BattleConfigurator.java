package fightgame.controller.cli;

import java.util.*;
import fightgame.model.battle.*;
import fightgame.model.factories.*;
import fightgame.model.entities.robotfighters.*;
import fightgame.model.strategies.refereestrategies.*;
import fightgame.model.strategies.fighterspositionningstrategies.*;
import fightgame.model.strategies.fightersstrategies.*;
import fightgame.view.cli.*;
import gameplayers.entities.players.*;
import gameplayers.util.geometry.*;
import gameplayers.util.configmanagers.*;


/**Controleur permettant de configurer la battle */
public class BattleConfigurator {

    private final FighterFactory FACTORY = new FighterFactory();

    private static final String DEFAULT_CONFIG_FILE_PATH = "res/configfiles/config1.xml";
    private static final ConfigManager DEFAULT_CONFIG = new ConfigManager(DEFAULT_CONFIG_FILE_PATH);

    private BattleSetup battleSetup;
    private MenuDisplayer displayer;
    private Prompter prompter;

    private boolean battleRequested;

    public BattleConfigurator (Prompter prompter) {
        this.battleSetup = new BattleSetup();
        this.battleSetup.setInitialBattleField(new Grid(DEFAULT_CONFIG.getValue("gridheight", "grid"),DEFAULT_CONFIG.getValue("gridwidth", "grid")));
        this.displayer = new MenuDisplayer();
        this.prompter = prompter;
        this.battleRequested = false;
    }

    /**Lance la configuration */
    public void launchBattleConfiguration () {
        this.displayer.displayConfiguration(this.battleSetup);
        this.displayer.displayMainMenu();
        int choice = this.prompter.getChoice();
        switch (choice) {
            case 3:
                this.setBattle();
                break;
            case 2:
                this.addFighter();
                break;
            case 1:
                this.goToBattle();
                break;
            case 0:
                this.quitConfiguration();
                break;
        }
    }

    public void setBattle () {
        this.displayer.displayBattleSettingsMenu();
        List<Object> configChoices = this.prompter.getChoices(0);
        if (configChoices.size() >= 4) {
            this.battleSetup.setInitialBattleField(new Grid((int) configChoices.get(0), (int) configChoices.get(1)));
            switch ((int) configChoices.get(2)) {
                case 1:
                    this.battleSetup.setPositionningStrategy(new SafePositioning());
                    break;
                default:
                    this.battleSetup.setPositionningStrategy(new RandomPositioning());
            }
            switch ((int) configChoices.get(3)) {
                case 0:
                    this.battleSetup.setTurnRefereeStrategy(new RandomTurnsReferee(0));
                    break;
                default:
                    this.battleSetup.setTurnRefereeStrategy(new OrderedTurnsReferee(0));
            }
        }
        this.launchBattleConfiguration();
    }

    public void addFighter () {
        this.displayer.displayNewFighterMenu();
        List<Object> fighterConfig = this.prompter.getChoices(1);
        if (fighterConfig.size() >= 3) {
            Personage fighter;
            switch ((int) fighterConfig.get(1)) {
                case 3:
                    fighter = new RobotFighter((String) fighterConfig.get(0), new Position(0,0), new DefenderStrategy());
                    break;
                case 2:
                    fighter = new RobotFighter((String) fighterConfig.get(0), new Position(0,0), new AttackerStrategy());
                    break;
                case 1:
                    fighter = new RobotFighter((String) fighterConfig.get(0), new Position(0,0), new ChaoticStrategy());
                    break;
                default:
                    fighter = new Player((String) fighterConfig.get(0), new Position(0,0));
            }
            String fighterType = FighterFactory.TACTICIAN;
            switch ((int) fighterConfig.get(2)) {
                case 4:
                    fighterType = FighterFactory.DEMOLISHER;
                    break;
                case 3:
                    fighterType = FighterFactory.SNIPER;
                    break;
                case 2:
                    fighterType = FighterFactory.TANKER;
                    break;
                case 1:
                    fighterType = FighterFactory.DEFENDER;
                    break;
            }
            this.battleSetup.addFighter(FACTORY.createFighter(fighter, fighterType));
        }
        this.launchBattleConfiguration();
    }

    public void goToBattle () {
        this.battleRequested = true;
    }

    public void quitConfiguration () {
        this.displayer.displayQuittingMessage();
    }

    public BattleSetup getBattleSetup () {
        return this.battleSetup;
    }

    public boolean battleRequested () {
        return this.battleRequested;
    }
    

}