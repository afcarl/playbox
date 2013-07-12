
package playground.entities;

import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.joints.RevoluteJoint;
import org.jbox2d.dynamics.joints.RevoluteJointDef;

import playground.Playground;

/**
 *
 */
public class ArmJoint extends Entity {

    /**
     * The JBox2D the revolute joint is based on.
     */
    public RevoluteJoint rj;
    public float angle_min, angle_max;

    /**
     * Create an arm joint.
     */
    public ArmJoint(Playground pg, Box boxA, Box boxB, Vec2 anchor, float angleA, float angleB, float angle_limit) {

        super(pg, false, 0.0f);

        createArmJoint(pg, boxA, boxB, anchor, angleA, angleB, angle_limit);
    }

    /**
     * Create an arm joint between segment i and i+1 of an arm.
     *
     * @param pg      the playground the arm belongs to.
     * @param arm     the arm where the joints are to be created.
     * @param i       the number of the joint to create.
     * @param angleA  the absolute orientation of boxA
     * @param angleB  the absolute orientation of boxB
     */
    public ArmJoint(Playground pg, Arm arm, int i, float angleA, float angleB, float angle_limit) {

        super(pg, false, 0.0f);

        angle_min = -angle_limit;
        angle_max =  angle_limit;

        Box boxA = i == 0 ? arm.base : arm.bodies.get(i-1);
        Box boxB = arm.bodies.get(i);
        Vec2 anchor;

        if (i == 0) {
            anchor = boxA.body.getPosition();
        } else {
            float alpha = angleA;
            Vec2  p_a   = boxA.body.getPosition();
            float baH   = pg.scalarP2W(boxA.h);
            anchor = new Vec2((float) (p_a.x + Math.sin(angleA)*baH/2.0f), (float) (p_a.y - Math.cos(angleA)*baH/2.0f));
        }


        createArmJoint(pg, boxA, boxB, anchor, angleA, angleB, angle_limit);
    }

    protected void createArmJoint(Playground pg, Box boxA, Box boxB, Vec2 anchor, float alpha, float beta, float angle_limit) {

        RevoluteJointDef rjd = new RevoluteJointDef();

        rjd.initialize(boxA.body, boxB.body, anchor);
        //rjd.bodyA = boxA.body;
        //rjd.bodyB = boxB.body;
        rjd.collideConnected = false;

        // Local anchors (rotation point)
        // float baH   = pg.scalarP2W(boxA.h);
        // Vec2 laA = new Vec2((float) (-baH/2.0f*Math.cos(alpha)), (float) (-baH/2.0f*Math.sin(alpha)));
        // float bbH   = pg.scalarP2W(boxB.h);
        // Vec2 laB = new Vec2((float) ( bbH/2.0f*Math.cos(beta)), (float) ( bbH/2.0f*Math.sin(beta)));
        // System.out.println(beta + " " + laB.x + " " + laB.y);
        //
        // rjd.localAnchorA.set(laA.x, laA.y);
        // rjd.localAnchorB.set(laB.y, laB.y);

        // Angle limits.
        rjd.referenceAngle = -(beta - alpha);
        rjd.upperAngle = angle_max;
        rjd.lowerAngle = angle_min;

        rj = (RevoluteJoint) pg.world.createJoint(rjd);
        rj.enableMotor(true);
        rj.enableLimit(true);
    }

}