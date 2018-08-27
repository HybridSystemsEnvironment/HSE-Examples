
package bouncingball.hybridsystem;

import edu.ucsc.cross.hse.core.modeling.JumpSet;

/**
 * Jump set implementation.
 */
public class Dp implements JumpSet<State> {

	/**
	 * Jump set computation
	 */
	@Override
	public boolean D(State x) {

		return x.yPosition <= 0.0 && x.yVelocity < 0.0;
	}

}
