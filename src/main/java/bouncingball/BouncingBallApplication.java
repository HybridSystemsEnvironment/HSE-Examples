package bouncingball;

import org.jfree.chart.ChartPanel;

import edu.ucsc.cross.hse.core.chart.ChartUtils;
import edu.ucsc.cross.hse.core.environment.EnvironmentSettings;
import edu.ucsc.cross.hse.core.environment.ExecutionParameters;
import edu.ucsc.cross.hse.core.environment.HSEnvironment;
import edu.ucsc.cross.hse.core.figure.Figure;
import edu.ucsc.cross.hse.core.logging.Console;
import edu.ucsc.cross.hse.core.logging.ConsoleSettings;
import edu.ucsc.cross.hse.core.modeling.SystemSet;
import edu.ucsc.cross.hse.core.specification.DomainPriority;
import edu.ucsc.cross.hse.core.specification.IntegratorType;
import edu.ucsc.cross.hse.core.trajectory.HybridTime;
import edu.ucsc.cross.hse.core.trajectory.TrajectorySet;
import edu.ucsc.cross.hse.core.variable.RandomVariable;

public class BouncingBallApplication
{

	public static void main(String args[])
	{
		loadBouncingBallConsoleSettings();
		HSEnvironment environment = generateEnvironment();
		environment.run();
		generateVerticalStateFigure(environment.getTrajectories()).display();
		generateFullStateFigure(environment.getTrajectories()).display();
	}

	/**
	 * Generate the bouncing ball environment
	 * 
	 * @return environment
	 */
	public static HSEnvironment generateEnvironment()
	{
		HSEnvironment environment = new HSEnvironment();
		SystemSet systems = getBouncingBallSystems();
		ExecutionParameters parameters = getBouncingBallExeParameters();
		EnvironmentSettings settings = getBouncingBallEnvSettings();

		environment = HSEnvironment.create(systems, parameters, settings);

		return environment;
	}

	/**
	 * Get the set of bouncing ball systems (extra function to define different
	 * configurations that can be toggled by comment)
	 * 
	 * @return system set of bouncing balls
	 */
	public static SystemSet getBouncingBallSystems()
	{
		//params: restitution gravity quantity xPosMin xPosMax xVelMin xVelMax  yPosMin yPosMax yVelMin yVelMax

		//return generateBouncingBallSystems(.97, 9.81, 1, 0, 0, 2, 2, 2, 2, 1, 1); // 1 ball fixed
		//return generateBouncingBallSystems(.99, 9.81, 1, 0, 3, 1, 2, 1, 3, 1, 2); // 1 ball random
		return generateBouncingBallSystems(.99, 9.81, 3, 0, 3, 1, 2, 1, 3, 1, 2); // 3 balls random
	}

	/**
	 * Initializes execution parameters with configuration B
	 * 
	 * @return ExecutionParameters
	 */
	public static ExecutionParameters getBouncingBallExeParameters()
	{
		ExecutionParameters parameters = new ExecutionParameters();
		parameters.maximumJumps = 10000;
		parameters.maximumTime = 8;
		parameters.dataPointInterval = .1;
		return parameters;
	}

	/**
	 * Initializes environment settings with configuration B
	 * 
	 * @return EnvironmentSettings
	 */
	public static EnvironmentSettings getBouncingBallEnvSettings()
	{
		EnvironmentSettings settings = new EnvironmentSettings();
		settings.odeMinimumStepSize = 1e-5;
		settings.odeMaximumStepSize = 1e-3;
		settings.odeSolverAbsoluteTolerance = 1.0e-8;
		settings.odeRelativeTolerance = 1.0e-8;
		settings.eventHandlerMaximumCheckInterval = 1e-6;
		settings.eventHandlerConvergenceThreshold = 1e-9;
		settings.maxEventHandlerIterations = 100;
		settings.integratorType = IntegratorType.DORMAND_PRINCE_853;
		settings.domainPriority = DomainPriority.JUMP;
		settings.storeNonPrimativeData = false;
		return settings;
	}

	/**
	 * Initializes console settings for bouncing ball environment
	 * 
	 * @return consoleSettings
	 */
	public static void loadBouncingBallConsoleSettings()
	{
		ConsoleSettings console = new ConsoleSettings();
		console.printStatusInterval = 10.0;
		console.printProgressIncrement = 10;
		console.printIntegratorExceptions = false;
		console.printInfo = true;
		console.printDebug = true;
		console.printWarning = true;
		console.printError = true;
		console.printLogToFile = true;
		console.terminateAtInput = true;
		Console.setSettings(console);
	}

