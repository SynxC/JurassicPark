package game.action;

import edu.monash.fit2099.engine.*;
import game.actor.Player;
import game.item.*;

/**
 * A class that tackles the VMPurchasingAction for players to interact and purchase items from the vending machine.
 * The vending machine items heavily depend on the eco points currently available on the player. If a purchase has
 * to be made, the player must have sufficient eco points.
 */
public class VMPurchasingAction extends Action {
    /**
     * Enums generated to store different types of items dispensable from the vending machine.
     */
    enum Types {
        Fruit,
        VegetarianMealKit,
        CarnivoreMealKit,
        StegosaurEgg,
        BrachiosaurEgg,
        AllosaurEgg,
        PterodactylEgg,
        LaserGun
    }

    /**
     * The item selected from the vending machine, by the player.
     */
    Item selection;

    /**
     * The constructor of the VMPurchasingAction class.
     * @param selection the item selected from the vending machine, by the player.
     */
    public VMPurchasingAction(Item selection) {
        this.selection = selection;
    }

    /**
     * This method is used to execute the purchasing action for the vending machine class. Based on the selection
     * type returned from the vending machine class, the method will dispense the item specified, only if the player
     * has sufficient eco points.
     * @param actor The actor performing the action.
     * @param map The map the actor is on.
     * @return a description of what happened that can be displayed to the user.
     */
    @Override
    public String execute(Actor actor, GameMap map) {
        Player player = (Player)actor;
        int itemPrice = 0;
        String message;

        //Converts the selection to a string format and makes the enum safe.
        Types whichSelection = Types.valueOf(selection.toString());

        switch (whichSelection){
            case Fruit:
                itemPrice = ((Fruit) selection).getPrice();
                break;
            case VegetarianMealKit:
                itemPrice = ((VegetarianMealKit) selection).getPrice();
                break;
            case CarnivoreMealKit:
                itemPrice = ((CarnivoreMealKit) selection).getPrice();
                break;
            case StegosaurEgg:
                itemPrice = ((StegosaurEgg) selection).getPrice();
                break;
            case BrachiosaurEgg:
                itemPrice = ((BrachiosaurEgg) selection).getPrice();
                break;
            case AllosaurEgg:
                itemPrice = ((AllosaurEgg) selection).getPrice();
                break;
            case PterodactylEgg:
                itemPrice = ((PterodactylEgg) selection).getPrice();
                break;
            case LaserGun:
                itemPrice = ((LaserGun) selection).getPrice();
                break;
        }

        if (Player.EcoPointStorage >= itemPrice) {
            player.addItemToInventory(selection);
            player.removeEcoPoints(itemPrice);
            message = selection.toString() + " purchased." + "(Current EcoPoints: " + Player.EcoPointStorage +").";
        }
        else {
            message = "Insufficient EcoPoints to purchase " +
                    selection.toString() +
                    " (Item Price: " + itemPrice + ", Current EcoPoints: " + Player.EcoPointStorage +").";
            }
        return message;
    }

    /**
     * Returns a descriptive string, in this case, the items available in the vending machine.
     * @param actor The actor performing the action.
     * @return the text we put on the menu
     */
    @Override
    public String menuDescription(Actor actor) {
        return "Item: " + selection.toString();
    }
}
