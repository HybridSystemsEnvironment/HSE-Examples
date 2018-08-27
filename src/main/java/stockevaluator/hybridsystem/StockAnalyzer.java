
package stockevaluator.hybridsystem;

import java.util.ArrayList;

import org.apache.commons.math3.stat.regression.SimpleRegression;

import pl.zankowski.iextrading4j.api.stocks.ChartRange;

public class StockAnalyzer {

	/*
	 * Computes the best log slope value
	 */
	public static Double computeBestLogSlope(ArrayList<SubState> stocks, State up) {

		Double bestSlope = -Double.MAX_VALUE;
		for (SubState stockHistory : stocks) {
			if (stockHistory.stockLogSlope > bestSlope) {
				bestSlope = stockHistory.stockLogSlope;
			}
		}
		return bestSlope;
	}

	/*
	 * Computes the best slope value
	 */
	public static Double computeBestSlope(ArrayList<SubState> stocks, State up) {

		Double bestSlope = -Double.MAX_VALUE;
		for (SubState stockHistory : stocks) {
			if (stockHistory.stockSlope > bestSlope) {
				bestSlope = stockHistory.stockSlope;
			}
		}
		return bestSlope;
	}

	/*
	 * Computes the worst slope value
	 */
	public static Double computeWorstSlope(ArrayList<SubState> stocks, State up) {

		Double worstSlope = Double.MAX_VALUE;
		for (SubState stockHistory : stocks) {
			if (stockHistory.stockSlope < worstSlope) {
				worstSlope = stockHistory.stockSlope;
			}
		}
		return worstSlope;
	}

	public static String getBestLogStockIndex(Parameters parameters, State up) {

		for (SubState stock : up.stocks) {
			Double h = stock.stockLogSlope;

			if (h.equals(up.bestLogSlopeValue)) {
				return stock.stockIndex;
			}
		}
		return "";
	}

	public static String getBestStockIndex(Parameters parameters, State up) {

		for (SubState stock : up.stocks) {
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
	public static SimpleRegression getStockHistory(Parameters parameters, SubState state) {

		ChartRange historyInterval = parameters.stockHistoryDataInterval; // get stock history data interval

		SimpleRegression stockHistory = DataPlugin.getStockHistory(historyInterval, state); // get history
		// regression
		return stockHistory;
	}

	/*
	 * Loads the stock histories of all stocks being monitored
	 */
	public static SimpleRegression getStockLogHistory(Parameters parameters, SubState state) {

		ChartRange historyInterval = parameters.stockHistoryDataInterval; // get stock history data interval

		SimpleRegression stockHistory = DataPlugin.getStockLogHistory(historyInterval, state); // get history
		// regression
		return stockHistory;
	}

	public static String getWorstStockIndex(State up) {

		for (SubState stock : up.stocks) {
			Double h = stock.stockSlope;

			if (h.equals(up.worstSlopeValue)) {
				return stock.stockIndex;
			}
		}
		return "";
	}

}
