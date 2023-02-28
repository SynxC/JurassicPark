package game.environment;

import edu.monash.fit2099.engine.Exit;
import edu.monash.fit2099.engine.Ground;
import edu.monash.fit2099.engine.Location;

import java.util.List;

/**
 * A class that represents bare dirt.
 */
public class Dirt extends Ground {
	private final double DEFAULT_BUSH_CHANCE = 	0.001;
	private final double TWO_BUSH_CHANCE = 0.01;
	private final double RAIN_PROBABILITY = 0.2;
	private final int RAIN_TURN = 10;
	private int currentTurn;
	private boolean isRaining;

	/**
	 * Overloading constructor of the Dirt class. Dirt will be represented by the char '.'
	 */
	public Dirt() {
		super('.');
		currentTurn = 1;
		isRaining = false;
	}

	/**
	 * Grows a bush if the current dirt object does not have a tree instance
	 * @param location The location of the Ground
	 */
	public void growBush(Location location){
		double possibility = Math.random();
		int bushes = 0;
		List<Exit> exitList = location.getExits();
		for (int count = 0; count < exitList.size();count++){
			Ground nearbyGround = exitList.get(count).getDestination().getGround();
			if (nearbyGround instanceof Bush){
				bushes++;
			}else if (nearbyGround instanceof Tree){
				return;
			}
		}
		if (bushes >= 2){
			if (possibility <= TWO_BUSH_CHANCE){
				Bush bush = new Bush();
				location.setGround(bush);
			}
		}else{
			if (possibility <= DEFAULT_BUSH_CHANCE){
				Bush bush = new Bush();
				location.setGround(bush);
			}
		}
	}

	public boolean isRaining(){
		return isRaining;
	}


	/**
	 * Tick updates the current turn of game for the class object. Every turn, there is a chance for a Bush to grow in
	 * this location. Every 10 turns, there is a possibility of rainfall.
	 * @param location The location of the Ground
	 */
	public void tick(Location location){
		super.tick(location);
		Ground ground = location.getGround();
		currentTurn++;
		if (ground instanceof Dirt){
			growBush(location);
		}
		if (currentTurn%RAIN_TURN == 0){
			double rainChance = Math.random();
			if (rainChance <= RAIN_PROBABILITY){
				isRaining = true;
			}
		}else{
			isRaining = false;
		}

}}
