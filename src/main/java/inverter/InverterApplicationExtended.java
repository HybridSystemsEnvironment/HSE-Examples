package inverter;

import java.awt.Color;

import org.jfree.chart.ChartPanel;

import edu.ucsc.cross.hse.core.chart.ChartType;
import edu.ucsc.cross.hse.core.chart.ChartUtils;
import edu.ucsc.cross.hse.core.chart.RendererConfiguration;
import edu.ucsc.cross.hse.core.environment.EnvironmentSettings;
import edu.ucsc.cross.hse.core.environment.HSEnvironment;
import edu.ucsc.cross.hse.core.environment.SystemSet;
import edu.ucsc.cross.hse.core.figure.Figure;
import edu.ucsc.cross.hse.core.figure.GraphicFormat;
import edu.ucsc.cross.hse.core.file.FileBrowser;
import edu.ucsc.cross.hse.core.integrator.DormandPrince853IntegratorFactory;
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
public class InverterApplicationExtended {

	/**
	 * Main method for running the Inverter application
	 * 
	 * @param args
	 *            none
	 */
	public static void main(String args[]) {
		// Load console settings
		loadConsoleSettings();
		// Create system set containing inverter
		SystemSet systems = new SystemSet(generateInverterSystem(60.0, 1.0, 0.1, 6.66e-5, 220.0, .05, 120.0, 1.0, 1.0,
				60.0, 120 * 6.66e-5 * 60.0 * 2 * Math.PI, 0.0));
		// Create configured settings
		EnvironmentSettings settings = getEnvironmentSettings();
		// Create loaded environment
		HSEnvironment environment = HSEnvironment.create(systems, settings);
		// Run simulation and store result trajectories
		environment.run();
		// Generate figures and display in window
		// generateFullStateFigure(environment.getTrajectories()).display();
		generateFullStateFigureSplitColored(environment.getTrajectories()).exportToFile(FileBrowser.save(),
				GraphicFormat.PDF);// .display();
		// generateFullStateFigureSplit(environment.getTrajectories()).display();
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

		EnvironmentSettings settings = getEnvironmentSettings();

		environment = HSEnvironment.create(systems, settings);

		return environment;
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
		settings.maximumJumps = 4000000;
		settings.maximumTime = 0.08;
		settings.dataPointInterval = .00005;
		settings.eventHandlerMaximumCheckInterval = 1E-8;
		settings.eventHandlerConvergenceThreshold = 1E-8;
		settings.maxEventHandlerIterations = 100;
		settings.domainPriority = DomainPriority.JUMP;
		settings.storeNonPrimativeData = false;
		// Specify integrator parameter values
		double odeMinimumStepSize = 1e-12;
		double odeMaximumStepSize = 1e-7;
		double odeRelativeTolerance = 1.0e-6;
		double odeSolverAbsoluteTolerance = 1.0e-6;
		// Create and store integrator factory
		DormandPrince853IntegratorFactory integ = new DormandPrince853IntegratorFactory(odeMaximumStepSize,
				odeMinimumStepSize, odeRelativeTolerance, odeSolverAbsoluteTolerance);
		settings.integrator = integ;
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
		console.printIntegratorExceptions = true;
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
	 * @return a figure displaying all vertical Inverter state elements
	 */
	public static Figure generateFullStateFigure(TrajectorySet solution) {
		// Create figure w:1000 h:600
		Figure figure = new Figure(1200, 1200);
		// Assign title to figure
		figure.getTitle().setText("Hybrid Inverter Simulation Results");
		// Create charts
		ChartPanel pA = ChartUtils.createPanel(solution, "iL", "vC");
		ChartPanel tA = ChartUtils.createPanel(solution, HybridTime.TIME, "q");
		ChartPanel pV = ChartUtils.createPanel(solution, HybridTime.TIME, "iL");
		ChartPanel sV = ChartUtils.createPanel(solution, HybridTime.TIME, "vC");
		// Label chart axis and configure legend visibility
		ChartUtils.configureLabels(pA, "iL", "vC", null, false);
		ChartUtils.configureLabels(tA, "Time (sec)", "q", null, false);
		ChartUtils.configureLabels(pV, "Time (sec)", "iL", null, false);
		ChartUtils.configureLabels(sV, "Time (sec)", "vC", null, false);
		// Add charts to figure
		figure.addComponent(1, 0, pA);
		figure.addComponent(1, 1, tA);
		figure.addComponent(0, 0, pV);
		figure.addComponent(0, 1, sV);
		// Return generated figure
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
		// Create main figure w:800 h:600
		Figure figure = new Figure(800, 600);
		// Create left subfigure (size irrelavant)
		Figure left = new Figure(800, 600);
		// Create right subfigure (size irrelavant)
		Figure right = new Figure(800, 600);
		// Assign title to figure
		figure.getTitle().setText("Hybrid Inverter Simulation Results");
		// Create charts
		ChartPanel pA = ChartUtils.createPanel(solution, "iL", "vC");
		ChartPanel sA = ChartUtils.createPanel(solution, HybridTime.TIME, "p");
		ChartPanel tA = ChartUtils.createPanel(solution, HybridTime.TIME, "q");
		ChartPanel pV = ChartUtils.createPanel(solution, HybridTime.TIME, "iL");
		ChartPanel sV = ChartUtils.createPanel(solution, HybridTime.TIME, "vC");
		// Label chart axis and configure legend visibility
		ChartUtils.configureLabels(pA, "iL", "vC", null, false);
		ChartUtils.configureLabels(sA, "Time (sec)", "p", null, false);
		ChartUtils.configureLabels(tA, "Time (sec)", "q", null, false);
		ChartUtils.configureLabels(pV, "Time (sec)", "iL", null, false);
		ChartUtils.configureLabels(sV, "Time (sec)", "vC", null, false);
		// Add charts to subfigures
		left.addComponent(1, 0, pA);
		right.addComponent(0, 0, sA);
		right.addComponent(1, 0, tA);
		right.addComponent(0, 1, pV);
		right.addComponent(1, 1, sV);
		// Add subfigures to main figure
		figure.addComponent(0, 0, left.getContentPanel());
		figure.addComponent(1, 0, right.getContentPanel());
		// Return generated figure
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
	public static Figure generateFullStateFigureSplitColored(TrajectorySet solution) {
		ChartUtils.includeVarNameInLabel = true;
		// Create main figure w:800 h:600
		Figure figure = new Figure(800, 800);
		// Create left subfigure (size irrelavant)
		Figure left = new Figure(800, 800);
		// Create right subfigure (size irrelavant)
		Figure right = new Figure(800, 800);
		// Assign title to figure
		figure.getTitle().setText("Hybrid Inverter Simulation Results");
		// Create renderer
		RendererConfiguration renderer = new RendererConfiguration();
		// Assign colors to each state element
		renderer.assignSeriesColor("InverterState (vC)", Color.RED);
		renderer.assignSeriesColor("InverterState (q)", Color.BLUE);
		renderer.assignSeriesColor("InverterState (p)", Color.GREEN);
		renderer.assignSeriesColor("InverterState (iL)", Color.MAGENTA);
		// Create charts
		ChartPanel pA = ChartUtils.createPanel(solution, "iL", "vC", ChartType.LINE, renderer, null);
		ChartPanel sA = ChartUtils.createPanel(solution, HybridTime.TIME, "p", ChartType.LINE, renderer, null);
		ChartPanel tA = ChartUtils.createPanel(solution, HybridTime.TIME, "q", ChartType.LINE, renderer, null);
		ChartPanel pV = ChartUtils.createPanel(solution, HybridTime.TIME, "iL", ChartType.LINE, renderer, null);
		ChartPanel sV = ChartUtils.createPanel(solution, HybridTime.TIME, "vC", ChartType.LINE, renderer, null);
		// Label chart axis and configure legend visibility
		ChartUtils.configureLabels(pA, "iL", "vC", null, false);
		ChartUtils.configureLabels(sA, "Time (sec)", "p", null, false);
		ChartUtils.configureLabels(tA, "Time (sec)", "q", null, false);
		ChartUtils.configureLabels(pV, "Time (sec)", "iL", null, false);
		ChartUtils.configureLabels(sV, "Time (sec)", "vC", null, false);
		// Add charts to subfigures
		left.addComponent(0, 0, pA);
		right.addComponent(0, 0, sA);
		right.addComponent(1, 0, tA);
		right.addComponent(0, 1, pV);
		right.addComponent(1, 1, sV);
		// Add subfigures to main figure
		figure.addComponent(0, 0, left.getContentPanel());
		figure.addComponent(0, 1, right.getContentPanel());
		// Return generated figure
		return figure;
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
		InverterState state = new InverterState(p0, q0, iL0, vC0);
		InverterSystem invSys = new InverterSystem(state, params);
		return invSys;
	}
}