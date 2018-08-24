
package documentation.stockeval;

import java.util.ArrayList;

import edu.ucsc.cross.hse.core.modeling.DataStructure;

public class StockEvaluatorState extends DataStructure {

	/*
	 * Timer that triggers the stock evaluation algorithm on expirationS
	 */
	public double evaluationTimer;

	/*
	 * State variable that contains the best current slope value
	 */
	public double bestSlopeValue;

	/*
	 * State variable that contains the best current log slope value
	 */
	public double bestLogSlopeValue;

	/*
	 * Worst slope value (example state)
	 */
	public double worstSlopeValue;

	public String worstSlopeIndex;

	public String bestSlopeIndex;

	public String bestLogSlopeIndex;

	/*
	 * List of stocks that are being evaluated
	 */
	public ArrayList<StockState> stocks;

	public StockEvaluatorState(StockEvaluatorParameters params) {

		evaluationTimer = 0.0;
		bestSlopeValue = 0.0;
		worstSlopeValue = 0.0;
		bestLogSlopeValue = 0.0;
		worstSlopeIndex = "";
		bestLogSlopeIndex = "";
		bestSlopeIndex = "";
		stocks = new ArrayList<StockState>();
		StockDataInput.createStockStates(params, this);
	}

	public StockEvaluatorState(Double evaluation_time, Double best_slope_value, Double worst_slope_value) {

		evaluationTimer = evaluation_time;
		bestSlopeValue = best_slope_value;
		worstSlopeValue = worst_slope_value;
		bestLogSlopeValue = 0.0;
		stocks = new ArrayList<StockState>();
	}

	public StockEvaluatorState(Double evaluation_time, Double best_slope_value, Double worst_slope_value,
			ArrayList<StockState> evaluated_stocks) {

		evaluationTimer = evaluation_time;
		bestSlopeValue = best_slope_value;
		worstSlopeValue = worst_slope_value;
		bestLogSlopeValue = 0.0;
		stocks = evaluated_stocks;
	}
}
