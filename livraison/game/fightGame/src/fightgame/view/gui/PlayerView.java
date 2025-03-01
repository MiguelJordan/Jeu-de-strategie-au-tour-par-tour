package fightgame.view.gui;

import javax.swing.*;

import fightgame.controller.BattleController;
import gameplayers.entities.players.Player;
import gameplayers.entities.weapons.Bomb;
import gameplayers.entities.weapons.Explosive;
import gameplayers.entities.weapons.Mine;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.*;

//La vue du joueur Humain
public class PlayerView extends JFrame implements ActionListener,KeyListener{

    private JLabel statusLabel;
    private JButton plantButton, shootButton, restButton;
    private JProgressBar playerEnergyBar;
    private int maxEnergy,currentEnergy;
    private GamePanel board;
    private BattleController controller;
    private Player player;
    private LogsPanel logsPanel;
    private Mine mine;
    private Bomb bomb;
    private PlayerInfoPanel infoPanel;
  

  
    public PlayerView(Player player,BattleController controller,LogsPanel logsPanel){
       this.controller = controller;
       this.player = player;
       this.logsPanel = logsPanel;
        maxEnergy = player.getMaxHealth(); //on va recuperer ca du joueur
        currentEnergy = maxEnergy;
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(800, 600);
        this.setLayout(new BorderLayout());
        statusLabel = new JLabel("Patientez Joueur : "+player.getName());
        this.add(statusLabel, BorderLayout.NORTH);

        playerEnergyBar = new JProgressBar(0,maxEnergy);
        playerEnergyBar.setValue(currentEnergy);
        playerEnergyBar.setStringPainted(true); 
        playerEnergyBar.setForeground(Color.GREEN);
        playerEnergyBar.setBackground(Color.LIGHT_GRAY);
       
        JLabel energyLabel = new JLabel("Energy");

       
      
        // Boutons d'actions
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        plantButton = new JButton("Poser Explosive");
        shootButton = new JButton("Tirer");
        restButton = new JButton("Passer");

        plantButton.addActionListener(this);
        shootButton.addActionListener(this);
        restButton.addActionListener(this);

        buttonPanel.add(energyLabel);
        buttonPanel.add(playerEnergyBar);
        buttonPanel.add(plantButton);
        buttonPanel.add(shootButton);
        buttonPanel.add(restButton);
       
        this.add(buttonPanel, BorderLayout.SOUTH);

        board = new GamePanel(true,controller.getBattle(),player,this);

        this.add(board,BorderLayout.CENTER);

        infoPanel = new PlayerInfoPanel();
        infoPanel.update(player);

        this.add(infoPanel,BorderLayout.EAST);


        this.addKeyListener(this);
        setFocusable(true);
        modelChanged();
        this.setVisible(true);
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
            setButtonsState(true);
           
            statusLabel.setText("C'est votre tour Joueur : "+player.getName());

        }else{
            setButtonsState(false);
            statusLabel.setText("Patientez Joueur : "+player.getName());

           
        }

       
    }


    public void gameFinished(){
        this.removeKeyListener(this);
        setButtonsState(false);
    }
    

   

    @Override
    public void actionPerformed(ActionEvent e) {
        String msg = "Player "+player.getName();
        if (e.getSource() == plantButton) {
            System.out.println("Poser Explosive");
           // controller.handlePlant(player, 1);
           plantDialog("Poser Un Explosive");
           //logsPanel.addMessage(msg+" Plant an Explosive");
           
        } else if (e.getSource() == shootButton) {
            System.out.println("Tirer");
            //logsPanel.addMessage(msg+" shoot");
            shootDialog("Fusil");
           
        } else if (e.getSource() == restButton) {
            controller.handleRest(player);
            logsPanel.addMessage(msg+" is resting");
           
        }

        this.requestFocusInWindow();  
          
    }

    //Pour déactivé les bouttons si le joueur pass son tour et l'activer si c'est son tour
    public void setButtonsState(boolean state){
        plantButton.setEnabled(state);
        shootButton.setEnabled(state);
        restButton.setEnabled(state);
    }


