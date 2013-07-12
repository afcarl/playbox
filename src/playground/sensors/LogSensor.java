
package playground.sensors;

import java.util.ArrayList;
import java.util.LinkedList;

/**
 * LogSensor abstract class.
 *
 * A log sensors keep a certain amount of past sensory data
 * in memory.
 */
public abstract class LogSensor implements Sensor {

    protected LinkedList<Integer> logDates;
    protected LinkedList<ArrayList<Float>> data;
    private int maxHistorySize;
    private int period;

    private int startstep = -1;

    /**
     * Constructor
     */
    public LogSensor(int maxHistorySize, int period) {

        assert period >= 1;
        this.period = period;
        this.maxHistorySize = maxHistorySize;
        this.logDates = new LinkedList<Integer>();
        this.data     = new LinkedList<ArrayList<Float>>();
    }

    public abstract ArrayList<Float> bareRead();

    /**
     * Log the sensor if necessary. If not, the sensor is not read.
     * @param step  the number of step since the start of the simulation.
     */
    public void log(int step)
    {
        if (this.startstep == -1) { this.startstep = step; }

        if (logDates.size() == 0 ||
            (step - this.logDates.getLast().intValue() >= this.period))
        {
            assert (this.data.size() == this.logDates.size());

            ArrayList<Float> reading = this.bareRead();
            this.data.add(reading);
            this.logDates.add(new Integer(step));
            if (this.data.size() > this.maxHistorySize) {
                this.data.remove();
                this.logDates.remove();
            }
        }
    }

    /**
     * Read and log the sensory reading if necessary.
     * It is safe to call this methods multiple time in a single timestep.
     */
    public ArrayList<Float> read(int step) {

        if (this.startstep == -1) { this.startstep = step; }
        ArrayList<Float> reading = this.bareRead();

        if (logDates.size() == 0 ||
            (step - this.logDates.getLast().intValue() > this.period))
        {
            assert (this.data.size() == this.logDates.size());
            this.data.add(reading);
            this.logDates.add(new Integer(step));
            if (this.data.size() > this.maxHistorySize) {
                this.data.remove();
                this.logDates.remove();
            }
        }

        return reading;
    }

    /**
     * Return the whole history of the sensor.
     */
    public LinkedList<ArrayList<Float>> history() {
        return this.data;
    }

    /**
     * Return the dates (in steps) at which log were made.
     */
    public LinkedList<Integer> logDates() {
        return this.logDates;
    }

    /**
     * Return the history size
     */
    public int historySize() {
        return this.data.size();
    }

    /**
     * Return the maximum history size
     */
    public int maxHistorySize() {
        return this.maxHistorySize;
    }

    /**
     * Set the maximum history size
     */
    public void setHistorySize(int mhs) {
        this.maxHistorySize = mhs;
    }

    /**
     * Return the maximum history size
     */
    public int period() {
        return this.period;
    }

    /**
     * Set the frequency of sensor reading.
     * @param p  a sensory reading will be kept into memory every
                 p steps of the physics engine.
     */
    public void setPeriod(int p) {
        this.period = p;
    }
}