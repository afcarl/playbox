
package playground.entities;

import java.util.ArrayList;

import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.joints.WeldJointDef;

import playground.Playground;
import playground.controllers.ArmController;
import playground.controllers.Controllable;
import playground.controllers.Controller;
import playground.sensors.PosFeat;
import playground.sensors.VelFeat;
import playground.sensors.AngFeat;

public class Arm extends Entity implements Controllable, PosFeat, VelFeat, AngFeat {

    public Vec2 root;
    public ArrayList<Float> lengths;
    public int  size;

    public Box base;
    public Ball tip;
    public ArrayList<Box> bodies;
    public ArrayList<ArmJoint> joints;
    public ArrayList<ArmController> controllers; // Usually only one.

    float angleOrder;
    float motorSpeed;

    public Vec2 origin; // Initial position.

    public Arm(Playground pg, int n, float length, float angle_limit, float x, float y) {

        super(pg, false, 0.0f);

        this.root = pg.cooP2W(x,y);
        this.size = n;
        this.lengths = new ArrayList<Float>();
        ArrayList<Float> init_pos     = new ArrayList<Float>();
        for (int i = 0; i < this.size; i++) {
            this.lengths.add(length);
            init_pos.add(0.0f);
        }

        this.bodies = new ArrayList<Box>();
        this.joints = new ArrayList<ArmJoint>();
        this.controllers = new ArrayList<ArmController>();

        this.motorSpeed = 0;

        // ArrayList<Float> angleInRadians = new ArrayList<Float>();
        // for(int i = 0 ; i < angle.size() ; i++){
        //     angleInRadians.add(new Float((float) Math.toRadians(angle.get(i).floatValue())));
        // }

        createArm(n, lengths, angle_limit, x, y, init_pos);

        this.origin = this.getPosition();

    }

    /**
     * @param angle_limit in degrees.
     */
    public Arm(Playground pg, int n, ArrayList<Float> lengths, float angle_limit, float x, float y, ArrayList<Float> init_pos) {

        super(pg, false, 0.0f);

        this.root = pg.cooP2W(x,y);
        this.lengths = lengths; // TODO transform into world coo.
        this.size = n;

        this.bodies = new ArrayList<Box>();
        this.joints = new ArrayList<ArmJoint>();
        this.controllers = new ArrayList<ArmController>();

        this.motorSpeed = 0;

        // ArrayList<Float> angleInRadians = new ArrayList<Float>();
        // for(int i = 0 ; i < angle.size() ; i++){
        //     angleInRadians.add(new Float((float) Math.toRadians(angle.get(i).floatValue())));
        // }

        createArm(n, lengths, angle_limit, x, y, init_pos);

        this.origin = this.getPosition();
    }

    /**
     * @param angle_limit in radians
     */
    protected void createArm(int n, ArrayList<Float> lengths, float angle_limit, float x, float y, ArrayList<Float> init_pos) {

        Vec2 centerPos = new Vec2(x, y);
        base = new Box(pg, x, y, 50, 50, true);

        float p_x = x;
        float p_y = y;
        float p_a = 0.0f;

        for (int i = 0; i < n; i++) {

            p_a = p_a + init_pos.get(i).floatValue();

            float c_x = p_x + (lengths.get(i).floatValue() / 2) * (float) Math.sin(p_a);
            float c_y = p_y + (lengths.get(i).floatValue() / 2) * (float) Math.cos(p_a);

            Vec2 sectionPos = new Vec2(c_x, c_y);

            Box section = new Box(pg, sectionPos.x, sectionPos.y, 15, lengths.get(i).floatValue()+10, p_a, false);
            bodies.add(section);

            ArmJoint j = new ArmJoint(pg, this, i, p_a - init_pos.get(i).floatValue(), p_a, angle_limit);
            if (i == 0) { j.rj.enableLimit(false); }

            p_x = p_x + (lengths.get(i).floatValue() + 6) * (float) Math.sin(p_a);
            p_y = p_y + (lengths.get(i).floatValue() + 6) * (float) Math.cos(p_a);

            joints.add(j);
        }

        Vec2 tipPos = new Vec2(p_x, p_y);
        tip = new Ball(pg, tipPos.x, tipPos.y, 10, false);

        WeldJointDef wjd = new WeldJointDef();
        wjd.initialize(bodies.get(bodies.size()-1).body, tip.body, tip.body.getPosition());
        pg.world.createJoint(wjd);
    }

    /**
     * Return the coordinate of a point rotated around the center of an given angle.
     * @param angle in radians.
     */
    protected Vec2 rotate(Vec2 center, Vec2 point, float angle) {
        Vec2 v = point.sub(center);
        double a = angle;
        Vec2 rotated = new Vec2((float) (Math.cos(a)*v.x - Math.sin(a)*v.y),
                                (float) (Math.sin(a)*v.x + Math.cos(a)*v.y));
        return center.add(rotated);
    }

    /**
     * Add an ArmController to the arm.
     */
    public void addController(Controller ctrl) {
        this.controllers.add((ArmController) ctrl);
    }

    /**
     * Return the current position of the tip of the arm.
     * Implementation of PosFeat sensor interface
     */
    public Vec2 getPosition() {
        return tip.getPosition();
    }

    /**
     * Return the current velocity of the the tip of the arm.
     * Implementation of VelFeat sensor interface
     */
    public Vec2 getLinearVelocity() {
        return tip.getLinearVelocity();
    }

    /**
     * Return the current velocity of the the tip of the arm.
     * Implementation of VelFeat sensor interface
     */
    public float getAngle() {
        Box tipbox = this.bodies.get(this.bodies.size() - 1);
        return tipbox.getAngle();
    }

    public void curlRight() {
        for (ArmJoint j : joints) {

            j.rj.setMaxMotorTorque(1000.0f);
            j.rj.setMotorSpeed(0.5f);
        }
    }

    public void curlLeft() {
        for (ArmJoint j : joints) {

            j.rj.setMaxMotorTorque(1000.0f);
            j.rj.setMotorSpeed(-0.5f);
        }
    }

    public void compliant(float torque) {
        for (ArmJoint j : joints) {

            j.rj.setMaxMotorTorque(torque);
            j.rj.setMotorSpeed(0.0f);
        }
    }

}