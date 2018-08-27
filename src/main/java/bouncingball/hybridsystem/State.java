
package bouncingball.hybridsystem;

import edu.ucsc.cross.hse.core.modeling.DataStructure;

/**
 * A data structure defining the state of a bouncing ball.
 */
public class State extends DataStructure {

	/**
	 * vertical position
	 */
	public double yPosition;

	/**
	 * vertical velocity
	 */
	public double yVelocity;

	/**
	 * Construct the state with the given initial conditions
	 * 
	 * @param y_position
	 *            initial y position
	 * @param y_velocity
	 *            initial y velocity
	 */
	public State(double y_position, double y_velocity) {

		yPosition = y_position;
		yVelocity = y_velocity;
	}

}
