
package bouncingball.combined;

import org.jfree.chart.ChartPanel;

import edu.ucsc.cross.hse.core.chart.ChartUtils;
import edu.ucsc.cross.hse.core.environment.EnvironmentSettings;
import edu.ucsc.cross.hse.core.environment.HSEnvironment;
import edu.ucsc.cross.hse.core.environment.SystemSet;
import edu.ucsc.cross.hse.core.figure.Figure;
import edu.ucsc.cross.hse.core.integrator.DormandPrince853IntegratorFactory;
import edu.ucsc.cross.hse.core.logging.Console;
import edu.ucsc.cross.hse.core.logging.ConsoleSettings;
import edu.ucsc.cross.hse.core.specification.DomainPriority;
import edu.ucsc.cross.hse.core.trajectory.HybridTime;
import edu.ucsc.cross.hse.core.trajectory.TrajectorySet;
import edu.ucsc.cross.hse.core.variable.RandomVariable;

/**
 * The main class of the bouncing ball application that prepares and operates
 * the environment, and generates figure(s).
 * 
 * @author Brendan Short
 *
 */
public class BouncingBallApplicationExtended {

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
		SystemSet systems = generateBouncingBallSystems(3, .99, 9.81, 2, 5, -1, 1);
		// Create configured settings
		EnvironmentSettings settings = getEnvironmentSettings();
		// Create loaded environment
		HSEnvironment environment = HSEnvironment.create(systems, settings);
		// Run simulation and store result trajectories
		TrajectorySet trajectories = environment.run();
		// Generate figure and display in window
		generateVerticalStateFigure(trajectories).display();
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
		settings.dataPointInterval = .05;
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
	 * Generate a figure with the vertical (y position and velocity) bouncing ball
	 * state elements
	 * 
	 * @param solution
	 *            trajectory set containing data to load into figure
	 * @return a figure displaying all vertical bouncing ball state elements
	 */
	public static Figure generateVerticalStateFigure(TrajectorySet solution) {

		// Create figure w:1000 h:600
		Figure figure = new Figure(1000, 600);
		// Assign title to figure
		figure.getTitle().setText("Bouncing Ball Simulation");
		// Create charts
		ChartPanel yPos = ChartUtils.createPanel(solution, HybridTime.TIME, "yPosition");
		ChartPanel yVel = ChartUtils.createPanel(solution, HybridTime.TIME, "yVelocity");
		// Label chart axis and configure legend visibility
		ChartUtils.configureLabels(yPos, "Time (sec)", "Y Position (m)", null, false);
		ChartUtils.configureLabels(yVel, "Time (sec)", "Y Velocity (m/s)", null, false);
		// Add charts to figure
		figure.addComponent(0, 0, yPos);
		figure.addComponent(0, 1, yVel);
		// Return generated figure
		return figure;
	}

	/**
	 * Generate a set of bouncing ball systems
	 * 
	 * @param quantity
	 *            number of bouncing ball systems to generate
	 * @param restitution_coefficient
	 *            restitution coefficient value
	 * @param gravity_constant
	 *            gravity constant value
	 * @param min_y_pos
	 *            minimum possible y position to generate
	 * @param max_y_pos
	 *            maximum possible y position to generate
	 * @param min_y_vel
	 *            minimum possible y velocity to generate
	 * @param max_y_vel
	 *            maximum possible y velocity to generate
	 * @return system set containing all generated bouncing ball systems
	 */
	public static SystemSet generateBouncingBallSystems(int quantity, double restitution_coefficient,
			double gravity_constant, double min_y_pos, double max_y_pos, double min_y_vel, double max_y_vel) {

		SystemSet systems = new SystemSet();
		BouncingBallParameters physics = new BouncingBallParameters(restitution_coefficient, gravity_constant);
		for (int ballNum = 0; ballNum < quantity; ballNum++) {
			BouncingBallState state = new BouncingBallState(RandomVariable.generate(min_y_pos, max_y_pos),
					RandomVariable.generate(min_y_vel, max_y_vel));
			BouncingBallSystem ballSystem = new BouncingBallSystem(state, physics);
			systems.add(ballSystem);
		}
		return systems;
	}
}