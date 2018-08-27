
package old.documentation.consensus;

import edu.ucsc.cross.hse.core.modeling.FlowMap;
import edu.ucsc.cross.hse.core.modeling.HybridSys;
import edu.ucsc.cross.hse.core.network.Network;

public class Fp implements FlowMap<AgentState> {

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
	public Fp(Network<HybridSys<AgentState>> network, ConsensusParameters params) {

		this.params = params;
		this.network = network;
	}

	/**
	 * Flow map
	 * 
	 * @param x
	 *            current state
	 * @param x_dot
	 *            derivative
	 */
	@Override
	public void F(AgentState x, AgentState x_dot) {

		x_dot.systemValue = x.controllerValue;
		x_dot.controllerValue = 0.0;
		if (x.equals(params.getSynchronizationAgent(x, network))) {
			x_dot.communicationTimer = -1.0;
		}
	}

}