package it.unibo.unibomber.game.model.api;

import java.util.Map;

import it.unibo.unibomber.game.ecs.api.Entity;
import it.unibo.unibomber.game.ecs.api.Type;
import it.unibo.unibomber.utilities.Pair;

public interface Field {
    
    /**
     * A method that supplies a copy of the field
     * @return the copy of the field
     */
    Map<Pair<Integer, Integer>, Pair<Type, Entity>> getField();

    /**
     * A method that updates the field
     */
    void updateField();
}
