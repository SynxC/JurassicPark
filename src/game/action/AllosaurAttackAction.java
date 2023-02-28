package game.action;

import edu.monash.fit2099.engine.Action;
import edu.monash.fit2099.engine.Actor;
import edu.monash.fit2099.engine.GameMap;
import game.actor.Allosaur;
import game.actor.Dinosaur;

/**
 * A class that tackles the AllosaurAttackAction for allosaurs. The allosaur can only attack a specified
 * target once before it goes into a cooldown. The target species is always a stegosaur.
 */
public class AllosaurAttackAction extends Action {
    /**
     * The dinosaur to be attacked for food source
     */
    protected Dinosaur target;
    private String stegosaur = "Stegosaur";
    private String pterodactyl = "Pterodactyl";
    private int damage;

    /**
     * The constructor for AllosaurAttackAction class
      * @param target the target parameter represents the target the actor is currently attacking, which is a
     *               Dinosaur class.
     */
    public AllosaurAttackAction(Dinosaur target){
        this.target = target;
    }

    /**
     * This method is used to execute the attack action on the target, specifically for the allosaur dinosaur classes.
     * The allosaur can only attack targets that they have yet encountered before, hence the method will check if
     * the target has been attacked recently.
     * @param actor The actor performing the action.
     * @param map The map the actor is on.
     * @return a description of what happened that can be displayed to the user.
     */
    @Override
    public String execute(Actor actor, GameMap map) {
        if (target.getSpecies().equals(stegosaur)) {
            if (((Allosaur) actor).getAttacked_dinosaur().contains(target)) {
                return "Can't attack same dinosaur";
            } else {
                int damage = 20;
                this.damage = damage;
                target.hurt(damage);
                if (target.getHitPoints() <= 0) {
                    target.Death(target, map, target.getCorpseHealth());
                }
                else {
                    ((Allosaur) actor).setAttacked_dinosaur(target);
                    ((Allosaur) actor).setAttacked_dinosaur_count(0);
                }
                actor.heal(damage);
            }
        }else if (target.getSpecies().equals(pterodactyl)){
            map.removeActor(target);
            this.damage = 100;
            actor.heal(damage);
        }
        return menuDescription(actor);
    }

    /**
     * Returns a descriptive string
     * @param actor The actor performing the action.
     * @return the text we put on the menu
     */
    @Override
    public String menuDescription(Actor actor) {
        return actor + " attacked " + target + " for " + this.damage;
    }
}
