package bouncingballxy;

import edu.ucsc.cross.hse.core.modeling.DataStructure;

/**
 * Bouncing ball parameter class
 * 
 * @author Brendan Short
 *
 */
public class BouncingBallParameters extends DataStructure
{

	/**
	 * Coefficient of restitution
	 */
	public double restitutionCoefficient;

	/**
	 * Gravity constant
	 */
	public double gravityConstant;

	/**
	 * Construct the parameters of the bouncing ball environment
	 * 
	 * @param restitution_coefficient
	 *            determines energy absorbed at each bounce
	 * @param gravity_constant
	 *            defines acceleration due to gravity
	 */
	public BouncingBallParameters(double restitution_coefficient, double gravity_constant)
	{
		restitutionCoefficient = restitution_coefficient;
		gravityConstant = gravity_constant;
	}
}
