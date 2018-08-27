
package stockevaluator.hybridsystem;

import org.apache.commons.math3.stat.regression.SimpleRegression;

import edu.ucsc.cross.hse.core.modeling.DataStructure;

public class SubState extends DataStructure {

	/*
	 * Regression of the stock closing values
	 */
	public SimpleRegression stockHistory;

	/*
	 * Regression of the log of the stock values
	 */
	public SimpleRegression stockHistoryLog;

	/*
	 * Index of the current stock ie - Google is GOOG
	 */
	public String stockIndex;

	/*
	 * Calculated stock slope based on the regression
	 */
	public double stockSlope;

	/*
	 * Calculated log stock slope based on the log of the slope regression
	 */
	public double stockLogSlope;

	/*
	 * Current stock value
	 */
	public double stockValue;

	/**
	 * Constructor with stock index defined
	 *
	 * @param index
	 *            stock index string
	 */
	public SubState(String index) {

		stockIndex = (index); // set the index as the state name
		createElements(index);
		getProperties().setName(index);
		this.getProperties().setStoreTrajectory(true);
	}

	/*
	 * Create stock component elements
	 */
	private void createElements(String index) {

		stockHistory = new SimpleRegression(true);
		stockHistoryLog = new SimpleRegression(true);
		stockIndex = index;
		stockSlope = 0.0;
		stockValue = 0.0;
		stockLogSlope = 0.0;
	}

	/*
	 * Computes the slope of the current loaded stock history
	 */
	public Double computeStockSlope() {

		return stockHistory.getSlope();
	}

	/*
	 * Computes the slope of the current loaded stock history
	 */
	public Double computeStockLogSlope() {

		return stockHistoryLog.getSlope();
	}

}
