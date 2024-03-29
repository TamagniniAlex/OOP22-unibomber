package it.unibo.unibomber.game.ecs.impl;

import java.util.Map;
import java.util.Optional;

import java.awt.event.KeyEvent;

import it.unibo.unibomber.game.ecs.api.Entity;
import it.unibo.unibomber.game.ecs.api.PowerUpType;
import it.unibo.unibomber.game.ecs.api.Type;
import it.unibo.unibomber.game.model.impl.AbstractComponent;
import it.unibo.unibomber.utilities.Constants;
import it.unibo.unibomber.utilities.Direction;
import it.unibo.unibomber.utilities.Pair;

/**
 * This class manage the input key.
 */
public class InputComponent extends AbstractComponent {

     @Override
     public final void update() {

          final Optional<Integer> moveKey = getMoveKey();
          final Optional<Integer> clickedKey = getClickedKey();
          final Direction moveTo = calculateMovement(moveKey);
          final Entity player = this.getEntity();
          if (clickedKey.isPresent() && clickedKey.get() == KeyEvent.VK_SPACE) {
               player.getComponent(BombPlaceComponent.class).get().placeBomb();
          }
          if (clickedKey.isPresent() && clickedKey.get() == KeyEvent.VK_E) {
               final Map<Pair<Integer, Integer>, Pair<Type, Entity>> fieldMap = player.getGame().getGameField()
                         .getField();
               final Pair<Integer, Integer> playerPos = new Pair<>(
                         (int) Math.round(player.getPosition().getX()),
                         (int) Math.round(player.getPosition().getY()));
               if (player.getComponent(PowerUpHandlerComponent.class).get().getPowerUpList()
                         .contains(PowerUpType.THROWBOMB)
                         && fieldMap.containsKey(playerPos)
                         && fieldMap.get(playerPos).getX() == Type.BOMB) {
                    final Entity bombEntity = fieldMap.get(playerPos).getY();
                    bombEntity.getComponent(CollisionComponent.class).get().setOver(true);
                    bombEntity.getComponent(ThrowComponent.class).get().throwBomb(playerPos,
                              player.getComponent(MovementComponent.class).get().getDirection());
               }
          }
          updateMovement(moveTo);
     }

     /**
      * @param direction the direction where the player should move
      *                  updates the movementComponent relative to this player
      */
     private void updateMovement(final Direction direction) {
          final var movementComponent = this.getEntity().getComponent(MovementComponent.class);
          if (movementComponent.isPresent()) {
               final MovementComponent move = movementComponent.get();
               move.moveBy(direction);
          }
     }

     /**
      * @param moveKey the last movement Key pressed
      * @return how much the player should move given moveKey
      */
     private Direction calculateMovement(final Optional<Integer> moveKey) {
          final Integer keyValue = moveKey.isPresent() ? moveKey.get() : Constants.Input.NO_KEYS_VALUE;
          switch (keyValue) {
               case KeyEvent.VK_W:
                    return Direction.UP;
               case KeyEvent.VK_A:
                    return Direction.LEFT;
               case KeyEvent.VK_S:
                    return Direction.DOWN;
               case KeyEvent.VK_D:
                    return Direction.RIGHT;
               default:
                    return Direction.CENTER;
          }
     }

     private Optional<Integer> getClickedKey() {
          final var keyPressed = this.getEntity().getGame().getWorld().getPlay().getFirstFrameKeys();
          return keyPressed.keySet().stream()
                    .filter(e -> keyPressed.get(e).equals(true))
                    .findFirst();
     }

     /**
      * @return the last key (relative to the 4 movement keys) the player pressed
      */
     private Optional<Integer> getMoveKey() {
          final var keyPressed = this.getEntity().getGame().getWorld().getPlay().getKeys();

          return keyPressed.stream()
                    .filter(e -> e == (int) KeyEvent.VK_W
                              || e == (int) KeyEvent.VK_A
                              || e == (int) KeyEvent.VK_S
                              || e == (int) KeyEvent.VK_D)
                    .findFirst();
     }

}
