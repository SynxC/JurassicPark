package game.behaviour;

import edu.monash.fit2099.engine.*;
import game.action.CarnivoreEatAction;
import game.actor.Pterodactyl;
import game.environment.Tree;

import game.interfaces.NearestTree;
import game.item.Food;

import java.util.List;
import java.util.Random;

/**
 * Behaviour class specifically for Pterodactyl as it handles flying, and landing on trees.
 */
public class PterodactylBehaviour implements Behaviour, NearestTree {
    private Random random = new Random();


    private int flyDuration;

    /**
     * This behaviour retrieves the Pterodactyl's current flyDuration and checks if it is within the threshold. If it surpasses
     * it, the dinosaur will land on the ground and search for the nearest tree in order to reset its flyDuration. However,
     * if the dinosaur is flying and the tile it is on contains a food source, it will land on the ground and return an
     * Eat Action.
     * @param actor the Actor acting
     * @param map the GameMap containing the Actor
     * @return the relevant action
     */
    @Override
    public Action getAction(Actor actor, GameMap map) {
        flyDuration = ((Pterodactyl) actor).getFlyDuration();
        Location location = map.locationOf(actor);
        Ground ground = location.getGround();
        List<Item> itemsHere = location.getItems();

        if (!actor.isConscious()){
            return new DoNothingAction();
        }

        for (Item item : itemsHere) {
            if (item instanceof Food) {
                if (((Pterodactyl) actor).canEat((Food) item)) {
                    ((Pterodactyl) actor).setOnGround(true);
                    return new CarnivoreEatAction((Food) item);
                }
            }
        }

        Location nearTree = NearestTree.getNearestTree(actor, map);
        if (ground instanceof Tree) {
            ((Pterodactyl) actor).resetFlyDuration();
            ((Pterodactyl) actor).setOnGround(false);
        } else {
            if (flyDuration >= 30) {
                ((Pterodactyl) actor).setOnGround(true);
                return WanderBehaviour.moveTo(actor, map, location, nearTree);
            }
        }

        return WanderBehaviour.moveTo(actor, map, location, nearTree);

    }

}
