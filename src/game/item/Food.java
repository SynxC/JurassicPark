package game.item;

/**
 * Abstract class creation for the item Food, an extension of PortableItem.
 */
public abstract class Food extends PortableItem{
    /**
     * The overloaded constructor for the Food class.
     * @param name the name of the food
     * @param displayChar the display char for the type of food
     */
    public Food(String name, char displayChar) {
        super(name, displayChar);
    }
}
