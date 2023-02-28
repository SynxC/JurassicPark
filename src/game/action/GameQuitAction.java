package game.action;

import edu.monash.fit2099.engine.Action;
import edu.monash.fit2099.engine.Actor;
import edu.monash.fit2099.engine.GameMap;

/**
 * A class that tackles the GameQuit sequencing for the Player. This action class allows the user to end
 * the current instance of the game previously unavailable. The bounded hotkey for quitting within the game
 * is set to char '0'.
 */
public class GameQuitAction extends Action {
    /**
     * This stores the hotkey selected, used to assign the action to its specified key.
     */
    protected String hotKey;

    /**
     * The default constructor for the GameQuitAction class. Takes in hotkey as a parameter.
     * @param hotKey the key designated for the action.
     */
    public GameQuitAction(String hotKey){
        this.hotKey = hotKey;
    }

    /**
     * This method is used to execute the GameQuit action on the actor (Player).
     * The action returns a string message letting the user know the game has been quited.
     * @param actor The actor performing the action.
     * @param map The map the actor is on.
     * @return a description of what happened that can be displayed to the user.
     */
    @Override
    public String execute(Actor actor, GameMap map) {
        map.removeActor(actor);
        return "Thank you for playing the game!";
    }

    /**
     * Returns a descriptive string
     * @param actor The actor performing the action.
     * @return the text we put on the menu
     */
    @Override
    public String menuDescription(Actor actor) {
        return actor + " can choose to quit game.";
    }

    /**
     * Returns this Action's hotkey.
     *
     * @return the hotkey
     */
    @Override
    public String hotkey() {
        return hotKey;
    }
}
