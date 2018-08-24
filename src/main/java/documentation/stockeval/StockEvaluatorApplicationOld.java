
package documentation.stockeval;

import org.jfree.chart.ChartPanel;

import edu.ucsc.cross.hse.core.chart.ChartUtils;
import edu.ucsc.cross.hse.core.environment.HSEnvironment;
import edu.ucsc.cross.hse.core.figure.Figure;
import edu.ucsc.cross.hse.core.trajectory.HybridTime;
import edu.ucsc.cross.hse.core.trajectory.TrajectorySet;
import pl.zankowski.iextrading4j.api.stocks.ChartRange;

/**
 * A stock market evaluator application that prepares and operates the
 * environment, and generates a figure.
 * 
 * @author Brendan Short
 *
 */
public class StockEvaluatorApplicationOld {

	/**
	 * Main method for running application
	 * 
	 * @param args
	 *            none
	 */
	public static void main(String args[]) {

		// Initialize environment
		HSEnvironment environment = new HSEnvironment();
		// Initialize stock indices
		String[] indices = new String[] { "GOOG", "AAPL", "FB" };
		// Initialize stock evaluator parameters (range, query interval, indices)
		StockEvaluatorParameters parameters = new StockEvaluatorParameters(ChartRange.ONE_YEAR, 60.0, indices);
		// Initialize stock evaluator state
		StockEvaluatorState state = new StockEvaluatorState(parameters);
		// Initialize stock evaluator system
		StockEvaluatorSystem system = new StockEvaluatorSystem(state, parameters);
		// Add stock evaluator system to environment
		environment.getSystems().add(system);
		// Run environment (max time duration, max jumps)
		environment.run(200.0, 20);
		// Generate a figure of the trajectories
		Figure figure = generateFullStateFigure(environment.getTrajectories(), state);
		// Display the figure in new window
		figure.display();

	}

	/**
	 * Generate a figure with all state elements
	 * 
	 * @param solution
	 *            trajectory set containing data to load into figure
	 * @return a figure displaying all state elements
	 */
	public static Figure generateFullStateFigure(TrajectorySet solution, StockEvaluatorState eval) {

		// Create figure w:1000 h:600
		Figure figure = new Figure(1000, 600);
		// Assign title to figure
		figure.getTitle().setText("Stock Market Evaluator Results");
		// Create charts
		ChartPanel stockValue = ChartUtils.createPanel(solution, HybridTime.TIME, "stockValue");
		ChartPanel bestSlope = ChartUtils.createPanel(solution, HybridTime.TIME, "bestSlopeValue");
		ChartPanel bestLog = ChartUtils.createPanel(solution, HybridTime.TIME, "bestLogSlopeValue");
		ChartPanel worstSlope = ChartUtils.createPanel(solution, HybridTime.TIME, "worstSlopeValue");
		ChartPanel slope = ChartUtils.createPanel(solution, HybridTime.TIME, "stockSlope");
		ChartPanel logSlope = ChartUtils.createPanel(solution, HybridTime.TIME, "stockLogSlope");
		// Label chart axis and configure legend visibility
		ChartUtils.configureLabels(stockValue, "Time (sec)", null, "Stock Values", true);
		ChartUtils.configureLabels(bestSlope, "Time (sec)", null, "Best Slope: " + eval.bestSlopeIndex, false);
		ChartUtils.configureLabels(bestLog, "Time (sec)", null, "Best Log Slope: " + eval.bestLogSlopeIndex, false);
		ChartUtils.configureLabels(worstSlope, "Time (sec)", null, "Worst Slope: " + eval.worstSlopeIndex, false);
		ChartUtils.configureLabels(slope, "Time (sec)", null, "Stock Slope", false);
		ChartUtils.configureLabels(logSlope, "Time (sec)", null, "Stock Log Slope: ", false);
		// Add charts to figure
		figure.addComponent(2, 1, stockValue);
		figure.addComponent(0, 0, bestSlope);
		figure.addComponent(1, 0, bestLog);
		figure.addComponent(2, 0, worstSlope);
		figure.addComponent(0, 1, slope);
		figure.addComponent(1, 1, logSlope);
		// Return generated figure
		return figure;
	}

}