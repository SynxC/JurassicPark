package game.action;

import edu.monash.fit2099.engine.Action;
import edu.monash.fit2099.engine.Actor;
import edu.monash.fit2099.engine.GameMap;
import game.actor.Player;

/**
 * A class that tackles the GameCompletion sequencing for the Player. When the game ends, the class will receive
 * a boolean parameter from the Player class indicating if the Player has won or lost the game. This action
 * class only applies for the challenge mode settings as the sandbox variant has no winning condition.
 */
public class GameCompletionAction extends Action {
    /**
     * This method is used to execute the GameCompletion action on the actor (Player).
     * The action returns a string message indicating the status of his completion (either a win or a lost).
     * @param actor The actor performing the action.
     * @param map The map the actor is on.
     * @return a description of what happened that can be displayed to the user.
     */
    @Override
    public String execute(Actor actor, GameMap map) {
        String final_statement;
        if(((Player) actor).isChallengeCompleted()){
            final_statement = "Congratulations! You just completed the game in challenge mode!";
        }
        else{
            final_statement = "You failed to achieve the challenge!";
        }
        return final_statement;
    }

    /**
     * Returns a descriptive string
     * @param actor The actor performing the action.
     * @return the text we put on the menu
     */
    @Override
    public String menuDescription(Actor actor) {
        return null;
    }
}
