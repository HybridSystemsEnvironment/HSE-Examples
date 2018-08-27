
package firefly.hybridsystem;

import java.util.ArrayList;

import edu.ucsc.cross.hse.core.modeling.FlowSet;
import edu.ucsc.cross.hse.core.modeling.HybridSys;
import edu.ucsc.cross.hse.core.network.Network;

/**
 * Firefly flow set
 */
public class Cp implements FlowSet<State> {

	/**
	 * Fly network
	 */
	public Network<HybridSys<State>> flyNetwork;

	/**
	 * Constructor for the flow set
	 * 
	 * @param fly_network
	 *            fly network
	 */
	public Cp(Network<HybridSys<State>> fly_network) {

		flyNetwork = fly_network;
	}

	/**
	 * Flow set computation
	 */
	@Override
	public boolean C(State x) {

		boolean flow = false;
		if (0 <= x.tau && x.tau < 1.0) {
			flow = true;
		}
		HybridSys<State> parentSys = x.getParentSystem(flyNetwork.getAllVertices());
		ArrayList<HybridSys<State>> systems = flyNetwork.getConnected(parentSys);
		for (HybridSys<State> flySys : systems) {
			if (0 <= flySys.getState().tau && flySys.getState().tau < 1.0) {
				flow = flow || true;
			}
		}
		return flow;
	}
}
