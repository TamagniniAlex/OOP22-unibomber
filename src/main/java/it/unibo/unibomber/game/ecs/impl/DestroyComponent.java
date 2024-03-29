package it.unibo.unibomber.game.ecs.impl;

import java.util.ArrayList;
import java.util.List;

import it.unibo.unibomber.game.ecs.api.PowerUpType;
import it.unibo.unibomber.game.ecs.api.Type;
import it.unibo.unibomber.game.model.api.Game;
import it.unibo.unibomber.game.model.impl.AbstractComponent;
import it.unibo.unibomber.utilities.Constants;
import it.unibo.unibomber.utilities.Pair;

import static it.unibo.unibomber.utilities.Constants.Destroy.STANDARD_FRAME_DURATION;
import static it.unibo.unibomber.utilities.Constants.Destroy.DROPPED_POWERUP_PERCENT;

/**
 * This component manage the destroy of the entity.
 */
public final class DestroyComponent extends AbstractComponent {

    private boolean isDestroyed;
    private int destroyFrames;
    private int destroyFramesPerType;

    /**
     * The constructor set 0 the field destroyFrames
     * and false the field isDestroyed.
     */
    public DestroyComponent() {
        this.isDestroyed = false;
        this.destroyFrames = 0;
        this.destroyFramesPerType = -1;
    }

    @Override
    public void update() {
        if (this.destroyFramesPerType == -1) {
            this.destroyFramesPerType = Constants.Destroy.getDestroyFramesPerType()
                    .containsKey(this.getEntity().getType())
                            ? Constants.Destroy.getDestroyFramesPerType().get(this.getEntity().getType())
                            : STANDARD_FRAME_DURATION;
        }
        if (this.isDestroyed) {
            this.destroyFrames++;
            if (this.destroyFrames >= this.destroyFramesPerType) {
                if (!this.getEntity().getType().equals(Type.BOMB)
                        && !this.getEntity().getType().equals(Type.POWERUP)) {
                    this.dropPowerUps();
                } else {
                    this.getEntity().getGame().updateGameState();
                    this.getEntity().getGame().removeEntity(this.getEntity());
                }
            }
        }
    }

    /**
     * A method to set if the entity is destroyed.
     */
    public void destroy() {
        this.isDestroyed = true;
    }

    /**
     * A method to know if the entity is destroyed.
     * 
     * @return true if the entity is destroyed, false otherwise.
     */
    public boolean isDestroyed() {
        return this.isDestroyed;
    }

    /**
     * A method to know the desctrution frames.
     * 
     * @return timer of destruction frame.
     */
    public int getDestroyFrames() {
        return this.destroyFrames;
    }

    /**
     * A method to drop the powerups.
     * It will drop a single powerup if the destroyed entity was a wall,
     * otherwise it will drop all powerups of the entity player/bot
     */
    private void dropPowerUps() {
        final var entity = this.getEntity();
        final List<PowerUpType> powerUps;
        int droppedPowerUps;
        this.getEntity().getGame().removeEntity(this.getEntity());
        entity.getGame().updateGameState();
        if (entity.getType().equals(Type.DESTRUCTIBLE_WALL) && Constants.RANDOM.RND.nextInt(4) % 2 == 0) {
            entity.getGame()
                    .addEntity(
                            entity.getGame().getFactory().makePowerUp(entity.getPosition(),
                                    PowerUpType.getRandomPowerUp()));
        } else if (entity.getComponent(PowerUpHandlerComponent.class).isPresent()) {
            powerUps = new ArrayList<>(entity.getComponent(PowerUpHandlerComponent.class).get().getPowerUpList());
            droppedPowerUps = (int) Math.ceil(powerUps.size() * DROPPED_POWERUP_PERCENT);
            this.dropRemaining(powerUps, droppedPowerUps, entity.getGame());
        }
    }

    /**
     * A method to drop the remaining powerups.
     * 
     * @param powerUps        the list of powerups
     * @param droppedPowerUps the number of powerups to drop
     * @param game            the game playing
     */
    private void dropRemaining(final List<PowerUpType> powerUps, final int droppedPowerUps, final Game game) {
        final Pair<Integer, Integer> gameDimensions = game.getDimensions();
        while (powerUps.size() > droppedPowerUps) {
            powerUps.remove((int) (Math.random() * powerUps.size()));
        }
        powerUps.stream()
                .forEach(p -> {
                    final var newRandomPos = getRandomPos(gameDimensions, game);
                    game.addEntity(game.getFactory().makePowerUp(newRandomPos, p));
                });
    }

    /**
     * A method to create a random position in the field.
     * 
     * @param gameDimensions the dimensions of the field
     * @param game           the game playing
     * @return a random position
     */
    private Pair<Float, Float> getRandomPos(final Pair<Integer, Integer> gameDimensions, final Game game) {
        Pair<Integer, Integer> coord;
        do {
            coord = new Pair<>(Constants.RANDOM.RND.nextInt(gameDimensions.getX()),
                    Constants.RANDOM.RND.nextInt(gameDimensions.getY()));
        } while (this.checkPosition(coord, game));
        return new Pair<>((float) coord.getX(),
                (float) coord.getY());
    }

    /**
     * A method to check if the random position is already used.
     * 
     * @param pos  the position to check
     * @param game the game playing
     * @return true if the position is already used, false otherwise
     */
    private boolean checkPosition(final Pair<Integer, Integer> pos, final Game game) {
        final var bombersPos = new ArrayList<>();
        game.getEntities().stream()
                .filter(e -> e.getType().equals(Type.BOMBER))
                .forEach(p -> bombersPos
                        .add(new Pair<>(Math.round(p.getPosition().getX()), Math.round(p.getPosition().getY()))));
        return game.getGameField().getField().containsKey(pos) || bombersPos.contains(pos);
    }
}
