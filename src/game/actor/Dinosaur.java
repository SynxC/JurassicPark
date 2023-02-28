package game.actor;
import edu.monash.fit2099.engine.*;
import game.behaviour.*;
import game.environment.Bush;
import game.environment.Dirt;
import game.environment.Tree;
import game.interfaces.NeedsPlayer;
import game.item.Corpse;
import game.item.Food;

import static game.WorldBuilder.MAPS;

/**
 * Abstract class creation of Dinosaur. Used as the template for all relation types of dinosaurs
 */
public abstract class Dinosaur extends Actor implements NeedsPlayer {
    /**
     * The threshold that indicates when the dinosaur is hungry.
     */
    final int HUNGRY_LEVEL;
    /**
     *
     */
    protected Behaviour behaviour;
    protected Actions actions;
    /**
     * String that represents the dinosaur's species. Currently, there are 4 options: "Stegosaur", "Brachiosaur",
     * "Allosaur", and "Pterodactyl".
     */
    final String SPECIES;
    /**
     * The dinosaur's current age.
     */
    int age;
    /**
     * The maximum duration of the dinosaur's pregnancy before it lays its egg.
     */
    final int PREGNANT_LENGTH;
    /**
     * The age threshold for when a dinosaur becomes an adult.
     */
    final int ADULT_AGE;
    /**
     * The display character for an adult dinosaur.
     */
    final char ADULT_DISPLAY;
    /**
     * The display character for a baby dinosaur.
     */
    final char BABY_DISPLAY;
    /**
     * Character that represents the dinosaur's gender, 'F' for female and 'M' for male.
     */
    char gender;
    /**
     * Boolean that indicates if the dinosaur is currently pregnant.
     */
    boolean isPregnant;
    /**
     * The minimum amount of hit points the dinosaur requires in order to start breeding.
     */
    final int BREEDING_LEVEL;
    /**
     * The maximum threshold for turns before the dinosaur dies.
     */
    final int UNCONSCIOUS_LIMIT;
    /**
     * Integer to keep track of hor many turns the dinosaur has been pregnant. Once a certain threshold is reached,
     * it will lay its egg.
     */
    int pregnancyCounter;
    /**
     * The number of turns a dinosaur remains unconscious. Once it reaches a certain threshold, it will die.
     */
    int deathTimer;
    /**
     * The maximum water capacity of the dinosaur.
     */
    final int MAX_THIRST;
    /**
     * The threshold that indicates when the dinosaur is thirsty.
     */
    final int THIRSTY_LEVEL;
    /**
     * Integer that represents the dinosaur's current thirst level.
     */
    int thirst;
    /**
     * The amount of hit points the corpse provides if another dinosaur were to consume it.
     */
    final int CORPSE_HEALTH;

    /**
     * Default gender probability.
     */
    private double genderProbability = 0.5;

    /**
     * First constructor of the Dinosaur class. Has multiple intake variables to help build separate dinosaur types.
     * @param species the species specified
     * @param displayChar the display char of the species
     * @param age the age
     * @param maxHitPoints the maximum hit points
     * @param hitPoints the current hit points
     * @param pregnant if the dinosaur is pregnant
     * @param adultAge the age of a fully grown dinosaur
     * @param adultDisplay the display char of the fully grown dinosaur
     * @param babyDisplay the display char of a baby dinosaur
     * @param breed the minimum hit points required for the dinosaur to breed
     * @param limit the number of a turns the dinosaur remains unconscious before it dies
     * @param hunger the minimum hit points that indicates if the dinosaur is hungry
     * @param maxThirst the maximum thirst level
     * @param thirst the current thirst level
     * @param thirstyLevel the minimum thirst level that indicates if the dinosaur is thirsty
     * @param corpse_health the hit points provided by the dinosaur's corpse
     *
     */
    public Dinosaur(String species, char displayChar, int age,int maxHitPoints,int hitPoints, int pregnant, int adultAge, char
                    adultDisplay, char babyDisplay, int breed, int limit, int hunger, int maxThirst, int thirst, int thirstyLevel, int corpse_health) {
        super(species, displayChar, hitPoints);
        double probability = Math.random();
        this.SPECIES = species;
        this.maxHitPoints = maxHitPoints;
        this.PREGNANT_LENGTH = pregnant;
        this.ADULT_AGE = adultAge;
        this.age= age;
        this.ADULT_DISPLAY = adultDisplay;
        this.BREEDING_LEVEL = breed;
        this.UNCONSCIOUS_LIMIT = limit;
        this.HUNGRY_LEVEL = hunger;
        this.MAX_THIRST = maxThirst;
        this.thirst = thirst;
        this.THIRSTY_LEVEL = thirstyLevel;
        this.CORPSE_HEALTH = corpse_health;
        this.BABY_DISPLAY = babyDisplay;
        this.pregnancyCounter = 0;

        if (age < ADULT_AGE){
            this.displayChar = BABY_DISPLAY;
        }
        behaviour = new WanderBehaviour();

        if (probability<=genderProbability){
            this.gender = 'F';
        }
        else{
            this.gender = 'M';
        }
    }

