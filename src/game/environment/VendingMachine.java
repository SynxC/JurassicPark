package game.environment;

import edu.monash.fit2099.engine.*;
import game.action.VMPurchasingAction;
import game.item.*;

import java.util.ArrayList;

/**
 * Class creation of vending machine object. The vending machine hosts multiple items that could be
 * purchased by the player by executing the VMPurchasingAction.
 */
public class VendingMachine extends Ground {
    /***
     * Overloading constructor of the VendingMachine class. VendingMachine will be represented by the char 'V'.
     */
    public VendingMachine() {
        super('V');
    }

    /**
     * This method adds a list of the items dispensable by the vending machine.
     * @param actor the Actor acting
     * @param location the current Location
     * @param direction the direction of the Ground from the Actor
     * @return the purchasing action
     */
    @Override
    public Actions allowableActions(Actor actor, Location location, String direction){
        ArrayList<Item> itemArrayList = new ArrayList<Item>();
        itemArrayList.add(new Fruit());
        itemArrayList.add(new VegetarianMealKit());
        itemArrayList.add(new CarnivoreMealKit());
        itemArrayList.add(new StegosaurEgg());
        itemArrayList.add(new BrachiosaurEgg());
        itemArrayList.add(new AllosaurEgg());
        itemArrayList.add(new PterodactylEgg());
        itemArrayList.add(new LaserGun());

        Actions action = new Actions();

        for (Item i: itemArrayList){
            action.add(new VMPurchasingAction(i));
        }
        return action;
    }

}

