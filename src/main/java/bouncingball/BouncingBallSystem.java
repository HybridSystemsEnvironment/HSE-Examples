package bouncingball;

import edu.ucsc.cross.hse.core.modeling.HybridSystem;

public class BouncingBallSystem extends HybridSystem<BouncingBallState>
{

	private BouncingBallParameters parameters;

	public BouncingBallSystem(BouncingBallState state, BouncingBallParameters parameters)
	{
		super(state, parameters);
		this.parameters = parameters;
	}

	@Override
	public boolean C(BouncingBallState x)
	{
		return x.yPosition >= 0.0;
	}

	@Override
	public void F(BouncingBallState x, BouncingBallState x_dot)
	{
		x_dot.xPosition = x.xVelocity;
		x_dot.yPosition = x.yVelocity;
		x_dot.xVelocity = 0;
		x_dot.yVelocity = -parameters.gravityConstant;
	}

	@Override
	public boolean D(BouncingBallState x)
	{
		return x.yPosition <= 0.0 && x.yVelocity < 0.0;
	}

	@Override
	public void G(BouncingBallState x, BouncingBallState x_plus)
	{
		x_plus.xPosition = x.xPosition;
		x_plus.yPosition = 0.0;
		x_plus.xVelocity = x.xVelocity;
		x_plus.yVelocity = -x.yVelocity * parameters.restitutionCoefficient;

	}

}
