package template;

import edu.ucsc.cross.hse.core.environment.EnvironmentSettings;
import edu.ucsc.cross.hse.core.environment.ExecutionParameters;
import edu.ucsc.cross.hse.core.environment.HSEnvironment;
import edu.ucsc.cross.hse.core.logging.Console;
import edu.ucsc.cross.hse.core.logging.ConsoleSettings;
import edu.ucsc.cross.hse.core.modeling.SystemSet;
import edu.ucsc.cross.hse.core.specification.DomainPriority;
import edu.ucsc.cross.hse.core.specification.IntegratorType;

public class HSESetup
{

	public static void main(String args[])
	{
		HSEnvironment environment = getEnvironment();
	}

	public static void operateEnvironment(HSEnvironment environment)
	{
		environment.run();
	}

	public static void processResults(HSEnvironment environment)
	{
		environment.run();
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
		//environment = HSEnvironment.create(getSystems(), getParameters(), getEnvSettings());

		// load environment from file using file browser
		//environment = HSEnvironment.loadFromFile(FileBrowser.load());

		// load environment from file from specified path
		//environment = HSEnvironment.loadFromFile(new File("path"));

		return environment;
	}

	/**
	 * Setup the parameters
	 * 
	 * @return parameters
	 */
	public static ExecutionParameters getParameters()
	{
		ExecutionParameters parameters = new ExecutionParameters(); // default

		//* uncomment line to select a configuration *\\

		//parameters = getParametersA();
		//parameters = getParametersB();

		return parameters; // return selected parameters
	}

	/**
	 * Setup the parameters
	 * 
	 * @return parameters
	 */
	public static EnvironmentSettings getEnvSettings()
	{
		EnvironmentSettings settings = new EnvironmentSettings(); // default

		//* uncomment a line to select a configuration *\\

		//settings = getEnvironmentSettingsA();
		//settings = getEnvironmentSettingsB();

		return settings; // return selected settings
	}

	/**
	 * Setup the system set
	 * 
	 * @return systems
	 */
	public static SystemSet getSystemSet()
	{
		SystemSet systems = new SystemSet(); // empty set

		//* add & uncomment a line to select a configuration *\\

		return systems;
	}

	/**
	 * Setup the system set
	 * 
	 * @return systems
	 */
	public static SystemSet getSystemsA()
	{
		SystemSet systems = new SystemSet(); // empty set

		//* add & uncomment a line to select a configuration *\\

		return systems;
	}

	/**
	 * Setup the system set
	 * 
	 * @return systems
	 */
	public static SystemSet getSystemsB()
	{
		SystemSet systems = new SystemSet(); // empty set

		//* add & uncomment a line to select a configuration *\\

		return systems;
	}

	/**
	 * Initializes parameters with configuration A
	 * 
	 * @return parametersA
	 */
	public static ExecutionParameters getParametersA()
	{
		ExecutionParameters parametersA = new ExecutionParameters();
		parametersA.maximumJumps = 20;
		parametersA.maximumTime = 20.0;
		parametersA.dataPointInterval = .05;
		return parametersA;
	}

	/**
	 * Initializes parameters with configuration B
	 * 
	 * @return parametersB
	 */
	public static ExecutionParameters getParametersB()
	{
		ExecutionParameters parametersB = new ExecutionParameters();
		parametersB.maximumJumps = 100;
		parametersB.maximumTime = 50.0;
		parametersB.dataPointInterval = .1;
		return parametersB;
	}

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

	public static boolean configureConsole()
	{
		ConsoleSettings consoleSettings = Console.getSettings(); // fetch default

		// Uncomment line to select

		//consoleSettings = getConsoleSettingsA();
		//consoleSettings = getConsoleSettingsB();

		Console.setSettings(consoleSettings); // load selected settings

		return true;
	}

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

	public static ConsoleSettings getConsoleSettingsB()
	{
		ConsoleSettings console = new ConsoleSettings();
		console.printStatusInterval = 2.0;
		console.printProgressIncrement = 20;
		console.printIntegratorExceptions = true;
		console.printInfo = true;
		console.printDebug = true;
		console.printWarning = true;
		console.printError = true;
		console.printLogToFile = true;
		console.terminateAtInput = true;
		return console;
	}

	public static final boolean consoleConfigured = configureConsole();
}
