package fightgame.view.gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FontMetrics;
import java.awt.Graphics;

import javax.swing.JPanel;

public class SquarePanel extends JPanel {
    
    private Color backgroundColor; 
    private String text; 
    private int size; 
    
   //Utilisée pour afficher les significations des case dans la grille.
    public SquarePanel(Color backgroundColor, String text, int size) {
        this.backgroundColor = backgroundColor;
        this.text = text;
        this.size = size;
        setPreferredSize(new Dimension(size, size)); 
    }
    
    
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g); 
        
        
        g.setColor(backgroundColor); 
        g.fillRect(0, 0, size, size); 
        
       
        g.setColor(Color.BLACK); 
        FontMetrics fm = g.getFontMetrics();
        int x = (size - fm.stringWidth(text)) / 2; 
        int y = (size + fm.getAscent()) / 2; 
        g.drawString(text, x, y); 
    }
}
