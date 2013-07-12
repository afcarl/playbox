
package playground.sensors;

/**
 * Angular sensor feature interface.
 */
public interface AngFeat extends Feat {

    /**
     * Provide the current angle of the entity, in screen coordinates,
     * (which is the same as in the physics coordinates).
     */
    public float getAngle();
}
