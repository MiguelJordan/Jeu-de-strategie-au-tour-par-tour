package fightgame.view.gui;

import java.awt.BorderLayout;
import java.awt.Color;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import fightgame.controller.BattleController;


//Pour la vue global
public class BattleView extends JFrame{
    private GamePanel board;
    private LogsPanel logsPanel;
    
    public BattleView(BattleController controller){
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(800, 600);
        this.setLayout(new BorderLayout());
        JLabel statusLabel = new JLabel("Vue global du jeu");
        this.add(statusLabel, BorderLayout.NORTH);


        board = new GamePanel(controller.getBattle());

        this.add(board,BorderLayout.CENTER);

        JPanel info = new JPanel();
        info.add(new JLabel("Mur "));
        info.add(new SquarePanel(Color.DARK_GRAY, "", 24));
        info.add(new JLabel(", Joueur Active "));
        info.add(new SquarePanel(Color.GREEN, "", 24));
        info.add(new JLabel(", Joueur En attente"));
        info.add(new SquarePanel(Color.GRAY, "", 24));
        info.add(new JLabel(", Pastille d'Energie"));
        info.add(new SquarePanel(Color.WHITE, "⚡💊", 24));
        info.add(new JLabel(", Bombe ou Mine"));
        info.add(new SquarePanel(Color.RED, "💣", 24));
        info.add(new JLabel(", Balles"));
        info.add(new SquarePanel(Color.WHITE, "🔫🪙", 24));



        this.logsPanel = new LogsPanel();

        this.add(logsPanel,BorderLayout.EAST);
        this.add(info, BorderLayout.SOUTH);

      setFocusable(true);
        this.setVisible(true);
    }

    public LogsPanel getLogsPanel() {
        return logsPanel;
    }


}
