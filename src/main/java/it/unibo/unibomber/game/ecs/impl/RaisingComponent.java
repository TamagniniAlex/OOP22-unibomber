package it.unibo.unibomber.game.ecs.impl;

import it.unibo.unibomber.game.ecs.api.Entity;
import it.unibo.unibomber.game.ecs.api.Type;
import it.unibo.unibomber.game.model.impl.AbstractComponent;
import it.unibo.unibomber.utilities.Constants;


/**
 * Raising Component.
 */
public final class RaisingComponent extends AbstractComponent {

     private int normalizedFrames;

     /**
      * Raising Component Controller.
      */
     public RaisingComponent() {
          this.normalizedFrames = 0;
     }

     @Override
     public void update() {
          final Entity entity = this.getEntity();
          this.normalizedFrames++;
          if (this.normalizedFrames >= Constants.Destroy.getDestroyFramesPerType().get(Type.RISING_WALL)) {
               entity.getGame().addEntity(entity.getGame().getFactory().makeIndestructibleWall(entity.getPosition()));
               entity.getGame().getEntities().remove(entity);
          }
     }

}
