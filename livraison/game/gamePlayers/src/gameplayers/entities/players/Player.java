package gameplayers.entities.players;

import java.util.*;
import gameplayers.util.geometry.*;
import gameplayers.entities.model.*;
import gameplayers.entities.weapons.*;


/**La classe Player représente un joueur */
public class Player extends AbstractEntity implements Personage {

    /**Le nom du joueur */
    private String name;
    /**Les points de vie du joueur */
    private int health;
    /**Les points de vie maximal du joueur */
    private int maxHealth;
    /**Les armes du joueur */
    private Set<Weapon> weapons;
    /**Le bouclier du joueur */
    private Shield shield;

    /**
     * Instancie un nouveau joueur avec avec un nom, une position et une configuration
     * @param name le nom du joueur
     * @param position la position initiale du joueur
     * @param initialConfiguration la configuration initiale du joueur
     */
    public Player (String name, Position position, PlayerConfiguration intitialConfiguration) {
        this(name, position);
        this.init(intitialConfiguration);
    }

    /**
     * Instancie un nouveau joueur avec avec un nom, une position et la configuration par défaut
     * @param name le nom du joueur
     * @param position la position initiale du joueur
     */
    public Player (String name, Position position) {
        super(position);
        this.name = name;
        PlayerConfiguration defaultConfiguration = new PlayerConfigurationBuilder(this).build();
        this.init(defaultConfiguration);
    }

    /**
     * Permet d'initialiser le joueur avec la configuration donnée
     * @param configuration la configuration
     */
    @Override
    public void init (PlayerConfiguration configuration) {
        this.maxHealth = configuration.getInitialHealth();
        this.health = maxHealth;
        this.weapons = configuration.getInitialWeapons();
        this.shield = configuration.getInitialShield();
    }

    /**
     * Récupère le nom du personnage
     * @return le nom du personnage
     */
    @Override
    public String getName () {
        return this.name;
    }

    /**
     * Récupère les armes du personnage
     * @return les armes du personnage
     */
    @Override
    public Set<Weapon> getWeapons () {
        return this.weapons;
    }

    /**
     * Récupère les explosifs du personnage
     * @return les explosifs du personnage
     */
    @Override
    public List<Explosive> getExplosives(){
        List<Explosive> explosives = new ArrayList<>();

        for (Weapon w : weapons) {
            if(w instanceof Explosive ){
                explosives.add((Explosive)w);
            }
        }

        return explosives;
    }

    /**
     * Récupère les fusils du personnage
     * @return les fusils du personnage
     */
    @Override
    public List<Rifle> getRifles(){
        List<Rifle> rifles = new ArrayList<>();

        for (Weapon w : weapons) {
            if(w instanceof Rifle ){
                rifles.add((Rifle)w);
            }
        }

        return rifles;
    }

    /**
     * Récupère le bouclier du joueur
     * @return le bouclier du joueur
     */
    @Override
    public Shield getShield () {
        return this.shield;
    }

    /**
     * Récupère les points de vie du personnage
     * @return les points de vie du personnage
     */
    @Override
    public int getHealth () {
        return this.health;
    }

    /**
     * Récupère les points de vie maximal du personnage
     * @return les points de vie maximal du personnage
     */
    @Override
    public int getMaxHealth () {
        return this.maxHealth;
    }

    /**
     * Indique si le personnage est en vie (ses points de vie sont supérieurs à 0) ou pas (ses points de vie sont supérieurs à 0)
     * @return true s'il est en vie, false sinon
     */
    @Override
    public boolean isAlive () {
        return this.health > 0; 
    }

    /**
     * Fait gagner des points de vie au personnage
     * @param gainedHealth les points de vie gagnés
     */
    @Override
    public void recoverHealth (int gainedHealth) {
        this.health += gainedHealth;
        if (this.health > this.maxHealth) {
            this.health = this.maxHealth;
        }
    }

    /**
     * Fait perdre des points de vie au personnage
     * @param damage les points de vie perdus
     */
    @Override
    public void takeDamage (int damage) {
        int finalDamage = this.shield.absorbDamage(damage);
        this.health -= finalDamage; 
    }

    /**
     * Permet d'ajouter une nouvelle arme au joueur
     * @param newWeapon l'arme à ajouter
     */
    public void addWeapon (Weapon newWeapon) {
        if (this.equals(newWeapon.getOwner())) {
            this.weapons.add(newWeapon);
        }
    }

    /**
     * Permet de retirer une arme au joueur
     * @param uselessWeapon l'arme à retirer
     */
    public void removeWeapon (Weapon uselessWeapon) {
        if (this.weapons.contains(uselessWeapon)) {
            this.weapons.remove(uselessWeapon);
        }
    }

    /**
     * Permet au joueur de se déplacer dans une direction donnée
     * Les directions sont 1 -> north, 2 -> east, 3 -> south, 4 -> west
     * @param direction la direction de déplacement
     */
    public void move (int direction) {
        this.shield.desactivate();
        this.position.change(direction);
    }
    /**
     * Permet au joueur de se déplacer dans à une position donnée
     * @param direction la position de destination
     */
    public void move (Position destination) {
        this.shield.desactivate();
        this.position.setX(destination.getX());
        this.position.setY(destination.getY());
    }

    /**
     * Permet au joueur de planter un explosif à une position donnée
     * Les positions sont 1 -> north, 2 -> east, 3 -> south, 4 -> west, 5 -> north-east, 6 -> south-east, 7 -> south-west, 8 -> north-west
     * @param explosive l'explosif à planter
     * @param positition la position où on le plante
     */
    public void plant (Explosive explosive, int itsPosition) {
        this.shield.desactivate();
        if (this.weapons.contains(explosive) && explosive != null) {
            explosive.getsPlanted(this.position.getNeighbor(itsPosition));
            this.weapons.remove(explosive);
        }
    }

    /**
     * Permet au joueur de tirer dans une direction donnée
     * Les directions sont 1 -> north, 2 -> east, 3 -> south, 4 -> west
     * @param usedGun le fusil ustilisé pour le tir
     * @param direction la direction du tir
     */
    public void shoot (Rifle usedGun, int direction) {
        this.shield.desactivate();
        if (this.weapons.contains(usedGun)) {
            usedGun.triggerHasBeenPulled(direction);
        }
    }

    /**
     * Permet au joueur de se reposer. Il ne fait rien. Dans un jeu, il passera son tour.
     */
    public void rest () {}

    /**
     * Permet au joueur d'activer son bouclier
     */
    public void shieldUp () {
        this.shield.activate();
    }

    /**Deux joueurs de même nom sont égaux */
    @Override
    public boolean equals (Object other) {
        if (other instanceof Player) {
            Player otherAsPlayer = (Player) other;
            return Objects.equals(otherAsPlayer.getName(), this.name);
        }
        return false;
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.name);
    }


    @Override
    public String toString () {
        String representation = "Player {\n\t";
        representation += "Name             : " + this.name + "\n\t";
        representation += "Health           : " + this.health + "\n\t";
        representation += "Current Position : " + this.position + "\n\t";
        representation += "Weapons          {\n";
        for (Weapon weapon : this.weapons) {
            representation += "\t\t" + weapon + "\n";
        }
        representation += "\t}\n\tShield           : " + this.shield + "\n}";
        return representation;
        //return this.name;
    }


}