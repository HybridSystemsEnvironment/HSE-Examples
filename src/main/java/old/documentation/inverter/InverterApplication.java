
package old.documentation.inverter;

import java.awt.Color;

import org.jfree.chart.ChartPanel;

import edu.ucsc.cross.hse.core.chart.ChartType;
import edu.ucsc.cross.hse.core.chart.ChartUtils;
import edu.ucsc.cross.hse.core.chart.RendererConfiguration;
import edu.ucsc.cross.hse.core.environment.HSEnvironment;
import edu.ucsc.cross.hse.core.figure.Figure;
import edu.ucsc.cross.hse.core.figure.GraphicFormat;
import edu.ucsc.cross.hse.core.integrator.DormandPrince853IntegratorFactory;
import edu.ucsc.cross.hse.core.modeling.HybridSys;
import edu.ucsc.cross.hse.core.trajectory.HybridTime;
import edu.ucsc.cross.hse.core.trajectory.TrajectorySet;

/**
 * A hybrid inverter application that prepares and operates the environment, and
 * generates a figure.
 * 
 * @author Brendan Short
 *
 */
public class InverterApplication {

	/**
	 * Main method for running application
	 * 
	 * @param args
	 *            none
	 */
	public static void main(String args[]) {

		// Initialize environment
		HSEnvironment environment = new HSEnvironment();
		// Initialize inverter parameters (f, R, L, Cap, V, e, b)
		InverterParameters parameters = new InverterParameters(60.0, 1.0, 0.1, 6.66e-5, 220.0, .05, 120.0);
		// Initialize inverter state (p0, q0, iL0, vC0)
		InverterState state = new InverterState(1.0, 1.0, 120 * 6.66e-5 * 60.0 * 2 * Math.PI, 0.0);
		HybridSys<InverterState> system = new HybridSys<InverterState>(state, new Fp(parameters), new Gp(parameters),
				new Cp(parameters), new Dp(parameters), parameters);
		// Add inverter system to environment
		environment.getSystems().add(system);
		// Specify integrator parameter values (need smaller step sizes)
		double odeMinimumStepSize = 1e-12;
		double odeMaximumStepSize = 1e-7;
		double odeRelativeTolerance = 1.0e-6;
		double odeSolverAbsoluteTolerance = 1.0e-6;
		// Create and store integrator factory
		DormandPrince853IntegratorFactory integrator = new DormandPrince853IntegratorFactory(odeMaximumStepSize,
				odeMinimumStepSize, odeRelativeTolerance, odeSolverAbsoluteTolerance);
		environment.getSettings().integrator = integrator;
		// Run environment (max time duration, max jumps)
		environment.run(.08, 1000000);
		// Generate a figure of the trajectories
		Figure figure = generateFullStateFigure(environment.getTrajectories());
		// Display the figure in new window
		// figure.exportToFile(GraphicFormat.PDF);
		generateFullStateFigureSplitColored(environment.getTrajectories()).exportToFile(GraphicFormat.PDF);
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
		Figure figure = new Figure(600, 600, "Hybrid Inverter Simulation Results");
		// Label chart axis and add charts to figure
		figure.addChart(1, 0, solution, "iL", "vC", "iL", "vC", null, false);
		figure.addChart(1, 1, solution, HybridTime.TIME, "q", "Time (sec)", "q", null, false);
		figure.addChart(0, 0, solution, HybridTime.TIME, "iL", "Time (sec)", "iL", null, false);
		figure.addChart(0, 1, solution, HybridTime.TIME, "vC", "Time (sec)", "vC", null, false);

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
		Figure figure = new Figure(650, 800);
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
}