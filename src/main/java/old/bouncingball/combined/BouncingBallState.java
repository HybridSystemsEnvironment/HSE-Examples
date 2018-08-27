package old.bouncingball.combined;

import edu.ucsc.cross.hse.core.modeling.DataStructure;

/**
 * A data structure defining the state of a bouncing ball.
 * 
 * @author Brendan Short
 *
 */
public class BouncingBallState extends DataStructure {

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
	public BouncingBallState(double y_position, double y_velocity) {
		yPosition = y_position;
		yVelocity = y_velocity;
	}

}
