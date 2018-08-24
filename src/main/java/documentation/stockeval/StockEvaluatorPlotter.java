
package documentation.stockeval;

import edu.ucsc.cross.hse.core.figure.Figure;
import edu.ucsc.cross.hse.core.modeling.HybridSys;
import edu.ucsc.cross.hse.core.trajectory.HybridTime;
import edu.ucsc.cross.hse.core.trajectory.TrajectorySet;

public class StockEvaluatorPlotter {

	public static Figure getAllValueFigure(TrajectorySet traj) {

		// Generate a figure to display the trajectories
		HybridSys<StockEvaluatorState> system = (HybridSys<StockEvaluatorState>) traj
				.getHybridTrajectorySetByObject(StockEvaluatorState.class).getSystems().getSystems().get(0);
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
