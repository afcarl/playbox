
package playground.entities;

import org.jbox2d.dynamics.BodyDef;
import org.jbox2d.dynamics.BodyType;

import playground.Playground;

/**
 * Point class, to create reference body and pivot.
 */
public class Point extends BodyEntity {

    /**
     * Create a point
     *
     * @param pg  the playground the point belongs too.
     * @param x   the position of the point, along the x axis.
     * @param y   the position of the point, along the y axis.
     */

    public Point(Playground pg, float x, float y) {

        super(pg, true, 0.0f);

        // Body
        BodyDef bd = new BodyDef();
        bd.type = BodyType.STATIC;
        bd.position.set(pg.cooP2W(x,y));
        body = pg.world.createBody(bd);

        // Finalizing
        body.setUserData(this);
    }

}