    /**
     * Second constructor of the Dinosaur class. Has multiple intake variables to help build separate dinosaur types.
     * @param species the species specified
     * @param displayChar the display char of the species
     * @param gender the gender of the dinosaur
     * @param age the age
     * @param maxHitPoints the maximum hit points
     * @param hitPoints the current hit points
     * @param pregnant if the dinosaur is pregnant
     * @param adultAge the age of a fully grown dinosaur
     * @param adultDisplay the display char of the fully grown dinosaur
     * @param babyDisplay the display char of a baby dinosaur
     * @param breed the minimum hit points required for the dinosaur to breed
     * @param limit the number of a turns the dinosaur remains unconscious before it dies
     * @param hunger the minimum hit points that indicates if the dinosaur is hungry
     * @param maxThirst the maximum thirst level
     * @param thirst the current thirst level
     * @param thirstyLevel the minimum thirst level that indicates if the dinosaur is thirsty
     * @param corpse_health the hit points provided by the dinosaur's corpse
     *
     */
    public Dinosaur(String species, char displayChar,char gender, int age,int maxHitPoints,int hitPoints, int pregnant, int adultAge,
                    char adultDisplay,char babyDisplay, int breed, int limit, int hunger, int maxThirst, int thirst, int thirstyLevel, int corpse_health) {
        super(species, displayChar, hitPoints);
        this.maxHitPoints = maxHitPoints;
        this.SPECIES = species;
        this.PREGNANT_LENGTH = pregnant;
        this.ADULT_AGE = adultAge;
        this.ADULT_DISPLAY = adultDisplay;
        this.gender = gender;
        this.age=age;
        this.pregnancyCounter = 0;
        this.deathTimer=0;
        this.BREEDING_LEVEL = breed;
        this.UNCONSCIOUS_LIMIT = limit;
        this.HUNGRY_LEVEL = hunger;
        this.MAX_THIRST = maxThirst;
        this.thirst = thirst;
        this.THIRSTY_LEVEL = thirstyLevel;
        this.CORPSE_HEALTH = corpse_health;
        this.BABY_DISPLAY = babyDisplay;
        this.pregnancyCounter = 0;

        if (age < ADULT_AGE){
            this.displayChar = BABY_DISPLAY;
        }
        behaviour = new WanderBehaviour();
    }

    /**
     * Returns the corpse's health
     * @return corpse's health
     */
    public int getCorpseHealth() {
        return CORPSE_HEALTH;
    }

    /**
     * Returns the gender
     * @return gender
     */
    public char getGender() {
        return gender;
    }

    /**
     * Death creation for dinosaurs
     * @param target represents the current dinosaur
     * @param map represents the current game map
     */
    public void Death(Dinosaur target, GameMap map, int health) {
        if (!target.isConscious()) {
            Corpse corpse = new Corpse(target.getSpecies(), map, health);
            map.locationOf(target).addItem(corpse);
            map.removeActor(target);

        }
    }

    /**
     * Checks if the dinosaur is an adult
     * @return returns true if dinosaur is an adult
     */
    public boolean isAdult(){
        boolean result = false;
        if (age >= ADULT_AGE){
            result = true;
        }
        return result;
    }

    /**
     * Returns the name of the dinosaur
     * @return name
     */
    public String getName(){
        return this.name;
    }

    /**
     * Specifies the species of the Dinosaur actor
     * @return the species of dinosaur
     */
    public String getSpecies(){
        return SPECIES;}

    /**
     * Specifies the different types of food the dinosaur can consume.
     * @param food the type of food
     * @return true if food is edible by current dinosaur
     */
    public abstract boolean canEat(Food food);

    /**
     * Checks if the current dinosaur is pregnant.
     * @return true if pregnant, false if not
     */
    public boolean isPregnant() {
        return isPregnant;
    }

