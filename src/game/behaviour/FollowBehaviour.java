package game.behaviour;

import edu.monash.fit2099.engine.*;
import game.actor.Dinosaur;
import game.behaviour.Behaviour;

/**
 * A class that figures out a MoveAction that will move the actor one step 
 * closer to a target Actor.
 */
public class FollowBehaviour implements Behaviour {

	private Actor target;

	/**
	 * Constructor for the FollowBehaviour class.
	 * 
	 * @param subject the Actor to follow
	 */
	public FollowBehaviour(Actor subject) {
		this.target = subject;
	}

	/**
	 * This behaviour allows the actor to locate a target, and move over to the target's location.
	 * @param actor the Actor enacting the behaviour
	 * @param map the map that actor is currently on
	 * @return MoveActorAction, which executes the move order or null if not found
	 */
	@Override
	public Action getAction(Actor actor, GameMap map) {
		if(!map.contains(target) || !map.contains(actor))
			return null;
		
		Location here = map.locationOf(actor);
		Location there = map.locationOf(target);

		int currentDistance = distance(here, there);
		for (Exit exit : here.getExits()) {
			Location destination = exit.getDestination();
			if (destination.canActorEnter(actor)) {
				int newDistance = distance(destination, there);
				if (newDistance < currentDistance) {
					return new MoveActorAction(destination, exit.getName());
				}
			}
		}

		return null;
	}


	/**
	 * Compute the Manhattan distance between two locations.
	 * 
	 * @param a the first location
	 * @param b the final location
	 * @return the number of steps between a and b if you only move in the four cardinal directions.
	 */
	public static int distance(Location a, Location b) {
		return Math.abs(a.x() - b.x()) + Math.abs(a.y() - b.y());
	}
}