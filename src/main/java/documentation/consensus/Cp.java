
package documentation.consensus;

import edu.ucsc.cross.hse.core.modeling.FlowSet;

public class Cp implements FlowSet<AgentState> {

	/**
	 * Flow set
	 * 
	 * @param x
	 *            current state
	 */
	@Override
	public boolean C(AgentState x) {

		if (x.communicationTimer >= 0.0) {
			return true;
		}
		return false;
	}

}
