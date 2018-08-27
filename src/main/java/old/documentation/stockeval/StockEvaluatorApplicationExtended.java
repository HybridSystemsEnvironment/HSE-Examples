
package old.documentation.stockeval;

import org.jfree.chart.ChartPanel;

import edu.ucsc.cross.hse.core.chart.ChartUtils;
import edu.ucsc.cross.hse.core.environment.EnvironmentSettings;
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
import pl.zankowski.iextrading4j.api.stocks.ChartRange;

public class StockEvaluatorApplicationExtended {

	/**
	 * Main method for running application
	 * 
	 * @param args
	 *            none
	 */
	public static void main(String args[]) {

		loadConsensusConsoleSettings();
		HSEnvironment environment = generateEnvironment();
		environment.run();
		// generateFullStateFigure(environment).display();
		generateFullFigureWithLegend(environment).display();

	}

	/**
	 * Generate the environment
	 * 
	 * @return environment
	 */
	@SuppressWarnings("unused")
	public static HSEnvironment generateEnvironment() {

		HSEnvironment environment = new HSEnvironment();

		ChartRange historyRange = ChartRange.ONE_YEAR;

		String[] indices = new String[] { "GOOG", "AAPL", "FB" };

		String[] top100 = new String[] { "AAL", "AAPL", "ADBE", "ADI", "ADP", "ADSK", "AKAM", "ALGN", "ALXN", "AMAT",
				"AMGN", "AMZN", "ATVI", "AVGO", "BIDU", "BIIB", "BMRN", "CA", "CELG", "CERN", "CHKP", "CHTR", "CTRP",
				"CTAS", "CSCO", "CTXS", "CMCSA", "COST", "CSX", "CTSH", "DISCA", "DISCK", "DISH", "DLTR", "EA", "EBAY",
				"ESRX", "EXPE", "FAST", "FB", "FISV", "FOX", "FOXA", "GILD", "GOOG", "GOOGL", "HAS", "HSIC", "HOLX",
				"ILMN", "INCY", "INTC", "INTU", "ISRG", "JBHT", "JD", "KLAC", "KHC", "LBTYK", "LILA", "LBTYA", "QRTEA",
				"MELI", "MAR", "MAT", "MDLZ", "MNST", "MSFT", "MU", "MXIM", "MYL", "NCLH", "NFLX", "NTES", "NVDA",
				"PAYX", "BKNG", "PYPL", "QCOM", "REGN", "ROST", "SHPG", "SIRI", "SWKS", "SBUX", "SYMC", "TSCO", "TXN",
				"TMUS", "ULTA", "VIAB", "VOD", "VRTX", "WBA", "WDC", "XRAY", "IDXX", "LILAK", "LRCX", "MCHP", "ORLY",
				"PCAR", "STX", "TSLA", "VRSK", "WYNN", "XLNX" };

		SystemSet systems = new SystemSet(createStockEvaluator(historyRange, indices));
		// SystemSet systems = new SystemSet(createStockEvaluator(historyRange,
		// top100));
		EnvironmentSettings settings = getBouncingBallEnvSettings();

		environment = HSEnvironment.create(systems, settings);

		return environment;
	}

	/**
	 * Initializes environment settings with configuration B
	 * 
	 * @return EnvironmentSettings
	 */
	public static EnvironmentSettings getBouncingBallEnvSettings() {

		EnvironmentSettings settings = new EnvironmentSettings();
		// Configure environment settings
		settings.maximumJumps = 10000;
		settings.maximumTime = 25;
		settings.dataPointInterval = .001;
		settings.eventHandlerMaximumCheckInterval = 1E-3;
		settings.eventHandlerConvergenceThreshold = 1E-9;
		settings.maxEventHandlerIterations = 100;
		settings.domainPriority = DomainPriority.JUMP;
		settings.storeNonPrimativeData = false;

		// Setup variable step integrator
		double odeMaximumStepSize = 1e-3;
		double odeMinimumStepSize = 1e-9;
		double odeRelativeTolerance = 1.0e-6;
		double odeSolverAbsoluteTolerance = 1.0e-6;
		FirstOrderIntegratorFactory defaultIntegrator = new DormandPrince853IntegratorFactory(odeMinimumStepSize,
				odeMaximumStepSize, odeRelativeTolerance, odeSolverAbsoluteTolerance);
		settings.integrator = defaultIntegrator;

		return settings;
	}

	/**
	 * Initializes and loads console settings
	 * 
	 * @return console settings
	 */
	public static void loadConsensusConsoleSettings() {

		ConsoleSettings console = new ConsoleSettings();
		console.printStatusInterval = 10.0;
		console.printProgressIncrement = 10;
		console.printIntegratorExceptions = false;
		console.printInfo = true;
		console.printDebug = false;
		console.printWarning = true;
		console.printError = true;
		console.printLogToFile = true;
		console.terminateAtInput = true;
		Console.setSettings(console);
	}

	public static StockEvaluatorSystem createStockEvaluator(ChartRange history_range, String[] indicies) {

		StockEvaluatorParameters params = new StockEvaluatorParameters(history_range, 60.0, indicies);
		StockEvaluatorState state = new StockEvaluatorState(params);
		StockEvaluatorSystem eval = new StockEvaluatorSystem(state, params);
		return eval;
	}

