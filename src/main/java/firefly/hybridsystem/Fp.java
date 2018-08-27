
package firefly.hybridsystem;

import edu.ucsc.cross.hse.core.modeling.FlowMap;

/**
 * Firefly flow map
 */
public class Fp implements FlowMap<State> {

	/**
	 * Flow map computation
	 */
	@Override
	public void F(State x, State x_dot) {

		x_dot.tau = 1.0;
	}
}
