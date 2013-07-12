
package playground.entities;

import playground.*;

public class Bumper extends Ball {
        
    public Bumper(Playground pg, float x, float y, float r) {
        
        super(pg, x, y, r, true);
        body.getFixtureList().setRestitution(5.0f);
    }
    
}