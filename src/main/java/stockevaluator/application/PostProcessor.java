
package stockevaluator.application;

import edu.ucsc.cross.hse.core.environment.HSEnvironment;
import edu.ucsc.cross.hse.core.figure.Figure;
import edu.ucsc.cross.hse.core.trajectory.HybridTime;
import edu.ucsc.cross.hse.core.trajectory.TrajectorySet;
import stockevaluator.hybridsystem.State;

/**
 * A post processor
 */
public class PostProcessor {

	/**
	 * Executes all processing tasks. This method is called by the main applications
	 */
	public static void processEnvironmentData(HSEnvironment environment) {

		Figure figure = generateAllStateFigure(environment.getTrajectories());
		figure.display();
	}

	/**
	 * Generate a figure
	 * 
	 * @param solution
	 *            trajectory set containing data to load into figure
	 * @return a figure displaying state values
	 */
	public static Figure generateAllStateFigure(TrajectorySet solution) {

		// Fetch stock evaluator state
		State state = (State) solution.getHybridTrajectorySetByObject(State.class).getSystems().getSystems().get(0)
				.getState();
		// Generate a figure to display the trajectories
		Figure figure = new Figure(600, 800, "Stock Evaluator Results");
		// create charts for each values of interest
		figure.add(2, 1, solution, HybridTime.TIME, "stockValue", "Time (sec)", null, "Stock Values", true);
		figure.add(0, 0, solution, HybridTime.TIME, "bestSlopeValue", "Time (sec)", null,
				"Best Slope: " + state.bestSlopeIndex, false);
		figure.add(0, 1, solution, HybridTime.TIME, "bestLogSlopeValue", "Time (sec)", null,
				"Best Log Slope: " + state.bestLogSlopeIndex, false);
		figure.add(2, 0, solution, HybridTime.TIME, "worstSlopeValue", "Time (sec)", null,
				"Worst Slope: " + state.worstSlopeIndex, true);
		figure.add(1, 0, solution, HybridTime.TIME, "stockSlope", "Time (sec)", null, "Stock Slope", false);
		figure.add(1, 1, solution, HybridTime.TIME, "stockLogSlope", "Time (sec)", null, "Stock Log Slope: ", false);

		return figure;
	}
}
