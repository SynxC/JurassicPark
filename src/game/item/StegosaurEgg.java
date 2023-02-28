package game.item;

import edu.monash.fit2099.engine.Location;
import game.actor.Player;
import game.actor.Stegosaur;

/**
 * Class that represents a Stegosaur's egg, an Egg item.
 */
public class StegosaurEgg extends Egg {
    public static final int PRICE = 200;

    /**
     * Overloading constructor of the StegosaurEgg class. StegosaurEgg will be represented by the char 'o'.
     */
    public StegosaurEgg() {
        super("Stegosaur");
    }

    /**
     * Tick updates the current turn of game for the class object
     * Will spawn the specified dinosaur object once the condition has been fulfilled.
     * @param location The location of the Ground
     */
    public void tick(Location location){
        super.tick(location);
        if (age == 20){
            Stegosaur babyDino = new Stegosaur(0);
            location.addActor(babyDino);
            location.removeItem(this);
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
