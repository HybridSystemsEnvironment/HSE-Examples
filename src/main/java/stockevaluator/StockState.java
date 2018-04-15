package stockevaluator;

import java.util.List;

import org.apache.commons.math3.stat.regression.SimpleRegression;

import edu.ucsc.cross.hse.core.modeling.DataStructure;
import pl.zankowski.iextrading4j.api.stocks.Chart;
import pl.zankowski.iextrading4j.api.stocks.ChartRange;
import pl.zankowski.iextrading4j.client.IEXTradingClient;
import pl.zankowski.iextrading4j.client.rest.request.stocks.ChartRequestBuilder;
import pl.zankowski.iextrading4j.client.rest.request.stocks.PriceRequestBuilder;

public class StockState extends DataStructure
{

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
	public StockState(String index)
	{
		stockIndex = (index); // set the index as the state name
		createElements(index);
		getProperties().setName(index);
		this.getProperties().setStoreTrajectory(true);
	}

	/*
	 * Create stock component elements
	 */
	private void createElements(String index)
	{
		stockHistory = new SimpleRegression(true);
		stockHistoryLog = new SimpleRegression(true);
		stockIndex = index;
		stockSlope = 0.0;
		stockValue = 0.0;
		stockLogSlope = 0.0;
	}

	/*
	 * Gets the stock history regression
	 * 
	 * @param start_date : starting date/time of the history
	 * 
	 * @param history_ChartRange : ChartRange to load history data points
	 */
	public SimpleRegression getStockLogHistory(ChartRange history_range)
	{
		SimpleRegression historyLogRegression = new SimpleRegression(true);
		try
		{

			// get stock with history information
			final List<Chart> chartList = getChartRequest(history_range);

			Integer dataIndex = 0;
			// iterate through all stock history data
			for (Chart chart : chartList)
			{
				// get the current quote

				// add the current quote closing value to the stock history regression
				historyLogRegression.addData(dataIndex++, Math.log(chart.getClose().doubleValue()));
			}

		} catch (Exception failedToLoadHistory)
		{
			// print failed to load history notification
			System.out.println("Failed to load stock history: " + stockIndex);

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
	public SimpleRegression getStockHistory(ChartRange history_range)
	{
		SimpleRegression historyRegression = new SimpleRegression(true);
		try
		{

			// get stock history information
			final List<Chart> chartList = getChartRequest(history_range);

			Integer dataIndex = 0;
			for (Chart chart : chartList)
			{
				// add the current quote closing value to the stock history regression
				historyRegression.addData(dataIndex++, (chart.getClose().doubleValue()));
			}

		} catch (Exception failedToLoadHistory)
		{
			// print failed to load history notification
			System.out.println("Failed to load stock history: " + stockIndex);

			// print failed to load details
			failedToLoadHistory.printStackTrace();
		}
		return historyRegression;
	}

	/*
	 * Gets the current stock price
	 */
	public Double getStockPrice()
	{
		// get stock with history information
		Double price = 0.0;
		try
		{
			final IEXTradingClient iexTradingClient = IEXTradingClient.create();
			price = iexTradingClient.executeRequest(new PriceRequestBuilder().withSymbol(stockIndex).build())
			.doubleValue();
		} catch (Exception e)
		{
			e.printStackTrace();
		}
		return price;
	}

	/*
	 * Computes the slope of the current loaded stock history
	 */
	public Double computeStockSlope()
	{
		return stockHistory.getSlope();
	}

	/*
	 * Computes the slope of the current loaded stock history
	 */
	public Double computeStockLogSlope()
	{
		return stockHistoryLog.getSlope();
	}

	/**
	 * Get the list of chart values for a given history range
	 * 
	 * @param history_range
	 *            range of values to fetch
	 * @return list of stock values
	 */
	public List<Chart> getChartRequest(ChartRange history_range)
	{
		final IEXTradingClient iexTradingClient = IEXTradingClient.create();
		final List<Chart> chartList = iexTradingClient
		.executeRequest(new ChartRequestBuilder().withChartRange(history_range).withSymbol(stockIndex).build());
		return chartList;
	}
}