	/**
	 * Generate a figure with all state elements
	 * 
	 * @param solution
	 *            trajectory set containing data to load into figure
	 * @return a figure displaying all state elements
	 */
	public static Figure generateFullStateFigure(HSEnvironment environment) {

		Figure figure = new Figure(1200, 1200);

		TrajectorySet solution = environment.getTrajectories();

		StockEvaluatorState eval = (solution
				.getTrajectory((StockEvaluatorSystem) environment.getSystems().getSystems().get(0))
				.getDataPoint(solution.getFinalTime()));

		ChartPanel stockValue = ChartUtils.createPanel(solution, HybridTime.TIME, "stockValue");
		ChartPanel bestSlope = ChartUtils.createPanel(solution, HybridTime.TIME, "bestSlopeValue");
		ChartPanel bestLogSlope = ChartUtils.createPanel(solution, HybridTime.TIME, "bestLogSlopeValue");
		ChartPanel worstSlope = ChartUtils.createPanel(solution, HybridTime.TIME, "worstSlopeValue");
		ChartPanel slope = ChartUtils.createPanel(solution, HybridTime.TIME, "stockSlope");
		ChartPanel logSlope = ChartUtils.createPanel(solution, HybridTime.TIME, "stockLogSlope");

		ChartUtils.configureLabels(stockValue, "Time (sec)", null, "Stock Values", true);
		ChartUtils.configureLabels(bestSlope, "Time (sec)", null, "Best Slope: " + eval.bestSlopeIndex, false);
		ChartUtils.configureLabels(bestLogSlope, "Time (sec)", null, "Best Log Slope: " + eval.bestLogSlopeIndex,
				false);
		ChartUtils.configureLabels(worstSlope, "Time (sec)", null, "Worst Slope: " + eval.worstSlopeIndex, false);
		ChartUtils.configureLabels(slope, "Time (sec)", null, "Stock Slope", false);
		ChartUtils.configureLabels(logSlope, "Time (sec)", null, "Stock Log Slope: ", false);
		figure.getTitle().setText("Stock Market Evaluator Results");

		figure.addComponent(2, 1, stockValue);
		figure.addComponent(0, 0, bestSlope);
		figure.addComponent(1, 0, bestLogSlope);
		figure.addComponent(2, 0, worstSlope);
		figure.addComponent(0, 1, slope);
		figure.addComponent(1, 1, logSlope);

		return figure;
	}

	public static Figure generateFullFigureWithLegend(HSEnvironment environment) {

		Figure row1 = new Figure(1200, 1200);
		Figure row2 = new Figure(1200, 1200);
		Figure row3 = new Figure(1200, 1200);
		Figure figure = new Figure(1200, 1200);

		TrajectorySet solution = environment.getTrajectories();

		StockEvaluatorState eval = (solution
				.getTrajectory((StockEvaluatorSystem) environment.getSystems().getSystems().get(0))
				.getDataPoint(solution.getFinalTime()));

		ChartPanel stockValue = ChartUtils.createPanel(solution, HybridTime.TIME, "stockValue");
		ChartPanel stockValueWithLegend = ChartUtils.createPanel(solution, HybridTime.TIME, "stockValue");
		ChartPanel bestSlope = ChartUtils.createPanel(solution, HybridTime.TIME, "bestSlopeValue");
		ChartPanel bestLogSlope = ChartUtils.createPanel(solution, HybridTime.TIME, "bestLogSlopeValue");
		ChartPanel worstSlope = ChartUtils.createPanel(solution, HybridTime.TIME, "worstSlopeValue");
		ChartPanel slope = ChartUtils.createPanel(solution, HybridTime.TIME, "stockSlope");
		ChartPanel logSlope = ChartUtils.createPanel(solution, HybridTime.TIME, "stockLogSlope");

		ChartUtils.configureLabels(stockValue, "Time (sec)", null, "Stock Values", false);
		ChartUtils.configureLabels(stockValueWithLegend, "Time (sec)", null, "Stock Values", true);
		ChartUtils.configureLabels(bestSlope, "Time (sec)", null, "Best Slope: " + eval.bestSlopeIndex, false);
		ChartUtils.configureLabels(bestLogSlope, "Time (sec)", null, "Best Log Slope: " + eval.bestLogSlopeIndex,
				false);
		ChartUtils.configureLabels(worstSlope, "Time (sec)", null, "Worst Slope: " + eval.worstSlopeIndex, false);
		ChartUtils.configureLabels(slope, "Time (sec)", null, "Stock Slope", false);
		ChartUtils.configureLabels(logSlope, "Time (sec)", null, "Stock Log Slope: ", false);

		row1.addComponent(0, 0, bestSlope);
		row1.addComponent(1, 0, bestLogSlope);
		row1.addComponent(2, 0, worstSlope);

		row2.addComponent(0, 0, slope);
		row2.addComponent(1, 0, logSlope);
		row2.addComponent(2, 0, stockValue);

		row3.addComponent(0, 0, stockValueWithLegend);

		figure.addComponent(0, 0, row1.getContentPanel());
		figure.addComponent(0, 1, row2.getContentPanel());
		figure.addComponent(0, 2, row3.getContentPanel());

		return figure;
	}

}
