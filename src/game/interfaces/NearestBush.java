package game.interfaces;

import edu.monash.fit2099.engine.Actor;
import edu.monash.fit2099.engine.GameMap;
import edu.monash.fit2099.engine.Ground;
import edu.monash.fit2099.engine.Location;
import game.behaviour.FollowBehaviour;
import game.environment.Bush;

/**
 * Interface used to retrieve the nearest Bush location.
 */
public interface NearestBush {


    /**
     * This method locates the nearest bush object relative to the current actor's position and returns its location.
     * @param actor the current selected actor
     * @param map the current instance of the map
     * @return the nearest bush object's location within search radius
     */
    static Location getNearestBush(Actor actor, GameMap map){
        Location location = map.locationOf(actor);
        Location nearestBush = null;
        int shortestDistance = 999999;
        for (int x: map.getXRange()){
            for (int y: map.getYRange()){
                Location currentLocation = map.at(x,y);
                Ground ground = currentLocation.getGround();
                if (ground instanceof Bush){
                    int currentDistance = FollowBehaviour.distance(location,currentLocation);
                    if (currentDistance < shortestDistance){
                        nearestBush = currentLocation;
                        shortestDistance = currentDistance;
                    }
                }
            }
        }
        return nearestBush;
    }

}
