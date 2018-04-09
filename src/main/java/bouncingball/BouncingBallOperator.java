package bouncingball;

import java.awt.GridLayout;

import javax.swing.JPanel;

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
import inverter.InverterEnvironmentOperator;

public class BouncingBallOperator
{

	public static void main(String args[])
	{
		ConsoleSettingConfig.configureConsole();
		HSEnvironment environment = getEnvironment();
		operateEnvironment(environment);
		processResults(environment);
	}

	public static void operateEnvironment(HSEnvironment environment)
	{
		environment.run();
	}

	public static void processResults(HSEnvironment environment)
	{
		generateVerticalFigure(environment.getTrajectories()).display();
		//generateQuadFigure(environment.getTrajectories()).display();
		InverterEnvironmentOperator.processResults(environment);
		//		environment.saveToFile(FileBrowser.save(), DataFormat.HSE);
	}

	/**
	 * Setup the environment
	 * 
	 * @return environment
	 */
	public static HSEnvironment getEnvironment()
	{
		HSEnvironment environment = new HSEnvironment();

		//* uncomment a line to select a configuration *\\

		// create new environment from selected configurations
		environment = HSEnvironment.create(SystemSetConfig.getSystemSet(),
		ExecutionParametersConfig.getExecutionParameters(), EnvironmentSettingConfig.getEnvironmentSettings());

		// load environment from file using file browser
		//environment = HSEnvironment.loadFromFile(FileBrowser.load());

		// load environment from file from specified path
		//environment = HSEnvironment.loadFromFile(new File("path"));

		return environment;
	}

	public static class SystemSetConfig
	{

		/**
		 * Select a system set to use
		 * 
		 * @return SystemSet selected set
		 */
		public static SystemSet getSystemSet()
		{
			/* uncomment for system set with one ball */
			//return generateSingleSystemSet(.95, 9.81, 0.0, 3.0, 3.0, 1.0);

			/*
			 * uncomment for system set with multiple balls with randomized
			 * initial conditions
			 */
			return generateRandomizedSystems(.99, 9.81, 96, 1, 3, 2, 3, 1, 3, 1, 2);

			/*
			 * uncomment for system set with multiple balls with randomized
			 * initial conditions and also a hybrid inverter
			 */
			//return getSystemSetA(.99, 9.81, 96, 1, 3, 2, 3, 1, 3, 1, 2);

			/* uncomment to load system set from file using browser */
			//return HSEFile.loadObjectFromFile(FileBrowser.load(), SystemSet.class);

			/* uncomment to load system set from file using path */
			//return HSEFile.loadObjectFromFile(new File("path"), SystemSet.class);
		}

		/**
		 * Initializes system set with configuration A
		 * 
		 * @return SystemSet
		 */
		public static SystemSet getSystemSetA(double restitution_coefficient, double gravity_constant, int quantity,
		double min_x_pos, double max_x_pos, double min_y_pos, double max_y_pos, double min_x_vel, double max_x_vel,
		double min_y_vel, double max_y_vel)
		{
			SystemSet console = generateRandomizedSystems(restitution_coefficient, gravity_constant, quantity,
			min_x_pos, max_x_pos, min_y_pos, max_y_pos, min_x_vel, max_x_vel, min_y_vel, max_y_vel);
			console.add(InverterEnvironmentOperator.SystemSetConfig.getSystemSetA());
			return console;
		}

		/**
		 * Initializes system set with configuration B
		 * 
		 * @return SystemSet
		 */
		public static SystemSet getSystemSetB()
		{
			SystemSet SystemSet = new SystemSet();
			return SystemSet;
		}

		public static SystemSet generateSingleSystemSet(double restitution_coefficient, double gravity_constant,
		double x_pos, double y_pos, double x_vel, double y_vel)
		{
			return new SystemSet(generateSystem(.95, 9.81, 0.0, 0.0, 3.0, 1.0));
		}

