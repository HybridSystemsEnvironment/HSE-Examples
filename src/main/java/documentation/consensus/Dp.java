
package documentation.consensus;

import edu.ucsc.cross.hse.core.modeling.HybridSys;
import edu.ucsc.cross.hse.core.modeling.JumpSet;
import edu.ucsc.cross.hse.core.network.Network;

public class Dp implements JumpSet<AgentState> {

	/**
	 * Consensus network parameters
	 */
	public ConsensusParameters params;

	/**
	 * Agent connection network
	 */
	public Network<HybridSys<AgentState>> network;

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
	public Dp(Network<HybridSys<AgentState>> network, ConsensusParameters params) {

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
	public boolean D(AgentState x) {

		if (params.getSynchronizationAgent(x, network).communicationTimer <= 0.0) {
			return true;
		}
		return false;
	}

}
