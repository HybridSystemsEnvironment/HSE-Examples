
package bouncingball.hybridsystem;

import edu.ucsc.cross.hse.core.modeling.FlowSet;

/**
 * Flow set implementation
 */
public class Cp implements FlowSet<State> {

	/**
	 * Jump set computation
	 */
	@Override
	public boolean C(State x) {

		return x.yPosition > 0.0 || ((x.yPosition == 0.0) && (x.yVelocity > 0));
	}

}
