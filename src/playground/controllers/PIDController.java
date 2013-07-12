package playground.controllers;

import playground.entities.Arm;

import java.util.ArrayList;

/**
 * ArmController class. Check the orders, and execute them on the joints of
 * of the arm.
 */
public class PIDController extends ArmController {

    public float Kp, Ki, Kd;

    protected ArrayList<PID> pids;
    protected ArrayList<Float> target;
    protected ArrayList<Float> curpos;

    protected ArrayList<Float> max_speed;
    protected ArrayList<Float> max_torque;

    public PIDController(Arm arm, float Kp, float Ki, float Kd) {
        super(arm);

        this.Kp = Kp;
        this.Ki = Ki;
        this.Kd = Kd;

        pids       = new ArrayList<PID>();
        target     = new ArrayList<Float>();
        curpos     = new ArrayList<Float>();
        max_speed  = new ArrayList<Float>();
        max_torque = new ArrayList<Float>();

        for(int i = 0; i < arm.size; i++) {
            PID pid = new PID(Kp, Ki, Kd);
            pids.add(pid);
            target.add(0.0f);
            curpos.add(0.0f);
            max_speed.add(2.0f);
            max_torque.add(10000.0f);
        }

        this.getArmPos();
    }

    protected void getArmPos() {
        for(int i = 0; i < arm.size; i++) {
            curpos.set(i, new Float(arm.joints.get(i).rj.getJointAngle()));
        }
        //System.out.println(curpos.get(0) + ", " + curpos.get(1) + ", " + curpos.get(2) + ", " + curpos.get(3) + ", " + curpos.get(4) + ", " + curpos.get(5));
    }

    public int length() {
        return 2*this.arm.joints.size();
    }

    /**
    * Set the target position
    */
    public void execute(ArrayList<Float> order) {

        if (order.size() == this.arm.size) {
            //System.out.println("s");
            execute_p(order);
        } else if (order.size() == 2*this.arm.size) {
            //System.out.println("ps");
            execute_ps(order);
        } else if (order.size() == 3*this.arm.size) {
            //System.out.println("pst");
            execute_pst(order);
        } else {
            assert(false);
        }

    }

    private void execute_p(ArrayList<Float> order) {
        assert(order.size() == 2*this.arm.size);

        for (int i = 0; i < this.arm.size; i++) {
            this.target.set   (i, order.get(i));
        }
    }

    private void execute_ps(ArrayList<Float> order) {
        assert(order.size() == 2*this.arm.size);

        for (int i = 0; i < this.arm.size; i++) {
            //System.out.println("target: " + order.get(2*i) + "  max_speed:" + order.get(2*i+1));
            this.target.set   (i, order.get(2*i));
            assert(order.get(2*i+1) >=0.0);
            this.max_speed.set(i, order.get(2*i+1));
        }
    }

    private void execute_pst(ArrayList<Float> order) {
        assert(order.size() == 3*this.arm.size);

        for (int i = 0; i < this.arm.size; i++) {
            this.target.set    (i, order.get(3*i));
            assert(order.get(3*i+1) >=0.0);
            this.max_speed.set (i, order.get(3*i+1));
            assert(order.get(3*i+2) >=0.0);
            this.max_torque.set(i, order.get(3*i+2));
        }
    }

    public void update() {
        this.getArmPos();
        //System.out.println(this.curpos.get(0).floatValue());
        for(int i = 0; i < arm.size; i++) {
            // Computing velocity using PIDs
            float target_i = Math.min(arm.joints.get(i).angle_max, Math.max(arm.joints.get(i).angle_min, this.target.get(i).floatValue()));

            pids.get(i).setTarget(target_i);
            float vel = pids.get(i).update(curpos.get(i).floatValue());
            // Enforcing max velocity
            float max_vel = this.max_speed.get(i).floatValue();
            vel = Math.min(max_vel, Math.max(-max_vel, vel));
            // Setting max torque and speed
            // System.out.println("current: " + arm.joints.get(i).rj.getJointAngle() + "  target: " + target_i + "  max_speed: " + vel);
            // System.out.println("jspeed:  " + arm.joints.get(i).rj.getJointSpeed() + "  mspeed: " + arm.joints.get(i).rj.getMotorSpeed());
            // System.out.println("motor:  " + arm.joints.get(i).rj.isMotorEnabled());
            arm.joints.get(i).rj.setMotorSpeed(vel);
            arm.joints.get(i).rj.setMaxMotorTorque(max_torque.get(i).floatValue());
        }
    }
}
