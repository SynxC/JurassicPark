package game.action;

import edu.monash.fit2099.engine.*;
import game.actor.Dinosaur;
import game.environment.Lake;


/**
 * An action class that is used for Dinosaurs to drink from lakes.
 * @see Dinosaur#drinksWater()
 * @see Lake#getSips()
 * @see Lake#reduceSip()
 */
public class DrinkAction extends Action {
    /**
     * Location of the lake
     */
    private Location lakeLocation;
    public DrinkAction(Location location){
        lakeLocation = location;
    }

    /**
     * Method used to execute the drink action. It uses the drinksWater() method from the Dinosaur class to increase
     * their water level, and uses the reduceSip() method from the Lake class to reduce the lake's available sips. It
     * returns an error message if there are no sips available in the lake.
     * @param actor The actor performing the action.
     * @param map The map the actor is on.
     * @return String that shows the action has been executed
     */
    @Override
    public String execute(Actor actor, GameMap map) {
        Ground lakeGround = lakeLocation.getGround();
        if (((Lake)lakeGround).getSips() < 0){
            return actor + " cannot drink because lake is empty!";
        }else {
            ((Lake) lakeGround).reduceSip();
            ((Dinosaur) actor).drinksWater();
            return menuDescription(actor);
        }
    }
    /**
     * Returns a descriptive string
     * @param actor The actor performing the action.
     * @return the text we put on the menu
     */
    @Override
    public String menuDescription(Actor actor) {
        return actor +" drank water!";
    }
}
