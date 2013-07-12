
package playground.entities;

import org.jbox2d.common.*;
import org.jbox2d.dynamics.*;
import org.jbox2d.collision.shapes.*;

import playground.*;

public class Box extends BodyEntity {

    public float w;
    public float h;

    public Box(Playground pg, float x, float y, float w, float h, boolean lock) {

        this(pg, x, y, w, h, 0.0f, lock);
    }

    public Box(Playground pg, float x, float y, float w, float h, float angle, boolean lock, float linearDamping, float angularDamping, float friction, float restitution, float density) {

        super(pg, lock, angle);

        this.w          = w;
        this.h          = h;

        // Body
        BodyDef bd = new BodyDef();
        bd.type = lock ? BodyType.STATIC : BodyType.DYNAMIC;
        bd.position.set(pg.cooP2W(x,y));
        bd.linearDamping = linearDamping;
        bd.angularDamping = angularDamping;
        body = pg.world.createBody(bd);

        // Shape
        PolygonShape ps = new PolygonShape();
        float box2dW = pg.scalarP2W(w/2);
        float box2dH = pg.scalarP2W(h/2);
        ps.setAsBox(box2dW, box2dH, new Vec2(0.0f, 0.0f), angle);

        // Physics
        FixtureDef fd = new FixtureDef();
        fd.shape = ps;
        fd.friction = friction;
        fd.restitution = restitution;
        fd.density = lock ? 0.0f : density;

        // Finalizing
        body.createFixture(fd);
        body.setUserData(this);
    }


    public Box(Playground pg, float x, float y, float w, float h, float angle, boolean lock) {

        this(pg, x, y, w, h, angle, lock, 1.0f, 1.0f, 0.3f, 1.0f, 1.0f);
    }

}

