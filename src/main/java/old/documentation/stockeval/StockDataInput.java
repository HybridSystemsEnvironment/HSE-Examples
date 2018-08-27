
package old.documentation.stockeval;

import java.util.List;

import org.apache.commons.math3.stat.regression.SimpleRegression;

import edu.ucsc.cross.hse.core.logging.Console;
import pl.zankowski.iextrading4j.api.stocks.Chart;
import pl.zankowski.iextrading4j.api.stocks.ChartRange;
import pl.zankowski.iextrading4j.client.IEXTradingClient;
import pl.zankowski.iextrading4j.client.rest.request.stocks.ChartRequestBuilder;
import pl.zankowski.iextrading4j.client.rest.request.stocks.PriceRequestBuilder;

public class StockDataInput {

	/*
	 * Creates all of the stock states, which aren't already included in the state,
	 * that will be monitored by this evaluator
	 */
	public static void createStockStates(StockEvaluatorParameters parameters, StockEvaluatorState eval_state) {

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
					eval_state.stocks.add(newStock);

				} catch (Exception createStockStateFail) {
					// Print a stock state creation failure notification to console
					Console.error("Failed to create stock state for index :" + stockIndex);

				}

			}
		}

	}

	/*
	 * Gets the stock history regression
	 * 
	 * @param start_date : starting date/time of the history
	 * 
	 * @param history_ChartRange : ChartRange to load history data points
	 */
	public static SimpleRegression getStockLogHistory(ChartRange history_range, StockState state) {

		SimpleRegression historyLogRegression = new SimpleRegression(true);
		try {

			// get stock with history information
			final List<Chart> chartList = getChartRequest(history_range, state);

			Integer dataIndex = 0;
			// iterate through all stock history data
			for (Chart chart : chartList) {
				// get the current quote

				// add the current quote closing value to the stock history regression
				historyLogRegression.addData(dataIndex++, Math.log(chart.getClose().doubleValue()));
			}

		} catch (Exception failedToLoadHistory) {
			// print failed to load history notification
			System.out.println("Failed to load stock history: " + state.stockIndex);

			// print failed to load details
			failedToLoadHistory.printStackTrace();
		}
		return historyLogRegression;
	}

	/*
	 * Gets the stock history regression
	 * 
	 * @param start_date : starting date/time of the history
	 * 
	 * @param history_ChartRange : ChartRange to load history data points
	 */
	public static SimpleRegression getStockHistory(ChartRange history_range, StockState state) {

		SimpleRegression historyRegression = new SimpleRegression(true);
		try {

			// get stock history information
			final List<Chart> chartList = getChartRequest(history_range, state);

			Integer dataIndex = 0;
			for (Chart chart : chartList) {
				// add the current quote closing value to the stock history regression
				historyRegression.addData(dataIndex++, (chart.getClose().doubleValue()));
			}

		} catch (Exception failedToLoadHistory) {
			// print failed to load history notification
			System.out.println("Failed to load stock history: " + state.stockIndex);

			// print failed to load details
			failedToLoadHistory.printStackTrace();
		}
		return historyRegression;
	}

	/*
	 * Gets the current stock price
	 */
	public static Double getStockPrice(StockState state) {

		// get stock with history information
		Double price = 0.0;
		try {
			final IEXTradingClient iexTradingClient = IEXTradingClient.create();
			price = iexTradingClient.executeRequest(new PriceRequestBuilder().withSymbol(state.stockIndex).build())
					.doubleValue();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return price;
	}

	/**
	 * Get the list of chart values for a given history range
	 * 
	 * @param history_range
	 *            range of values to fetch
	 * @return list of stock values
	 */
	public static List<Chart> getChartRequest(ChartRange history_range, StockState state) {

		final IEXTradingClient iexTradingClient = IEXTradingClient.create();
		final List<Chart> chartList = iexTradingClient.executeRequest(
				new ChartRequestBuilder().withChartRange(history_range).withSymbol(state.stockIndex).build());
		return chartList;
	}
}
