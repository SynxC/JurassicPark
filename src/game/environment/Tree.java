package game.environment;

import edu.monash.fit2099.engine.Actor;
import edu.monash.fit2099.engine.GameMap;
import edu.monash.fit2099.engine.Ground;
import edu.monash.fit2099.engine.Location;
import game.actor.Player;
import game.behaviour.FollowBehaviour;
import game.item.Egg;
import game.item.Fruit;
import game.item.PterodactylEgg;

import java.util.ArrayList;

/**
 * Class creation for the Tree objects found in the game. Trees will generate fruits based upon
 * a set probability.
 */
public class Tree extends Ground {
	private int age = 0;
	/**
	 * The fixed probability of rainfall
	 */
	private final double RAIN_PROBABILITY = 0.2;
	/**
	 * The fixed probability of a fruit growing
	 */
	private final double FRUIT_GROWTH_PROBABILITY = 0.5;
	/**
	 * The fixed probability of a fruit dropping from the tree
	 */
	private final double FRUIT_DROP_PROBABILITY = 0.05;
	/**
	 * The maximum number of fruits it can contain
	 */
	private final int MAX_FRUITS = 10;
	/**
	 * Boolean that indicates if currently, the location of the tree is experiencing rainfall.
	 */
	private boolean isRaining;
	/**
	 * Boolean that is set to true if there is an Egg object in the tree. By default, it is set to false.
	 */
	private boolean occupied;
	/**
	 * The number of turns for a rainfall to occur
	 */
	private final int RAIN_TURN = 10;
	/**
	 * References an Egg object
	 */
	private Egg egg;

	/**
	 * Array list to store the fruit objects
	 */
	private ArrayList<Fruit> fruits = new ArrayList<>();

	/**
	 * Overloading constructor of the Tree class. Tree will be represented by the char '+'.
	 */
	public Tree() {
		super('+');
		isRaining = false;
	}

	/**
	 * Getter for the occupied attribute.
	 * @return boolean true if occupied, false if unoccupied.
	 */
	public boolean isOccupied() {
		return occupied;
	}

	/**
	 * Setter for the occupied attribute.
	 * @param occupied
	 */
	public void setOccupied(boolean occupied) {
		this.occupied = occupied;
	}

	/**
	 * Adds an Egg object to the tree. This is done by setting the egg attribute in the object to reference a new
	 * Egg object. Sets occupied to true.
	 */
	public void addEgg(){
		egg = new PterodactylEgg();
		setOccupied(true);
	}

	/**
	 * Removes the Egg from the tree by setting the egg attribute to null, and setting occupied to false.
	 */
	public void removeEgg(){
		egg = null;
		setOccupied(false);
	}

	/**
	 * Returns the fruits list.
	 * @return Returns the fruits list
	 */
	public ArrayList<Fruit> getFruits() {
		return fruits;
	}

	/**
	 * Removes a fruit from the array list
	 */
	public void removeFruit(){
		fruits.remove(fruits.size()-1);
	}

	/**
	 * Tick updates the current turn of game for the class object. Every turn, there is a chance for a new fruit to grow
	 * if the tree is not full, and there is a chance for fruit to drop from the tree. Every 10 turns, there is a possibility
	 * of rainfall.
	 * @param location The location of the Ground
	 */
	@Override
	public void tick(Location location) {
		super.tick(location);

		age++;
		if (age == 10)
			displayChar = 't';
		if (age == 20)
			displayChar = 'T';

		double fruitChance = Math.random();
		if (fruitChance >= FRUIT_GROWTH_PROBABILITY && fruits.size() <= MAX_FRUITS){
			Fruit fruit = new Fruit();
			fruits.add(fruit);
			Player.updateEcoPoints(1);
		}

		double fruitDropChance = Math.random();
		if (fruitDropChance <= FRUIT_DROP_PROBABILITY && !fruits.isEmpty()){
			fruits.remove(fruits.size()-1);
			Fruit fruit = new Fruit();
			location.addItem(fruit);
		}

		if (age%RAIN_TURN == 0){
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
