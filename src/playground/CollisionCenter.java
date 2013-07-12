package playground;

import java.util.ArrayList;
import java.util.HashSet;

import org.jbox2d.callbacks.ContactFilter;
import org.jbox2d.dynamics.Fixture;

import playground.entities.*;


/*
 * Filter the collisions. It's pretty crude, and entities are probably not the right level. Can be a bit slow too.
 */
public class CollisionCenter extends ContactFilter {

    protected ArrayList<HashSet<Entity>> groups; // object belonging to the same group won't collide

    public CollisionCenter( ){
        groups = new ArrayList<HashSet<Entity>>();
    }

    public int addGroup() {

        HashSet<Entity> group = new HashSet<Entity>();
        groups.add(group);

        return (groups.size() - 1);
    };

    public void addEntityToGroup(Entity e, int groupIx) {

        groups.get(groupIx).add(e);
    }

    @Override
    public boolean shouldCollide(Fixture fixtureA, Fixture fixtureB) {

        Entity entityA = (Entity) fixtureA.getBody().getUserData();
        Entity entityB = (Entity) fixtureB.getBody().getUserData();

        for (HashSet<Entity> group : this.groups) {
            if (group.contains(entityA) && group.contains(entityB)) {
                return false;
            }
        }

        return true;
    }

}
