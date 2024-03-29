package it.unibo.unibomber.game.ecs.api;

import java.util.stream.Collectors;

import it.unibo.unibomber.utilities.Constants;

import java.util.List;

/**
 * This enum represent all powerUp types, complex and not.
 */
public enum PowerUpType {

    /**
     * Increase bomb number.
     */
    BOMBUP(false),
    /**
     * Decrease bomb number.
     */
    BOMBDOWN(false),
    /**
     * Increase bomb fire.
     */
    FIREUP(false),
    /**
     * Decrease bomb fire.
     */
    FIREDOWN(false),
    /**
     * Set bomb fire max.
     */
    FIREFULL(true),
    /**
     * Increase speed.
     */
    SPEEDUP(false),
    /**
     * Decrease speed.
     */
    SPEEDDOWN(false),
    /**
     * Player can kick bomb.
     */
    KICKBOMB(true),
    /**
     * Player can throw bomb.
     */
    THROWBOMB(true);

    private boolean isComplex;

    /**
     * @param isComplex to establish if powerUp is complex or not
     */
    PowerUpType(final Boolean isComplex) {
        this.isComplex = isComplex;
    }

    /**
     * A method that returns a random powerup 25% complex and 75% not complex.
     * 
     * @return random powerUp
     */
    public static PowerUpType getRandomPowerUp() {
        final boolean complexList = Constants.RANDOM.RND.nextInt(100) <= Constants.PowerUp.COMPLEX_PERCENTAGE;
        final List<PowerUpType> powerUpTypeList = List.of(PowerUpType.values()).stream()
                .filter(e -> e.isComplex == complexList)
                .collect(Collectors.toList());
        return powerUpTypeList.get(Constants.RANDOM.RND.nextInt(powerUpTypeList.size()));
    }

    /**
     * @return if powerUp is positive or not
     */
    public boolean isPositive() {
        switch (this) {
            case BOMBUP:
            case FIREUP:
            case FIREFULL:
            case SPEEDUP:
            case KICKBOMB:
            case THROWBOMB:
                return true;
            default:
                return false;
        }
    }

}
