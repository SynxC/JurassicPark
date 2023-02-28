package game.environment;

import edu.monash.fit2099.engine.Ground;
import edu.monash.fit2099.engine.Location;
import game.item.Fruit;

import java.util.ArrayList;

/**
 * Class creation for the Bush objects found in the game. Bushes will generate fruits based upon
 * a set probability.
 */
public class Bush extends Ground {
    /**
     * Array list to store the fruit objects
     */
    private ArrayList<Fruit> fruits = new ArrayList<>();
    /**
     * Counts how many turns has passed.
     */
    private int currentTurn;
    /**
     * The fixed probability of rainfall
     */
    private final double RAIN_PROBABILITY = 0.2;
    /**
     * The fixed probability of a fruit growing
     */
    private final double FRUIT_GROWTH_PROBABILITY = 0.1;
    /**
     * The maximum number of fruits it can contain
     */
    private final int MAX_FRUITS = 5;
    /**
     * The number of turns for a rainfall to occur
     */
    private final int RAIN_TURN = 10;
    /**
     * Boolean that indicates if it is raining in this turn
     */
    private boolean isRaining;
    /**
     * Overloading constructor of the Bush class. Bush will be represented by the char 'w'.
     */
    public Bush(){
        super('w');
        isRaining = false;
        currentTurn = 1;
    }

    public ArrayList<Fruit> getFruits() {
        return fruits;
    }

    /**
     * Removes the fruit from the array list
     */
    public void removeFruit(){
        fruits.remove(fruits.size()-1);
    }

    /**
     * Tick updates the current turn of game for the class object. Every turn, there is a chance for a new fruit to grow
     * if the bush is not full. Every 10 turns, there is a possibility for rainfall.
     * @param location The location of the Ground
     */
    public void tick(Location location){
        super.tick(location);

        currentTurn++;
        double possibility = Math.random();
        if (possibility<= FRUIT_GROWTH_PROBABILITY && fruits.size() <= MAX_FRUITS){
            fruits.add(new Fruit());
        }

        if (currentTurn%RAIN_TURN == 0){
            double rainChance = Math.random();
            if (rainChance <= RAIN_PROBABILITY){
                isRaining = true;
            }
        }else{
            isRaining = false;
        }
    }

    public boolean isRaining() {
        return isRaining;
    }
}
