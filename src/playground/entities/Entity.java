
package playground.entities;

import java.util.ArrayList;

import playground.*;
import playground.sensors.*;

/* An entity in the simulation.
 * Entity are collection of JBox2D Joint and Body.
 * They can create complex physical objects.
 */
public abstract class Entity implements Feat {

    /**
     * List of sensors available for the object.
     */
    protected ArrayList<Sensor> sensors;

    /**
     * The playground the entity belongs to.
     */
    public Playground pg;

    /**
     * If true, the object is not moving.
     */
    public boolean lock;

    /**
     * The initial offset angle of the shape : this is not the current angle
     * of the shape, only the angle at creation.
     */
    public float shapeAngle;

    /**
     * Create an entity.
     *
     * @param pg     the Playground the entity belongs to.
     * @param lock   is the physical object static or dynamic ?
     * @param angle  initial angle for the object.
     */
    public Entity(Playground pg, boolean lock, float angle) {
        this.pg = pg;
        this.lock = lock;
        this.shapeAngle = angle;

        sensors = new ArrayList<Sensor>();
    }

    /**
     * @return  the list of the instanciated sensors of the entity.
     */
    public ArrayList<Sensor> getSensors() {
        return sensors;
    };

    // /**
    //  * @return  the list of the instanciated sensors of the entity.
    //  */
    //     public void addSensor(Sensor sensor) {
    //     sensors.add(sensor);
    //     pg.cc.addSensor(sensor);
    // };
}