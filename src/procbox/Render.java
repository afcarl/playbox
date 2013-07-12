
package procbox;

import java.util.ArrayList;

import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;

import playground.Mouse;
import playground.Playground;
import playground.entities.*;

import processing.core.PApplet;
import processing.core.PConstants;

public class Render {

    protected Playground    pg;
    protected PApplet       pa;
    protected Mouse      mouse;
    protected Colors    colors;

    public Render(PApplet pa) {
        this.pa         = pa;
        this.colors     = new Colors(pa);
    }

    public Render(PApplet pa, Mouse mouse) {
        this(pa);
        this.mouse      = mouse;
    }

    // Render every object of the physic scene according to
    // its class.
    public void render(Playground playground) {
        this.pg         = playground;

        pa.background(Colors.BACKGROUND_COLOR);

        for (Entity d : pg.entities) {
            String className = d.getClass().getSimpleName();

            if (className.equals("Box")) {

                pa.noStroke();
                if (d.lock) pa.fill(Colors.WALL_COLOR);
                else        pa.fill(Colors.BALL_COLOR);

                displayBox((Box) d);
            } else if (className.equals("Ball")) {

                pa.noStroke();
                if (d.lock) pa.fill(Colors.WALL_COLOR);
                else        pa.fill(Colors.BALL_COLOR);
                displayBall((Ball) d);

            } else if (className.equals("Bumper")) {

                pa.noStroke();
                if (d.lock) pa.fill(Colors.WALL_COLOR);
                else        pa.fill(Colors.BALL_COLOR);
                displayBall((Ball) d);

            } else if (className.equals("Arm")) {

                displayArm((Arm) d);
            } else if (className.equals("Blob")) {

                displayBlob((Blob) d);
            } else {
                System.out.println("className " + className + " not recognized");

            }
        }

        displayMouse();
        displayOverlay();
    }

    protected void displayOverlay() {
        @SuppressWarnings("unchecked")
        ArrayList<Vec2> history = (ArrayList<Vec2>) this.pg.overlay.get("history");
        if (history != null) {
            for (Vec2 p : history) {
                pa.noStroke();
                pa.fill(0, 100, 0);
                pa.ellipse(p.x, p.y, 1, 1);
            }
        }

    }

    protected void displayMouse() {
        if (this.mouse != null && this.mouse.mj != null) {
            Vec2 bodyPos = new Vec2();
            mouse.mj.getAnchorB(bodyPos);
            Vec2 pBodyPos = pg.cooW2P(bodyPos);

            pa.noStroke();
            pa.fill(255, 0, 0, 170);
            pa.ellipse(pBodyPos.x, pBodyPos.y, 2, 2);

            pa.stroke(255, 0, 0, 170);
            pa.strokeWeight(1);
            pa.line(pBodyPos.x, pBodyPos.y, pa.mouseX, pa.mouseY);
        }

        Vec2 target = (Vec2) this.pg.overlay.get("mouse");
        if (target != null) {
            System.out.println((Vec2) this.pg.overlay.get("mouse"));

            pa.stroke(255, 0, 0, 170);
            pa.strokeWeight(1);
            pa.fill(255, 0, 0, 170);
            pa.line(target.x - 5, target.y, target.x + 5, target.y);
            pa.line(target.x, target.y - 5, target.x, target.y + 5);
            pa.ellipse(target.x, target.y, 2, 2);

        }
    }

    protected void displayBox(Box b) {

        Vec2 pos = pg.cooW2P(b.body.getPosition());
        float a = b.body.getAngle();

        pa.pushMatrix();
        pa.translate(pos.x, pos.y);
        pa.rotate(-a - b.shapeAngle);
        pa.rect(0,0,b.w,b.h);
        pa.popMatrix();
    }

    protected void displayBall(Ball b) {

        Vec2 pos = pg.cooW2P(b.body.getPosition());
        float a = b.body.getAngle();

        pa.pushMatrix();
        pa.translate(pos.x, pos.y);
        pa.rotate(-a - b.shapeAngle);
        pa.ellipse(0,0,2*b.r,2*b.r);
        pa.popMatrix();
    }

    protected void displayArmJoint(ArmJoint aj) {

        Vec2 anchorPos = new Vec2();
        aj.rj.getAnchorB(anchorPos);
        Vec2 anchorXY = pg.cooW2P(anchorPos);

        pa.noStroke();
        pa.fill(Colors.ARM_JOINT_COLOR);
        pa.ellipse(anchorXY.x, anchorXY.y, 18, 18);

        pa.noStroke();
        pa.fill(Colors.ARM_BASE_COLOR);
        pa.ellipse(anchorXY.x, anchorXY.y, 5, 5);
    }

    protected void displayArm(Arm a) {

        pa.noStroke();
        pa.fill(Colors.ARM_BASE_COLOR);
        displayBox(a.base);

        pa.noStroke();
        pa.fill(Colors.ARM_SECTION_COLOR);
        for (Box b : a.bodies) { displayBox(b); }

        for (ArmJoint aj : a.joints) { displayArmJoint(aj); }

        pa.noStroke();
        pa.fill(Colors.ARM_TIP_COLOR);
        displayBall(a.tip);
    }

    protected void displayBlob(Blob blob) {

        // Draw the outline
        pa.beginShape();
        pa.fill(207,190,39);
        pa.stroke(Colors.SPRING_COLOR);
        pa.strokeWeight(1);

        for (Body b: blob.skeleton) {
            Vec2 pos = pg.cooW2P(b.getPosition());
            pa.vertex(pos.x,pos.y);
        }
        pa.endShape(PConstants.CLOSE);

        // Draw the individual circles
        for (Body b: blob.skeleton) {
            Vec2 pos = pg.cooW2P(b.getPosition());

            pa.fill(Colors.BALL_COLOR);
            pa.noStroke();

            pa.pushMatrix();
            pa.translate(pos.x,pos.y);
            pa.ellipse(0,0, blob.bodyRadius*2, blob.bodyRadius*2);
            pa.popMatrix();
        }

    }

}
