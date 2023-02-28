package game.item;

import edu.monash.fit2099.engine.GameMap;
import edu.monash.fit2099.engine.Item;
import edu.monash.fit2099.engine.Location;

/**
 * Base class for any item that can be picked up and dropped.
 */
public class PortableItem extends Item {
	/**
	 * A constructor that sets the name and display character of the Portable Item.
	 * @param name
	 * @param displayChar
	 */
	public PortableItem(String name, char displayChar) {
		super(name, displayChar, true);
	}

	/**
	 * This method allows you to retrieve the location of any Portable Item instantiated.
	 * @param map the target game map
	 * @param item The item to locate
	 * @return location of item
	 */
	public static Location getLocation(GameMap map, Item item) {
		Location location = null;
		for (int x_coordinate : map.getXRange()) {
			for (int y_coordinate : map.getYRange()) {
				if (map.at(x_coordinate, y_coordinate).getItems().contains(item)) {
					location = map.at(x_coordinate, y_coordinate);
				}
			}
		}
		return location;
	}

}
