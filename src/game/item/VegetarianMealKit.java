package game.item;

/**
 * Class creation for the item VegetarianMealKit, a food item. Could on be used on herbivorous dinosaurs.
 */
public class VegetarianMealKit extends Food {
    public static final int PRICE = 100;
    public static final int FEED_POINTS = 160;

    /**
     * Overloading constructor of the VegetarianMealKit class. VegetarianMealKit will be represented by the char '&'.
     */
    public VegetarianMealKit() {
        super("VegetarianMealKit", '&');
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
