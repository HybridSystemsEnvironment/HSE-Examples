
package consensus.hybridsystem;

import edu.ucsc.cross.hse.core.modeling.HybridSys;
import edu.ucsc.cross.hse.core.modeling.JumpSet;
import edu.ucsc.cross.hse.core.network.Network;

public class Dp implements JumpSet<State> {

	/**
	 * Consensus network parameters
	 */
	public Parameters params;

	/**
	 * Agent connection network
	 */
	public Network<HybridSys<State>> network;

	/**
	 * Constructor for the agent system
	 * 
	 * @param state
	 *            agent state
	 * @param network
	 *            agent network (shared)
	 * @param params
	 *            consensus network parameters
	 */
	public Dp(Network<HybridSys<State>> network, Parameters params) {

		this.params = params;
		this.network = network;
	}

	/**
	 * Flow map
	 * 
	 * @param x
	 *            current state
	 * @param x_dot
	 *            state derivative
	 */
	@Override
	public boolean D(State x) {

		if (params.getSynchronizationAgent(x, network).communicationTimer <= 0.0) {
			return true;
		}
		return false;
	}

}
