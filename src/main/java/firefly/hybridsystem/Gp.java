
package firefly.hybridsystem;

import edu.ucsc.cross.hse.core.modeling.JumpMap;

/**
 * Firefly jump map
 */
public class Gp implements JumpMap<State> {

	/**
	 * Firefly parameters
	 */
	public Parameters flyParameters;

	/**
	 * Constructor for jump map
	 */
	public Gp(Parameters fly_parameters) {

		flyParameters = fly_parameters;
	}

	/**
	 * Jump map computation
	 */
	@Override
	public void G(State x, State x_plus) {

		if ((1.0 + flyParameters.eConstant) * x.tau < 1) {
			x_plus.tau = (1 + flyParameters.eConstant) * x.tau;
		} else if ((1.0 + flyParameters.eConstant) * x.tau >= 1) {
			x_plus.tau = 0.0;
		} else {
			x_plus.tau = x.tau;
		}
	}
}
