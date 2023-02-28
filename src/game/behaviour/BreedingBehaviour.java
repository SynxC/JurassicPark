package game.behaviour;

import edu.monash.fit2099.engine.*;
import game.action.BreedingAction;
import game.action.HerbivoreEatAction;
import game.actor.Dinosaur;
import game.actor.Player;
import game.actor.Pterodactyl;
import game.actor.Stegosaur;
import game.environment.Tree;
import game.interfaces.NearestTree;
import game.item.Food;

import java.util.ArrayList;
import java.util.List;

/**
 * A class that figures out the BreedingBehaviour for dinosaurs. The dinosaur that
 * is looking to mate will execute this behaviour and begin to look for potential
 * mates within the map instance. Once found, they will move over to the selected
 * partner and initiate the breeding action.
 */
public class BreedingBehaviour implements Behaviour, NearestTree {
    /**
     * This behaviour allows the dinosaurs of the same species to find a partner to breed with. If the dinosaur is
     * adjacent to a mate, it will return a BreedingAction. If the mate is out of range, the dinosaur will move towards
     * the mate. This is the same for all dinosaurs except for the Pterodactyl which has specific conditions (must be on
     * a tree to breed).
     * @param actor the Actor enacting the behaviour
     * @param map the map that actor is currently on
     * @return either a FollowBehaviour if the mate is not nearby or if the mate is within range, BreedingAction
     */
    @Override
    public Action getAction(Actor actor, GameMap map) {
        Dinosaur mate = null;
        Location actorLocation = map.locationOf(actor);
        Ground ground = actorLocation.getGround();
        ArrayList<Dinosaur> potentialMates = getMates((Dinosaur) actor, map);

        if (!actor.isConscious()){
            return new DoNothingAction();
        }

        if (potentialMates.isEmpty()) {
            return (((Dinosaur) actor).getWanderBehaviour().getAction(actor, map));
        } else {
            int shortestDistance = 9999999;
            for (int i = 0; i < potentialMates.size(); i++) {
                mate = potentialMates.get(i);
                Location mateLocation = map.locationOf(mate);
                int currentDistance = FollowBehaviour.distance(actorLocation, mateLocation);
                if (currentDistance < shortestDistance) {
                    shortestDistance = currentDistance;
                }
            }

            Location mateLocation = map.locationOf(mate);
            Location nearestTree = NearestTree.getNearestTree(mate,map);
            Ground mateGround = mateLocation.getGround();
             if (actor instanceof Pterodactyl && shortestDistance == 1) {  //breeding for Pterodactyl only
                 if (mateGround instanceof Tree && ground instanceof Tree) {
                     return new BreedingAction(mate);
                 }
                 else{
                     List<Exit> exits = nearestTree.getExits();
                     for (Exit i: exits){
                         Ground exitGround = i.getDestination().getGround();
                         if (exitGround instanceof Tree){
                             return WanderBehaviour.moveTo(actor, map, actorLocation, i.getDestination());
                         }
                     }
                 }
             }else if ( actor instanceof Pterodactyl && shortestDistance > 1 ) {
                 return WanderBehaviour.moveTo(actor, map, actorLocation, nearestTree);
             }

            if (shortestDistance > 1) {  //breeding for all dinosaurs except Pterodactyl
                return new FollowBehaviour(mate).getAction(actor, map);
            } else {
                return new BreedingAction(mate);
            }
        }}


    /**
     * This method locates the nearest dinosaurs of the same species to be mated with. The method will search
     * the entirety of the map till the mate has been located. Once the location has been found, the dinosaur added to
     * an array list to be returned.
     * @param dino  the dinosaur that's looking to mate
     * @param map   the current instance of the map
     * @return      array list of potential mates
     */
    public ArrayList<Dinosaur> getMates(Dinosaur dino, GameMap map){
        ArrayList<Dinosaur> mateList = new ArrayList<>();
        for (int x: map.getXRange()){
            for (int y: map.getYRange()){
                Location location = map.at(x,y);
                if (location.containsAnActor() && !(location.getActor() instanceof Player)){
                    Actor target = map.getActorAt(location);
                    if ((dino.isAdult() && ((Dinosaur)target).isAdult()) &&
                                (dino.getSpecies().equals(((Dinosaur)target).getSpecies())) &&
                                (dino.getGender() != ((Dinosaur) target).getGender()) && (!dino.isPregnant() &&
                                !((Dinosaur)target).isPregnant())){
                            mateList.add((Dinosaur)target);

                }}
            }
        }
        return mateList;
    }



}
