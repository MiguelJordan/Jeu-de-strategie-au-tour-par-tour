package fightgame.view.gui;

import javax.swing.*;

import fightgame.controller.BattleController;
import fightgame.model.battle.*;
import fightgame.model.entities.robotfighters.RobotFighter;
import fightgame.model.factories.FighterFactory;
import fightgame.model.strategies.fighterspositionningstrategies.*;
import fightgame.model.strategies.fightersstrategies.*;
import fightgame.model.strategies.refereestrategies.*;
import gameplayers.entities.players.*;
import gameplayers.util.geometry.Position;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.CopyOnWriteArrayList;

/*
 * Le menu sert a selectionner les joueurs et definir la configuration du jeu.
 * Si la configuration n'est pas définie, on utilisera la configuration par defaut.
 */
public class Menu extends JFrame implements ActionListener {

    private static final char[] PlayerNames = "ABCDEFGHIJKLMNOPQRSTUVWXYZ".toCharArray();
    private static final List<FighterStrategy> strategies = List.of(new AttackerStrategy(),new ChaoticStrategy(),new DefenderStrategy()) ;
        
    

    private CardLayout cardLayout;
    private JPanel cardPanel;
    private JTextArea menuLabel;
    private JButton choosePlayerButton,chooseConfigButton, addPlayerButton, jouer,configureBattle,exitButton;
    private JRadioButton[] radioButtons,turnsButtons,positionsButtons;
    private List<String> selectedPlayers;
    private ButtonGroup playersGroup,turnsGroup,positionsGroup;
    private String playersOrderChoice,playersPositinsChoice;
    private int gridSize ;
    private JTextField fieldSize;


