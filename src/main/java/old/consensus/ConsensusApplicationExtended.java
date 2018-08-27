
package old.consensus;

import java.util.ArrayList;

import org.jfree.chart.ChartPanel;

import edu.ucsc.cross.hse.core.chart.ChartUtils;
import edu.ucsc.cross.hse.core.environment.EnvironmentSettings;
import edu.ucsc.cross.hse.core.environment.HSEnvironment;
import edu.ucsc.cross.hse.core.environment.SystemSet;
import edu.ucsc.cross.hse.core.figure.Figure;
import edu.ucsc.cross.hse.core.figure.GraphicFormat;
import edu.ucsc.cross.hse.core.integrator.DormandPrince853IntegratorFactory;
import edu.ucsc.cross.hse.core.logging.Console;
import edu.ucsc.cross.hse.core.logging.ConsoleSettings;
import edu.ucsc.cross.hse.core.modeling.HybridSystem;
import edu.ucsc.cross.hse.core.network.Network;
import edu.ucsc.cross.hse.core.specification.DomainPriority;
import edu.ucsc.cross.hse.core.trajectory.HybridTime;
import edu.ucsc.cross.hse.core.trajectory.TrajectorySet;

/**
 * The main class of the consensus network application that prepares and
 * operates the environment, and generates figure(s).
 * 
 * @author Brendan Short
 *
 */
public class ConsensusApplicationExtended {

	/**
	 * Main method for running application
	 * 
	 * @param args
	 *            none
	 */
	public static void main(String args[]) {

		// Load console settings
		loadConsoleSettings();
		// Create set of connected agents
		SystemSet systems = generateConsensusAgentSystems(10, 5, false, .3, .3, 1.0);
		// Create configured settings
		EnvironmentSettings settings = getEnvironmentSettings();
		// Create loaded environment
		HSEnvironment environment = HSEnvironment.create(systems, settings);
		// Run simulation and store result trajectories
		TrajectorySet trajectories = environment.run();
		 
		// generate figure
				// Create figure w:800 h:600
				Figure figure = new Figure(800, 600, "Consensus Network Simulation");

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
	 * Creates the configured environment settings
	 * 
	 * @return EnvironmentSettings
	 */
	public static EnvironmentSettings getEnvironmentSettings() {

		// Create engine settings
		EnvironmentSettings settings = new EnvironmentSettings();
		// Specify general parameter values
		settings.maximumJumps = 10000;
		settings.maximumTime = 25;
		settings.dataPointInterval = .5;
		settings.eventHandlerMaximumCheckInterval = 1E-3;
		settings.eventHandlerConvergenceThreshold = 1E-9;
		settings.maxEventHandlerIterations = 100;
		settings.domainPriority = DomainPriority.JUMP;
		settings.storeNonPrimativeData = false;
		// Specify integrator parameter values
		double odeMaximumStepSize = 1e-1;
		double odeMinimumStepSize = 1e-3;
		double odeRelativeTolerance = 1.0e-6;
		double odeSolverAbsoluteTolerance = 1.0e-6;
		// Create and store integrator factory
		settings.integrator = new DormandPrince853IntegratorFactory(odeMinimumStepSize, odeMaximumStepSize,
				odeRelativeTolerance, odeSolverAbsoluteTolerance);
		// Return configured settings
		return settings;
	}

	/**
	 * Creates and loads console settings
	 */
	public static void loadConsoleSettings() {

		// Create new console settings
		ConsoleSettings console = new ConsoleSettings();
		// Configure message type visibility
		console.printInfo = true;
		console.printDebug = false;
		console.printWarning = true;
		console.printError = true;
		// Configure status messages
		console.printIntegratorExceptions = false;
		console.printStatusInterval = 10.0;
		console.printProgressIncrement = 10;
		// Configure input and output handling
		console.printLogToFile = true;
		console.terminateAtInput = true;
		// Load configured settings
		Console.setSettings(console);
	}

	/**
	 * Generate a figure with all state elements
	 * 
	 * @param solution
	 *            trajectory set containing data to load into figure
	 * @return a figure displaying all state elements
	 */
	public static Figure generateFullStateFigure(TrajectorySet solution) {

		// Create figure w:800 h:600
		Figure figure = new Figure(800, 600);
		// Assign title to figure
		figure.getTitle().setText("Consensus Network Simulation");
		// Create charts
		ChartPanel xPos = ChartUtils.createPanel(solution, HybridTime.TIME, "systemValue");
		ChartPanel yPos = ChartUtils.createPanel(solution, HybridTime.TIME, "controllerValue");
		ChartPanel xVel = ChartUtils.createPanel(solution, HybridTime.TIME, "communicationTimer");
		// Label chart axis and configure legend visibility
		ChartUtils.configureLabels(xPos, "Time (sec)", "System Value", null, false);
		ChartUtils.configureLabels(yPos, "Time (sec)", "Controller Value", null, false);
		ChartUtils.configureLabels(xVel, "Time (sec)", "Communication Timer", null, false);
		// Add charts to figure
		figure.addComponent(0, 0, xPos);
		figure.addComponent(0, 1, xVel);
		figure.addComponent(0, 2, yPos);
		// Return generated figure
		return figure;
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
	public static SystemSet generateConsensusAgentSystems(int num_nodes, int num_connections, boolean synchronous,
			double controller_gain, double min_communication_time, double max_communication_time) {

		SystemSet systems = new SystemSet();
		ConsensusParameters params = new ConsensusParameters(controller_gain, min_communication_time,
				max_communication_time, synchronous);

		for (int i = 0; i < num_nodes; i++) {
			ConsensusAgentState agent = new ConsensusAgentState(Math.random(), Math.random(), Math.random() + .05);
			ConsensusAgentSystem system = new ConsensusAgentSystem(agent, null, params);
			systems.add(system);
		}

		ConsensusApplicationExtended.createRandomlyConnectedNetwork(systems, num_connections);

		return systems;

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
	public static Network<ConsensusAgentState> createRandomlyConnectedNetwork(SystemSet systems, int num_connections) {

		Network<ConsensusAgentState> network = new Network<ConsensusAgentState>(true);
		ArrayList<HybridSystem<?>> conns = new ArrayList<HybridSystem<?>>(systems.getSystems());

		for (HybridSystem<?> node : systems.getSystems()) {
			ConsensusAgentSystem self = ((ConsensusAgentSystem) node);
			self.network = network;
			for (int coni = 0; coni < num_connections; coni++) {
				ConsensusAgentSystem connect = ((ConsensusAgentSystem) conns.get(0));
				connect.network = network;
				while (connect.getComponents().getState().equals(self.getComponents().getState())) {
					connect = ((ConsensusAgentSystem) conns.get(Math.round(conns.size()) - 1));
				}
				network.connect(self.getComponents().getState(), connect.getComponents().getState());
				conns.remove(connect);
				if (conns.size() <= 1) {
					conns.clear();
					conns.addAll(systems.getSystems());
				}
			}
		}
		return network;
	}

}
