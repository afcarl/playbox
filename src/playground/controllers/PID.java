package playground.controllers;

import java.lang.Math;

public class PID {

    public float Kp, Ki, Kd;
    protected float deriv, integ;
    protected float target, error;

    public PID(float Kp, float Ki, float Kd) {
        this.Kp = Kp;
        this.Ki = Ki;
        this.Kd = Kd;

        this.deriv = 0.0f;
        this.integ = 0.0f;

        this.error = 0.0f;
    }

    public void setTarget(float tgt_value) {
        this.target = tgt_value;
    }

    public float update(float pv_value) {
        float error = this.target - pv_value;
        //System.out.println("target: "+this.target + "  error: "+error);

        float P = this.Kp * error;
        float D = this.Kd * (error - this.deriv);

        this.deriv =  error;
        this.deriv = Math.min(10.0f, Math.max(-10.0f, this.deriv));
        this.integ += error;
        this.integ = Math.min(2.0f, Math.max(-2.0f, this.integ));

        float I = this.Ki * this.integ;

        //System.out.println("P: "+P + "  I:"+I + "  D:" +D);
        float PID = P + I + D;

        return PID;
    }
}
//class PID:
//    """
//    Discrete PID control
//    """
//
//    def update(self,current_value):
//        """
//        Calculate PID output value for given reference input and feedback
//        """
//
//        self.error = self.set_point - current_value
//
//        self.P_value = self.Kp * self.error
//        self.D_value = self.Kd * ( self.error - self.Derivator)
//        self.Derivator = self.error
//
//        self.Integrator = self.Integrator + self.error
//
//        if self.Integrator > self.Integrator_max:
//            self.Integrator = self.Integrator_max
//        elif self.Integrator < self.Integrator_min:
//            self.Integrator = self.Integrator_min
//
//        self.I_value = self.Integrator * self.Ki
//
//        PID = self.P_value + self.I_value + self.D_value
//
//        return PID