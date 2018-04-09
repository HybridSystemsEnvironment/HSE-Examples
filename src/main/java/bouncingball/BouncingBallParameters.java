package bouncingball;

import edu.ucsc.cross.hse.core.modeling.DataStructure;

public class BouncingBallParameters extends DataStructure
{

	public double restitutionCoefficient; // restitution of coeficcient
	public double gravityConstant; // gravity constant

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
