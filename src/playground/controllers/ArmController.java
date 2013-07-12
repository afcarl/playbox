
package playground.controllers;

import java.util.ArrayList;


import playground.entities.*;

/**
 * ArmController class. Check the orders, and execute them on the joints of
 * of the arm.
 */
public class ArmController implements Controller {

    protected Arm arm;
    boolean pid_on = false;

    /**
     * Create a controller for an arm.
     */
    public ArmController(Arm arm) {
        this.arm = arm;

        arm.addController(this);
    }

    /**
     * Execute an order on the arm;
     * An order must specify the desired velocity and maximal torque
     * for each joint. Thus an order's length is 2 times the number of
     * joints.
     */
    public void execute(ArrayList<Float> order) {
        assert(order.size() == this.length());

        for (int i = 0; i < length()/2; i++) {
            arm.joints.get(i).rj.setMotorSpeed(order.get(2*i).floatValue());
            arm.joints.get(i).rj.setMaxMotorTorque(order.get(2*i+1).floatValue());
        }
    }

    public void execute(ArrayList<Float> order, ArrayList<Float> torque) {
        assert(order.size() == this.length());
        assert(torque.size() == this.length());

        for (int i = 0; i < length(); i++) {
            arm.joints.get(i).rj.setMotorSpeed(order.get(i).floatValue());
            arm.joints.get(i).rj.setMaxMotorTorque(torque.get(i).floatValue());
        }
    }

    /**
     * The length of the ArrayList of float that constitute a legal order.
     */
    public int length() {
        return 2*(this.arm.joints).size();
    }

    /**
     * Apply the same speed and torque value to every motor.
     */
    public void curl(float speed, float torque) {

        for (ArmJoint j : arm.joints) {
            j.rj.setMaxMotorTorque(torque);
            j.rj.setMotorSpeed(speed);
        }
    }

    /**
     * Set the torque of every joint to the given value, and the velocity to zero.
     */
    public void compliant(float torque) {

        for (ArmJoint j : arm.joints) {
            j.rj.setMaxMotorTorque(torque);
            j.rj.setMotorSpeed(0.0f);
        }
    }

    /**
     *
     */
    public ArrayList<Float> bounds() {

        ArrayList<Float> b = new ArrayList<Float>();

        // for (ArmJoint j : arm.joints) {
        //             b.add(j.rj.);
        //             b.add(j.rj.);
        //         }

        return b;
    }

    public void update() {

    }
}