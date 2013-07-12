
package playground;

import java.util.ArrayList;
import java.util.HashMap;

import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.World;

import playground.controllers.Controller;
import playground.entities.Box;
import playground.entities.Entity;
import playground.entities.Point;
import playground.sensors.Sensor;

/**
 * Playground class.
 * This is the central class of the interface between JBox2D and Processing
 * The Processing and JBox2D world do not use the same system of coordinates
 * (origin, scale, and axis orientation). Playground make the translation
 * between the two worlds.
 */
public class Playground {

    protected float scaleFactor = 20;
    protected float yFlip       = -1;

    /**
     * The JBox2D world.
     */
    public World world;

    /**
     * width of the Processing world.
     */
    public int w;
    /**
     * height of the Processing world.
     */
    public int h;
    /**
     * width of the margins (edges) of the Processing world.
     */
    public float margin;

    /**
     * List of objects rendered by processing.
     */
    public ArrayList<Entity> entities;

    /**
     * Control and sensor interface.
     */
    public ControlCenter cc;

    /**
     * Collision filter.
     */
    public CollisionCenter cf;

    /**
     * A overlay, to host non-physical debug info.
     */
    public HashMap<String, Object> overlay;

    /**
     * Some joints requires a dummy, static reference body.
     */
    public Point refPoint;

    /**
     * Some joints requires a dummy, static reference body.
     */
    public Playground(int w, int h, float margin) {

        this.w = w;
        this.h = h;
        this.margin = margin;

        entities = new ArrayList<Entity>();
        cc = new ControlCenter();
        cf = new CollisionCenter();

        overlay = new HashMap<String, Object>();

        createWorld();
    }

    /**
     * Reset the world : remove every entity, set gravity to zero.
     */
    public Playground reset(){

        createWorld();

        return this;
    }

    /**
     * Create the JBox2D world.

     */
    protected void createWorld() {

        Vec2 gravity = new Vec2(0.0f, 0.0f);
        boolean doSleep = true;
        world = new World(gravity);
        world.setWarmStarting(true);
        world.setContinuousPhysics(true);
        world.setContactFilter(cf);

        refPoint = new Point(this, 0.0f, 0.0f);

        createEdges();
    }

    /**
     * Add an entity to the playground.
     */
    public Entity add(Entity e) {
        entities.add(e);
        return e;
    }

    /**
     * Add a sensor to the playground.
     */
    public Sensor add(Sensor s) {
        cc.addSensor(s);
        return s;
    }

    /**
     * Add a controller to the playground.
     */
    public Controller add(Controller c) {
        cc.addController(c);
        return c;
    }

    /**
     * Create the edges of the world.
     */
    protected void createEdges() {
        if (margin > 0) {
            add(new Box(this,   margin/2,        h/2, margin,      h, 0.0f, true, 1.0f, 1.0f, 1.0f, 0.0f, 0.0f));
            add(new Box(this, w-margin/2,        h/2, margin,      h, 0.0f, true, 1.0f, 1.0f, 1.0f, 0.0f, 0.0f));
            add(new Box(this,        w/2, h-margin/2,      w, margin, 0.0f, true, 1.0f, 1.0f, 1.0f, 0.0f, 0.0f));
            add(new Box(this,        w/2,   margin/2,      w, margin, 0.0f, true, 1.0f, 1.0f, 1.0f, 0.0f, 0.0f));
        }
    }

    /**
     * Update the physic simulation, with default time step (1/60th of a second),
     * velocity iterations (8) and position iteration (3) of the constraint solver.
     */
    public void step() {
        this.step(1.0f/60.0f, 8, 3);
    }

