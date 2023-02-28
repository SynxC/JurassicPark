package game.actor;


import edu.monash.fit2099.engine.*;
import game.action.FeedingAction;
import game.behaviour.WanderBehaviour;
import game.action.AttackAction;
import game.interfaces.NeedsPlayer;
import game.item.*;

import static java.util.Objects.isNull;

/**
 * Class creation for Stegosaur, a herbivorous dinosaur.
 */
public class Stegosaur extends Dinosaur {
	/**
	 * String that represents the dinosaur's species.
	 */
	static final String SPECIES = "Stegosaur";
	/**
	 * The age threshold that indicates the adult age for the dinosaur.
	 */
	static final int ADULT_AGE = 20;
	/**
	 * The maximum hit points for the dinosaur.
	 */
	static final int MAX_HIT_POINTS=100;
	/**
	 * The hit points threshold that indicates if the dinosaur is hungry.
	 */
	static final int HUNGRY_LEVEL = 90;
	/**
	 * The duration of the dinosaur's pregnancy. It is the maximum number of turns before the dinosaur lays its egg.
	 */
	static final int PREGNANT_LENGTH = 10;
	/**
	 * The display character for a baby dinosaur.
	 */
	static final char BABY_STEGOSAUR_DISPLAY = 's';
	/**
	 * The display character for an adult dinosaur.
	 */
	static final char ADULT_STEGOSAUR_DISPLAY = 'S';
	/**
	 * The minimum hit points required for a dinosaur to breed.
	 */
	static final int BREEDING_LEVEL =50;
	/**
	 * The number of turns a dinosaur can remain unconscious before it dies.
	 */
	static final int UNCONSCIOUS_LIMIT =20;
	/**
	 * The maximum thirst level for a dinosaur.
	 */
	static final int MAX_THIRST = 100;
	/**
	 * The starting values for the thirst level. Used when spawning a dinosaur.
	 */
	static final int STARTING_THIRST = 60;
	/**
	 * The thirst level threshold that indicates if a dinosaur is thirsty.
	 */
	static final int THIRSTY_LEVEL = 40;
	/**
	 * The amount of hit points the dinosaur's corpse provides when it dies.
	 */
	static final int CORPSE_HEALTH = 50;
	/**
	 * The starting values for the hit points. Used when spawning a dinosaur.
	 */
	static final int STARTING_HIT_POINTS = 50;

	/**
	 * First overloaded constructor of the Stegosaur class. All Stegosaurs have 100 hit points. The baby
	 * Stegosaur will have 's' as its display and the adult will have 'S'.
	 * @param age the current age of the Stegosaur to be created for
	 */
	public Stegosaur(int age) {
		super(SPECIES, ADULT_STEGOSAUR_DISPLAY, age, MAX_HIT_POINTS,STARTING_HIT_POINTS,PREGNANT_LENGTH, ADULT_AGE,
				ADULT_STEGOSAUR_DISPLAY, BABY_STEGOSAUR_DISPLAY,BREEDING_LEVEL, UNCONSCIOUS_LIMIT, HUNGRY_LEVEL, MAX_THIRST, STARTING_THIRST, THIRSTY_LEVEL, CORPSE_HEALTH);

	}
	/**
	 * Second overloaded constructor of the Stegosaur class.
	 * @param age the current age of the Stegosaur to be created for
	 * @param gender the gender of the Stegosaur to be created for
	 */
	public Stegosaur(int age, char gender) {
		super(SPECIES, ADULT_STEGOSAUR_DISPLAY, gender,age, MAX_HIT_POINTS,STARTING_HIT_POINTS,PREGNANT_LENGTH, ADULT_AGE,
				ADULT_STEGOSAUR_DISPLAY, BABY_STEGOSAUR_DISPLAY,BREEDING_LEVEL, UNCONSCIOUS_LIMIT, HUNGRY_LEVEL, MAX_THIRST, STARTING_THIRST, THIRSTY_LEVEL, CORPSE_HEALTH);

	}

	/**
	 * Lists all the actions that the other actor can perform on the current actor. In this case, it allows the Player
	 * to feed the dinosaur.
	 * @param otherActor the Actor that might be performing attack
	 * @param direction  String representing the direction of the other Actor
	 * @param map        current GameMap
	 * @return           actions
	 */
	public Actions getAllowableActions(Actor otherActor, String direction, GameMap map) {
		actions = new Actions();
		actions.add(new AttackAction(this));
		Player player = NeedsPlayer.retrievePlayer(map);
		if (!isNull(player)){
			for(Item item: player.getInventory()){
				if (item instanceof Fruit){
					actions.add(new FeedingAction(this,((Food)item),Fruit.getFeedPoints()));
				}else if (item instanceof VegetarianMealKit){
					actions.add(new FeedingAction(this,((Food)item), VegetarianMealKit.getFeedPoints()));
				}
			}
		}
		return actions;

	}

	/**
	 * The method that will be run every turn in order for the dinosaur to decide which action to take.
	 * @see edu.monash.fit2099.engine.Actor#playTurn(Actions, Action, GameMap, Display)
	 */
	@Override
	public Action playTurn(Actions actions, Action lastAction, GameMap map, Display display) {
		Action wander = behaviour.getAction(this, map);
		Location location = map.locationOf(this);
		super.turn(map, location);


		for (Item item: inventory){
			if (item instanceof StegosaurEgg){
				this.isPregnant = true;
				break;
			}else{
				this.isPregnant = false;
			}
		}

		if (this.isPregnant){
			if (this.pregnancyCounter < 20){
				pregnancyTurn();
			}
			else {
				this.isPregnant = false;
				LayEgg(map.locationOf(this));
				removeEgg();
				this.pregnancyCounter = 0;
			}
		}

		if (wander != null)
			return wander;

		return new DoNothingAction();
	}

	/**
	 * Specifies the different types of food the dinosaur can consume.
	 * @param food the type of food
	 * @return true if food is edible by current dinosaur
	 */
	@Override
	public boolean canEat(Food food) {
		boolean result = false;
		if (food instanceof Fruit || food instanceof VegetarianMealKit){
			result = true;
		}
		return result;
	}

	/**
	 * The eating action. If the food is a Fruit, increase hit points by 10. If the food is a meal kit, increase to max
	 * hit points.
	 * @param food the type of food
	 */
	@Override
	public void eatsFood(Food food) {
		if (food instanceof Fruit){
			heal(10);
		}else if (food instanceof VegetarianMealKit){
			heal(maxHitPoints-hitPoints);
		}
	}

	/**
	 * The drinking water action executed. This increases the dinosaur's water levels by 30 points.
	 */
	@Override
	public void drinksWater() {
		this.thirst += 30;
	}

	/**
	 * Lays an egg object on the current location of the dinosaur.
	 * @param location the location the dinosaur is current at
	 */
	public void LayEgg(Location location){
		location.addItem(new StegosaurEgg());
	}
}
