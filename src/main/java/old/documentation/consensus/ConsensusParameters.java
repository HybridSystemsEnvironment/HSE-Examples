
package old.documentation.consensus;

import edu.ucsc.cross.hse.core.modeling.HybridSys;
import edu.ucsc.cross.hse.core.network.Network;

/**
 * Consensus network parameter data structure
 * 
 * @author Brendan Short
 *
 */
public class ConsensusParameters {

	/**
	 * Jump gain constant for the control law
	 */
	public Double controllerGain;

	/**
	 * Minimum duration between communication events
	 */
	public Double minimumCommunicationInterval;

	/**
	 * Maximum duration between communication events
	 */
	public Double maximumCommunicationInterval;

	/**
	 * Flag indicating if the agents are communication synchronously (true) or
	 * asynchrnouosly (false)
	 */
	public boolean synchronous;

	/**
	 * Get the agent whose timer triggers the communication event (one agent in
	 * network if the system is synchronous, or every agent in network if the system
	 * is asynchronous)
	 */
	public AgentState getSynchronizationAgent(AgentState agent,
			Network<HybridSys<AgentState>> network) {

		AgentState syncAgent = agent;
		if (synchronous) {
			syncAgent = network.getAllVertices().get(0).getState();
		}
		return syncAgent;
	}

	/**
	 * Constructor for consensus network parameters
	 * 
	 * @param flow_gain
	 *            flow gain constant for the control law
	 * @param controller_gain
	 *            jump gain constant for the control law
	 * @param minimum_comm
	 *            minimum duration between communication events
	 * @param maximum_comm
	 *            maximum duration between communication events
	 * @param synchronous
	 */
	public ConsensusParameters(Double controller_gain, Double minimum_comm, Double maximum_comm, boolean synchronous) {

		this.controllerGain = controller_gain;
		this.minimumCommunicationInterval = minimum_comm;
		this.maximumCommunicationInterval = maximum_comm;
		this.synchronous = synchronous;
	}

}
