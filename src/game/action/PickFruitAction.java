package game.action;

import edu.monash.fit2099.engine.*;
import game.actor.Player;
import game.environment.Bush;
import game.environment.Tree;
import game.item.Fruit;

/**
 * A class that tackles the PickFruitAction for players to pluck fruits from bushes or trees.
 * There is a set probability that a fruit could be pluck from the object if the player is at its location.
 */
public class PickFruitAction extends Action {
    /**
     * The probability of a fruit could be plucked from a tree or bush object.
     */
    private final double FRUIT_PROBABILITY = 0.6;

    /**
     * This method is used to execute the pick fruit action. The method checks for the location of the actor,
     * and if actor is in the premise of either a tree or a bush, there will be a probability a fruit could
     * be picked from either object.
     * @param actor The actor performing the action.
     * @param map The map the actor is on.
     * @return a description of what happened that can be displayed to the user.
     */
    @Override
    public String execute(Actor actor, GameMap map) {
        Location location = map.locationOf(actor);
        Ground ground = location.getGround();

        String output;
        //The random probability
        double probability = Math.random();
        if (probability <= FRUIT_PROBABILITY) {
            actor.addItemToInventory(new Fruit());
            Player.updateEcoPoints(10);
            if (ground instanceof Tree){
                ((Tree)ground).removeFruit();
            }else if (ground instanceof Bush){
                ((Bush)ground).removeFruit();
            }
            output = "Fruit collected!";
        }else{
            output = "Player search the tree or bush for fruit, but you can't find any ripe ones.";
        }
        return output;
    }

    /**
     * Returns a descriptive string
     * @param actor The actor performing the action.
     * @return the text we put on the menu
     */
    @Override
    public String menuDescription(Actor actor) {
        return "Player pick Fruit from Tree/Bush";
    }
}
