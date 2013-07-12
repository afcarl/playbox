// This file contain boilerplate Processing code for a non-interactive simulation.

import procbox.*;

Playground playground;
Render     render;

void setup() {
    
    playground = createPlayground();
    
    size(playground.w, playground.h);
    smooth();
    rectMode(CENTER);
    
    render = new Render(this);
}

void draw() {
    for (int i = 0; i < 3; i++) {
      playground.step(1.0f/(3*60.0f), 8, 3);
    }  
    render.render(playground);
}

