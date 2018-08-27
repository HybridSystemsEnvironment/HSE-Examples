
package firefly.hybridsystem;

import java.util.ArrayList;

import edu.ucsc.cross.hse.core.modeling.HybridSys;
import edu.ucsc.cross.hse.core.modeling.JumpSet;
import edu.ucsc.cross.hse.core.network.Network;

/**
 * Firefly jump set
 */
public class Dp implements JumpSet<State> {

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
	public Dp(Network<HybridSys<State>> fly_network) {

		flyNetwork = fly_network;
	}

	/**
	 * Flow set computation
	 */
	@Override
	public boolean D(State x) {

		boolean jump = false;
		HybridSys<State> parentSys = x.getParentSystem(flyNetwork.getAllVertices());
		ArrayList<HybridSys<State>> systems = flyNetwork.getConnected(parentSys);
		for (HybridSys<State> flySys : systems) {
			if (flySys.getState().tau >= 1.0) {
				jump = true;
			}
		}
		if (x.tau >= 1.0) {
			jump = true;
		}
		return jump;
	}
}