    /**
     * Removes the current egg within the dinosaur inventory.
     */
    public void removeEgg(){
        for(Item item: inventory){
            if (item.toString().equals(this.getSpecies()+"Egg")){
                this.removeItemFromInventory(item);
                break;
            }
        }
    }

    /**
     * The generalized turn counter to update age and hungry values. It also sets the dinosaur's behaviour according to
     * it's current stats. If dinosaur is unconscious due to thirst, it will check every turn to see if the location experiences
     * rain.
     */
    public void turn(GameMap map, Location location){
        Age();
        increaseHunger(map);
        increaseThirst(map);
        unconsciousPeriod(map);

        if(hitPoints >= BREEDING_LEVEL && isAdult() && !isPregnant()){
            setBehaviour(new BreedingBehaviour());
        }else if (isPregnant()){
            setBehaviour(new WanderBehaviour());
        }else if (thirst < THIRSTY_LEVEL){
            setBehaviour(new ThirstyBehaviour());
        }else if (hitPoints < BREEDING_LEVEL){
            setBehaviour(new HungryBehaviour());
        }else{
            setBehaviour(new WanderBehaviour());
        }

        Ground ground = location.getGround();
        if (!isConscious() && thirst <= 0) {
            if (ground instanceof Tree && ((Tree)ground).isRaining()){
                thirst = 10;
            }
            if (ground instanceof Bush && ((Bush)ground).isRaining()){
                thirst = 10;
            }
            if (ground instanceof Dirt && ((Dirt)ground).isRaining()){
                thirst = 10;
            }
        }
    }

    /**
     * The pregnancy turn counter for keeping track and updating the pregnancy duration.
     */
    public void pregnancyTurn(){
        pregnancyCounter ++;
    }

    /**
     * The unconscious timer to determine if death is applicable.
     */
    public void deathTimerUpdate(){
        deathTimer++;
    }

    /**
     * The function to execute death if the dinosaur is unconscious for a certain amount of time.
     * @param map The current instance of the map
     */
    public void unconsciousPeriod(GameMap map){

        if (hitPoints <= 0 || thirst <=0){
            if (!isConscious() && deathTimer < 15){
                System.out.println("Dinosaur is unconscious");
                deathTimerUpdate();
            }
            else if (!isConscious() && deathTimer == 15){
                Death(this, map, CORPSE_HEALTH);
            }
        }
        else{
            this.deathTimer = 0;
        }
    }

    /**
     * Reduces the health points of a dinosaur and returns a message if the dinosaur is hungry.
     */
    public void increaseHunger(GameMap map){
        Location location = map.locationOf(this);
        if (hitPoints < HUNGRY_LEVEL){
            System.out.println(this +" at (" + location.x()+ ","+ location.y()+ ") is hungry!");
        }
        hurt(1);
    }

    /**
     * Reduces the thirst points of a dinosaur and returns a message if the dinosaur is thirsty.
     * @param map
     */
    public void increaseThirst(GameMap map){
        Location location = map.locationOf(this);
        thirst--;
        if (thirst < THIRSTY_LEVEL){
            System.out.println(this +" at (" + location.x()+ ","+ location.y()+ ") is thirsty!");
        }
    }

    /**
     * Updates the age of the dinosaur.
     */
    public void Age(){
        age++;
        if(age== ADULT_AGE){
            displayChar = ADULT_DISPLAY;
        }
    }

    /**
     * The default behaviour for dinosaurs.
     * @return the wander behaviour class execution
     */
    public WanderBehaviour getWanderBehaviour(){
        return new WanderBehaviour();
    }

    /**
     * Returns the current behaviour of the dinosaur.
     * @return the behaviour
     */
    public Behaviour getBehaviour() {
        return behaviour;
    }

    /**
     * Sets the current behaviour of the dinosaur.
     * @param behaviour the behaviour
     */
    public void setBehaviour(Behaviour behaviour) {
        this.behaviour = behaviour;
    }

    /**
     * The eating action that increases hit points accordingly.
     * @param food the type of food
     */
    public abstract void eatsFood(Food food);

    /**
     * The drinking action that increases thirst points accordingly.
     */
    public abstract void drinksWater();

    /**
     * Returns the current hit points of the dinosaur.
     * @return the current hit points
     */
    public int getHitPoints(){
        return this.hitPoints;
    }

    /**
     * Method that allows the dinosaur to lay its egg at a specific location.
     * @param location the location to lay the egg
     */
    public abstract void LayEgg(Location location);


}
