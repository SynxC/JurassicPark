package game.item;

/**
 * Class creation for the Fish object, which is extended from Food. Only obtainable from the Lake object instances.
 */
public class Fish extends Food{
    /**
     * Overloading constructor of the Fish class. Fish will be represented by the char 'f'.
     */
    public Fish() {
        super("Fish", 'f');
    }
}
