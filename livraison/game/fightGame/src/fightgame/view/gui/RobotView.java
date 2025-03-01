package fightgame.view.gui;


import fightgame.controller.BattleController;
import fightgame.model.actions.*;
import fightgame.model.entities.robotfighters.RobotFighter;



import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;


//Pour la vue du joueur Robot
public class RobotView extends JFrame implements ActionListener{

    private JLabel statusLabel;

    private JProgressBar playerEnergyBar;
    private int maxEnergy,currentEnergy;
    private GamePanel board;
    private BattleController controller;
    private RobotFighter player;
    private LogsPanel logsPanel;
    private JButton nexButton;
    private PlayerInfoPanel infoPanel;

  
    public RobotView(RobotFighter player,BattleController controller,LogsPanel logsPanel){
        this.controller = controller;
        this.player = player;
        this.logsPanel = logsPanel;
         maxEnergy = player.getMaxHealth(); 
         currentEnergy = maxEnergy;
         this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
         this.setSize(800, 600);
         this.setLayout(new BorderLayout());
         statusLabel = new JLabel("C'est votre tour Joueur : "+player.getName());
         this.add(statusLabel, BorderLayout.NORTH);
 
         playerEnergyBar = new JProgressBar(0,maxEnergy);
         playerEnergyBar.setValue(currentEnergy);
         playerEnergyBar.setStringPainted(true); 
         playerEnergyBar.setForeground(Color.GREEN);
         playerEnergyBar.setBackground(Color.LIGHT_GRAY);
        
         JLabel energyLabel = new JLabel("Energy");
         nexButton = new JButton("Next");
         nexButton.addActionListener(this);

         JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));

         buttonPanel.add(energyLabel);
         buttonPanel.add(playerEnergyBar);
         buttonPanel.add(nexButton);
       

         this.add(buttonPanel, BorderLayout.SOUTH);

         
         board = new GamePanel(true,controller.getBattle(),player,this); 
 
         this.add(board,BorderLayout.CENTER);

        infoPanel = new PlayerInfoPanel();
        infoPanel.update(player);

        this.add(infoPanel,BorderLayout.EAST);

         modelChanged();
         this.setVisible(true);
    }

    public void play(){       
            Action action = player.getNextAction(controller.getBattle().getBattleField());
            String msg = "Player "+player.getName();
            

            if(action instanceof Moving){
                logsPanel.addMessage(msg+" moved");
                
            }else if(action instanceof Planting){
                logsPanel.addMessage(msg+" Plant Explosive");
            }else if(action instanceof Resting){
                logsPanel.addMessage(msg+" is resting");
            }
            else if(action instanceof Shooting){
                logsPanel.addMessage(msg+" shooting");
            }else{ 
                logsPanel.addMessage(msg+" shielded up");
            }
            controller.handleAction(action);

           
}


public void gameFinished(){
    nexButton.setEnabled(false);
}

    //Cette methode permet de mettre le joueur en attente ou active.
    //Elle permet aussi de mettre a jour les stats du joueur (vie,armes).
    public void modelChanged(){
        currentEnergy = player.getHealth(); 
        float percentage = (currentEnergy * 100/maxEnergy) ;
        playerEnergyBar.setValue(currentEnergy);
        playerEnergyBar.setForeground(percentage<50?Color.RED:percentage<75? Color.ORANGE:Color.GREEN);
        infoPanel.update(player);
        if(controller.getBattle().getActiveFighter().equals(player)){
            nexButton.setEnabled(true);
           
            statusLabel.setText("C'est votre tour Joueur (Robot) : "+player.getName());

        }else{
            nexButton.setEnabled(false);
            statusLabel.setText("Patientez Joueur (Robot) : "+player.getName());

           
        }

       
        
    }

    @Override
    public void actionPerformed(ActionEvent e) {
       
        if (e.getSource() == nexButton) {
            play();
          
        }
    }
    
}
