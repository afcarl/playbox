
package playground.entities;

import org.jbox2d.collision.shapes.CircleShape;
import org.jbox2d.dynamics.BodyDef;
import org.jbox2d.dynamics.BodyType;
import org.jbox2d.dynamics.FixtureDef;

import playground.Playground;

public class Ball extends BodyEntity {

    public float r;

    public Ball(Playground pg, float x, float y, float r, boolean lock, float linearDamping, float angularDamping, float friction, float restitution, float density) {

        super(pg, lock, 0.0f);

        this.r = r;

        // Body
        BodyDef bd = new BodyDef();
        bd.type = lock ? BodyType.STATIC : BodyType.DYNAMIC;
        bd.position = pg.cooP2W(x,y);
        bd.linearDamping = linearDamping;
        bd.angularDamping = angularDamping;
        body = pg.world.createBody(bd);

        // Shape
        CircleShape cs = new CircleShape();
        cs.m_radius = pg.scalarP2W(r);

        // Physics
        FixtureDef fd = new FixtureDef();
        fd.shape = cs;
        fd.density = lock ? 0.0f : density;
        fd.friction = friction;
        fd.restitution = restitution;

        // Finalizing
        body.createFixture(fd);
        body.setUserData(this);

    }

    public Ball(Playground pg, float x, float y, float r, boolean lock) {

        this(pg, x, y, r, lock, 1.0f, 1.0f, 0.3f, 1.0f, 1.0f);

    }

}
