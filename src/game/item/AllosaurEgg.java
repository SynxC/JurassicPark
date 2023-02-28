package game.item;

import edu.monash.fit2099.engine.Location;
import game.actor.Allosaur;
import game.actor.Player;

/**
 * Class that represents a AllosaurEgg, an Egg item.
 */
public class AllosaurEgg extends Egg {
    public static final int PRICE = 1000;

    /**
     * Overloading constructor of the AllosaurEgg class. AllosaurEgg will be represented by the char 'o'.
     */
    public AllosaurEgg() {
        super("Allosaur");
    }

    /**
     * Tick updates the current turn of game for the class object
     * Will spawn the specified dinosaur object once the condition has been fulfilled.
     * @param location The location of the Ground
     */
    public void tick(Location location){
        super.tick(location);
        if (age == 50){
            Allosaur babyDino = new Allosaur(0);
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
