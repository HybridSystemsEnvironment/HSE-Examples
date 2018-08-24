
package inverter;

import org.jfree.chart.ChartPanel;

import edu.ucsc.cross.hse.core.chart.ChartUtils;
import edu.ucsc.cross.hse.core.environment.HSEnvironment;
import edu.ucsc.cross.hse.core.figure.Figure;
import edu.ucsc.cross.hse.core.trajectory.HybridTime;
import edu.ucsc.cross.hse.core.trajectory.TrajectorySet;

public class InverterPlotLib {

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

	public static InverterSystem loadEnvironmentComponents(HSEnvironment environment) {

		// Initialize inverter parameters (f, R, L, Cap, V, e, b)
		InverterParameters parameters = new InverterParameters(60.0, 1.0, 0.1, 6.66e-5, 220.0, .05, 120.0);
		// Initialize inverter state (p0, q0, iL0, vC0)
		InverterState state = new InverterState(1.0, 1.0, 120 * 6.66e-5 * 60.0 * 2 * Math.PI, 0.0);
		InverterSystem system = new InverterSystem(state, parameters);
		// Add inverter system to environment
		environment.getSystems().add(system);
		return system;
	}
}
