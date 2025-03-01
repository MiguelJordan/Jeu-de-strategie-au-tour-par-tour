package gameplayers.entities.players;

import java.util.*;
import gameplayers.entities.model.*;
import gameplayers.entities.weapons.*;
import gameplayers.util.geometry.*;

/**L'interface Personage représente un personnage d'un jeu donné (un joueur) */
public interface Personage extends Entity {

    /**
     * Récupère les points de vie du personnage
     * @return les points de vie du personnage
     */
    public int getHealth ();
    
    /**
     * Récupère les points de vie maximal du personnage
     * @return les points de vie maximal du personnage
     */
    public int getMaxHealth ();
    
    /**
     * Fait gagner des points de vie au personnage
     * @param gainedHealth les points de vie gagnés
     */
    public void recoverHealth (int gainedHealth);

    /**
     * Indique si le personnage est en vie (ses points de vie sont supérieurs à 0) ou pas (ses points de vie sont supérieurs à 0)
     * @return true s'il est en vie, false sinon
     */
    public boolean isAlive ();

    /**
     * Récupère le nom du personnage
     * @return le nom du personnage
     */
    public String getName ();

    /**
     * Récupère les armes du personnage
     * @return les armes du personnage
     */
    public Set<Weapon> getWeapons ();

    /**
     * Récupère les explosifs du personnage
     * @return les explosifs du personnage
     */
    public List<Explosive> getExplosives ();

    /**
     * Récupère les fusils du personnage
     * @return les fusils du personnage
     */
    public List<Rifle> getRifles ();

    /**
     * Fait perdre des points de vie au personnage
     * @param damage les points de vie perdus
     */
    public void takeDamage (int damage);

    /**
     * Permet au joueur de se déplacer dans une direction donnée
     * Les directions sont 1 -> north, 2 -> east, 3 -> south, 4 -> west
     * @param direction la direction de déplacement
     */
    public void move (int direction);
    /**
     * Permet au joueur de se déplacer dans à une position donnée
     * @param direction la position de destination
     */
    public void move (Position destination);

    /**
     * Permet au joueur de tirer dans une direction donnée
     * Les directions sont 1 -> north, 2 -> east, 3 -> south, 4 -> west
     * @param usedGun le fusil ustilisé pour le tir
     * @param direction la direction du tir
     */
    public void shoot (Rifle usedGun, int direction);

    /**
     * Permet au joueur de planter un explosif à une position donnée
     * Les positions sont 1 -> north, 2 -> east, 3 -> south, 4 -> west, 5 -> north-east, 6 -> south-east, 7 -> south-west, 8 -> north-west
     * @param explosive l'explosif à planter
     * @param positition la position où on le plante
     */
    public void plant (Explosive explosive, int position);

    /**
     * Permet au joueur d'activer son bouclier
     */
    public void shieldUp ();
    /**
     * Récupère le bouclier du joueur
     * @return le bouclier du joueur
     */
    public Shield getShield ();

    /**
     * Permet au joueur de se reposer. Il ne fait rien. Dans un jeu, il passera son tour.
     */
    public void rest ();

    /**
     * Permet de retirer une arme au joueur
     * @param uselessWeapon l'arme à retirer
     */
    public void removeWeapon (Weapon uselessWeapon);

    /**
     * Permet d'ajouter une nouvelle arme au joueur
     * @param newWeapon l'arme à ajouter
     */
    public void addWeapon (Weapon newWeapon);

    /**
     * Permet d'initialiser le joueur avec la configuration donnée
     * @param configuration la configuration
     */
    public void init (PlayerConfiguration configuration);

}