
package simplemath.hybridsystem;

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

		x_plus.value = 1.0 + (x.value % 2);
	}

}
