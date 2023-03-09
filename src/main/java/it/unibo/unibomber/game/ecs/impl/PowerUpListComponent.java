package it.unibo.unibomber.game.ecs.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.MissingFormatArgumentException;
import java.util.Optional;

import it.unibo.unibomber.game.ecs.api.Entity;
import it.unibo.unibomber.game.ecs.api.PowerUpType;

/**
 * This component manage a list of all bombers powerUp.
 */
public class PowerUpListComponent extends AbstractComponent {

    protected int bombNumber;
    protected int bombFire;
    protected List<PowerUpType> powerUpList = new ArrayList<>();

    /**
     * This method sets all bomber's powerups.
     * @param bombNumber
     * @param bombFire
     * @param powerUpList
     */
    public PowerUpListComponent(final int bombNumber, final int bombFire, final List<PowerUpType> powerUpList) {
        this.bombNumber = bombNumber;
        this.bombFire = bombFire;
        this.powerUpList = powerUpList;
    }

    /**
     * This method takes all powerups from giver.
     * @param giver 
     */
    public PowerUpListComponent(final Entity giver) {
        Optional<PowerUpListComponent> giversList = giver.getComponent(PowerUpListComponent.class);
        if (giversList.isPresent()) {
            this.bombNumber = giversList.get().getBombNumber();
            this.bombFire = giversList.get().getBombFire();
            this.powerUpList = giversList.get().getPowerUpList();
        } else {
            throw new MissingFormatArgumentException("Giver does not contain a PowerUpListComponent itSelf");
        }
    }

    @Override
    public void update() {

    }

    /**
     * @return actual bomb number of player
     */
    public int getBombNumber() {
        return bombNumber;
    }

    /**
     * @return actual bomb power of player
     */
    public int getBombFire() {
        return bombFire;
    }

    /**
     * @return list of powerup of player
     */
    public List<PowerUpType> getPowerUpList() {
        return powerUpList;
    }

}
