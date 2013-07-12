
package playground.entities;

import org.jbox2d.dynamics.*;
import org.jbox2d.common.*;

import playground.*;
import playground.sensors.*;

/**
 * A Body entity, featuring most notably a public Body variable.
 */
public abstract class BodyEntity extends Entity 
    implements AngFeat, PosFeat, AngVelFeat, VelFeat {
    
    /**
     * Body variable, in order to access JBox2D object and methods relative
     * to the entity.
     */
    public Body body;
    
    /**
     * Create a body entity.
     * 
     * @param pg     the Playground the entity belongs to. 
     * @param lock   is the physical object static or dynamic ?
     * @param angle  initial angle for the object.
     */
    public BodyEntity(Playground pg, boolean lock, float angle) {
        super(pg, lock, angle);
    }

    /**
     * Return the current angle of the entity
     * Implementation of AngFeat sensor interface
     */
    public float getAngle() {
        return body.getAngle();
    }

    /**
     * Return the current angular velocity of the entity
     * Implementation of AngVelFeat sensor interface
     */
    public float getAngularVelocity() {
        return body.getAngularVelocity();    
    }

    /**
     * Return the current position of the entity
     * Implementation of PosFeat sensor interface
     */
    public Vec2 getPosition() {
        return pg.cooW2P(body.getPosition());
    }

    /**
     * Return the current velocity of the entity
     * Implementation of VelFeat sensor interface
     */
    public Vec2 getLinearVelocity() {
        return pg.cooW2P(body.getLinearVelocity());
    }

}