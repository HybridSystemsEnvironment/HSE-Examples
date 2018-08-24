
package stockevaluator.externaldef;

import java.util.ArrayList;

import org.apache.commons.math3.stat.regression.SimpleRegression;

import pl.zankowski.iextrading4j.api.stocks.ChartRange;

public class StockAnalyzer {

	/*
	 * Computes the best log slope value
	 */
	public static Double computeBestLogSlope(ArrayList<StockState> stocks, StockEvaluatorState up) {

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
	public static Double computeBestSlope(ArrayList<StockState> stocks, StockEvaluatorState up) {

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
	public static Double computeWorstSlope(ArrayList<StockState> stocks, StockEvaluatorState up) {

		Double worstSlope = Double.MAX_VALUE;
		for (StockState stockHistory : stocks) {
			if (stockHistory.stockSlope < worstSlope) {
				worstSlope = stockHistory.stockSlope;
			}
		}
		return worstSlope;
	}

	public static String getBestLogStockIndex(StockEvaluatorParameters parameters, StockEvaluatorState up) {

		for (StockState stock : up.stocks) {
			Double h = stock.stockLogSlope;

			if (h.equals(up.bestLogSlopeValue)) {
				return stock.stockIndex;
			}
		}
		return "";
	}

	public static String getBestStockIndex(StockEvaluatorParameters parameters, StockEvaluatorState up) {

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
	public static SimpleRegression getStockHistory(StockEvaluatorParameters parameters, StockState state) {

		ChartRange historyInterval = parameters.stockHistoryDataInterval; // get stock history data interval

		SimpleRegression stockHistory = StockDataInput.getStockHistory(historyInterval, state); // get history
		// regression
		return stockHistory;
	}

	/*
	 * Loads the stock histories of all stocks being monitored
	 */
	public static SimpleRegression getStockLogHistory(StockEvaluatorParameters parameters, StockState state) {

		ChartRange historyInterval = parameters.stockHistoryDataInterval; // get stock history data interval

		SimpleRegression stockHistory = StockDataInput.getStockLogHistory(historyInterval, state); // get history
		// regression
		return stockHistory;
	}

	public static String getWorstStockIndex(StockEvaluatorState up) {

		for (StockState stock : up.stocks) {
			Double h = stock.stockSlope;

			if (h.equals(up.worstSlopeValue)) {
				return stock.stockIndex;
			}
		}
		return "";
	}

}
