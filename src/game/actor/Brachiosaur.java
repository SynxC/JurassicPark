package game.actor;

import edu.monash.fit2099.engine.*;
import game.action.AttackAction;
import game.action.FeedingAction;
import game.behaviour.BreedingBehaviour;
import game.behaviour.WanderBehaviour;
import game.environment.Bush;
import game.environment.Dirt;
import game.interfaces.NeedsPlayer;
import game.item.*;

import static java.util.Objects.isNull;

/**
 * Class creation for Brachiosaur, a herbivorous dinosaur.
 */
public class Brachiosaur extends Dinosaur {
    /**
     * String that represents the dinosaur's species.
     */
    static final String SPECIES = "Brachiosaur";
    /**
     * The age threshold that indicates the adult age for the dinosaur.
     */
    static final int ADULT_AGE = 50;
    /**
     * The maximum hit points for the dinosaur.
     */
    static final int MAX_HIT_POINTS=160;
    /**
     * The hit points threshold that indicates if the dinosaur is hungry.
     */
    static final int HUNGRY_LEVEL = 140;
    /**
     * The duration of the dinosaur's pregnancy. It is the maximum number of turns before the dinosaur lays its egg.
     */
    static final int PREGNANT_LENGTH = 30;
    /**
     * The display character for a baby dinosaur.
     */
    static final char BABY_BRACHIOSAUR_DISPLAY = 'b';
    /**
     * The display character for an adult dinosaur.
     */
    static final char ADULT_BRACHIOSAUR_DISPLAY = 'B';
    /**
     * The minimum hit points required for a dinosaur to breed.
     */
    static final int BREEDING_LEVEL =70;
    /**
     * The number of turns a dinosaur can remain unconscious before it dies.
     */
    static final int UNCONSCIOUS_LIMIT =15;
    /**
     * The maximum thirst level for a dinosaur.
     */
    static final int MAX_THIRST = 200;
    /**
     * The starting values for the thirst level. Used when spawning a dinosaur.
     */
    static final int STARTING_THIRST = 120;
    /**
     * The thirst level threshold that indicates if a dinosaur is thirsty.
     */
    static final int THIRSTY_LEVEL = 80;
    /**
     * The amount of hit points the dinosaur's corpse provides when it dies.
     */
    static final int CORPSE_HEALTH = 100;
    /**
     * The starting values for the hit points. Used when spawning a dinosaur.
     */
    static final int STARTING_HIT_POINTS = 100;

    /**
     * First overloaded constructor of the Brachiosaur class. All Brachiosaur have 160 hit points. The baby
     * Brachiosaur will have 'b' as its display and the adult will have 'B'.
     * @param age the current age of the Brachiosaur to be created for
     */
    public Brachiosaur(int age) {
        super(SPECIES, ADULT_BRACHIOSAUR_DISPLAY, age, MAX_HIT_POINTS,STARTING_HIT_POINTS,PREGNANT_LENGTH, ADULT_AGE,
                ADULT_BRACHIOSAUR_DISPLAY, BABY_BRACHIOSAUR_DISPLAY,BREEDING_LEVEL, UNCONSCIOUS_LIMIT,HUNGRY_LEVEL, MAX_THIRST, STARTING_THIRST, THIRSTY_LEVEL, CORPSE_HEALTH);

    }

    /**
     * Second overloaded constructor of the Brachiosaur class.
     * @param age the current age of the Brachiosaur to be created for
     * @param gender the gender of the Brachiosaur to be created for
     */
    public Brachiosaur(int age, char gender) {
        super(SPECIES, ADULT_BRACHIOSAUR_DISPLAY, gender,age, MAX_HIT_POINTS,STARTING_HIT_POINTS,PREGNANT_LENGTH, ADULT_AGE,ADULT_BRACHIOSAUR_DISPLAY,
                BABY_BRACHIOSAUR_DISPLAY,BREEDING_LEVEL, UNCONSCIOUS_LIMIT,HUNGRY_LEVEL, MAX_THIRST, STARTING_THIRST, THIRSTY_LEVEL, CORPSE_HEALTH);

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
     * Figure out what to do next.
     * @see edu.monash.fit2099.engine.Actor#playTurn(Actions, Action, GameMap, Display)
     */
    @Override
    public Action playTurn(Actions actions, Action lastAction, GameMap map, Display display) {
        Location location = map.locationOf(this);
        Ground ground = location.getGround();
        if (ground instanceof Bush){
            double killBushChance = Math.random();
            if (killBushChance > 0.5){
                Dirt dirt = new Dirt();
                location.setGround(dirt);
            }
        }


        Action wander = behaviour.getAction(this, map);
        super.turn(map, location);
        for (Item item: inventory){
            if (item instanceof BrachiosaurEgg){
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
     * The eating action. If the food is a Fruit, increase hit points by 5. If the food is a meal kit, increase to max
     * hit points.
     * @param food the type of food
     */
    @Override
    public void eatsFood(Food food) {
        if (food instanceof Fruit){
            heal(5);
        }else if(food instanceof VegetarianMealKit){
            heal(maxHitPoints-hitPoints);
        }
    }

    /**
     * The drinking water action executed. This increases the dinosaur's water levels by 80 points.
     */
    @Override
    public void drinksWater() {
        this.thirst += 80;
    }

    /**
     * Lays an egg object on the current location of the dinosaur.
     * @param location the location the dinosaur is current at
     */
    public void LayEgg(Location location){
        location.addItem(new BrachiosaurEgg());
    }
}
