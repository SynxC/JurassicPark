package game.item;

/**
 * Class creation for the item CarnivoreMealKit, a food item. Could on be used on carnivorous dinosaurs.
 */
public class CarnivoreMealKit extends Food {
    public static final int PRICE = 500;
    public static final int FEED_POINTS = 100;

    /**
     * Overloading constructor of the CarnivoreMealKit class. CarnivoreMealKit will be represented by the char '^'.
     */
    public CarnivoreMealKit() {
        super("CarnivoreMealKit", '^');
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
}
