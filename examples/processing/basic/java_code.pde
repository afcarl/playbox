// This file contains the java code for the simulation.
// It can be compiled and executed without dependency to Processing.

import playground.*;
import playground.entities.*;


Playground createPlayground() {
    // Initialize box2d physics and create the world
    playground = new Playground(800, 600, 50);
    playground.setGravity(0.0f, -10.0f);
    
    playground.add(new Ball(playground, playground.w/2, playground.h/2, 40, false));
    
    return playground;
}

