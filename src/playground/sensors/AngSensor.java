// A position sensor for BodyEntity instances.

package playground.sensors;

import java.util.ArrayList;

/**
 * Sensor for the current angle of the entity
 */
public class AngSensor extends LogSensor {

    protected AngFeat   entity;

    /**
     * Create a Angle sensor.
     *
     * @param entity  a entity that implements the AngFeat interface.
     */
    public AngSensor(AngFeat entity) {

        this(entity, 1000, 1);
    }

    /**
     * Create a Angle sensor.
     *
     * @param entity  a entity that implements the AngFeat interface.
     * @param maxHistorySize   the maximum size of logged previous reads.
     * @param period           the number of timestep between each logs.
     */
    public AngSensor(AngFeat entity, int maxHistorySize, int period) {

        super(maxHistorySize, period);

        this.entity = entity;
        this.entity.getSensors().add(this);
    }

    /**
     * Return the current values of the sensor.
     */
    public ArrayList<Float> bareRead() {

        ArrayList<Float> reading = new ArrayList<Float>();

        reading.add(new Float(entity.getAngle()));

        return reading;
    };

    /**
     * Return the length of the sensor array.
     */
    public int lenght() {
        return 1;
    };
}
