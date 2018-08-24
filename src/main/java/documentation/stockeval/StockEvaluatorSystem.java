
package documentation.stockeval;

import java.util.ArrayList;

import org.apache.commons.math3.stat.regression.SimpleRegression;

import edu.ucsc.cross.hse.core.logging.Console;
import edu.ucsc.cross.hse.core.modeling.HybridSystem;
import pl.zankowski.iextrading4j.api.stocks.ChartRange;
import pl.zankowski.iextrading4j.client.IEXTradingClient;
import pl.zankowski.iextrading4j.client.rest.request.stocks.ChartRequestBuilder;

public class StockEvaluatorSystem extends HybridSystem<StockEvaluatorState> {

	/**
	 * Evaluation parameters
	 */
	public StockEvaluatorParameters parameters;

	/*
	 * Constructor that creates an instance of this class
	 */
	public StockEvaluatorSystem(StockEvaluatorState state, StockEvaluatorParameters parameters) {

		this(state, parameters, true);
	}

	public StockEvaluatorSystem(StockEvaluatorState state, StockEvaluatorParameters parameters, boolean initialize) {

		super(state);
		this.parameters = parameters;
		if (initialize) {
			createStockStates();
		}
	}

	@Override
	public boolean C(StockEvaluatorState x) {

		// Check if necessary to wait to query stock market
		boolean waitToQueryStockMarket = x.evaluationTimer >= 0.0;
		return waitToQueryStockMarket;
	}

	@Override
	public void F(StockEvaluatorState x, StockEvaluatorState x_dot) {

		for (StockState stock : x_dot.stocks) // iterate through evaluated stocks
		{
			stock.stockSlope = 0.0; // set stock slope derivative

			stock.stockValue = 0.0; // set stock value derivative

			stock.stockLogSlope = 0.0;
		}

		x_dot.evaluationTimer = -1.0; // set the evaluation timer derivative

		x_dot.bestSlopeValue = 0.0; // set the best slope value derivative

		x_dot.bestLogSlopeValue = 0.0; // set the best log slope derivative

		x_dot.worstSlopeValue = 0.0; // set the worst slope value derivative
	}

	@Override
	public boolean D(StockEvaluatorState x) {

		// Check if necessary to query stock market again
		boolean queryStockMarket = x.evaluationTimer <= 0.0;
		return queryStockMarket;
	}

	@Override
	public void G(StockEvaluatorState x, StockEvaluatorState x_plus) {

		x_plus.evaluationTimer = parameters.evaluationPeriod; // reset the evaluation timer state

		for (int i = 0; i < x_plus.stocks.size(); i++) {
			x_plus.stocks.get(i).stockIndex = x.stocks.get(i).stockIndex; // keep same index

			x_plus.stocks.get(i).stockHistory = getStockHistory(x.stocks.get(i)); // update stock history

			x_plus.stocks.get(i).stockHistoryLog = getStockLogHistory(x.stocks.get(i)); // update stock log history

			x_plus.stocks.get(i).stockValue = StockDataInput.getStockPrice(x_plus.stocks.get(i)); // compute stock value

			x_plus.stocks.get(i).stockSlope = x_plus.stocks.get(i).computeStockSlope(); // compute stock slope

			x_plus.stocks.get(i).stockLogSlope = x_plus.stocks.get(i).computeStockLogSlope(); // compute stock log slope
		}

		x_plus.bestSlopeValue = computeBestSlope(x_plus.stocks, x_plus); // compute best slope value

		x_plus.bestLogSlopeValue = computeBestLogSlope(x_plus.stocks, x_plus); // compute best log slope value

		x_plus.worstSlopeValue = computeWorstSlope(x_plus.stocks, x_plus); // compute worst slope value

		x_plus.bestSlopeIndex = getBestStockIndex(x_plus); // get best slope index

		x_plus.bestLogSlopeIndex = getBestLogStockIndex(x_plus); // get best log slope index

		x_plus.worstSlopeIndex = getWorstStockIndex(x_plus); // get worst slope index

	}

