
package playground.sensors;

import java.util.ArrayList;

/**
 * Sensor interface.
 *
 * Sensors provides a standardized way to provide sensors reading
 * (as ArrayList of Floats).
 */
public interface Sensor {

    /**
     * Return the current values of the sensor.
     */
    public ArrayList<Float> read(int step);

    /**
     * Return the length of the sensor array.
     */
    public int lenght();
}