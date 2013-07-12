
package playground.controllers;

/**
 * Entity that can be controlled should implement the interface Controllable.
 */
public interface Controllable {
    
    /**
     * Add a controller to the controllable entity.
     */
    void addController(Controller c);

}