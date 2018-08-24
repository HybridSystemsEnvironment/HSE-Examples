
package stockevaluator.externaldef;

import edu.ucsc.cross.hse.core.environment.HSEnvironment;
import edu.ucsc.cross.hse.core.figure.Figure;
import edu.ucsc.cross.hse.core.figure.GraphicFormat;
import edu.ucsc.cross.hse.core.modeling.HybridSys;
import edu.ucsc.cross.hse.core.trajectory.HybridTime;
import edu.ucsc.cross.hse.core.trajectory.TrajectorySet;
import pl.zankowski.iextrading4j.api.stocks.ChartRange;

/**
 * A bouncing ball application that prepares and operates the environment, and
 * generates a figure.
 */
public class StockApplication {

	/**
	 * Main method for running application
	 */
	public static void main(String args[]) {

		// Initialize environment
		HSEnvironment environment = new HSEnvironment();
		// Initialize stock indices
		String[] indices = new String[] { "GOOG", "AAPL", "FB" };
		// Initialize stock evaluator parameters (range, query interval, indices)
		StockEvaluatorParameters params = new StockEvaluatorParameters(ChartRange.ONE_YEAR, 60.0, indices);
		// Initialize stock evaluator system
		HybridSys<StockEvaluatorState> system = new HybridSys<StockEvaluatorState>(new StockEvaluatorState(params),
				new Fp(), new Gp(params), new Cp(), new Dp(), params);

		// Add bouncing ball system to environment
		environment.getSystems().add(system);
		// Run environment (max time duration, max jumps)
		TrajectorySet traj = environment.run(120.0, 10);
		// generate figure
		Figure figure = getAllValueFigure(system, traj);
		// export figure to pdf
		figure.exportToFile(GraphicFormat.PDF);
	}

	public static Figure getAllValueFigure(HybridSys<StockEvaluatorState> system, TrajectorySet traj) {

		// Generate a figure to display the trajectories
		Figure figure = new Figure(600, 800, "Stock Evaluator Results");
		// create charts for each values of interest
		figure.addChart(1, 2, traj, HybridTime.TIME, "stockValue", "Time (sec)", null, "Stock Values", true);
		figure.addChart(0, 0, traj, HybridTime.TIME, "bestSlopeValue", "Time (sec)", null,
				"Best Slope: " + system.getState().bestSlopeIndex, false);
		figure.addChart(1, 0, traj, HybridTime.TIME, "bestLogSlopeValue", "Time (sec)", null,
				"Best Log Slope: " + system.getState().bestLogSlopeIndex, false);
		figure.addChart(0, 2, traj, HybridTime.TIME, "worstSlopeValue", "Time (sec)", null,
				"Worst Slope: " + system.getState().worstSlopeIndex, true);
		figure.addChart(0, 1, traj, HybridTime.TIME, "stockSlope", "Time (sec)", null, "Stock Slope", false);
		figure.addChart(1, 1, traj, HybridTime.TIME, "stockLogSlope", "Time (sec)", null, "Stock Log Slope: ", false);

		return figure;
	}

}