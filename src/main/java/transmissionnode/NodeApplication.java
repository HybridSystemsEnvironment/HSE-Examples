package transmissionnode;

import org.jfree.chart.ChartPanel;

import edu.ucsc.cross.hse.core.chart.ChartUtils;
import edu.ucsc.cross.hse.core.environment.EnvironmentSettings;
import edu.ucsc.cross.hse.core.environment.HSEnvironment;
import edu.ucsc.cross.hse.core.environment.SystemSet;
import edu.ucsc.cross.hse.core.figure.Figure;
import edu.ucsc.cross.hse.core.figure.GraphicFormat;
import edu.ucsc.cross.hse.core.file.FileBrowser;
import edu.ucsc.cross.hse.core.integrator.DormandPrince853IntegratorFactory;
import edu.ucsc.cross.hse.core.logging.Console;
import edu.ucsc.cross.hse.core.logging.ConsoleSettings;
import edu.ucsc.cross.hse.core.specification.DomainPriority;
import edu.ucsc.cross.hse.core.trajectory.HybridTime;
import edu.ucsc.cross.hse.core.trajectory.TrajectorySet;
import network.IdealNetwork;

/**
 * The main class of the consensus network application that prepares and
 * operates the environment, and generates figure(s).
 * 
 * @author Brendan Short
 *
 */
public class NodeApplication {

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
		SystemSet systems = generateNodeSystems(.3, 1.0);
		// Create configured settings
		EnvironmentSettings settings = getEnvironmentSettings();
		// Create loaded environment
		HSEnvironment environment = HSEnvironment.create(systems, settings);
		// Run simulation and store result trajectories
		TrajectorySet trajectories = environment.run();
		// Generate figure and display in window
		generateFullStateFigure(trajectories).exportToFile(FileBrowser.save(), GraphicFormat.PDF);// .display();
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
		Figure figure = new Figure(600, 800);
		// Assign title to figure
		figure.getTitle().setText("Transmission Node Network Simulation");
		// Create charts
		ChartPanel received = ChartUtils.createPanel(solution, HybridTime.TIME, "packetsReceived");
		ChartPanel transmitted = ChartUtils.createPanel(solution, HybridTime.TIME, "packetsTransmitted");
		ChartPanel timer = ChartUtils.createPanel(solution, HybridTime.TIME, "transmissionTimer");
		// Label chart axis and configure legend visibility
		ChartUtils.configureLabels(received, "Time (sec)", "Packets Received", null, false);
		ChartUtils.configureLabels(transmitted, "Time (sec)", "Packets Transmitted", null, false);
		ChartUtils.configureLabels(timer, "Time (sec)", "Transmission Timer", null, true);
		// Add charts to figure
		figure.addComponent(0, 0, received);
		figure.addComponent(0, 1, transmitted);
		figure.addComponent(0, 2, timer);
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
	public static SystemSet generateNodeSystems(double min_time, double max_time) {
		// Create the system set
		SystemSet systems = new SystemSet();
		// Create the node parameters
		NodeParameters params = new NodeParameters(min_time, max_time);
		// Create the network
		IdealNetwork<NodeSystem> network = new IdealNetwork<NodeSystem>();
		// Create the nodes
		NodeSystem nodeA = new NodeSystem(new NodeState(0, 0, Math.random()), params, network);
		NodeSystem nodeB = new NodeSystem(new NodeState(0, 0, Math.random()), params, network);
		NodeSystem nodeC = new NodeSystem(new NodeState(0, 0, Math.random()), params, network);
		// Establish connections between the nodes
		network.connect(nodeA, nodeB);
		network.connect(nodeA, nodeC);
		network.connect(nodeB, nodeC);
		network.connect(nodeC, nodeA);
		// Add the nodes to the system set
		systems.add(nodeA, nodeB, nodeC);
		// Return the loaded system set
		return systems;

	}

}
