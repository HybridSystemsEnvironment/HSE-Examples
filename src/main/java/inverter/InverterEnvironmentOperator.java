package inverter;

import org.jfree.chart.ChartPanel;

import edu.ucsc.cross.hse.core.chart.ChartUtils;
import edu.ucsc.cross.hse.core.environment.EnvironmentSettings;
import edu.ucsc.cross.hse.core.environment.ExecutionParameters;
import edu.ucsc.cross.hse.core.environment.HSEnvironment;
import edu.ucsc.cross.hse.core.figure.Figure;
import edu.ucsc.cross.hse.core.file.DataFormat;
import edu.ucsc.cross.hse.core.file.FileBrowser;
import edu.ucsc.cross.hse.core.logging.Console;
import edu.ucsc.cross.hse.core.logging.ConsoleSettings;
import edu.ucsc.cross.hse.core.modeling.SystemSet;
import edu.ucsc.cross.hse.core.specification.DomainPriority;
import edu.ucsc.cross.hse.core.specification.IntegratorType;
import edu.ucsc.cross.hse.core.trajectory.HybridTime;
import edu.ucsc.cross.hse.core.trajectory.TrajectorySet;

public class InverterEnvironmentOperator
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
		InverterEnvironmentOperator.generateQuadFigure(environment.getTrajectories()).display();
		environment.saveToFile(FileBrowser.save(), DataFormat.HSE);
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
			/* uncomment for config A */
			return getSystemSetA();

			/* uncomment for config A */
			//return getSystemSetB();

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
		public static SystemSet getSystemSetA()
		{
			SystemSet console = new SystemSet();
			InverterParameters params = InverterParameters.idealParameters(.05);
			Double p0 = 1.0;
			Double q0 = 1.0;
			//Double c = params.ci + Math.random() * (params.co - params.ci);
			Double vIn0 = params.V;
			Double iL0 = params.a * 1.0;
			Double vC0 = 0.0;//params.b * Math.sqrt(c - Math.pow((iL0 / params.a), 2));
			InverterState state = new InverterState(p0, q0, iL0, vC0, vIn0);
			InverterSystem invSys = new InverterSystem(state, params);
			console.add(invSys);
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
			return getExecutionParametersA();

			/* uncomment for config A */
			//return getExecutionParametersB();

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
			parameters.maximumJumps = 4000000;
			parameters.maximumTime = 0.3;
			parameters.dataPointInterval = .005;
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
			parameters.maximumTime = 0.05;
			parameters.dataPointInterval = .01;
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
			settings.odeMinimumStepSize = .5E-9;
			settings.odeMaximumStepSize = .5E-5;
			settings.odeSolverAbsoluteTolerance = 1.0e-7;
			settings.odeRelativeTolerance = 1.0e-12;
			settings.eventHandlerMaximumCheckInterval = .0000000001;
			settings.eventHandlerConvergenceThreshold = .0000000001;
			settings.maxEventHandlerIterations = 22;
			settings.integratorType = IntegratorType.DORMAND_PRINCE_853;
			settings.domainPriority = DomainPriority.JUMP;
			settings.storeNonPrimativeData = false;
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
			settings.odeMaximumStepSize = .5E-4;
			settings.odeSolverAbsoluteTolerance = 1.0e-10;
			settings.odeRelativeTolerance = 1.0e-12;
			settings.eventHandlerMaximumCheckInterval = .1e-10;
			settings.eventHandlerConvergenceThreshold = .1e-6;
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
		public static ConsoleSettings getConsoleSettings()
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
		Figure figure = new Figure(1200, 1200);

		ChartPanel pA = ChartUtils.createPanel(solution, "iL", "vC");
		ChartPanel sA = ChartUtils.createPanel(solution, HybridTime.TIME, "p");
		ChartPanel tA = ChartUtils.createPanel(solution, HybridTime.TIME, "q");
		ChartPanel pV = ChartUtils.createPanel(solution, HybridTime.TIME, "iL");
		ChartPanel sV = ChartUtils.createPanel(solution, HybridTime.TIME, "vC");
		ChartPanel tV = ChartUtils.createPanel(solution, HybridTime.TIME, "tau");

		figure.addComponent(1, 0, pA);
		figure.addComponent(2, 0, sA);
		figure.addComponent(3, 0, tA);
		figure.addComponent(1, 1, pV);
		figure.addComponent(2, 1, sV);
		figure.addComponent(3, 1, tV);

		ChartUtils.configureLabels(pA, "iL", "vC", null, false);
		ChartUtils.configureLabels(sA, "Time (sec)", "p", null, false);
		ChartUtils.configureLabels(tA, "Time (sec)", "q", null, false);
		ChartUtils.configureLabels(pV, "Time (sec)", "iL", null, false);
		ChartUtils.configureLabels(sV, "Time (sec)", "vC", null, false);
		ChartUtils.configureLabels(tV, "Time (sec)", "tau", null, false);

		figure.getTitle().setText("Hybrid Inverter Simulation Results");
		return figure;
	}
}