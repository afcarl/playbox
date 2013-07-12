// A position sensor for BodyEntity instances.

package playground.sensors;

import java.util.ArrayList;

import org.jbox2d.common.Vec2;

/**
 * Sensor for the current linear velocity of the entity
 */
public class VelSensor extends LogSensor {

    protected VelFeat entity;


    /**
     * Create a Linear Velocity sensor.
     *
     * @param entity  a entity that implements the VelFeat interface.
     */
    public VelSensor(VelFeat entity) {

        this(entity, 1000, 1);
    }

    /**
     * Create a Linear Velocity sensor.
     *
     * @param entity           a entity that implements the VelFeat interface.
     * @param maxHistorySize   the maximum size of logged previous reads.
     * @param period           the number of timestep between each logs.
     */
    public VelSensor(VelFeat entity, int maxHistorySize, int period) {

        super(maxHistorySize, period);

        this.entity = entity;
        this.entity.getSensors().add(this);
    }

    /**
     * Return the current values of the sensor.
     */
    public ArrayList<Float> bareRead() {

        ArrayList<Float> reading = new ArrayList<Float>();

        Vec2 pos = entity.getLinearVelocity();
        reading.add(new Float(pos.x));
        reading.add(new Float(pos.y));

        return reading;
    };

    /**
     * Return the length of the sensor array.
     */
    public int lenght() {
        return 2;
    };
}