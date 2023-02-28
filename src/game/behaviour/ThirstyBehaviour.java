package game.behaviour;

import edu.monash.fit2099.engine.*;
import game.action.DrinkAction;
import game.interfaces.NearestLake;

import java.util.List;

/**
 * A behaviour class that is called when a Dinosaur gets thirsty. It allows the dinosaur to actively search for a lake
 * to drink from.
 */
public class ThirstyBehaviour implements Behaviour, NearestLake{
    /**
     * This behaviour will return a DrinkAction if the dinosaur is adjacent to a lake and if the lake has available sips.
     * If it is not, it will search the map for the nearest lake and return a moveTo action to move the dinosaur towards it.
     * @param actor the Actor acting
     * @param map the GameMap containing the Actor
     * @return either a DrinkAction or a moveTo action
     */
    @Override
    public Action getAction(Actor actor, GameMap map) {
        Location location = map.locationOf(actor);
        Location nearLake = NearestLake.getNearestLake(actor,map);

        if (!actor.isConscious()){
            return new DoNothingAction();
        }

        if (nearLake != null){
            List<Exit> exitList = map.locationOf(actor).getExits();
            if (exitList.contains(nearLake)){
                return new DrinkAction(nearLake);
            }else{
                return WanderBehaviour.moveTo(actor, map,location, nearLake);
            }
        }
        return null;
    }

}
