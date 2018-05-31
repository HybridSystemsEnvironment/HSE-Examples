package consensus;

import org.jfree.chart.ChartPanel;

import edu.ucsc.cross.hse.core.chart.ChartUtils;
import edu.ucsc.cross.hse.core.environment.HSESettings;
import edu.ucsc.cross.hse.core.environment.HSEnvironment;
import edu.ucsc.cross.hse.core.environment.SystemSet;
import edu.ucsc.cross.hse.core.figure.Figure;
import edu.ucsc.cross.hse.core.integrator.DormandPrince853IntegratorFactory;
import edu.ucsc.cross.hse.core.logging.Console;
import edu.ucsc.cross.hse.core.logging.ConsoleSettings;
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
public class ConsensusApplication {

	/**
	 * Main method for running application
	 * 
	 * @param args
	 *            none
	 */
	public static void main(String args[]) {
		// Generate environment
		HSEnvironment environment = generateEnvironment();
		// Run simulation and store result trajectories
		TrajectorySet trajectories = environment.run();
		// Generate figure and display in window
		generateFullStateFigure(trajectories).display();
	}

	/**
	 * Generate the environment
	 * 
	 * @return environment
	 */
	public static HSEnvironment generateEnvironment() {
		HSEnvironment environment = new HSEnvironment();
		SystemSet systems = generateConsensusAgentSystems(10, 5, false, .3, .3, 1.0);
		HSESettings settings = getBouncingBallEnvSettings();
		environment = HSEnvironment.create(systems, settings);
		return environment;
	}

	/**
	 * Creates the configured environment settings
	 * 
	 * @return EnvironmentSettings
	 */
	public static HSESettings getBouncingBallEnvSettings() {
		// Load console settings
		loadConsensusConsoleSettings();
		// Create engine settings
		HSESettings settings = new HSESettings();
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
	 * 
	 * @return console settings
	 */
	public static boolean loadConsensusConsoleSettings() {
		ConsoleSettings console = new ConsoleSettings();
		console.printStatusInterval = 10.0;
		console.printProgressIncrement = 10;
		console.printIntegratorExceptions = false;
		console.printInfo = true;
		console.printDebug = false;
		console.printWarning = true;
		console.printError = true;
		console.printLogToFile = true;
		console.terminateAtInput = true;
		Console.setSettings(console);
		return true;
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
		ConsensusNetwork network = new ConsensusNetwork();
		ConsensusParameters params = new ConsensusParameters(controller_gain, min_communication_time,
				max_communication_time, synchronous);

		for (int i = 0; i < num_nodes; i++) {
			ConsensusAgentState agent = new ConsensusAgentState(Math.random(), Math.random(), Math.random() + .05);
			ConsensusAgentSystem system = new ConsensusAgentSystem(agent, network.getNetwork(), params);
			systems.add(system);
		}

		network.connectAgentsRandomly(systems, num_connections);

		return systems;

	}

	/**
	 * Generate a figure with all state elements
	 * 
	 * @param solution
	 *            trajectory set containing data to load into figure
	 * @return a figure displaying all state elements
	 */
	public static Figure generateFullStateFigure(TrajectorySet solution) {
		Figure figure = new Figure(800, 600);

		ChartPanel xPos = ChartUtils.createPanel(solution, HybridTime.TIME, "systemValue");
		ChartPanel yPos = ChartUtils.createPanel(solution, HybridTime.TIME, "controllerValue");
		ChartPanel xVel = ChartUtils.createPanel(solution, HybridTime.TIME, "communicationTimer");

		figure.addComponent(0, 0, xPos);
		figure.addComponent(0, 1, xVel);
		figure.addComponent(0, 2, yPos);

		ChartUtils.configureLabels(xPos, "Time (sec)", "System Value", null, false);
		ChartUtils.configureLabels(yPos, "Time (sec)", "Controller Value", null, false);
		ChartUtils.configureLabels(xVel, "Time (sec)", "Communication Timer", null, false);

		return figure;
	}

}
