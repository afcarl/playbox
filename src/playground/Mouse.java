
package playground;

import org.jbox2d.common.Vec2;

import org.jbox2d.callbacks.QueryCallback;
import org.jbox2d.collision.AABB;

import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.BodyType;
import org.jbox2d.dynamics.Fixture;
import org.jbox2d.dynamics.joints.MouseJoint;
import org.jbox2d.dynamics.joints.MouseJointDef;

/**
 * Mouse class, used to allow interaction with the world.
 *
 * Each time the mouse is clicked over an object, a spring joint is created,
 * allowing dragging of the object along the mouse position. When the mouse
 * is released, the joint is destroyed.
 *
 * Events binded to the mousePressed(), mouseDragged and mouseReleased()
 * functions. For Processing this translate into the following code :
 *
 * // Mouse events
 * void mousePressed()  { mouse.mousePressed(mouseX, mouseY); }
 * void mouseReleased() { mouse.mouseReleased(mouseX, mouseY); }
 * void mouseDragged()  { mouse.mouseDragged(mouseX, mouseY); }
 */
public class Mouse {

    protected Playground pg;
    protected float mouseForce = 3000000.0f;
    public MouseJoint mj;

    /**
     * Create the Mouse interaction class.
     */
    public Mouse(Playground pg) {
        this.pg = pg;
    }

    /**
     * Inner, private class used for querying the world about the presence
     * of object at the mouse position.
     */
    protected class MouseQueryCallback implements QueryCallback {

        Fixture fixture;

        public MouseQueryCallback() {
            fixture = null;
        }

        public boolean reportFixture(Fixture f) {
            if (f.getBody().getType() != BodyType.STATIC) {

                fixture = f;
                return false; // End the query, no other fixture is reported.
            }
            else {
                return true;
            }
        }
    }

    // protected void setMouseAnchor(Vec2 v) {
    //
    //         try {
    //             Class c = mj.getClass();
    //             Field field = c.getDeclaredField("m_localAnchor");
    //             field.setAccessible(true);
    //
    //             ((Vec2) field.get(mj)).set(v);
    //         } catch (Exception e) {
    //         }
    //     }
    //
    //     public final Vec2 getMouseAnchor() {
    //
    //         try {
    //             Class c = mj.getClass();
    //             Field field = c.getDeclaredField("m_localAnchor");
    //             field.setAccessible(true);
    //
    //             return (Vec2) field.get(mj);
    //         } catch (Exception e) {
    //             return new Vec2();
    //         }
    //     }

    /**
     * Set the force of the mouse joint.
     * The actual force of the joint will be proportional to the mass of the
     * actual object. This is the force for a force of unitary mass.
     *
     * @param f  the force of the mouse joint (default is 3000.0f)
     */
    public void setMouseForce(float f) {
        mouseForce = f;
    }

    /**
     * Get the current force of the mouse joint.
     *
     * @return the force of the mouse joint (default is 3000.0f)
     */
    public float getMouseForce() {
        return mouseForce;
    }

    /**
     * Method to call when a mouse click event is received.
     *
     * @param mouseX  the position of the mouse, along the x axis.
     * @param mouseY  the position of the mouse, along the y axis.
     */
    public boolean mousePressed(float mouseX, float mouseY) {

        // Are we over an object ?
        Vec2 pos = pg.cooP2W(mouseX, mouseY);

        AABB aabb = new AABB(new Vec2(pos.x - 0.001f, pos.y - 0.001f),
                             new Vec2(pos.x + 0.001f, pos.y + 0.001f));
        MouseQueryCallback mqc = new MouseQueryCallback();
        pg.world.queryAABB(mqc, aabb);

        // If yes, create joint
        if (mqc.fixture != null) {
            MouseJointDef mjd = new MouseJointDef();
            Body b = mqc.fixture.getBody();
            mjd.bodyA = pg.refPoint.body;
            mjd.bodyB = b;
            mjd.target.set(pos);

            mjd.collideConnected = true;
            mjd.maxForce = mouseForce * b.getMass();

            mj = (MouseJoint) pg.world.createJoint(mjd);
            //System.out.println("MouseJoint created");
            return true;
        } else { // Create a target
            return false;
        }
    }

    /**
     * Method to call each time a mouse drag event is received.
     *
     * @param mouseX  the position of the mouse, along the x axis.
     * @param mouseY  the position of the mouse, along the y axis.
     */
    public boolean mouseDragged(float mouseX, float mouseY) {
        // Retarget the joint
        Vec2 pos = pg.cooP2W(mouseX, mouseY);

        if (mj != null) {
            mj.setTarget(pos);
            return true;
        } else {
            return false;
        }
    }

    /**
     * Method to call when a mouse release event is received.
     *
     * @param mouseX  the position of the mouse, along the x axis.
     * @param mouseY  the position of the mouse, along the y axis.
     */
    public void mouseReleased(float mouseX, float mouseY) {
        // Destroy joint
        if (mj != null) {
            pg.world.destroyJoint(mj);
            mj = null;
        }
    }

}
