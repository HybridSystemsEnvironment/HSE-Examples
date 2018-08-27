
package old.documentation.consensus;

import java.util.ArrayList;

import edu.ucsc.cross.hse.core.environment.EnvironmentSettings;
import edu.ucsc.cross.hse.core.environment.HSEnvironment;
import edu.ucsc.cross.hse.core.environment.SystemSet;
import edu.ucsc.cross.hse.core.figure.Figure;
import edu.ucsc.cross.hse.core.figure.GraphicFormat;
import edu.ucsc.cross.hse.core.modeling.HybridSys;
import edu.ucsc.cross.hse.core.network.Network;
import edu.ucsc.cross.hse.core.trajectory.HybridTime;
import edu.ucsc.cross.hse.core.trajectory.TrajectorySet;

/**
 * The main class of the consensus network application that prepares and
 * operates the environment, and generates figure(s).
 * 
 * @author Brendan Short
 *
 */
public class ConsensusApplicationRandomGenerated {

	/**
	 * Main method for running application
	 * 
	 * @param args
	 *            none
	 */
	public static void main(String args[]) {

		// Create set of connected agents
		SystemSet systems = generateHybridSys(15, 3, false, .3, 1.5, 2.0);
		// Create configured settings
		EnvironmentSettings settings = new EnvironmentSettings();
		// Create loaded environment
		HSEnvironment environment = HSEnvironment.create(systems, settings);
		// Run simulation and store result trajectories
		TrajectorySet solution = environment.run(8.0, 10000);
		environment.getSettings().dataPointInterval = .3;
		// generate figure
		// Create figure w:800 h:600
		Figure figure = new Figure(650, 800, "Consensus Network Simulation");

		// Create charts and add to figure
		figure.addChart(0, 0, solution, HybridTime.TIME, "systemValue", "Time (sec)", "System Value", null, false);
		figure.addChart(0, 1, solution, HybridTime.TIME, "controllerValue", "Time (sec)", "Controller Value", null,
				false);
		figure.addChart(0, 2, solution, HybridTime.TIME, "communicationTimer", "Time (sec)", "Communication Timer",
				null, false);
		// export figure to pdf
		figure.exportToFile(GraphicFormat.PDF);
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

		ArrayList<HybridSys<AgentState>> systems = new ArrayList<HybridSys<AgentState>>();
		ConsensusParameters params = new ConsensusParameters(controller_gain, min_communication_time,
				max_communication_time, synchronous);

		// Initialize network
		Network<HybridSys<AgentState>> network = new Network<HybridSys<AgentState>>(false);
		// Initialize three consensus agent systems

		for (int i = 0; i < num_nodes; i++) {
			AgentState agent = new AgentState(Math.random(), Math.random(), Math.random() + .05);
			HybridSys<AgentState> system = new HybridSys<AgentState>(agent, new Fp(network, params),
					new Gp(network, params), new Cp(), new Dp(network, params), params);
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
	public static Network<HybridSys<AgentState>> createRandomlyConnectedNetwork(Network<HybridSys<AgentState>> network,
			ArrayList<HybridSys<AgentState>> systems, int num_connections) {

		ArrayList<HybridSys<AgentState>> conns = new ArrayList<HybridSys<AgentState>>(systems);

		for (HybridSys<AgentState> self : systems) {

			for (int coni = 0; coni < num_connections; coni++) {
				HybridSys<AgentState> connect = ((HybridSys<AgentState>) conns.get(0));

				while (connect.equals(self)) {
					connect = ((HybridSys<AgentState>) conns.get(Math.round(conns.size()) - 1));
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