	/*
	 * Computes the best log slope value
	 */
	private Double computeBestLogSlope(ArrayList<StockState> stocks, StockEvaluatorState up) {

		Double bestSlope = -Double.MAX_VALUE;
		for (StockState stockHistory : stocks) {
			if (stockHistory.stockLogSlope > bestSlope) {
				bestSlope = stockHistory.stockLogSlope;
			}
		}
		return bestSlope;
	}

	/*
	 * Computes the best slope value
	 */
	private Double computeBestSlope(ArrayList<StockState> stocks, StockEvaluatorState up) {

		Double bestSlope = -Double.MAX_VALUE;
		for (StockState stockHistory : stocks) {
			if (stockHistory.stockSlope > bestSlope) {
				bestSlope = stockHistory.stockSlope;
			}
		}
		return bestSlope;
	}

	/*
	 * Computes the worst slope value
	 */
	private Double computeWorstSlope(ArrayList<StockState> stocks, StockEvaluatorState up) {

		Double worstSlope = Double.MAX_VALUE;
		for (StockState stockHistory : stocks) {
			if (stockHistory.stockSlope < worstSlope) {
				worstSlope = stockHistory.stockSlope;
			}
		}
		return worstSlope;
	}

	/*
	 * Creates all of the stock states, which aren't already included in the state,
	 * that will be monitored by this evaluator
	 */
	private void createStockStates() {

		// gets an array of all the stock indices to be monitored
		String[] stockIndices = parameters.stockIndicesListString;

		// iterates through the array of stock indices
		for (String stockIndex : stockIndices) {
			if (stockIndex != null) // make sure index is non-null
			{
				try // attempt to create a stock state for the current index
				{

					// create api client
					final IEXTradingClient iexTradingClient = IEXTradingClient.create();

					// attempt to get stock values
					iexTradingClient.executeRequest(new ChartRequestBuilder().withChartRange(ChartRange.ONE_DAY)
							.withSymbol(stockIndex).build());

					// Create a new stock state for the current stock index
					StockState newStock = new StockState(stockIndex);

					// set store trajectories for stock state
					newStock.getProperties().setStoreTrajectory(true);

					// Add the stock state to the list of stocks being evaluated
					this.getComponents().getState().stocks.add(newStock);

				} catch (Exception createStockStateFail) {
					// Print a stock state creation failure notification to console
					Console.error("Failed to create stock state for index :" + stockIndex);

				}

			}
		}

	}

	private String getBestLogStockIndex(StockEvaluatorState up) {

		for (StockState stock : up.stocks) {
			Double h = stock.stockLogSlope;

			if (h.equals(up.bestLogSlopeValue)) {
				return stock.stockIndex;
			}
		}
		return "";
	}

	private String getBestStockIndex(StockEvaluatorState up) {

		for (StockState stock : up.stocks) {
			Double h = stock.stockSlope;
			if (h.equals(up.bestSlopeValue)) {
				return stock.stockIndex;
			}
		}
		return "";
	}

	/*
	 * Loads the stock histories of all stocks being monitored
	 */
	private SimpleRegression getStockHistory(StockState state) {

		ChartRange historyInterval = parameters.stockHistoryDataInterval; // get stock history data interval

		SimpleRegression stockHistory = StockDataInput.getStockHistory(historyInterval, state); // get history
		// regression
		return stockHistory;
	}

	/*
	 * Loads the stock histories of all stocks being monitored
	 */
	private SimpleRegression getStockLogHistory(StockState state) {

		ChartRange historyInterval = parameters.stockHistoryDataInterval; // get stock history data interval

		SimpleRegression stockHistory = StockDataInput.getStockLogHistory(historyInterval, state); // get history
		// regression
		return stockHistory;
	}

	private String getWorstStockIndex(StockEvaluatorState up) {

		for (StockState stock : up.stocks) {
			Double h = stock.stockSlope;

			if (h.equals(up.worstSlopeValue)) {
				return stock.stockIndex;
			}
		}
		return "";
	}
}
