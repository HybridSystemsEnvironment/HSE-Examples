package inverter;

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

/**
 * Inverter application setup class
 * 
 * @author Brendan Short
 *
 */
public class InverterApplication
{

	/**
	 * Main method for running the Inverter application
	 * 
	 * @param args
	 *            none
	 */
	public static void main(String args[])
	{
		loadBouncingBallConsoleSettings();
		HSEnvironment environment = generateEnvironment();
		environment.run();
		generateFullStateFigure(environment.getTrajectories()).display();
	}

	/**
	 * Generate the Inverter environment
	 * 
	 * @return environment
	 */
	public static HSEnvironment generateEnvironment()
	{
		HSEnvironment environment = new HSEnvironment();
		SystemSet systems = new SystemSet(generateInverterSystem(60.0, 1.0, 0.1, 6.66e-5, 220.0, .05, 120.0, 1.0, 1.0,
		60.0, 120 * 6.66e-5 * 60.0 * 2 * Math.PI, 0.0));
		ExecutionParameters parameters = getBouncingBallExeParameters();

		EnvironmentSettings settings = getInverterEnvSettings();

		environment = HSEnvironment.create(systems, parameters, settings);

		return environment;
	}

	/**
	 * Initializes execution parameters with configuration B
	 * 
	 * @return ExecutionParameters
	 */
	public static ExecutionParameters getBouncingBallExeParameters()
	{
		ExecutionParameters parameters = new ExecutionParameters();
		parameters.maximumJumps = 4000000;
		parameters.maximumTime = 0.12;
		parameters.dataPointInterval = .00005;
		return parameters;
	}

	/**
	 * Initializes environment settings
	 * 
	 * @return environment settings
	 */
	public static EnvironmentSettings getInverterEnvSettings()
	{
		EnvironmentSettings settings = new EnvironmentSettings();
		settings.odeMinimumStepSize = 1E-9;
		settings.odeMaximumStepSize = 1E-6;
		settings.odeSolverAbsoluteTolerance = 1E-6;
		settings.odeRelativeTolerance = 1E-6;
		settings.eventHandlerMaximumCheckInterval = 1E-6;
		settings.eventHandlerConvergenceThreshold = 1E-9;
		settings.maxEventHandlerIterations = 100;
		settings.integratorType = IntegratorType.DORMAND_PRINCE_853;
		settings.domainPriority = DomainPriority.JUMP;
		settings.storeNonPrimativeData = false;
		return settings;
	}

	/**
	 * Initializes console settings for Inverter environment
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
	 * Generate an inverter system
	 * 
	 * @param f
	 *            frequency
	 * @param R
	 *            resistance
	 * @param L
	 *            inductance
	 * @param Cap
	 *            capacitance
	 * @param V
	 *            set voltage
	 * @param e
	 * @param b
	 * @param p0
	 * @param q0
	 * @param vIn0
	 * @param iL0
	 * @param vC0
	 * @return
	 */
	public static InverterSystem generateInverterSystem(double f, double R, double L, double Cap, double V, double e,
	double b, double p0, double q0, double vIn0, double iL0, double vC0)
	{
		InverterParameters params = new InverterParameters(f, R, L, Cap, V, e, b);
		InverterState state = new InverterState(p0, q0, iL0, vC0, vIn0);
		InverterSystem invSys = new InverterSystem(state, params);
		return invSys;
	}

	/**
	 * Generate a figure with the vertical (y position and velocity) bouncing
	 * ball state elements
	 * 
	 * @param solution
	 *            trajectory set containing data to load into figure
	 * @return a figure displaying all vertical Inverter state elements
	 */
	public static Figure generateFullStateFigure(TrajectorySet solution)
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