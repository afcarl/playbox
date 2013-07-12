// This file contain boilerplate Processing code for a interactive simulation.

import procbox.*;

Playground playground;
Render     render;
Mouse      mouse;

void setup() {
  
    playground = createPlayground();
    
    size(int(playground.w), int(playground.h));
    smooth();
    
    rectMode(CENTER);
    
    mouse = new Mouse(playground);
    render = new Render(this, mouse);
}

void draw() {
    for (int i = 0; i < 3; i++) {
      playground.step(1.0f/(3*60.0f), 8, 3);
    }  
    render.render(playground);
}

// Mouse events
void mousePressed() { mouse.mousePressed(mouseX, mouseY); }
void mouseReleased() { mouse.mouseReleased(mouseX, mouseY); }
void mouseDragged() { mouse.mouseDragged(mouseX, mouseY); }
