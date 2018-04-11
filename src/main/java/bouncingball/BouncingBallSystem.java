package bouncingball;

import edu.ucsc.cross.hse.core.modeling.HybridSystem;

/**
 * Bouncing ball hybrid system class
 * 
 * @author Brendan Short
 *
 */
public class BouncingBallSystem extends HybridSystem<BouncingBallState>
{

	/**
	 * System parameters
	 */
	private BouncingBallParameters parameters;

	/**
	 * Constructor for bouncing ball system
	 * 
	 * @param state
	 *            bouncing ball state
	 * @param parameters
	 *            bouncing ball parameters
	 */
	public BouncingBallSystem(BouncingBallState state, BouncingBallParameters parameters)
	{
		super(state, parameters);
		this.parameters = parameters;
	}

	/**
	 * Flow set
	 * 
	 * @param x
	 *            current state
	 */
	@Override
	public boolean C(BouncingBallState x)
	{
		return x.yPosition >= 0.0;
	}

	/**
	 * Flow map
	 * 
	 * @param x
	 *            current state
	 * @param x_dot
	 *            state derivative
	 */
	@Override
	public void F(BouncingBallState x, BouncingBallState x_dot)
	{
		x_dot.xPosition = x.xVelocity;
		x_dot.yPosition = x.yVelocity;
		x_dot.xVelocity = 0;
		x_dot.yVelocity = -parameters.gravityConstant;
	}

	/**
	 * Jump set
	 * 
	 * @param x
	 *            current state
	 */
	@Override
	public boolean D(BouncingBallState x)
	{
		return x.yPosition <= 0.0 && x.yVelocity < 0.0;
	}

	/**
	 * Jump map
	 * 
	 * @param x
	 *            current state
	 * @param x_dot
	 *            updated state
	 */
	@Override
	public void G(BouncingBallState x, BouncingBallState x_plus)
	{
		x_plus.xPosition = x.xPosition;
		x_plus.yPosition = 0.0;
		x_plus.xVelocity = x.xVelocity;
		x_plus.yVelocity = -x.yVelocity * parameters.restitutionCoefficient;

	}

}
