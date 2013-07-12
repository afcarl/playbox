// This file contain boilerplate Processing code for a interactive simulation.
// This file is identical to the mouse example (except this line and the last method).

import procbox.*;

Playground playground;
Render     render;
Mouse      mouse;

void setup() {
    
    size(1000, 800);
    smooth();
    
    rectMode(CENTER);
    
    playground = createPlayground();
    
    mouse = new Mouse(playground);
    render = new Render(this, mouse);
}

void draw() {
    for (int i = 0; i < 3; i++) {
      playground.step(1.0f/(3*60.0f), 8, 3);
    }  
    render.render(playground);
}

void mousePressed() {
    mouse.mousePressed(mouseX, mouseY);
}

void mouseReleased() {
    mouse.mouseReleased(mouseX, mouseY);
}

void mouseDragged() {
    mouse.mouseDragged(mouseX, mouseY);
}

// Keyboard interaction code.
void keyPressed() {
    
    if (key == 'a') {
        ((ArmController) playground.cc.controllers.get(0)).curl( 5.0f, 100000.0f);
    } else if (key == 'd') {
        ((ArmController) playground.cc.controllers.get(0)).curl(-5.0f, 100000.0f);
    } else if (key == 's') {
        ((ArmController) playground.cc.controllers.get(0)).curl( 0.0f, 0.0f);
    }
}
