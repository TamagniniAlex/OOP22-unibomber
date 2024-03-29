package it.unibo.unibomber.utilities;

import java.util.List;
import java.util.Optional;
import java.util.Random;

/**
 * Direction enum.
 */
public enum Direction {
    /**
     * default direction not move.
     */
    CENTER(0, 0),
    /**
     * up direction.
     */
    UP(0, -1),
    /**
     * left direction.
     */
    LEFT(-1, 0),
    /**
     * down direction.
     */
    DOWN(0, 1),
    /**
     * right direction.
     */
    RIGHT(1, 0);

    private int x;
    private int y;
    private static final Random RANDOM = new Random();

    /**
     * @param x
     * @param y
     */
    Direction(final int x, final int y) {
        this.x = x;
        this.y = y;
    }

    /**
     * @return x
     */
    public int getX() {
        return this.x;
    }

    /**
     * @return y
     */
    public int getY() {
        return this.y;
    }

    /**
     * @param movement movement of entity.
     * @return direction based on movement.
     */
    public static Optional<Direction> extractDirecion(final Pair<Float, Float> movement) {
        /*
         * int x = movement.getX() > 0 ? 1 : movement.getX() < 0 ? -1 : 0
         * int y = movement.getY() > 0 ? 1 : movement.getY() < 0 ? -1 : 0
         * return Optiona.of(new Direction(x,y))
         */
        if (movement.getX() > 0 && movement.getY() == 0) {
            return Optional.of(RIGHT);
        } else if (movement.getX() < 0 && movement.getY() == 0) {
            return Optional.of(LEFT);
        } else if (movement.getX() == 0 && movement.getY() < 0) {
            return Optional.of(UP);
        } else if (movement.getX() == 0 && movement.getY() > 0) {
            return Optional.of(DOWN);
        }
        return Optional.empty();
    }

    /**
     * @param dir1 first direction.
     * @param dir2 second direction.
     * @return direction based on two direction.
     */
    public static Optional<Direction> extractDirecionBetweenTwo(final Pair<Integer, Integer> dir1,
            final Pair<Integer, Integer> dir2) {
        /*
         * int x = movement.getX() > 0 ? 1 : movement.getX() < 0 ? -1 : 0
         * int y = movement.getY() > 0 ? 1 : movement.getY() < 0 ? -1 : 0
         * return Optiona.of(new Direction(x,y))
         */
        if (dir1.getX() > dir2.getX() && dir1.getY().equals(dir2.getY())) {
            return Optional.of(UP);
        } else if (dir1.getX() < dir2.getX() && dir1.getY().equals(dir2.getY())) {
            return Optional.of(DOWN);
        } else if (dir1.getX().equals(dir2.getX()) && dir1.getY() < dir2.getY()) {
            return Optional.of(RIGHT);
        } else if (dir1.getX().equals(dir2.getX()) && dir1.getY() > dir2.getY()) {
            return Optional.of(LEFT);
        }
        return Optional.of(CENTER);
    }

    /**
     * @return list of all directions
     */
    public static List<Direction> valuesNoCenter() {
        return List.of(LEFT, DOWN, RIGHT, UP);
    }

    /**
     * @param x1 first coord.
     * @param x2 second coord.
     * @return distance from two points.
     */
    public static int getDistance(final Pair<Integer, Integer> x1,
            final Pair<Integer, Integer> x2) {
        return (int) Math.sqrt((x1.getX() - x2.getX()) * (x1.getX() - x2.getX())
                + (x1.getY() - x2.getY()) * (x1.getY() - x2.getY()));
    }

    /**
     * @return the current direction 90° shifted
     */
    public Direction getNextClockwise() {
        switch (this) {
            case LEFT:
                return UP;
            case UP:
                return RIGHT;
            case RIGHT:
                return DOWN;
            case DOWN:
                return LEFT;
            default:
                return CENTER;
        }
    }

    /**
     * @return reverse direction
     */
    public Direction reverse() {
        if (this == UP) {
            return DOWN;
        } else if (this == DOWN) {
            return UP;
        } else if (this == RIGHT) {
            return LEFT;
        } else {
            return RIGHT;
        }
    }

    /**
     * @return a random direction
     */
    public static Direction getRandom() {

        return Direction.valuesNoCenter().get(RANDOM.nextInt(4));
    }
}
