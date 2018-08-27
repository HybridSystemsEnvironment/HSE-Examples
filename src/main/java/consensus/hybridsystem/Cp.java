
package consensus.hybridsystem;

import edu.ucsc.cross.hse.core.modeling.FlowSet;

public class Cp implements FlowSet<State> {

	/**
	 * Flow set
	 * 
	 * @param x
	 *            current state
	 */
	@Override
	public boolean C(State x) {

		if (x.communicationTimer >= 0.0) {
			return true;
		}
		return false;
	}

}