public void shootDialog(String action){
    JDialog dialog = new JDialog(this,action, true);
    dialog.setSize(300, 300);
    dialog.setLayout(new GridLayout(6, 1)); 

    JPanel buttonPanel = new JPanel();
    buttonPanel.setLayout(new FlowLayout());

    if(player.getRifles().size() !=0){
         
        String text = "Entrez une position (1 à 4)";
        JLabel labelChiffres = new JLabel(text);
        JTextField textField = new JTextField();
        dialog.add(labelChiffres);
        dialog.add(textField);

        JButton confirmButton = new JButton("Confirmer");

        confirmButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                
                String inputText = textField.getText().trim();
                int input = Integer.parseInt(inputText);

                if(input >0 && input<5){

                    controller.handleShoot(player, input, player.getRifles().get(0));
                    logsPanel.addMessage("Player " + player.getName() + " Shoot" + " at " + input);


                }else{
                    JOptionPane.showMessageDialog(dialog, "Veuillez entrer un nombre entre 1 et 4 chiffres.", "Erreur", JOptionPane.ERROR_MESSAGE);
                }


                dialog.dispose();
            }
        });
        dialog.add(confirmButton);

    }else{
        JLabel msg = new JLabel("Vous N'avez aucun fusil");
        dialog.add(msg);
    }

       
        buttonPanel.setLayout(new FlowLayout());
        
        JButton closeButton = new JButton("Fermer");
        closeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dialog.dispose(); 
            }
            });
    
            buttonPanel.add(closeButton);
            dialog.add(buttonPanel);

            
            dialog.setLocationRelativeTo(this);
            dialog.setVisible(true);
}


public void plantDialog(String action){

    JDialog dialog = new JDialog(this,action, true);
    dialog.setSize(300, 300);
    dialog.setLayout(new GridLayout(6, 1)); 

    JPanel buttonPanel = new JPanel();
    buttonPanel.setLayout(new FlowLayout());
    
    
    if(player.getExplosives().size()!=0){

        
    
        Set<String> explosivesTypes = new HashSet<>();
       

        for (Explosive e : player.getExplosives()) {
            if(e instanceof Bomb){
                explosivesTypes.add("Bombe");
                bomb = (Bomb) e;
            }else if( e instanceof Mine){
                explosivesTypes.add("Mine");
                mine = (Mine) e;
            }
        }


        
        
        String text =  "Entrez un direction (1 à 8)";
        JLabel labelChiffres = new JLabel(text);
        JTextField textField = new JTextField();
        dialog.add(labelChiffres);
        dialog.add(textField);

      

     
        JRadioButton option1 = new JRadioButton("Mine");
        JRadioButton option2 = new JRadioButton("Bombe");

        JButton confirmButton = new JButton("Confirmer");
        confirmButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Actions à réaliser lors de la confirmation
                
                
                // Vérification du champ de texte (1 à 8 chiffres)
                String inputText = textField.getText().trim();
                String match = "\\d{1,8}";
                if (inputText.matches(match)) {

                    System.out.println("Nombre saisi : " + inputText);

                    int input = Integer.parseInt(inputText);

                    if (option1.isSelected()) {
                        controller.handlePlant(player, input,mine);
                        logsPanel.addMessage("Player " + player.getName() + " plant Mine" + " at " + input);
                    } else if (option2.isSelected()) {
                        controller.handlePlant(player, input,bomb);
                        logsPanel.addMessage("Player " + player.getName() + " plant Bombe" + " at " + input);
                    }  else {
                        System.out.println("Aucune option sélectionnée");
                    }
                } else {
                    
                    JOptionPane.showMessageDialog(dialog, "Veuillez entrer un nombre entre 1 et 8 chiffres.", "Erreur", JOptionPane.ERROR_MESSAGE);
                }

                dialog.dispose(); 
            }
        });

      
            ButtonGroup group = new ButtonGroup();
            
            group.add(option1);
            group.add(option2);
          
        
            
            dialog.add(option1);
            dialog.add(option2);
        
       
        dialog.add(confirmButton);
       
    }else{
        JLabel msg = new JLabel("Vous N'avez aucun explosive");
        dialog.add(msg);
    }

       
        buttonPanel.setLayout(new FlowLayout());
        
        JButton closeButton = new JButton("Fermer");
        closeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dialog.dispose(); 
            }
            });
    
            buttonPanel.add(closeButton);
            dialog.add(buttonPanel);

            
            dialog.setLocationRelativeTo(this);
            dialog.setVisible(true);
}


@Override
public void keyTyped(KeyEvent e) {}


@Override
public void keyPressed(KeyEvent e) {

    int direction = -1;
    int key = e.getKeyCode();

    if(controller.getBattle().getActiveFighter().equals(player)){
    if(key == KeyEvent.VK_UP){
        direction = 1; // Haut
    }else if(key == KeyEvent.VK_RIGHT){
        direction = 2; // Droite
    }else if(key == KeyEvent.VK_DOWN){
        direction = 3; // Bas
    }else if(key == KeyEvent.VK_LEFT){
        direction = 4; // Gauche
    }
    //System.out.println(" I am at position : "+player.getPosition()+" and decided to move at direction : " + direction);
    logsPanel.addMessage("Player "+player.getName() + " Moved");
    controller.handleMove(player, direction);

   
   

}
   
}


@Override
public void keyReleased(KeyEvent e) {}



}


