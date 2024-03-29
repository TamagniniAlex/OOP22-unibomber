package it.unibo.unibomber.game.ecs.impl;

import it.unibo.unibomber.game.model.impl.AbstractComponent;
import it.unibo.unibomber.utilities.Constants;
import it.unibo.unibomber.utilities.Direction;
import it.unibo.unibomber.utilities.Pair;
import it.unibo.unibomber.utilities.Utilities;

/**
 * The class is responsible for handling the movement of the enetity it is
 * attatched to
 * it also keeps track of the frames said entity spent in one direction so it
 * can be
 * graphically implemented.
 */
public class MovementComponent extends AbstractComponent {

    private static float globalSpeedMultiplier = Constants.Movement.MULTIPLIER_GLOBAL_SPEED;
    private boolean hasMoved;
    private Pair<Float, Float> moveBy = new Pair<>(0f, 0f);
    private Direction direction = Direction.CENTER;
    private int framesInDirection;
    private int passedFrame;

    @Override
    public final void update() {
        if (Utilities.isAlive(this.getEntity())) {
            this.getEntity().addPosition(moveBy);
            handleDirection();
        }
    }

    /**
     * Given the direction it updates the number of frames spent
     * in that direction for the animation's sake.
     */
    private void handleDirection() {
        final Direction newDirection = Direction.extractDirecion(moveBy).orElse(direction);
        if (this.direction == newDirection) {
            this.framesInDirection++;
        } else {
            this.framesInDirection = 0;
            this.direction = newDirection;
        }
        if (framesInDirection == Constants.Movement.FRAME_DELAY) {
            passedFrame++;
            this.framesInDirection = 0;
        }
    }

    /**
     * @param direction the direction to move by
     */
    public final void moveBy(final Direction direction) {
        this.moveBy = new Pair<>(
                direction.getX() * this.getEntity().getSpeed() * globalSpeedMultiplier * Constants.Input.POSITIVE_MOVE,
                direction.getY() * this.getEntity().getSpeed() * globalSpeedMultiplier * Constants.Input.POSITIVE_MOVE);
        if (moveBy.equals(new Pair<Float, Float>(0f, 0f))) {
            hasMoved = false;
        } else {
            hasMoved = true;
        }
    }

    /**
     * @return the direction this entity is facing
     */
    public final Direction getDirection() {
        return this.direction;
    }

    /**
     * @return the number of frames spent in one direction
     */
    public final int getFrameInDirection() {
        return this.framesInDirection;
    }

    /**
     * @return the number of frames spent in one direction
     *         keeping in mind the FRAME_DELAY
     */
    public final int getPassedFrames() {
        return this.passedFrame;
    }

    /**
     * @param speed the new value of globalSpeedMultiplier
     */
    public static void setGlobalSpeedMultiplier(final float speed) {
        globalSpeedMultiplier = speed;
    }

    /**
     * @return whether the entity has moved in the last game frame
     */
    public boolean hasMoved() {
        return this.hasMoved;
    }
}
