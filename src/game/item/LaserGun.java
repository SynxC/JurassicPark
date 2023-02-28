package game.item;

import edu.monash.fit2099.engine.WeaponItem;

/**
 * Class creation for the item LaserGun, a WeaponItem. Can be used by the player to attack dinosaurs as a weapon.
 */
public class LaserGun extends WeaponItem {
    public static final int PRICE = 500;

    /**
     * Overloading constructor of the LaserGun class. LaserGun will be represented by the char 'g'.
     */
    public LaserGun(){
        super("LaserGun",'g',100,"zaps");
        this.asWeapon();
    }

    /**
     * Returns the price of the object.
     * @return the price
     */
    public int getPrice(){
        return PRICE;
    }
}