		public static BouncingBallSystem generateSystem(double restitution_coefficient, double gravity_constant,
		double x_pos, double y_pos, double x_vel, double y_vel)
		{
			BouncingBallParameters physics = new BouncingBallParameters(restitution_coefficient, gravity_constant);
			BouncingBallState state = new BouncingBallState(x_pos, y_pos, x_vel, y_vel);
			BouncingBallSystem system = new BouncingBallSystem(state, physics);
			return system;
		}

		public static SystemSet generateRandomizedSystems(double restitution_coefficient, double gravity_constant,
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
	}

	public static class ExecutionParametersConfig
	{

		/**
		 * Select a execution parameters to use
		 * 
		 * @return ExecutionParameters selected set
		 */
		public static ExecutionParameters getExecutionParameters()
		{
			/* uncomment for config A */
			//return getExecutionParametersA();

			/* uncomment for config A */
			return getExecutionParametersB();

			/* uncomment to load execution parameters from file using browser */
			//return HSEFile.loadObjectFromFile(FileBrowser.load(), ExecutionParameters.class);

			/* uncomment to load execution parameters from file using path */
			//return HSEFile.loadObjectFromFile(new File("path"), ExecutionParameters.class);
		}

		/**
		 * Initializes execution parameters with configuration A
		 * 
		 * @return ExecutionParameters
		 */
		public static ExecutionParameters getExecutionParametersA()
		{
			ExecutionParameters parameters = new ExecutionParameters();
			parameters.maximumJumps = 10000;
			parameters.maximumTime = 10.0;
			parameters.dataPointInterval = .05;
			return parameters;
		}

		/**
		 * Initializes execution parameters with configuration B
		 * 
		 * @return ExecutionParameters
		 */
		public static ExecutionParameters getExecutionParametersB()
		{
			ExecutionParameters parameters = new ExecutionParameters();
			parameters.maximumJumps = 10000;
			parameters.maximumTime = 8;
			parameters.dataPointInterval = .1;
			return parameters;
		}

	}

	public static class EnvironmentSettingConfig
	{

		/**
		 * Select a environment settings to use
		 * 
		 * @return EnvironmentSettings selected set
		 */
		public static EnvironmentSettings getEnvironmentSettings()
		{
			/* uncomment for config A */
			return getEnvironmentSettingsA();

			/* uncomment for config A */
			//return getEnvironmentSettingsB();

			/* uncomment to load environment settings from file using browser */
			//return HSEFile.loadObjectFromFile(FileBrowser.load(), EnvironmentSettings.class);

			/* uncomment to load environment settings from file using path */
			//return HSEFile.loadObjectFromFile(new File("path"), EnvironmentSettings.class);
		}

		/**
		 * Initializes environment settings with configuration A
		 * 
		 * @return EnvironmentSettings
		 */
		public static EnvironmentSettings getEnvironmentSettingsA()
		{
			EnvironmentSettings settings = new EnvironmentSettings();
			return settings;
		}

		/**
		 * Initializes environment settings with configuration B
		 * 
		 * @return EnvironmentSettings
		 */
		public static EnvironmentSettings getEnvironmentSettingsB()
		{
			EnvironmentSettings settings = new EnvironmentSettings();
			settings.odeMinimumStepSize = .5E-9;
			settings.odeMaximumStepSize = .5E-5;
			settings.odeSolverAbsoluteTolerance = 1.0e-7;
			settings.odeRelativeTolerance = 1.0e-10;
			settings.eventHandlerMaximumCheckInterval = .1e-12;
			settings.eventHandlerConvergenceThreshold = .1e-8;
			settings.maxEventHandlerIterations = 25;
			settings.integratorType = IntegratorType.DORMAND_PRINCE_853;
			settings.domainPriority = DomainPriority.JUMP;
			settings.storeNonPrimativeData = false;
			return settings;
		}
	}

	public static class ConsoleSettingConfig
	{

		/**
		 * Get selected console settings
		 * 
		 * @return true is configuration is successfull
		 */
		public static ConsoleSettings getConsoleSettings() throws Exception
		{
			/* uncomment for config A */
			//return getConsoleSettingsA();

			/* uncomment for config A */
			return getConsoleSettingsB();

			/* uncomment to load console settings from file using browser */
			//return HSEFile.loadObjectFromFile(FileBrowser.load(), ConsoleSettings.class);

			/* uncomment to load console settings from file using path */
			//return HSEFile.loadObjectFromFile(new File("path"), ConsoleSettings.class);
		}

