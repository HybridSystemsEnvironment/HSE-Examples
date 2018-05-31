package bouncingball4d;

import edu.ucsc.cross.hse.core.modeling.DataStructure;

/**
 * A data structure defining the state of a bouncing ball.
 * 
 * @author Brendan Short
 *
 */
public class BouncingBallState extends DataStructure {

	/**
	 * horizontal position
	 */
	public double xPosition;
	/**
	 * vertical position
	 */
	public double yPosition;
	/**
	 * horizontal velocity
	 */
	public double xVelocity;
	/**
	 * vertical velocity
	 */
	public double yVelocity;

	/**
	 * Construct the state with the given initial conditions
	 * 
	 * @param x_position
	 *            initial x position
	 * @param y_position
	 *            initial y position
	 * @param x_velocity
	 *            initial x velocity
	 * @param y_velocity
	 *            initial y velocity
	 */
	public BouncingBallState(double x_position, double y_position, double x_velocity, double y_velocity) {
		xPosition = x_position;
		yPosition = y_position;
		xVelocity = x_velocity;
		yVelocity = y_velocity;
	}

}
