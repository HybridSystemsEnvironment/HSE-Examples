
package bouncingball.hybridsystem;

import edu.ucsc.cross.hse.core.modeling.JumpMap;

/**
 * Jump map implementation
 */
public class Gp implements JumpMap<State> {

	/**
	 * Bouncing ball parameters
	 */
	public Parameters parameters;

	/**
	 * Constructor to load the parameters
	 * 
	 * @param parameters
	 *            bouncing ball parameters
	 */
	public Gp(Parameters parameters) {

		this.parameters = parameters;
	}

	/**
	 * Jump map computation
	 */
	@Override
	public void G(State x, State x_plus) {

		x_plus.yPosition = 0.0;
		x_plus.yVelocity = -x.yVelocity * parameters.restitutionCoefficient;
	}

}
