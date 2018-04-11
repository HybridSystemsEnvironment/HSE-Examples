package bouncingball;

import edu.ucsc.cross.hse.core.modeling.DataStructure;

public class BouncingBallState extends DataStructure
{

	public double xPosition; // horizontal position
	public double yPosition; // vertical position
	public double xVelocity; // horizontal velocity
	public double yVelocity; // vertical velocity

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
	public BouncingBallState(double x_position, double y_position, double x_velocity, double y_velocity)
	{
		xPosition = x_position;
		yPosition = y_position;
		xVelocity = x_velocity;
		yVelocity = y_velocity;
	}

}