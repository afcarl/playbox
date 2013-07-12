// This file contains the java code for the simulation.
// It can be compiled and executed without dependency to Processing.

import playground.*;
import playground.entities.*;
import playground.controllers.*;

Playground createPlayground() {

    // Initialize box2d physics and create the world
    playground = new Playground(1000, 800, 50);
    playground.setGravity(0.0f, 0.0f);

    playground.add(new Box(playground, 300, height/2 + 200, 20, 300, -0.8, true));
    playground.add(new Ball(playground, 300, 200, 40, true));

    //playground.add(new Ball(playground, 650, 300, 20, false));
    playground.add(new Ball(playground, 700, 250, 30, false));

    playground.add(new Bumper(playground, 650, 300, 20));

    playground.add(new Box(playground, 300, 300, 50, 50, false));
    //playground.add(new Blob(playground, width/2 + 100, height/2 + 100, 30, 18, 3));

    Arm arm = new Arm(playground, 6, 52, 30.0f, width/2, height/2);
    playground.add(arm);

    // Control interface.
    ArmController ac = (ArmController) playground.add(new ArmController(arm));
    ac.compliant(100.0f);

    return playground;
}


