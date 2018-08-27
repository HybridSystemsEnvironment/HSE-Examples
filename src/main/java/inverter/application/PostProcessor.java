
package inverter.application;

import java.awt.Color;

import org.jfree.chart.ChartPanel;

import edu.ucsc.cross.hse.core.chart.ChartType;
import edu.ucsc.cross.hse.core.chart.ChartUtils;
import edu.ucsc.cross.hse.core.chart.RendererConfiguration;
import edu.ucsc.cross.hse.core.environment.HSEnvironment;
import edu.ucsc.cross.hse.core.figure.Figure;
import edu.ucsc.cross.hse.core.trajectory.HybridTime;
import edu.ucsc.cross.hse.core.trajectory.TrajectorySet;

/**
 * A post processor
 */
public class PostProcessor {

	/**
	 * Executes all processing tasks. This method is called by the main applications
	 */
	public static void processEnvironmentData(HSEnvironment environment) {

		Figure figure = generateAllStateFigure(environment.getTrajectories());
		Figure figure2 = generateFullStateFigureSplitColored(environment.getTrajectories());
		figure.display();
		figure2.display();
	}

	/**
	 * Generate a figure with the vertical (y position and velocity) bouncing ball
	 * state elements
	 * 
	 * @param solution
	 *            trajectory set containing data to load into figure
	 * @return a figure displaying all vertical Inverter state elements
	 */
	public static Figure generateAllStateFigure(TrajectorySet solution) {

		// Create figure w:1000 h:600
		Figure figure = new Figure(600, 600, "Hybrid Inverter Simulation Results");
		// Label chart axis and add charts to figure
		figure.add(0, 1, solution, "iL", "vC", "iL", "vC", null, false);
		figure.add(1, 1, solution, HybridTime.TIME, "q", "Time (sec)", "q", null, false);
		figure.add(0, 0, solution, HybridTime.TIME, "iL", "Time (sec)", "iL", null, false);
		figure.add(1, 0, solution, HybridTime.TIME, "vC", "Time (sec)", "vC", null, false);

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
		renderer.assignSeriesColor("State (vC)", Color.RED);
		renderer.assignSeriesColor("State (q)", Color.BLUE);
		renderer.assignSeriesColor("State (p)", Color.GREEN);
		renderer.assignSeriesColor("State (iL)", Color.MAGENTA);
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
		left.add(0, 0, pA);
		right.add(0, 0, sA);
		right.add(0, 1, tA);
		right.add(1, 0, pV);
		right.add(1, 1, sV);
		// Add subfigures to main figure
		figure.add(0, 0, left.getContentPanel());
		figure.add(1, 0, right.getContentPanel());
		// Return generated figure
		return figure;
	}

}
