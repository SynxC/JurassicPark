package game.actor;

import edu.monash.fit2099.engine.*;
import game.action.FeedingAction;
import game.behaviour.AllosaurHungryBehaviour;
import game.behaviour.WanderBehaviour;
import game.action.AttackAction;
import game.interfaces.NeedsPlayer;
import game.item.*;

import java.util.ArrayList;

import static java.util.Objects.isNull;

/**
 * Class creation for the Allosaur, a carnivorous dinosaur.
 */
public class Allosaur extends Dinosaur {
    /**
     * String that represents the dinosaur's species.
     */
    static final String SPECIES = "Allosaur";
    /**
     * The age threshold that indicates the adult age for the dinosaur.
     */
    static final int ADULT_AGE = 50;
    /**
     * The maximum hit points for the dinosaur.
     */
    static final int MAX_HIT_POINTS = 100;
    /**
     * The hit points threshold that indicates if the dinosaur is hungry.
     */
    static final int HUNGRY_LEVEL = 80;
    /**
     * The duration of the dinosaur's pregnancy. It is the maximum number of turns before the dinosaur lays its egg.
     */
    static final int PREGNANT_LENGTH = 20;
    /**
     * The display character for a baby dinosaur.
     */
    static final char BABY_ALLOSAUR_DISPLAY = 'a';
    /**
     * The display character for an adult dinosaur.
     */
    static final char ADULT_ALLOSAUR_DISPLAY = 'A';
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
     * Array list to keep track of the different dinosaurs attacked.
     */
    private ArrayList<Dinosaur> attacked_dinosaur = new ArrayList<>();
    /**
     * Array list to keep track of the timer for each attacked dinosaur object.
     */
    private ArrayList<Integer> attacked_dinosaur_count = new ArrayList<>();

    /**
     * First overloaded constructor of the Allosaur class. All Allosaur have 100 hit points. The baby
     * Allosaur will have 'a' as its display and the adult will have 'A'.
     * @param age the current age of the allosaur to be created for
     */
    public Allosaur(int age) {
        super(SPECIES, ADULT_ALLOSAUR_DISPLAY, age, MAX_HIT_POINTS, STARTING_HIT_POINTS,PREGNANT_LENGTH,ADULT_AGE,
                ADULT_ALLOSAUR_DISPLAY, BABY_ALLOSAUR_DISPLAY,BREEDING_LEVEL, UNCONSCIOUS_LIMIT,HUNGRY_LEVEL, MAX_THIRST, STARTING_THIRST, THIRSTY_LEVEL, CORPSE_HEALTH) ;

    }

    /**
     * Second overloaded constructor of the Allosaur class.
     * @param age the current age of the allosaur to be created for
     * @param gender the gender of the allosaur to be created for
     */
    public Allosaur(int age, char gender) {
        super(SPECIES, ADULT_ALLOSAUR_DISPLAY,gender, age, MAX_HIT_POINTS, STARTING_HIT_POINTS,PREGNANT_LENGTH,ADULT_AGE,
                ADULT_ALLOSAUR_DISPLAY, BABY_ALLOSAUR_DISPLAY,BREEDING_LEVEL, UNCONSCIOUS_LIMIT,HUNGRY_LEVEL,MAX_THIRST, STARTING_THIRST,THIRSTY_LEVEL, CORPSE_HEALTH);

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
                if (item instanceof Egg){
                    actions.add(new FeedingAction(this,((Food)item), Egg.getFeedPoints()));
                }else if (item instanceof CarnivoreMealKit){
                    actions.add(new FeedingAction(this,((Food)item), CarnivoreMealKit.getFeedPoints()));
                }
            }
        }
        return actions;

    }

    /**
     * The eating action. If the food is an Egg, increase hit points by 10, and if it is a Corpse, increase by the corpse's
     * available hit points. If the food is a meal kit, increase to max hit points.
     * @param food the type of food
     */
    @Override
    public void eatsFood(Food food) {
        if (food instanceof Egg){
            heal(10);
        }else if (food instanceof Corpse){
            heal(((Corpse)food).getHealth());
        }else if (food instanceof CarnivoreMealKit){
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
     * The method that will be run every turn in order for the dinosaur to decide which action to take.
     * @see edu.monash.fit2099.engine.Actor#playTurn(Actions, Action, GameMap, Display)
     */
    @Override
    public Action playTurn(Actions actions, Action lastAction, GameMap map, Display display) {
        Action wander = behaviour.getAction(this, map);
        Location location = map.locationOf(this);
        super.turn(map, location);
        updateAttackedDinosaurCount();
        updateAttackDinosaur();

        for (Item item: inventory){
            if (item instanceof AllosaurEgg){
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
        if(hitPoints <= HUNGRY_LEVEL){
            setBehaviour(new AllosaurHungryBehaviour());
        }
        else{
            setBehaviour(new WanderBehaviour());
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
        if (food instanceof Egg || food instanceof CarnivoreMealKit || food instanceof Corpse){
            result = true;
        }
        return result;
    }

    /**
     * Lays an egg object on the current location of the dinosaur.
     * @param location the location the dinosaur is current at
     */
    public void LayEgg(Location location){
        location.addItem(new AllosaurEgg());
    }

    /**
     * Adding the dinosaurs that had been attacked to the list.
     * @param dinosaur the target attacked dinosaur to be added
     */
    public void setAttacked_dinosaur(Dinosaur dinosaur){
        this.attacked_dinosaur.add(dinosaur);
    }

    /**
     * Returns the list of dinosaurs attacked by the current dinsaur.
     * @return the list of dinosaurs attacked
     */
    public ArrayList<Dinosaur> getAttacked_dinosaur(){
        return this.attacked_dinosaur;
    }

    /**
     * Adding the count to the dinosaurs that had been attacked.
     * @param count the count value
     */
    public void setAttacked_dinosaur_count(int count){
        this.attacked_dinosaur_count.add(count);
    }

    /**
     * Returns the timer of each attacked dinosaur
     * @return the timer for each attacked dinosaur
     */
    public ArrayList<Integer> getAttacked_dinosaur_count(){
        return this.attacked_dinosaur_count;
    }

    /**
     * Updates the timer for every attacked dinosaur.
     */
    public void updateAttackedDinosaurCount(){
        for (int i=0; i < attacked_dinosaur_count.size(); i++){
            int currentValue = attacked_dinosaur_count.get(i);
            currentValue ++;
            attacked_dinosaur_count.set(i, currentValue);
        }
    }

    /**
     * Removes the attacked dinosaur from the list if the cooldown is fulfilled.
     */
    public void updateAttackDinosaur() {
        int count = 0;
        while (count < attacked_dinosaur_count.size()){
            int currentValue = attacked_dinosaur_count.get(count);
            if (currentValue == 20){
                attacked_dinosaur.remove(count);
                attacked_dinosaur_count.remove(count);
                count--;
            }
            count++;
        }
    }
}
