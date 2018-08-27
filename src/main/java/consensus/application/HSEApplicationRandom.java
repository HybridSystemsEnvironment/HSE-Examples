
package consensus.application;

import java.util.ArrayList;

import consensus.hybridsystem.Cp;
import consensus.hybridsystem.Dp;
import consensus.hybridsystem.Fp;
import consensus.hybridsystem.Gp;
import consensus.hybridsystem.Parameters;
import consensus.hybridsystem.State;
import edu.ucsc.cross.hse.core.environment.HSEnvironment;
import edu.ucsc.cross.hse.core.environment.SystemSet;
import edu.ucsc.cross.hse.core.modeling.HybridSys;
import edu.ucsc.cross.hse.core.network.Network;

/**
 * The main class of the consensus network application that prepares and
 * operates the environment, and generates figure(s).
 * 
 * @author Brendan Short
 *
 */
public class HSEApplicationRandom {

	/**
	 * Main method for running application
	 * 
	 * @param args
	 *            none
	 */
	public static void main(String args[]) {

		// load console settings
		ConfigurationLoader.loadConsoleSettings();
		// initialize environment
		HSEnvironment environment = new HSEnvironment();
		// load environment settings
		ConfigurationLoader.loadEnvironmentSettings(environment);
		// load environment content
		environment.loadSystems(generateHybridSys(22, 3, false, .3, 0.5, 1.0));
		// run environment
		environment.run();
		// process results
		PostProcessor.processEnvironmentData(environment);

	}

	/**
	 * Generates consensus agent systems with randomized values and connections
	 * 
	 * @param num_nodes
	 *            number of agent systems to generate
	 * @param num_connections
	 *            number of connections for each agent
	 * @param synchronous
	 *            flag indicating synchronous if true, or asynchronous if false
	 * @param flow_gain
	 *            flow gain constant for the control law
	 * @param controller_gain
	 *            jump gain constant for the control law
	 * @param minimum_comm
	 *            minimum duration between communication events
	 * @param maximum_comm
	 *            maximum duration between communication events
	 * @return set of agent systems
	 */
	public static SystemSet generateHybridSys(int num_nodes, int num_connections, boolean synchronous,
			double controller_gain, double min_communication_time, double max_communication_time) {

		ArrayList<HybridSys<State>> systems = new ArrayList<HybridSys<State>>();
		Parameters params = new Parameters(controller_gain, min_communication_time, max_communication_time,
				synchronous);

		// Initialize network
		Network<HybridSys<State>> network = new Network<HybridSys<State>>(false);
		// Initialize three consensus agent systems

		for (int i = 0; i < num_nodes; i++) {
			State agent = new State(Math.random(), Math.random(), Math.random() + .05);
			HybridSys<State> system = new HybridSys<State>(agent, new Fp(network, params), new Gp(network, params),
					new Cp(), new Dp(network, params), params);
			systems.add(system);
		}

		createRandomlyConnectedNetwork(network, systems, num_connections);
		SystemSet systemz = new SystemSet(systems.toArray(new HybridSys[systems.size()]));
		return systemz;

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
	public static Network<HybridSys<State>> createRandomlyConnectedNetwork(Network<HybridSys<State>> network,
			ArrayList<HybridSys<State>> systems, int num_connections) {

		ArrayList<HybridSys<State>> conns = new ArrayList<HybridSys<State>>(systems);

		for (HybridSys<State> self : systems) {

			for (int coni = 0; coni < num_connections; coni++) {
				HybridSys<State> connect = ((HybridSys<State>) conns.get(0));

				while (connect.equals(self)) {
					connect = ((HybridSys<State>) conns.get(Math.round(conns.size()) - 1));
				}
				network.connect(self, connect);
				conns.remove(connect);
				if (conns.size() <= 1) {
					conns.clear();
					conns.addAll(systems);
				}
			}
		}
		return network;
	}

}
