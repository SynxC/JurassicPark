package game.action;

import edu.monash.fit2099.engine.*;
import game.actor.Dinosaur;
import game.actor.Pterodactyl;
import game.item.Corpse;
import game.item.Egg;
import game.item.Food;

/**
 * A class that tackles the CarnivoreEatAction for carnivorous dinosaurs. Corpses are currently the only
 * non-vegetative items that could be found on the map naturally. Excludes the CarnivoreMealKit found in vending
 * machines
 */
public class CarnivoreEatAction extends Action {
    /**
     * The food to be consumed.
     */
    private Food food;

    /**
     * The constructor for the CarnivoreEatAction class.
     * @param food the type of food the current actor is trying to eat.
     */
    public CarnivoreEatAction(Food food){
        this.food = food;
    }

    /**
     * This method is used to execute the eat action on the target. Carnivores can only take in foods that are non
     * vegetative. The method will check if the food object is of a specified species, adjusting the different
     * food levels for each unique object.
     * @param actor The actor performing the action.
     * @param map The map the actor is on.
     * @return a description of what happened that can be displayed to the user.
     */
    @Override
    public String execute(Actor actor, GameMap map) {
        if (this.food instanceof Corpse){
            if (actor instanceof Pterodactyl){
                int damage = 10;
                actor.heal(damage);
                ((Corpse) this.food).deductHealth(damage);
                if (((Corpse) this.food).getHealth() <= 0){
                    ((Corpse) this.food).removeCorpse();
                }
            }
            else {
                int points = ((Corpse) this.food ).getHealth();
                actor.heal(points);
                ((Corpse) this.food).removeCorpse();
            }
        }
        else if (this.food instanceof Egg){
            ((Dinosaur)actor).eatsFood(this.food);
            map.locationOf(actor).removeItem(this.food);
        }
        return menuDescription(actor);
    }

    /**
     * Returns a descriptive string
     * @param actor The actor performing the action.
     * @return the text we put on the menu
     */
    @Override
    public String menuDescription(Actor actor) {
        return actor + " ate " + this.food;
    }
}
