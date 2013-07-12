// A position sensor for BodyEntity instances.

package playground.sensors;

import java.util.ArrayList;

/**
 * Sensor for the current angular velocity of the entity
 */
public class AngVelSensor extends LogSensor {

    protected AngVelFeat entity;

    /**
     * Create a Angular Velocity sensor.
     *
     * @param entity  a entity that implements the AngVelFeat interface.
     */
    public AngVelSensor(AngVelFeat entity) {

        this(entity, 1000, 1);
    }

    /**
     * Create a Angular Velocity sensor.
     *
     * @param entity  a entity that implements the AngVelFeat interface.
     * @param maxHistorySize   the maximum size of logged previous reads.
     * @param period           the number of timestep between each logs.
     */
    public AngVelSensor(AngVelFeat entity, int maxHistorySize, int period) {

        super(maxHistorySize, period);

        this.entity = entity;
        this.entity.getSensors().add(this);
    }

    /**
     * Return the current values of the sensor.
     */
    public ArrayList<Float> bareRead() {

        ArrayList<Float> reading = new ArrayList<Float>();

        reading.add(new Float(entity.getAngularVelocity()));

        return reading;
    };

    /**
     * Return the length of the sensor array.
     */
    public int lenght() {
        return 1;
    };
}