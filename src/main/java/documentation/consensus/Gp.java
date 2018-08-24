
package documentation.consensus;

import edu.ucsc.cross.hse.core.modeling.HybridSys;
import edu.ucsc.cross.hse.core.modeling.JumpMap;
import edu.ucsc.cross.hse.core.network.Network;

public class Gp implements JumpMap<AgentState> {

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
	public Gp(Network<HybridSys<AgentState>> network, ConsensusParameters params) {

		this.params = params;
		this.network = network;
	}

	/**
	 * Jump map
	 * 
	 * @param x
	 *            current state
	 * @param x_dot
	 *            updated state
	 */
	@Override
	public void G(AgentState x, AgentState x_plus) {

		double controllerUpdate = 0.0;
		for (HybridSys<AgentState> connectedAgent : network
				.getConnected(x.getParentSystem(network.getAllVertices()))) {
			controllerUpdate += -params.controllerGain * (x.systemValue - connectedAgent.getState().systemValue);
		}
		if (x.communicationTimer <= 0.0) {
			Double nextEvent = Math.random()
					* (params.maximumCommunicationInterval - params.minimumCommunicationInterval)
					+ params.minimumCommunicationInterval;
			x_plus.communicationTimer = nextEvent;
		}

		x_plus.controllerValue = controllerUpdate;
		x_plus.systemValue = x.systemValue;
	}

}