	/**
	 * Generate a set of bouncing ball systems
	 * 
	 * @param restitution_coefficient
	 *            restitution coefficient value
	 * @param gravity_constant
	 *            gravity constant value
	 * @param quantity
	 *            number of bouncing ball systems to generate
	 * @param min_x_pos
	 *            minimum possible x position to generate
	 * @param max_x_pos
	 *            maximum possible x position to generate
	 * @param min_y_pos
	 *            minimum possible y position to generate
	 * @param max_y_pos
	 *            maximum possible y position to generate
	 * @param min_x_vel
	 *            minimum velsible x velocity to generate
	 * @param max_x_vel
	 *            maximum velsible x velocity to generate
	 * @param min_y_vel
	 *            minimum velsible y velocity to generate
	 * @param max_y_vel
	 *            maximum velsible y velocity to generate
	 * @return system set containing all generated bouncing ball systems
	 */
	public static SystemSet generateBouncingBallSystems(double restitution_coefficient, double gravity_constant,
	int quantity, double min_x_pos, double max_x_pos, double min_y_pos, double max_y_pos, double min_x_vel,
	double max_x_vel, double min_y_vel, double max_y_vel)
	{
		SystemSet systems = new SystemSet();
		BouncingBallParameters physics = new BouncingBallParameters(restitution_coefficient, gravity_constant);
		for (int ballNum = 0; ballNum < quantity; ballNum++)
		{
			BouncingBallState state = new BouncingBallState(RandomVariable.generate(min_y_pos, max_y_pos),
			RandomVariable.generate(min_x_pos, max_x_pos), RandomVariable.generate(min_y_vel, max_y_vel),
			RandomVariable.generate(min_y_vel, max_y_vel));
			BouncingBallSystem ballSystem = new BouncingBallSystem(state, physics);
			systems.add(ballSystem);
		}
		return systems;
	}

	/**
	 * Generate a figure with all four bouncing ball state elements
	 * 
	 * @param solution
	 *            trajectory set containing data to load into figure
	 * @return a figure displaying all four bouncing ball state elements
	 */
	public static Figure generateFullStateFigure(TrajectorySet solution)
	{
		Figure figure = new Figure(1000, 600);

		ChartPanel xPos = ChartUtils.createPanel(solution, HybridTime.TIME, "xPosition");
		ChartPanel yPos = ChartUtils.createPanel(solution, HybridTime.TIME, "yPosition");
		ChartPanel xVel = ChartUtils.createPanel(solution, HybridTime.TIME, "xVelocity");
		ChartPanel yVel = ChartUtils.createPanel(solution, HybridTime.TIME, "yVelocity");

		figure.addComponent(0, 0, xPos);
		figure.addComponent(0, 1, xVel);
		figure.addComponent(1, 0, yPos);
		figure.addComponent(1, 1, yVel);

		ChartUtils.configureLabels(xPos, "Time (sec)", "X Position (m)", null, false);
		ChartUtils.configureLabels(yPos, "Time (sec)", "Y Position (m)", null, false);
		ChartUtils.configureLabels(xVel, "Time (sec)", "X Velocity (m/s)", null, false);
		ChartUtils.configureLabels(yVel, "Time (sec)", "Y Velocity (m/s)", null, false);

		figure.getTitle().setText("Bouncing Ball Simulation");
		return figure;
	}

	/**
	 * Generate a figure with the vertical (y position and velocity) bouncing
	 * ball state elements
	 * 
	 * @param solution
	 *            trajectory set containing data to load into figure
	 * @return a figure displaying all vertical bouncing ball state elements
	 */
	public static Figure generateVerticalStateFigure(TrajectorySet solution)
	{
		Figure figure = new Figure(1000, 600);

		ChartPanel yPos = ChartUtils.createPanel(solution, HybridTime.TIME, "yPosition");
		ChartPanel yVel = ChartUtils.createPanel(solution, HybridTime.TIME, "yVelocity");

		figure.addComponent(0, 0, yPos);
		figure.addComponent(0, 1, yVel);

		ChartUtils.configureLabels(yPos, "Time (sec)", "Y Position (m)", null, false);
		ChartUtils.configureLabels(yVel, "Time (sec)", "Y Velocity (m/s)", null, false);

		figure.getTitle().setText("Bouncing Ball Simulation: Vertical States");

		return figure;
	}

}