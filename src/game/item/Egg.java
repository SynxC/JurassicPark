package game.item;

import edu.monash.fit2099.engine.Location;

/**
 * Abstract class creation for the item Egg, a food item.
 */
public abstract class Egg extends Food {
    private String species;
    protected int age = 0;
    static final int FEED_POINTS = 10;

    /**
     * The overloaded constructor for the Egg class. All eggs will be represented by the char 'o'.
     * @param species the species of the egg
     */
    public Egg(String species) {
        super(species+"Egg", 'o');
        setSpecies(this.species);
    }

    /**
     * Returns the current species.
     * @return species
     */
    public String getSpecies() {
        return species;
    }

    /**
     * Sets the current species.
     * @return species
     */
    public void setSpecies(String species) {
        this.species = species;
    }

    /**
     * Returns the total restorative points of the object
     * @return the feed points
     */
    public static int getFeedPoints() {
        return FEED_POINTS;
    }

    /**
     * Tick updates the current turn of game for the class object
     * Will spawn the specified dinosaur object once the condition has been fulfilled.
     * @param currentLocation The location of the Ground
     */
    public void tick(Location currentLocation){
        super.tick(currentLocation);
        age++;
            }
        }


