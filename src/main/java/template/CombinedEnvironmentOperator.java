package template;

import edu.ucsc.cross.hse.core.environment.EnvironmentSettings;
import edu.ucsc.cross.hse.core.environment.ExecutionParameters;
import edu.ucsc.cross.hse.core.environment.HSEnvironment;
import edu.ucsc.cross.hse.core.file.DataFormat;
import edu.ucsc.cross.hse.core.file.FileBrowser;
import edu.ucsc.cross.hse.core.logging.Console;
import edu.ucsc.cross.hse.core.logging.ConsoleSettings;
import edu.ucsc.cross.hse.core.modeling.SystemSet;
import edu.ucsc.cross.hse.core.specification.DomainPriority;
import edu.ucsc.cross.hse.core.specification.IntegratorType;

public class CombinedEnvironmentOperator
{

	/**
	 * Prepare and operate the environment
	 */
	public static void main(String args[])
	{
		ConsoleSettingConfig.configureConsole();
		HSEnvironment environment = setupEnvironment();
		operateEnvironment(environment);
		processResults(environment);
	}

	/**
	 * Perform the tasks necessary to operate the environment
	 */
	public static void operateEnvironment(HSEnvironment environment)
	{
		environment.run();
	}

	/**
	 * Perform the tasks necessary to process the results
	 */
	public static void processResults(HSEnvironment environment)
	{
		environment.saveToFile(FileBrowser.save(), DataFormat.HSE);
	}

	/**
	 * Setup the environment
	 * 
	 * @return environment
	 */
	public static HSEnvironment setupEnvironment()
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
		private static SystemSet getSystemSet()
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
		private static ExecutionParameters getExecutionParameters()
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
			parameters.maximumJumps = 20;
			parameters.maximumTime = 20.0;
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
			parameters.maximumJumps = 100;
			parameters.maximumTime = 50.0;
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
		private static EnvironmentSettings getEnvironmentSettings()
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
			settings.odeMinimumStepSize = .5E-8;
			settings.odeMaximumStepSize = .5E-4;
			settings.odeSolverAbsoluteTolerance = 1.0e-6;
			settings.odeRelativeTolerance = 1.0e-10;
			settings.eventHandlerMaximumCheckInterval = .1e-10;
			settings.eventHandlerConvergenceThreshold = .1e-5;
			settings.maxEventHandlerIterations = 25;
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
			settings.odeMaximumStepSize = .5E-5;
			settings.odeSolverAbsoluteTolerance = 1.0e-7;
			settings.odeRelativeTolerance = 1.0e-12;
			settings.eventHandlerMaximumCheckInterval = .1e-16;
			settings.eventHandlerConvergenceThreshold = .1e-8;
			settings.maxEventHandlerIterations = 50;
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
			return getConsoleSettingsA();

			/* uncomment for config A */
			//return getConsoleSettingsB();

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
}
