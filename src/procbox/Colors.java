
package procbox;

import processing.core.*;

public class Colors {

    protected static PApplet pa;

    public static int BACKGROUND_COLOR;
    public static int EDGE_COLOR;
    public static int WALL_COLOR;
    public static int GHOST_COLOR;

    // Toys
    public static int BALL_COLOR;
    public static int SPRING_COLOR;
    public static int BUMPER_COLOR;
    public static int INTERACTION_COLOR;

    // Arm
    public static int ARM_BASE_COLOR;
    public static int ARM_SECTION_COLOR;
    public static int ARM_JOINT_COLOR;
    public static int ARM_TIP_COLOR;

    Colors(PApplet pa) {
        Colors.pa = pa;

        // World
        BACKGROUND_COLOR  = pa.color( 188, 189, 172);
        EDGE_COLOR        = pa.color(  59,  45,  56);
        WALL_COLOR        = pa.color(  59,  45,  56);
        GHOST_COLOR       = pa.color(  59,  45,  56, 128);

        // Toys
        BALL_COLOR        = pa.color( 240,  36, 117);
        SPRING_COLOR      = pa.color( 242, 116,  53);
        BUMPER_COLOR      = pa.color( 207, 190,  39);
        INTERACTION_COLOR = pa.color( 240,  36, 117);


        // Arm
        ARM_BASE_COLOR    = pa.color( 204,  51,  63);
        ARM_SECTION_COLOR = pa.color( 235, 104,  65);
        ARM_JOINT_COLOR   = pa.color(   0, 160, 176, 180);
        ARM_TIP_COLOR     = pa.color(   0, 160, 176, 220);

    }
}
