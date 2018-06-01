package consensus;

import edu.ucsc.cross.hse.core.modeling.HybridSystem;
import network.IdealNetwork;

/**
 * Consensus agent hybrid system implementation.
 * 
 * @author Brendan Short
 *
 */
public class ConsensusAgentSystem extends HybridSystem<ConsensusAgentState> {

	/**
	 * Consensus network parameters
	 */
	public ConsensusParameters params;

	/**
	 * Agent connection network
	 */
	public IdealNetwork<ConsensusAgentState> network;

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
	public ConsensusAgentSystem(ConsensusAgentState state, IdealNetwork<ConsensusAgentState> network,
			ConsensusParameters params) {
		super(state);
		this.params = params;
		this.network = network;
	}

	/**
	 * Flow set
	 * 
	 * @param x
	 *            current state
	 */
	@Override
	public boolean C(ConsensusAgentState x) {
		if (x.communicationTimer >= 0.0) {
			return true;
		}
		return false;
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
	public boolean D(ConsensusAgentState x) {
		if (getSynchronizationAgent().communicationTimer <= 0.0) {
			return true;
		}
		return false;
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
	public void F(ConsensusAgentState x, ConsensusAgentState x_dot) {
		x_dot.systemValue = x.controllerValue;
		x_dot.controllerValue = 0.0;
		if (this.getComponents().getState().equals(getSynchronizationAgent())) {
			x_dot.communicationTimer = -1.0;
		}
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
	public void G(ConsensusAgentState x, ConsensusAgentState x_plus) {
		double controllerUpdate = 0.0;
		for (ConsensusAgentState connectedAgent : network.getConnections(getComponents().getState())) {
			controllerUpdate += -params.controllerGain * (x.systemValue - connectedAgent.systemValue);
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

	/**
	 * Get the agent whose timer triggers the communication event (one agent in
	 * network if the system is synchronous, or every agent in network if the system
	 * is asynchronous)
	 */
	public ConsensusAgentState getSynchronizationAgent() {

		ConsensusAgentState syncAgent = getComponents().getState();
		if (params.synchronous) {
			syncAgent = this.network.getAllVertices().get(0);
		}
		return syncAgent;
	}
}
