package fightgame.view.gui;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;


import javax.swing.JPanel;

import fightgame.model.battle.*;
import fightgame.model.entities.collectables.AmmoPack;
import fightgame.model.entities.collectables.EnergyPellet;
import fightgame.model.entities.robotfighters.RobotFighter;
import fightgame.model.entities.blocks.Block;
import gameplayers.entities.model.Entity;
import gameplayers.entities.players.*;
import gameplayers.entities.weapons.Explosive;

import util.mvc.Listener;

// Cette classe permet d'afficher la grille que ce soit pour un robot, humain ou la vue global 
//en fonction du constructeur appelé
public class GamePanel extends JPanel implements Listener {
    private boolean isPlayer=false;
    private Battle battle;
    private Player player;
    private PlayerView playerView;
    private RobotView robotView;


    //Pour la vue du joueur Humain
    public GamePanel(boolean isPlayer,Battle battle,Player player,PlayerView parentView){
        this.isPlayer = isPlayer;
        this.battle = battle;
        this.player = player;
        this.playerView = parentView;
        this.battle.addListener(this);  
    }

   
    //Pour la vue global du jeu
    public GamePanel(Battle battle){
     this.battle = battle;
     this.battle.addListener(this);
    }

    //Pour la vue du joueur  robot
    public GamePanel(boolean isPlayer,Battle battle,RobotFighter player,RobotView parentView){
        this.isPlayer = isPlayer;
        this.battle = battle;
        this.player = player;
        this.robotView = parentView;
        this.battle.addListener(this);
    }

    

    @Override
protected void paintComponent(Graphics g) {
    super.paintComponent(g);

    int linesCount = battle.getBattleField().getLinesCount();
    int columnsCount = battle.getBattleField().getColumnsCount();

    int actorWidth = getWidth() / columnsCount;
    int actorHeigth = getHeight() / linesCount;

    Grid battleField =  battle.getBattleField();

    for (int x = 0; x < linesCount; x++) {
        for (int y = 0; y < columnsCount; y++) {
            Entity actor = battleField.get(x, y);

            g.setColor(Color.WHITE);
            g.fillRect(y * actorWidth, x * actorHeigth, actorWidth, actorHeigth);


            if (actor instanceof Personage) {
                Player perso = (Player) actor;

                if (isPlayer) {
                    g.setColor(player.equals(perso) ? Color.GREEN : Color.GRAY);
                } else {
                    Player currentPlayer = (Player) battle.getActiveFighter();
                    g.setColor(currentPlayer.equals(perso) ? Color.GREEN : Color.GRAY);
                }

                g.fillRect(y * actorWidth, x * actorHeigth, actorWidth, actorHeigth);

                g.setColor(Color.WHITE);
                String name = perso.getShield().isActivated()?  perso.getName()+"🛡️" : perso.getName();
                int textWidth = g.getFontMetrics().stringWidth(name);
                g.drawString(name, y * actorWidth + (actorWidth - textWidth) / 2,
                        x * actorHeigth + (actorHeigth + g.getFontMetrics().getHeight()) / 2);
            }

            else if (actor instanceof Explosive) {

               PlayerProxy playerProxy = new PlayerProxy(player);
                if (!isPlayer ||isPlayer && (playerProxy.canSee(actor))) {
                   
                        g.setColor(Color.RED);
                        g.fillRect(y * actorWidth, x * actorHeigth, actorWidth, actorHeigth);

                        g.setColor(Color.BLACK);
                        g.drawString("💣", y * actorWidth + actorWidth / 2, x * actorHeigth + actorHeigth / 2);
                    
                } 
            }else if(actor instanceof AmmoPack){
                if(!isPlayer){
                   
                    g.setColor(Color.BLACK);
                    g.drawString("🔫🪙", y * actorWidth + actorWidth / 2, x * actorHeigth + actorHeigth / 2);
                
                }
            }else if(actor instanceof EnergyPellet){
                if(!isPlayer){
                    g.setColor(Color.BLACK);
                    g.drawString("⚡💊", y * actorWidth + actorWidth / 2, x * actorHeigth + actorHeigth / 2);
                
                }
            }

            else if (actor instanceof Block) {
                Block block = (Block) actor;
                g.setColor(block.isCrossable() ? Color.WHITE : Color.DARK_GRAY);
                g.fillRect(y * actorWidth, x * actorHeigth, actorWidth, actorHeigth);
            }
            
            g.setColor(Color.BLACK);
            g.drawRect(y * actorWidth, x * actorHeigth, actorWidth, actorHeigth);
        }
    }



    if (battle.isOver()) {

        

        String message = "Le Joueur " + battle.getFighters().get(0).getName() + " a gagné";
        
        if(isPlayer){
            if(playerView!= null) playerView.setButtonsState(false);
            message = "Vous avez perdu.....";

            if(battle.getFighters().get(0).equals(player)){
                message = "Vous avez gagnez !!!!!!!!!";
            }

        }
        
        g.setColor(Color.RED); 
        g.setFont(new Font("Arial", Font.BOLD, 20)); 
      
        int messageLargeur = g.getFontMetrics().stringWidth(message);
        int messageHauteur = g.getFontMetrics().getHeight();
        int messageX = (getWidth() - messageLargeur) / 2;
        int messageY = (getHeight() - messageHauteur) / 2;
        g.drawString(message, messageX, messageY);
        
    }
}

    @Override
    public void updatedModel(Object source) {
       repaint();

       if(!battle.isOver()){

        if(isPlayer) {
            if(player instanceof RobotFighter) {
                robotView.modelChanged();
            }
     
            else {
                playerView.modelChanged();
            }
       }
      
        
    }else{
        if(isPlayer) {
             if(player instanceof RobotFighter) {
                 robotView.gameFinished();
             }
      
             else {
                 playerView.gameFinished();
                
             }
        }
    }
    }

    
}
