
package simplemath.hybridsystem;

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

		return x.value == 1.0 || x.value == 2.0;
	}

}
