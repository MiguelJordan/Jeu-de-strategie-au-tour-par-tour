package fightgame.view.cli;


/**Afficheur ligne de commande */
public class Displayer {

    protected static final String SEPARATOR = "------------------------------------------------------------------------------------------------------------------------";

    public void clear () {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

    public void displayQuittingMessage () {
        this.clear();
        System.out.println("\nFight you soon !\n");
    }

}