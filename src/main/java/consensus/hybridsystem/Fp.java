
package consensus.hybridsystem;

import edu.ucsc.cross.hse.core.modeling.FlowMap;
import edu.ucsc.cross.hse.core.modeling.HybridSys;
import edu.ucsc.cross.hse.core.network.Network;

public class Fp implements FlowMap<State> {

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
	public Fp(Network<HybridSys<State>> network, Parameters params) {

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
	public void F(State x, State x_dot) {

		x_dot.systemValue = x.controllerValue;
		x_dot.controllerValue = 0.0;
		if (x.equals(params.getSynchronizationAgent(x, network))) {
			x_dot.communicationTimer = -1.0;
		}
	}

}