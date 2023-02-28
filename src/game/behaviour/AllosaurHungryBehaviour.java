package game.behaviour;

import edu.monash.fit2099.engine.*;
import game.action.AllosaurAttackAction;
import game.action.AttackAction;
import game.action.CarnivoreEatAction;
import game.actor.Allosaur;
import game.actor.Dinosaur;
import game.actor.Pterodactyl;
import game.actor.Stegosaur;
import game.item.Food;

import java.util.List;

/**
 * A class that figures out the AllosaurAttackBehaviour for allosaurs. Allosaurs will continuously look
 * for preys to feast upon and move to their respective locations. The allosaur can only attack a specified
 * target once before it goes into a cooldown. At the instance, it will locate another target within range to
 * move towards. If no target is found, it defaults back to its wander behaviour.
 */
public class AllosaurHungryBehaviour implements Behaviour{
    static final int RADIUS = 50;

    /**
     * This behaviour allows the Allosaur to find and attack or consume its food source.
     * It checks if the current location has a food source,
     * and returns an action to indicate either at the
     * location it was the dinosaur or was it a corpse
     * @param actor the Actor enacting the behaviour
     * @param map the map that actor is currently on
     * @return CarnivoreEatAction if the a corpse is found, AllosaurAttackAction if a stegosaur was found
     */
    @Override
    public Action getAction(Actor actor, GameMap map) {
        Location actorLocation = map.locationOf(actor);
        List<Item> itemsHere = actorLocation.getItems();

        if (!actor.isConscious()){
            return new DoNothingAction();
        }

        if (!itemsHere.isEmpty()) {
            for (Item item : itemsHere) {
                if (item instanceof Food){
                    if (((Dinosaur)actor).canEat((Food)item)){
                        return new CarnivoreEatAction((Food)item);
                }}
            }}

        List<Exit> exitList = map.locationOf(actor).getExits();
        Actor nearbyTarget = null;
        Action move = null;
        for (Exit exit: exitList){
            Location location = exit.getDestination();
            if (location.containsAnActor()){
                Actor currentTarget = location.getActor();
                if (currentTarget instanceof Stegosaur){
                    if (!((Allosaur)actor).getAttacked_dinosaur().contains(currentTarget)){
                        nearbyTarget = currentTarget;
                        break;
                    }
                }
            }
        }
        if (nearbyTarget != null){
            return new AllosaurAttackAction((Dinosaur)nearbyTarget);
        }

        Dinosaur target = findTargetInRadius((Dinosaur)actor,map);
        if (target != null){
            return new FollowBehaviour(target).getAction(actor, map);
        }

        for (Exit exit: exitList){
            Location goTowards = exit.getDestination();
            if (goTowards.canActorEnter(actor)){
                 move = exit.getDestination().getMoveAction(actor, "around", exit.getHotKey());
                 break;
            }
        }
        return move;
    }

    /**
     * This method locates the nearest target (stegosaur) and provides the locational details on
     * where the target is currently at. It returns the target that is the closest in proximity to
     * the current allosaur actor.
     * @param actor
     * @param map
     * @return
     */
    public Dinosaur findTargetInRadius(Dinosaur actor, GameMap map){
        Location location = map.locationOf(actor);
        Dinosaur closestTarget = null;
        int shortestDistance = RADIUS;
        int targetDistance;

        for(int x: map.getXRange()){
            for(int y: map.getYRange()){
                Location targetLocation = map.at(x,y);
                if (targetLocation.containsAnActor() && targetLocation.getActor() instanceof Stegosaur) {
                    targetDistance = FollowBehaviour.distance(location, targetLocation);
                    Dinosaur target = (Dinosaur) targetLocation.getActor();
                    if (target instanceof Stegosaur && !((Allosaur)actor).getAttacked_dinosaur().contains(target)){
                        if (targetDistance < shortestDistance) {
                            shortestDistance = targetDistance;
                            closestTarget = target;
                        }
                }
                }
                if (targetLocation.containsAnActor() && targetLocation.getActor() instanceof Pterodactyl){
                    if (((Pterodactyl)actor).isOnGround()){
                        targetDistance = FollowBehaviour.distance(location, targetLocation);
                        Dinosaur target = (Dinosaur) targetLocation.getActor();
                        if (targetDistance < shortestDistance) {
                            shortestDistance = targetDistance;
                            closestTarget = target;
                        }
                    }
                }
            }
        }
        return closestTarget;
    }
}
