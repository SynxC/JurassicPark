package game.interfaces;

import edu.monash.fit2099.engine.Actor;
import edu.monash.fit2099.engine.GameMap;
import edu.monash.fit2099.engine.Ground;
import edu.monash.fit2099.engine.Location;
import game.behaviour.FollowBehaviour;
import game.environment.Lake;
/**
 * Interface used to retrieve the nearest Lake location.
 */
public interface NearestLake {
    /**
     *
     * This method checks the entire map and returns the location of the nearest lake relative to the actor's location.
     * @param actor the current actor.
     * @param map the map the actor is in.
     * @return location of the nearest lake to the actor
     */
    static Location getNearestLake(Actor actor, GameMap map){
        Location location = map.locationOf(actor);
        Location nearestLake = null;
        int shortestDistance =999999;
        for (int x: map.getXRange()){
            for (int y: map.getYRange()){
                Location currentLocation = map.at(x, y);
                Ground ground = currentLocation.getGround();
                if (ground instanceof Lake){
                    int currentDistance = FollowBehaviour.distance(location,currentLocation);
                    if (currentDistance < shortestDistance){
                        nearestLake = currentLocation;
                        shortestDistance = currentDistance;
                    }
                }
            }
        }

        return nearestLake;
    }
}
