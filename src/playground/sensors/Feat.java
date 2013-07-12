
package playground.sensors;

import java.util.ArrayList;

/**
 * Feature interface, to declare the availability of a sensor for an entity.
 */
public abstract interface Feat {

    /**
     * Return the list of the sensors for the entity.
     */
    public ArrayList<Sensor> getSensors();
}