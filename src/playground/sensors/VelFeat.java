
package playground.sensors;

import org.jbox2d.common.*;

/**
 * Linear Velocity sensor feature interface.
 */
public interface VelFeat extends Feat {

    /**
     * Provide the current linear velocity of the entity, in screen coordinates.
     */
    public Vec2 getLinearVelocity();
}
