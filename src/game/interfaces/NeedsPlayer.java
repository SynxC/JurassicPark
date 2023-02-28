package game.interfaces;

import edu.monash.fit2099.engine.Actor;
import edu.monash.fit2099.engine.GameMap;
import edu.monash.fit2099.engine.Location;
import game.actor.Player;

public interface NeedsPlayer {
    /**
     * An interface that is used to return the Player object.
     * @param map where the player is located
     * @return PLayer instance
     */
    static Player retrievePlayer(GameMap map){
        for (int x: map.getXRange()){
            for (int y:map.getYRange()){
                Location location = map.at(x,y);
                Actor actor = map.getActorAt(location);
                if (actor instanceof Player){
                    return (Player) actor;
                }
            }
        }
        return null;
    }
}
