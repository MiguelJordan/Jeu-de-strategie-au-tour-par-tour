package fightgame.view.gui;

import java.awt.Color;
import java.awt.Dimension;

import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

//Pour garder une trace des actions faites par les joueurs dans le jeu.
//Utilisée par RobotView et PlayerView mais elle est affichée dans le BattleView.
public class LogsPanel extends JPanel {
    private List<String> logs;
    private JPanel messagesPanel;
    private JScrollPane scrollPanel;

    public LogsPanel(){
        logs = new ArrayList<>();
        messagesPanel = new JPanel();
        messagesPanel.setLayout(new BoxLayout(messagesPanel, BoxLayout.Y_AXIS)); 

        scrollPanel = new JScrollPane(messagesPanel);
        scrollPanel.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS); 
        scrollPanel.setPreferredSize(new Dimension(200, 500));
      

        this.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
      
        this.add(scrollPanel);
    }

    public void addMessage(String msg){ 
        logs.add(msg);
        JLabel message = new JLabel(msg);
        messagesPanel.add(message);
        messagesPanel.revalidate();
        messagesPanel.repaint();
        scrollPanel.revalidate();
        scrollPanel.repaint();
    }
}
