
package playground.controllers;

import java.util.ArrayList;

/**
 * The interface for a Controller of a Controllable Entity.
 */
public interface Controller {
    
    /**
     * Execute an order on the entity the controller is associated to.
     */ 
    public void execute(ArrayList<Float> order);

    /**
     * The length of the ArrayList<Float> a legal order would constitute. 
     */
    public int length();
    
    /**
     * The bounds on the values of an legal order.
     * The ArrayList<Float> returned is of size 2*length(), and its structure is:
     * (min_0, max_0, min_1, max_1, ..., min_n, max_n)
     */
    public ArrayList<Float> bounds();
    
    public void update();
}