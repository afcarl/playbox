
package playground.sensors;

import org.jbox2d.common.*;

/**
 * Position sensor feature interface.
 */
public interface PosFeat extends Feat  {

    /**
     * Provide the current position of the entity, in screen coordinates.
     */
    public Vec2 getPosition();
}