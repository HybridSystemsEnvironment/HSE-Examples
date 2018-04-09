package inverter;

import org.jfree.chart.ChartPanel;

import edu.ucsc.cross.hse.core.chart.ChartUtils;
import edu.ucsc.cross.hse.core.environment.EnvironmentSettings;
import edu.ucsc.cross.hse.core.environment.ExecutionParameters;
import edu.ucsc.cross.hse.core.environment.HSEnvironment;
import edu.ucsc.cross.hse.core.figure.Figure;
import edu.ucsc.cross.hse.core.file.DataFormat;
import edu.ucsc.cross.hse.core.file.FileBrowser;
import edu.ucsc.cross.hse.core.file.HSEFile;
import edu.ucsc.cross.hse.core.logging.Console;
import edu.ucsc.cross.hse.core.modeling.SystemSet;
import edu.ucsc.cross.hse.core.specification.DomainPriority;
import edu.ucsc.cross.hse.core.specification.IntegratorType;
import edu.ucsc.cross.hse.core.trajectory.HybridTime;
import edu.ucsc.cross.hse.core.trajectory.TrajectorySet;

public class InverterEnvironmentOperator
{

	public static void main(String args[])
	{
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
		private static SystemSet getSelectedSystemSet() throws Exception
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

		/**
		 * Application to create a new system set file for external
		 * configuration
		 */
		public static void main(String args[])
		{
			createNewSystemSetFile();
		}

		///////// Internal Methods ///////// 

		/**
		 * Create a new console setting file
		 */
		public static void createNewSystemSetFile()
		{
			SystemSet console = new SystemSet();
			HSEFile file = new HSEFile(console);
			file.saveToFile(FileBrowser.save(), DataFormat.XML);
		}

		/**
		 * Attempt to configure console
		 * 
		 * @return true is configuration is successfull
		 */
		public static SystemSet getSystemSet()
		{
			SystemSet systemSet = new SystemSet(); // fetch default
			try
			{
				systemSet = getSelectedSystemSet();
				return systemSet;
			} catch (Exception getFail)
			{
				Console.error("Unable to get system set", getFail);
			}

			return new SystemSet(); // return initialized flag
		}

	}

	public static class ExecutionParametersConfig
	{

		/**
		 * Select a execution parameters to use
		 * 
		 * @return ExecutionParameters selected set
		 */
		private static ExecutionParameters getSelectedExecutionParameters() throws Exception
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

		/**
		 * Application to create a new execution parameters file for external
		 * configuration
		 */
		public static void main(String args[])
		{
			createNewExecutionParametersFile();
		}

		/**
		 * Create a new console setting file
		 */
		public static void createNewExecutionParametersFile()
		{
			ExecutionParameters console = new ExecutionParameters();
			HSEFile file = new HSEFile(console);
			file.saveToFile(FileBrowser.save(), DataFormat.XML);
		}

		/**
		 * Attempt to configure console
		 * 
		 * @return true is configuration is successfull
		 */
		public static ExecutionParameters getExecutionParameters()
		{
			ExecutionParameters ExecutionParameters = new ExecutionParameters(); // fetch default
			try
			{
				ExecutionParameters = getSelectedExecutionParameters();
				return ExecutionParameters;
			} catch (Exception getFail)
			{
				Console.error("Unable to get execution parameters", getFail);
			}

			return new ExecutionParameters(); // return initialized flag
		}

	}

	public static class EnvironmentSettingConfig
	{

		/**
		 * Select a environment settings to use
		 * 
		 * @return EnvironmentSettings selected set
		 */
		private static EnvironmentSettings getSelectedEnvironmentSettings() throws Exception
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
		 * Application to create a new environment settings file for external
		 * configuration
		 */
		public static void main(String args[])
		{
			createNewEnvironmentSettingsFile();
		}

		/**
		 * Create a new console setting file
		 */
		public static void createNewEnvironmentSettingsFile()
		{
			EnvironmentSettings console = new EnvironmentSettings();
			HSEFile file = new HSEFile(console);
			file.saveToFile(FileBrowser.save(), DataFormat.XML);
		}

		/**
		 * Attempt to configure console
		 * 
		 * @return true is configuration is successfull
		 */
		public static EnvironmentSettings getEnvironmentSettings()
		{
			EnvironmentSettings EnvironmentSettings = new EnvironmentSettings(); // fetch default
			try
			{
				EnvironmentSettings = getSelectedEnvironmentSettings();
				return EnvironmentSettings;
			} catch (Exception getFail)
			{
				Console.error("Unable to get environment settings", getFail);
			}

			return new EnvironmentSettings(); // return initialized flag
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