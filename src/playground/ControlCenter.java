
package playground;

import java.util.ArrayList;

import playground.controllers.*;
import playground.sensors.*;

/**
 * @author fabien
 *
 */
public class ControlCenter {

    public ArrayList<Sensor> sensors;
    public ArrayList<LogSensor> logSensors;
    public ArrayList<Controller> controllers;

    public ControlCenter() {
        sensors     = new ArrayList<Sensor>();
        logSensors  = new ArrayList<LogSensor>();
        controllers = new ArrayList<Controller>();
    }

    //Add a sensor to the interface.
    public void addSensor(Sensor sensor) {

        if (sensor instanceof LogSensor) {
            logSensors.add((LogSensor) sensor);
        }
        sensors.add(sensor);
    }

    //Add a controller to the interface.
    public void addController(Controller controller) {
        controllers.add(controller);
    }


    /**
     * Log every sensor that support logging.
     * @param step  the number of step since the start of the simulation.
     */
    public void logSensors(int step) {
        for (LogSensor ls : this.logSensors) {
            ls.log(step);
        }
    }

    public void update() {
        for(Controller c : controllers) {
            c.update();
        }
    }

}