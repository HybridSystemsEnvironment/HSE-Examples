package consensus;

import java.util.ArrayList;

import edu.ucsc.cross.hse.core.environment.SystemSet;
import edu.ucsc.cross.hse.core.modeling.HybridSystem;
import network.DirectNetwork;

public class ConsensusNetwork {

	private DirectNetwork<ConsensusAgentState> network;

	public ConsensusNetwork() {
		network = new DirectNetwork<ConsensusAgentState>();
	}

	/**
	 * Connects each agent in a network to a specified number of other agents at
	 * random
	 * 
	 * @param network
	 *            network containing all agents as vertices
	 * @param num_connections
	 *            number of connections to assign to each agent
	 */
	public void connectAgentsRandomly(SystemSet systems, int num_connections) {
		ArrayList<HybridSystem<?>> conns = new ArrayList<HybridSystem<?>>(systems.getSystems());

		for (HybridSystem<?> node : systems.getSystems()) {
			for (int coni = 0; coni < num_connections; coni++) {
				ConsensusAgentState connect = ((ConsensusAgentSystem) conns.get(0)).getComponents().getState();
				while (connect.equals(((ConsensusAgentSystem) node).getComponents().getState())) {
					connect = ((ConsensusAgentSystem) conns.get(Math.round(conns.size()) - 1)).getComponents()
							.getState();
				}
				network.connect(((ConsensusAgentSystem) node).getComponents().getState(), connect);
				conns.remove(connect);
				if (conns.size() <= 1) {
					conns.clear();
					conns.addAll(systems.getSystems());
				}
			}
		}
	}

	public DirectNetwork<ConsensusAgentState> getNetwork() {
		return network;
	}

}
