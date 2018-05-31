package inverter;

import org.jfree.chart.ChartPanel;

import edu.ucsc.cross.hse.core.chart.ChartUtils;
import edu.ucsc.cross.hse.core.environment.HSESettings;
import edu.ucsc.cross.hse.core.environment.HSEnvironment;
import edu.ucsc.cross.hse.core.environment.SystemSet;
import edu.ucsc.cross.hse.core.figure.Figure;
import edu.ucsc.cross.hse.core.integrator.DormandPrince853IntegratorFactory;
import edu.ucsc.cross.hse.core.integrator.FirstOrderIntegratorFactory;
import edu.ucsc.cross.hse.core.logging.Console;
import edu.ucsc.cross.hse.core.logging.ConsoleSettings;
import edu.ucsc.cross.hse.core.specification.DomainPriority;
import edu.ucsc.cross.hse.core.trajectory.HybridTime;
import edu.ucsc.cross.hse.core.trajectory.TrajectorySet;

/**
 * Inverter application setup class
 * 
 * @author Brendan Short
 *
 */
public class InverterApplication {

	/**
	 * Main method for running the Inverter application
	 * 
	 * @param args
	 *            none
	 */
	public static void main(String args[]) {
		loadConsoleSettings();
		HSEnvironment environment = generateEnvironment();
		environment.run();
		generateFullStateFigure(environment.getTrajectories()).display();
		generateFullStateFigureSplit(environment.getTrajectories()).display();
	}

	/**
	 * Generate the Inverter environment
	 * 
	 * @return environment
	 */
	public static HSEnvironment generateEnvironment() {
		HSEnvironment environment = new HSEnvironment();
		SystemSet systems = new SystemSet(generateInverterSystem(60.0, 1.0, 0.1, 6.66e-5, 220.0, .05, 120.0, 1.0, 1.0,
				60.0, 120 * 6.66e-5 * 60.0 * 2 * Math.PI, 0.0));

		HSESettings settings = getEnvironmentSettings();

		environment = HSEnvironment.create(systems, settings);

		return environment;
	}

	/**
	 * Initializes environment settings
	 * 
	 * @return environment settings
	 */
	public static HSESettings getEnvironmentSettings() {
		HSESettings settings = new HSESettings();
		settings.maximumJumps = 4000000;
		settings.maximumTime = 0.12;
		settings.dataPointInterval = .00005;
		settings.eventHandlerMaximumCheckInterval = 1E-6;
		settings.eventHandlerConvergenceThreshold = 1E-10;
		settings.maxEventHandlerIterations = 100;
		double odeMaximumStepSize = 1e-6;
		double odeMinimumStepSize = 1e-9;
		double odeRelativeTolerance = 1.0e-6;
		double odeSolverAbsoluteTolerance = 1.0e-6;
		FirstOrderIntegratorFactory defaultIntegrator = new DormandPrince853IntegratorFactory(odeMinimumStepSize,
				odeMaximumStepSize, odeRelativeTolerance, odeSolverAbsoluteTolerance);
		settings.integrator = defaultIntegrator;
		// settings.integrator = new EulerIntegrator(1E-8);
		settings.domainPriority = DomainPriority.JUMP;
		settings.storeNonPrimativeData = false;
		settings.dataPointInterval = .00005;
		return settings;
	}

	/**
	 * Initializes console settings
	 * 
	 * @return console settings
	 */
	public static void loadConsoleSettings() {
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
			double b, double p0, double q0, double vIn0, double iL0, double vC0) {
		InverterParameters params = new InverterParameters(f, R, L, Cap, V, e, b);
		InverterState state = new InverterState(p0, q0, iL0, vC0, vIn0);
		InverterSystem invSys = new InverterSystem(state, params);
		return invSys;
	}

	/**
	 * Generate a figure with the vertical (y position and velocity) bouncing ball
	 * state elements
	 * 
	 * @param solution
	 *            trajectory set containing data to load into figure
	 * @return a figure displaying all vertical Inverter state elements
	 */
	public static Figure generateFullStateFigure(TrajectorySet solution) {
		Figure figure = new Figure(1200, 1200);

		ChartPanel pA = ChartUtils.createPanel(solution, "iL", "vC");
		ChartPanel sA = ChartUtils.createPanel(solution, HybridTime.TIME, "p");
		ChartPanel tA = ChartUtils.createPanel(solution, HybridTime.TIME, "q");
		ChartPanel pV = ChartUtils.createPanel(solution, HybridTime.TIME, "iL");
		ChartPanel sV = ChartUtils.createPanel(solution, HybridTime.TIME, "vC");
		ChartPanel tV = ChartUtils.createPanel(solution, HybridTime.TIME, "tau");

		ChartUtils.configureLabels(pA, "iL", "vC", null, false);
		ChartUtils.configureLabels(sA, "Time (sec)", "p", null, false);
		ChartUtils.configureLabels(tA, "Time (sec)", "q", null, false);
		ChartUtils.configureLabels(pV, "Time (sec)", "iL", null, false);
		ChartUtils.configureLabels(sV, "Time (sec)", "vC", null, false);
		ChartUtils.configureLabels(tV, "Time (sec)", "tau", null, false);

		figure.addComponent(1, 0, pA);
		figure.addComponent(2, 0, sA);
		figure.addComponent(3, 0, tA);
		figure.addComponent(1, 1, pV);
		figure.addComponent(2, 1, sV);
		figure.addComponent(3, 1, tV);

		figure.getTitle().setText("Hybrid Inverter Simulation Results");
		return figure;
	}

	/**
	 * Generate a figure with the vertical (y position and velocity) bouncing ball
	 * state elements
	 * 
	 * @param solution
	 *            trajectory set containing data to load into figure
	 * @return a figure displaying all vertical Inverter state elements
	 */
	public static Figure generateFullStateFigureSplit(TrajectorySet solution) {
		Figure figure = new Figure(1200, 1200);
		Figure left = new Figure(1200, 1200);
		Figure right = new Figure(1200, 1200);

		ChartPanel pA = ChartUtils.createPanel(solution, "iL", "vC");
		ChartPanel sA = ChartUtils.createPanel(solution, HybridTime.TIME, "p");
		ChartPanel tA = ChartUtils.createPanel(solution, HybridTime.TIME, "q");
		ChartPanel pV = ChartUtils.createPanel(solution, HybridTime.TIME, "iL");
		ChartPanel sV = ChartUtils.createPanel(solution, HybridTime.TIME, "vC");

		ChartUtils.configureLabels(pA, "iL", "vC", null, false);
		ChartUtils.configureLabels(sA, "Time (sec)", "p", null, false);
		ChartUtils.configureLabels(tA, "Time (sec)", "q", null, false);
		ChartUtils.configureLabels(pV, "Time (sec)", "iL", null, false);
		ChartUtils.configureLabels(sV, "Time (sec)", "vC", null, false);

		left.addComponent(1, 0, pA);
		right.addComponent(0, 0, sA);
		right.addComponent(1, 0, tA);
		right.addComponent(0, 1, pV);
		right.addComponent(1, 1, sV);

		figure.getTitle().setText("Hybrid Inverter Simulation Results");
		figure.addComponent(0, 0, left.getContentPanel());
		figure.addComponent(1, 0, right.getContentPanel());

		return figure;
	}
}