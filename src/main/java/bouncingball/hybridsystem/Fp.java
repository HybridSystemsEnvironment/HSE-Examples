
package bouncingball.hybridsystem;

import edu.ucsc.cross.hse.core.modeling.FlowMap;

/**
 * Flow map implementation
 */
public class Fp implements FlowMap<State> {

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
	public Fp(Parameters parameters) {

		this.parameters = parameters;
	}

	/**
	 * Flow map computation
	 */
	@Override
	public void F(State x, State x_dot) {

		x_dot.yPosition = x.yVelocity;
		x_dot.yVelocity = -parameters.gravityConstant;
	}

}
