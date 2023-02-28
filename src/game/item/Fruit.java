package game.item;

import edu.monash.fit2099.engine.Location;

/**
 * Class creation for the Fruit object, which is extended from Food. The most abundant food resource available in
 * the game map.
 */
public class Fruit extends Food {
    public static final int PRICE = 30;
    public static final int FEED_POINTS = 20;
    private int age;

    /**
     * Overloading constructor of the Fruit class. Fruit will be represented by the char '*'.
     */
    public Fruit(){
        super("Fruit", '*');
    }

    /**
     * A static method that returns the total restorative points of the object
     * @return the feed points
     */
    public static int getFeedPoints() {
        return FEED_POINTS;
    }

    /**
     * Returns the price of the object.
     * @return the price
     */
    public int getPrice(){
        return PRICE;
    }

    /**
     * Tick updates the current turn of game for the class object
     * This tackles the rot portion of the fruit item, removing it from the map
     * once the age condition is fulfilled.
     * @param location The location of the Ground
     */
    public void tick(Location location){
        age++;
        if (age>=15){
            location.removeItem(this);
        }
    }
}
