package fightgame.controller.cli;

import java.util.*;


/**Permet de récupérer les entrées du joueur en utilisant un scanner */
public class Prompter {

    private Scanner prompt;

    public Prompter () {
        this.prompt = new Scanner(System.in);
    }

    public void close () {
        this.prompt.close();
    }


    public int getChoice () {
        while (true) {
            try {
                return Integer.parseInt(prompt.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("Invalid input.");
            }
        }
    }

    public List<Object> getChoices (int requiredIntStart) {
        while (true) {
            try {
                String input = prompt.nextLine().trim();
                String[] parts = input.split("\\s+");
                
                if (parts.length > 0 && parts[0].equals("0")) {
                    return List.of(0); // Si le joueur entre "0", retour immédiat.
                }
                
                List<Object> choices = new ArrayList<>();
                for (int i = 0; i < requiredIntStart; i++) {
                    choices.add(parts[i].trim()); // Ajout des chaînes.
                }
                for (int i = requiredIntStart; i < parts.length; i++) {
                    choices.add(Integer.parseInt(parts[i].trim())); // Ajout des entiers.
                }
                return choices;
            } catch (Exception e) {
                System.out.println("Invalid input.");
            }
        }
    }
    

}