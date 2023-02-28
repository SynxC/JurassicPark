package game.item;

import edu.monash.fit2099.engine.Ground;
import edu.monash.fit2099.engine.Location;
import game.actor.Player;
import game.actor.Pterodactyl;
import game.environment.Tree;

/**
 * Class that represents a PterodactylEgg, an Egg item.
 */
public class PterodactylEgg extends Egg{
    public static final int PRICE = 200;
    /**
     * The overloaded constructor for the Egg class. All eggs will be represented by the char 'o'.
     */
    public PterodactylEgg() {
        super("Pterodactyl");
    }

    /**
     * Tick updates the current turn of game for the class object
     * Will spawn the specified dinosaur object once the condition has been fulfilled. If the egg is on a tree, it will
     * be removed and the tree will be set to unoccupied.
     * @param location The location of the Ground
     */
    public void tick(Location location){
        super.tick(location);
        if (age == 20){
            Pterodactyl babyDino = new Pterodactyl(0);
            location.addActor(babyDino);
            Ground ground = location.getGround();
            if (ground instanceof Tree){
                Tree tree = ((Tree)ground);
                tree.removeEgg();
            }else{
                location.removeItem(this);
            }

            Player.updateEcoPoints(100);
        }
    }

    /**
     * Returns the price of the object.
     * @return the price
     */
    public int getPrice(){
        return PRICE;
    }
}
