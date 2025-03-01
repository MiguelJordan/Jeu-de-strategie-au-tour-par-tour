package fightgame.view.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import gameplayers.entities.players.Player;
import gameplayers.entities.weapons.Bomb;
import gameplayers.entities.weapons.Mine;
import gameplayers.entities.weapons.Rifle;
import gameplayers.entities.weapons.Weapon;

public class PlayerInfoPanel extends JPanel{

    private JPanel messagesPanel;
    private JScrollPane scrollPanel;
    

    public PlayerInfoPanel(){
        this.setLayout(new BorderLayout());

        
        messagesPanel = new JPanel();
        messagesPanel.setLayout(new BoxLayout(messagesPanel, BoxLayout.Y_AXIS)); 

        scrollPanel = new JScrollPane(messagesPanel);
        scrollPanel.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS); 
        scrollPanel.setPreferredSize(new Dimension(200, 200));
      

        this.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
      
        this.add(new JLabel("Player's Weapons"),BorderLayout.NORTH);
        this.add(scrollPanel,BorderLayout.CENTER);
    }

     public void addStats(String stat){ 
        JLabel message = new JLabel(stat);
        messagesPanel.add(message);
    }

    public void update(Player player){
        messagesPanel.removeAll();
        int bombs =0;
        int mines = 0;
        int rifles = 0;

        for (Weapon weapon : player.getWeapons()) {
            if(weapon instanceof Bomb){
                bombs += 1;
            }else if(weapon instanceof Mine){
                mines += 1;
            }else if(weapon instanceof Rifle){
                rifles += 1;
            }
        }

        addStats("Bombs : " + bombs);
        addStats("Mines : " + mines);
        addStats("Rifles : " + rifles);

        messagesPanel.revalidate();
        messagesPanel.repaint();
        scrollPanel.revalidate();
        scrollPanel.repaint();
    }
}
