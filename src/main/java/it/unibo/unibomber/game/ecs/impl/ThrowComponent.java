package it.unibo.unibomber.game.ecs.impl;

import java.util.Map;

import it.unibo.unibomber.game.ecs.api.Type;
import it.unibo.unibomber.game.model.impl.AbstractComponent;
import it.unibo.unibomber.game.ecs.api.Entity;
import it.unibo.unibomber.utilities.Constants;
import it.unibo.unibomber.utilities.Direction;
import it.unibo.unibomber.utilities.Pair;
import it.unibo.unibomber.utilities.Utilities;

/**
 * This component throw entities.
 */
public class ThrowComponent extends AbstractComponent {
    private boolean isThrowing;
    private Direction playerDir;
    private Pair<Integer, Integer> startingPos;
    private Pair<Integer, Integer> finalPos;

    @Override
    public final void update() {
        final MovementComponent bombMovement = this.getEntity().getComponent(MovementComponent.class).get();
        final Entity placerEntity = this.getEntity().getComponent(ExplodeComponent.class).get().getPlacer();
        final float bombActualPositionX = this.getEntity().getPosition().getX();
        final float bombActualPositionY = this.getEntity().getPosition().getY();
        if (isThrowing && Utilities.isAlive(placerEntity)) {
            if (Math.round(bombActualPositionX) != (float) (finalPos.getX())
                    || Math.round(bombActualPositionY) != (float) (finalPos.getY())) {
                final int dimensionX = this.getEntity().getGame().getDimensions().getX();
                final int dimensionY = this.getEntity().getGame().getDimensions().getY();
                if (!Utilities.isBetweenIncluded(bombActualPositionX, 0, dimensionX - 1)
                        || !Utilities.isBetweenIncluded(bombActualPositionY, 0, dimensionY - 1)) {

                    int nextX = Math.abs(finalPos.getX() + dimensionX) % (dimensionX);
                    int nextY = Math.abs(finalPos.getY() + dimensionY) % (dimensionY);

                    this.finalPos = new Pair<>(nextX, nextY);

                    switch (bombMovement.getDirection()) {
                        case UP:
                            nextY = dimensionY - 1;
                            break;
                        case DOWN:
                            nextY = 0;
                            break;
                        case LEFT:
                            nextX = dimensionX - 1;
                            break;
                        case RIGHT:
                            nextX = 0;
                            break;
                        default:
                            break;
                    }

                    this.getEntity().setPosition(new Pair<Float, Float>((float) nextX, (float) nextY));
                }

                bombMovement.moveBy(playerDir);

            } else {
                if (checkFinalPosition()) {
                    bombMovement.moveBy(Direction.CENTER);
                    this.getEntity().setPosition(Utilities.getFloatPair(finalPos));
                    this.isThrowing = false;
                } else {
                    finalPos = new Pair<>(finalPos.getX() + (playerDir.getX()),
                            finalPos.getY() + (playerDir.getY()));
                }
            }

        }
    }

    /**
     * This method set throwing status.
     * 
     * @param startingPos bomb starting position
     * @param playerDir   direction of player
     */
    public final void throwBomb(final Pair<Integer, Integer> startingPos, final Direction playerDir) {
        this.startingPos = startingPos;
        this.playerDir = playerDir;
        this.isThrowing = true;
        this.finalPos = calculateStandardPosition();
    }

    /**
     * This method return throwing status.
     * 
     * @return bomb throwing status
     */
    public final boolean isThrowing() {
        return this.isThrowing;
    }

    /**
     * This method calculate where bomb will be throwed.
     * 
     * @return standard position
     */
    private Pair<Integer, Integer> calculateStandardPosition() {
        return new Pair<>(startingPos.getX() + (playerDir.getX() * Constants.Bomb.STANDARD_THROW),
                startingPos.getY() + (playerDir.getY() * Constants.Bomb.STANDARD_THROW));
    }

    /**
     * This method check if bomb can be stopped.
     * 
     * @return final position status
     */
    private boolean checkFinalPosition() {
        final Map<Pair<Integer, Integer>, Pair<Type, Entity>> fieldMap = this.getEntity().getGame().getGameField()
                .getField();
        return fieldMap.entrySet().stream()
                .filter(e -> e.getKey().equals(finalPos))
                .map(Map.Entry::getValue)
                .noneMatch(value -> value.getX().equals(Type.INDESTRUCTIBLE_WALL)
                        || value.getX().equals(Type.DESTRUCTIBLE_WALL)
                        || value.getX().equals(Type.BOMB));
    }

}
