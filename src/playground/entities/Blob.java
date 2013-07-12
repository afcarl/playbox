
package playground.entities;

import java.util.ArrayList;
import static java.lang.Math.*;

import org.jbox2d.common.*;
import org.jbox2d.dynamics.*;
import org.jbox2d.dynamics.joints.*;
import org.jbox2d.collision.shapes.*;

import playground.*;
import playground.sensors.*;

/**
 * A Blob entity, featuring a deformable body keeping
 * more or less the same volume when perturbated.
 */
public class Blob extends Entity 
    implements PosFeat, VelFeat {

    /**
     * Skeleton of the blob.
     */
    public ArrayList<Body> skeleton;

    /**
     * The radius of each body that makes up the skeleton
     */
    public float bodyRadius;
    
    /**
     * The radius of the entire blob
     */
    public float radius;
    
    public Blob(Playground pg, float cx, float cy, float radius, float bodyCount, float bodyRadius) {
     
        super(pg, false, 0.0f);
     
        skeleton = new ArrayList<Body>();

        ConstantVolumeJointDef cvjd = new ConstantVolumeJointDef();

        // Where and how big is the blob
        Vec2 center = new Vec2(cx,cy);
        this.radius = radius;
        this.bodyRadius = bodyRadius;

        // Initialize all the points
        for (int i = 0; i < bodyCount; i++) {
          // Look polar to cartesian coordinate transformation!
            float theta = pg.map(i, 0, bodyCount, 0,  2*((float) PI));
            float x = center.x + radius * ((float) sin(theta));
            float y = center.y + radius * ((float) cos(theta));

            // Make each individual body
            BodyDef bd = new BodyDef();
            bd.type = BodyType.DYNAMIC;
            bd.fixedRotation = true; // no rotation!
            bd.position = pg.cooP2W(x,y);
            Body body = pg.world.createBody(bd);

            // The body is a circle
            CircleShape cs = new CircleShape();
            cs.m_radius = pg.scalarP2W(bodyRadius);

            FixtureDef fd = new FixtureDef();
            fd.shape = cs;
            fd.density = 100.0f;
            //fd.friction = 0.0f;
            //fd.restitution = 1.0f;

            //fd.filter.groupIndex = -2;

            body.createFixture(fd);
            body.setUserData(this);

            cvjd.addBody(body);
            skeleton.add(body);
        }

        // These parameters control how stiff vs. jiggly the blob is
        cvjd.frequencyHz = 10.0f;
        cvjd.dampingRatio = 1.0f;

        // Put the joint thing in our world!
        pg.world.createJoint(cvjd);

    }

    /**
     * Return the current position of the blob, as the center of mass of the skeleton.
     * Implementation of the PosFeat sensor interface
     */
    public Vec2 getPosition() {
        
        Vec2 center = new Vec2(0.0f, 0.0f);
        float total = 0.0f;
        
        for (Body b : skeleton) {
            center.addLocal(b.getPosition().mul(b.getMass()));
            total += b.getMass();
        }
        
        return center.mul(1.0f/total);
    }

    /**
     * Return the current position of the blob, as the center of mass of the skeleton.
     * Implementation of the PosFeat sensor interface
     */
    public Vec2 getLinearVelocity() {
        
        Vec2 center = new Vec2(0.0f, 0.0f);
        
        for (Body b : skeleton) {
            center.addLocal(b.getLinearVelocity());
        }
        
        return center.mul(1.0f/skeleton.size());
    }


}