    /**
     * Update the physic simulation with custom parameters.
     * <p>
     * To better understand those parameters, here is an excerpt of the (J)Box2D manual:
     * <p>
     *  In addition to the integrator, Box2D also uses a larger bit of code called
     *  a constraint solver. The constraint solver solves all the constraints in
     *  the simulation, one at a time. A single constraint can be solved perfectly.
     *  However, when we solve one constraint, we slightly disrupt other
     *  constraints. To get a good solution, we need to iterate over all
     *  constraints a number of times.
     * <p>
     *  There are two phases in the constraint solver: a velocity phase and a
     *  position phase. In the velocity phase the solver computes the impulses
     *  necessary for the bodies to move correctly. In the position phase the
     *  solver adjusts the positions of the bodies to reduce overlap and joint
     *  detachment. Each phase has its own iteration count. In addition, the
     *  position phase may exit iterations early if the errors are small.
     * <p>
     *  The suggested iteration count for Box2D is 8 for velocity and 3 for position.
     *  You can tune this number to your liking, just keep in mind that this has a
     *  trade-off between speed and accuracy. Using fewer iterations increases
     *  performance but accuracy suffers. Likewise, using more iterations decreases
     *  performance but improves the quality of your simulation.
     * <p>
     *
     * @param timeStep  the amount of time to make the simulation advance.
                        Values higher than 1/30th of a second are not recommended.
                        Recommended values are in the interval [1/60, 1/400].
     * @param velocityIterations  The number of velocity iterations of the constraint solver per timestep.
     * @param positionIterations  the number of position iteraction of the constraint solver per timestep.
     *
     */
    public void step(float timeStep, int velocityIterations, int positionIterations) {
        world.step(timeStep, velocityIterations, positionIterations);
    }

    /**
     * Change the gravity force of the physic world. This can safely be changed dynamically at every
     * step of the simulation.
     *
     * @param x  the gravity force along the x axis.
     * @param y  the gravity force along the y axis.
     */
    public void setGravity(float x, float y) {
        world.setGravity(new Vec2(x, y));
    }


    /**
     * Map, in an homothetic manner the interval [a, b] into its the interval [c, d].
     *
     * @param x  the element of [a, b] to be mapped onto [c, d]. Note that x does not necesseraly
     *           needs to belong to [a, b].
     */
    public float map(float x, float a, float b, float c, float d) {
        return (x-a)/(b-a)*(d-c)+c;
    }

    /**
     * Transform a vector in world coordinate into a vector in screen coordinate.
     */
    public Vec2 cooW2P(Vec2 world) {
        return cooW2P(world.x,world.y);
    }

    /**
     * Transform a vector in world coordinate into a vector in screen coordinate.
     */
    public Vec2 cooW2P(float worldX, float worldY) {
        float pixelX = map(worldX, 0f, 1f, w/2, w/2+scaleFactor);
        float pixelY = map(worldY, 0f, 1f, h/2, h/2+scaleFactor);
        if (yFlip == -1.0f) pixelY = h-pixelY;
        return new Vec2(pixelX, pixelY);
    }

    /**
     * Transform a scalar in world coordinate into a scalar in screen coordinate.
     */
    public float scalarW2P(float pixel) {
        return map(pixel, 0f, 1f, 0f, scaleFactor);
    }

      /**
     * Transform a vector in screen coordinate into a vector in world coordinate.
     */
    public Vec2 cooP2W(Vec2 screen) {
        return cooP2W(screen.x,screen.y);
    }

      /**
     * Transform a vector in screen coordinate into a vector in world coordinate.
     */
    public Vec2 cooP2W(float pixelX, float pixelY) {
        float worldX = map(pixelX, w/2, w/2+scaleFactor, 0f, 1f);
        if (yFlip == -1.0f) pixelY = h-pixelY;
        float worldY = map(pixelY, h/2, h/2+scaleFactor, 0f, 1f);
        return new Vec2(worldX,worldY);
    }

      /**
     * Transform a scalar in screen coordinate into a scalar in world coordinate.
     */
    public float scalarP2W(float pixel) {
        return map(pixel, 0f, scaleFactor, 0f, 1f);
    }
}