    public Menu() {
        setTitle("Menu");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(500, 500);
        this.setLocationRelativeTo(null); 
        setLayout(new BorderLayout());

        Color color = new Color(204, 0, 204);


        
        cardLayout = new CardLayout();
        cardPanel = new JPanel(cardLayout);

        JPanel menuPanel = new JPanel(new GridLayout(5, 1));
        menuPanel.setLayout(new BorderLayout());       
        menuPanel.setBackground(color);

      

        JPanel buttonNavigationPanel = new JPanel();
        buttonNavigationPanel.setBackground(color);

      
        jouer = new JButton("JOUER");
        jouer.setEnabled(false);
        jouer.addActionListener(this);
        jouer.setBackground(new Color(128, 0, 128));
        jouer.setForeground(Color.WHITE);
        jouer.setPreferredSize(new Dimension(10, 40));
        jouer.setFont(new Font("Arial", Font.BOLD, 14));

        gridSize = 10;

        selectedPlayers = new ArrayList<>();
        playersPositinsChoice = "random";
        playersOrderChoice = "ordered";

       


        menuLabel = new JTextArea(10, 32);
        menuLabel.setEditable(false);
      

        exitButton = new JButton("Exit");
        exitButton.addActionListener(this);


        addPlayerButton = new JButton("Choisissez un joueur");
        addPlayerButton.addActionListener(this); 

        configureBattle = new JButton("Configurez le jeu");
        configureBattle.addActionListener(this);

        buttonNavigationPanel.add(new JScrollPane(menuLabel));
        buttonNavigationPanel.add(configureBattle);
        buttonNavigationPanel.add(addPlayerButton);

        JLabel label = new JLabel("Ajoutez au moins deux joueurs pour lancer le jeu !", SwingConstants.CENTER);
        label.setForeground(Color.WHITE);
        label.setFont(new Font("Arial", Font.BOLD, 16));
        buttonNavigationPanel.add(label);



        menuPanel.add(buttonNavigationPanel,BorderLayout.CENTER);


        menuPanel.add(jouer, BorderLayout.SOUTH);

        JPanel radioPage = new JPanel();
        radioPage.setLayout(new GridLayout(6, 1));


        JPanel battleConfigPage = new JPanel();
        battleConfigPage.setLayout(new GridLayout(10, 1));


       
        playersGroup = new ButtonGroup();
        
        positionsGroup = new ButtonGroup();
        turnsGroup = new ButtonGroup();

        JRadioButton demolisherButton = new JRadioButton("demolisher");
        JRadioButton robotButton = new JRadioButton("robot");
        JRadioButton sniperButton = new JRadioButton("sniper");
        JRadioButton tankerButton = new JRadioButton("tanker");
        JRadioButton defenderButton = new JRadioButton("defender");

        playersGroup.add(demolisherButton);
        playersGroup.add(robotButton);
        playersGroup.add(sniperButton);
        playersGroup.add(tankerButton);
        playersGroup.add(defenderButton);


        JRadioButton randomButton = new JRadioButton("random");
        JRadioButton safeButton = new JRadioButton("safe");

        positionsGroup.add(safeButton);
        positionsGroup.add(randomButton);


        JRadioButton orderButton = new JRadioButton("ordered");
        JRadioButton randomTurnButton = new JRadioButton("random");


        turnsGroup.add(orderButton);
        turnsGroup.add(randomTurnButton);

        radioButtons = new JRadioButton[]{demolisherButton, robotButton,sniperButton,tankerButton,defenderButton};
        positionsButtons = new JRadioButton[]{safeButton, randomButton};
        turnsButtons = new JRadioButton[]{orderButton, randomTurnButton};

        ActionListener listener = e -> {
            
            boolean group1Selected = isAnySelected(positionsButtons,"position");
            boolean group2Selected = isAnySelected(turnsButtons,"order");
            chooseConfigButton.setEnabled(group1Selected && group2Selected);
        };

        
        for (JRadioButton button : radioButtons) {
            button.addActionListener(this);  
        }
        battleConfigPage.add(new JLabel("Choisissez la taille de la grille (Default 10)"));

         fieldSize = new JTextField(10);

        battleConfigPage.add(fieldSize);
        battleConfigPage.add(new JLabel("Choisissez comment positioner les joueurs"));

        for (JRadioButton button : positionsButtons) {
            button.addActionListener(listener);  
            battleConfigPage.add(button);
        }

        battleConfigPage.add(new JLabel("Choisissez comment choisir le tour des joueurs"));

        for (JRadioButton button : turnsButtons) {
            button.addActionListener(listener); 
            battleConfigPage.add(button);
 
        }


        choosePlayerButton = new JButton("OK");
        choosePlayerButton.setEnabled(false);
        choosePlayerButton.addActionListener(this); 
        choosePlayerButton.setBackground(color);
        choosePlayerButton.setForeground(Color.WHITE);

        chooseConfigButton = new JButton("OK");
        chooseConfigButton.setEnabled(false);
        chooseConfigButton.addActionListener(this);
        chooseConfigButton.setBackground(color);
        chooseConfigButton.setForeground(Color.WHITE);

        
        for (JRadioButton button : radioButtons) {
            radioPage.add(button);

            button.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    choosePlayerButton.setEnabled(true);
                }
            });
        
        }

        radioPage.add(choosePlayerButton);
        battleConfigPage.add(chooseConfigButton);


     
        cardPanel.add(menuPanel, "Menu");
        cardPanel.add(radioPage, "Players");
        cardPanel.add(battleConfigPage,"battleConfig");

        this.add(cardPanel);
        cardPanel.setOpaque(true);
        this.setVisible(true);
    }

    public void setPlayersPositinsChoice(String choice) {
        this.playersPositinsChoice = choice;
    }

    public void setPlayersOrderChoice(String choice) {
        this.playersOrderChoice = choice;
    }

    private boolean isAnySelected(JRadioButton[] buttons,String choice) {
        for (JRadioButton button : buttons) {
            if (button.isSelected()) {
                if(choice == "position"){
                    setPlayersPositinsChoice(button.getText());
                }else{
                    setPlayersOrderChoice(button.getText());
                }
                return true;
            }
        }
        return false;
    }

    
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == addPlayerButton) {
            cardLayout.show(cardPanel, "Players");  
        }else if(e.getSource() == configureBattle){
            cardLayout.show(cardPanel, "battleConfig"); 

        } else if (e.getSource() == choosePlayerButton) {
           
            for (JRadioButton button : radioButtons) {
                if (button.isSelected()) {
                    selectedPlayers.add(button.getText());
                    break;  
                }
            }

           
            if (selectedPlayers.size() > 1) {
                jouer.setEnabled(true);
            }

           
            menuLabel.setText("Sélections:\n" + selectedPlayers.toString());

            
            cardLayout.show(cardPanel, "Menu");
        } else if (e.getSource()  == chooseConfigButton ) {
            
            cardLayout.show(cardPanel, "Menu");
        }
        else if(e.getSource() == jouer){

            BattleSetup battleSetup = new BattleSetup();

            String inputText =  fieldSize.getText().trim();
            String match = "\\d+";

           

            if (inputText.matches(match)){
                int size = Integer.parseInt(inputText);
                if(size >4 && size <51){
                    gridSize = size;
                }
            }

           

            int numberFighters = selectedPlayers.size();

            TurnRefereeStrategy refree = playersOrderChoice == "random"? new RandomTurnsReferee(numberFighters) : new OrderedTurnsReferee(numberFighters);
            FightersPositioningStrategy positionning = playersPositinsChoice == "random" ? new RandomPositioning():new SafePositioning();

            battleSetup.setInitialBattleField(new Grid(gridSize, gridSize));
            battleSetup.setPositionningStrategy(positionning);
            battleSetup.setTurnRefereeStrategy(refree);


            int id=0;
            FighterFactory factory = new FighterFactory();

            for (String type : selectedPlayers) {
                Personage fighter;
                if(type == "robot"){
                    fighter = new RobotFighter("R"+id, new Position(0, 0),  getRandomStrategy());
                    battleSetup.addFighter(factory.createFighter(fighter, "demolisher"));
                }else{
                    fighter = new Player(Character.toString(PlayerNames[id]), new Position(0, 0));
                    battleSetup.addFighter(factory.createFighter(fighter, type));
                }

               id++;
            }

            Battle battle = new Battle(battleSetup);
            BattleController controller = new BattleController(battle);


      
        BattleView battleView = new BattleView(controller);

       CopyOnWriteArrayList<Personage> fighters = new CopyOnWriteArrayList<>(battle.getFighters());
        
        for (Personage perso : fighters) {
            
            if(perso instanceof RobotFighter){
                RobotFighter robot = (RobotFighter) perso;
                new RobotView(robot, controller, battleView.getLogsPanel());
            }else{
                Player player = (Player) perso;
                new PlayerView(player, controller, battleView.getLogsPanel());
            }
            
        }
            
            this.dispose();
        }else if(e.getSource() == exitButton){
            this.dispose();
        }
    }



    private FighterStrategy getRandomStrategy(){
       Random rand = new Random();
       return strategies.get(rand.nextInt(strategies.size()));
    }


}
