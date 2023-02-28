package game.actor;

import edu.monash.fit2099.engine.*;
import game.GameDriver;
import game.WorldBuilder;
import game.action.GameCompletionAction;
import game.action.GameQuitAction;
import game.action.PickFruitAction;
import game.environment.Bush;
import game.environment.Tree;

/**
 * Class representing the Player.
 */
public class Player extends Actor {
	private Menu menu = new Menu();
	/**
	 * The total EcoPoints the player possesses. Static variable.
	 */
	public static int EcoPointStorage;

	/**
	 * The current turn the player game instance is at. Static variable.
	 */
	public static int TurnCounter;

	/**
	 * The current status of challenge mode.  Static variable.
	 */
	public static boolean ChallengeOver = false;

	/**
	 * The current status of challenge mode. Static variable.
	 */
	public static boolean ChallengeCompleted = false;

	/**
	 * Constructor.
	 *
	 * @param name        Name to call the player in the UI
	 * @param displayChar Character to represent the player in the UI
	 * @param hitPoints   Player's starting number of hitpoints
	 */
	public Player(String name, char displayChar, int hitPoints) {
		super(name, displayChar, hitPoints);
		EcoPointStorage = 0;
		TurnCounter = 0;
	}

	/**
	 * The method that will be run every turn in order for the player to decide which action to take,
	 * based on selected inputs.
	 * @see edu.monash.fit2099.engine.Actor#playTurn(Actions, Action, GameMap, Display)
	 */
	@Override
	public Action playTurn(Actions actions, Action lastAction, GameMap map, Display display) {
		//Updating the current game turn
		TurnCounter++;
		System.out.println("Current Turn: " + TurnCounter);

		//Adding quit option for user
		actions.add(new GameQuitAction("0"));

		//Challenge Mode game ending sequences
		if (GameDriver.isChallengeMode()){
			if (TurnCounter >= GameDriver.getChallengeTurn()){
				if (EcoPointStorage < GameDriver.getChallengeEcoPoints()){
					map.removeActor(this);
					ChallengeOver = true;
					return new GameCompletionAction();
				}
			}
			else {
				if (EcoPointStorage >= GameDriver.getChallengeEcoPoints()){
					map.removeActor(this);
					ChallengeOver = true;
					ChallengeCompleted = true;
					return new GameCompletionAction();
				}
			}
		}

		// Handle multi-turn Actions
		if (lastAction.getNextAction() != null)
			return lastAction.getNextAction();
		Location location = map.locationOf(this);
		Ground ground = location.getGround();
		if (ground instanceof Tree || ground instanceof Bush){
			actions.add(new PickFruitAction());
		}

		//Relocating player between 2 instances of maps.
		Location original_map_north = WorldBuilder.MAPS.get(0).at(location.x(), 0);
		Location new_map_south = WorldBuilder.MAPS.get(1).at(location.x(), map.getYRange().max());

		if (location.y() == 0 && map == WorldBuilder.MAPS.get(0)){
			actions.add(new MoveActorAction(new_map_south, "North", "8"));
		}
		else if (location.y() == map.getYRange().max() && map == WorldBuilder.MAPS.get(1)){
			actions.add(new MoveActorAction(original_map_north, "South", "2"));
		}

		return menu.showMenu(this, actions, display);
	}

	/**
	 * Getter method to obtain the current eco points the player is holding
	 * @return The total eco points the player is holding
	 */
	public int getEcoPointStorage(){
		return EcoPointStorage;
	}

	/**
	 * Removes the amount of eco points specified.
	 * @param remove the int value to be removed for
	 */
	public void removeEcoPoints(int remove){
		EcoPointStorage = EcoPointStorage - remove;
	}

	/**
	 * Updating the eco points.
	 * @param EcoPoints the ecopoints to be updated for
	 */
	public static void updateEcoPoints(int EcoPoints){
		EcoPointStorage += EcoPoints;
	}

	/**
	 * Getter method which signifies if the challenge mode is over
	 * @return True if the game has ended. False if not.
	 */
	public boolean isChallengeOver(){
		return ChallengeOver;
	}

	/**
	 * Getter method which signifies if the challenge mode has been completed.
	 * @return True if the game has ended. False if not.
	 */
	public boolean isChallengeCompleted(){
		return ChallengeCompleted;
	}

}
