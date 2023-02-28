package game.item;

import edu.monash.fit2099.engine.Location;
import game.actor.Brachiosaur;
import game.actor.Player;

/**
 * Class that represents a BrachiosaurEgg, an Egg item.
 */
public class BrachiosaurEgg extends Egg {
    public static final int PRICE = 500;

    /**
     * Overloading constructor of the BrachiosaurEgg class. BrachiosaurEgg will be represented by the char 'o'.
     */
    public BrachiosaurEgg() {
        super("Brachiosaur");
    }

    /**
     * Tick updates the current turn of game for the class object
     * Will spawn the specified dinosaur object once the condition has been fulfilled.
     * @param location The location of the Ground
     */
    public void tick(Location location){
        super.tick(location);
        if (age == 40){
            Brachiosaur babyDino = new Brachiosaur(0);
            location.addActor(babyDino);
            location.removeItem(this);
            Player.updateEcoPoints(1000);
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
