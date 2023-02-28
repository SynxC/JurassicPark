package game.action;

import edu.monash.fit2099.engine.Action;
import edu.monash.fit2099.engine.Actor;
import edu.monash.fit2099.engine.GameMap;
import game.actor.*;
import game.item.CarnivoreMealKit;
import game.item.Food;
import game.item.Fruit;
import game.item.VegetarianMealKit;

/**
 * A class that tackles the FeedingAction from the player for dinosaurs. Items could be fed are located in
 * player's inventory and is consumed when used. Items could be found on the ground, e.g fruits or purchased
 * from the vending machine.
 */
public class FeedingAction extends Action {
    /**
     * The target of the dinosaur to be interacted with.
     */
    protected Dinosaur target;
    /**
     * The chosen food to feed the dinosaur.
     */
    protected Food food;
    /**
     * The amount of hit points the food can restore.
     */
    protected int foodPoints;

    /**
     * The constructor for FeedingAction class.
     * @param target        the target specifies the dinosaur the actor (Player) is trying to interact with.
     * @param food          the type of food the current actor is trying to eat.
     * @param foodPoints    the total hungry restoration value the food holds.
     */
    public FeedingAction(Dinosaur target, Food food, int foodPoints){
        this.foodPoints = foodPoints;
        this.target = target;
        this.food = food;

    }

    /**
     * This method is used to execute the feeding action on the target. The food is fed to the target and removed from
     * the current player's inventory.
     * @param actor The actor performing the action.
     * @param map The map the actor is on.
     * @return a description of what happened that can be displayed to the user.
     */
    @Override
    public String execute(Actor actor, GameMap map) {
        actor.removeItemFromInventory(food);
        target.heal(foodPoints);
        if (food instanceof Fruit){
            Player.updateEcoPoints(10);
        }
        return "Player fed " + food + " to "+ target;
    }

    /**
     * Returns a descriptive string
     * @param actor The actor performing the action.
     * @return the text we put on the menu
     */
    @Override
    public String menuDescription(Actor actor) {
        return "Player feed " +food+" to "+target;
    }
}
