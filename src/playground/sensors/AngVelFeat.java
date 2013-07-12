
package playground.sensors;

/**
 * Angular velocity sensor feature interface.
 */
public interface AngVelFeat extends Feat {

    /**
     * Provide the current angular velocity of the entity, in screen coordinates,
     * (which is the same as in the physics coordinates).
     */
    public float getAngularVelocity();
}
