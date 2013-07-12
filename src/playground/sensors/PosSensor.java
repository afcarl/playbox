// A position sensor for BodyEntity instances.

package playground.sensors;

import java.util.ArrayList;

import org.jbox2d.common.Vec2;


/**
 * Sensor for the current position of the entity
 */
public class PosSensor extends LogSensor {

    protected PosFeat entity;

    /**
     * Create a Position sensor.
     *
     * @param entity  a entity that implements the PosFeat interface.
     */
    public PosSensor(PosFeat entity) {

        this(entity, 1000, 1);
    }

    /**
     * Create a Position sensor.
     *
     * @param entity  a entity that implements the PosFeat interface.
     * @param maxHistorySize   the maximum size of logged previous reads.
     * @param period           the number of timestep between each logs.
     */
    public PosSensor(PosFeat entity, int maxHistorySize, int period) {

        super(maxHistorySize, period);

        this.entity = entity;
        this.entity.getSensors().add(this);
    }


    /**
     * Return the current values of the sensor (without logging)
     */
    public ArrayList<Float> bareRead() {

        ArrayList<Float> reading = new ArrayList<Float>();

        Vec2 pos = entity.getPosition();
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