		/**
		 * Initializes console settings with configuration A
		 * 
		 * @return consoleSettings
		 */
		public static ConsoleSettings getConsoleSettingsA()
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
			return console;
		}

		/**
		 * Initializes console settings with configuration B
		 * 
		 * @return consoleSettings
		 */
		public static ConsoleSettings getConsoleSettingsB()
		{
			ConsoleSettings consoleSettings = new ConsoleSettings();
			consoleSettings.printStatusInterval = 2.0;
			consoleSettings.printProgressIncrement = 20;
			consoleSettings.printIntegratorExceptions = true;
			consoleSettings.printInfo = true;
			consoleSettings.printDebug = true;
			consoleSettings.printWarning = true;
			consoleSettings.printError = true;
			consoleSettings.printLogToFile = true;
			consoleSettings.terminateAtInput = true;
			return consoleSettings;
		}

		/**
		 * Attempt to configure console
		 */
		public static void configureConsole()
		{
			ConsoleSettings consoleSettings = Console.getSettings(); // fetch default
			try
			{
				consoleSettings = getConsoleSettings();
			} catch (Exception getFail)
			{
				Console.error("Unable to get console settings");
			}

			Console.setSettings(consoleSettings); // store specified settings

		}

	}

	public static Figure generateQuadFigure(TrajectorySet solution)
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

		ChartUtils.configureLabels(xPos, "Time (sec)", "X Position (m)", null, true);
		ChartUtils.configureLabels(yPos, "Time (sec)", "Y Position (m)", null, false);
		ChartUtils.configureLabels(xVel, "Time (sec)", "X Velocity (m/s)", null, false);
		ChartUtils.configureLabels(yVel, "Time (sec)", "Y Velocity (m/s)", null, false);

		figure.getTitle().setText("Bouncing Ball Simulation: Full State");
		return figure;
	}

	public static Figure generateVerticalFigure(TrajectorySet solution)
	{
		Figure figure = new Figure(1000, 600);

		ChartPanel yPos = ChartUtils.createPanel(solution, HybridTime.TIME, "yPosition");
		ChartPanel yVel = ChartUtils.createPanel(solution, HybridTime.TIME, "yVelocity");

		figure.addComponent(0, 0, yPos);
		figure.addComponent(0, 1, yVel);

		ChartUtils.configureLabels(yPos, "Time (sec)", "Y Position (m)", null, false);
		ChartUtils.configureLabels(yVel, "Time (sec)", "Y Velocity (m/s)", null, false);

		figure.getTitle().setText("Bouncing Ball Simulation: Vertical State");

		return figure;
	}

	public static Figure generateFigure(TrajectorySet solution)
	{
		Figure figure = new Figure(1000, 1000);

		ChartPanel xPos = ChartUtils.createPanel(solution, HybridTime.TIME, "xPosition");
		ChartPanel yPos = ChartUtils.createPanel(solution, HybridTime.TIME, "yPosition");
		ChartPanel xVel = ChartUtils.createPanel(solution, HybridTime.TIME, "xVelocity");
		ChartPanel yVel = ChartUtils.createPanel(solution, HybridTime.TIME, "yVelocity");

		JPanel grid = Figure.createSubPanel(new GridLayout(2, 1));
		grid.add(yPos);
		grid.add(yVel);
		figure.addComponent(0, 0, xPos);
		figure.addComponent(1, 0, grid);

		ChartUtils.configureLabels(xPos, "Time (sec)", "X Position (m)", null, true);
		ChartUtils.configureLabels(yPos, "Time (sec)", "Y Position (m)", null, false);
		ChartUtils.configureLabels(xVel, "Time (sec)", "X Velocity (m/s)", null, false);
		ChartUtils.configureLabels(yVel, "Time (sec)", "Y Velocity (m/s)", null, false);

		figure.getTitle().setText("Bouncing Ball Simulation");
		return figure;
	}

}