
package old.inverter;

import org.jfree.chart.ChartPanel;

import edu.ucsc.cross.hse.core.chart.ChartUtils;
import edu.ucsc.cross.hse.core.environment.HSEnvironment;
import edu.ucsc.cross.hse.core.figure.Figure;
import edu.ucsc.cross.hse.core.integrator.DormandPrince853IntegratorFactory;
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
		InverterSystem system = new InverterSystem(state, parameters);
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
		figure.display();

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
		Figure figure = new Figure(1000, 600);
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

}