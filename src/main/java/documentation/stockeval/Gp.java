
package documentation.stockeval;

import edu.ucsc.cross.hse.core.modeling.JumpMap;

public class Gp implements JumpMap<StockEvaluatorState> {

	StockEvaluatorParameters parameters;

	public Gp(StockEvaluatorParameters parameters) {

		this.parameters = parameters;
	}

	@Override
	public void G(StockEvaluatorState x, StockEvaluatorState x_plus) {

		x_plus.evaluationTimer = parameters.evaluationPeriod; // reset the evaluation timer state

		for (int i = 0; i < x_plus.stocks.size(); i++) {
			x_plus.stocks.get(i).stockIndex = x.stocks.get(i).stockIndex; // keep same index

			x_plus.stocks.get(i).stockHistory = StockAnalyzer.getStockHistory(parameters, x.stocks.get(i)); // update
																											// stock
			// history

			x_plus.stocks.get(i).stockHistoryLog = StockAnalyzer.getStockLogHistory(parameters, x.stocks.get(i)); // update
			// stock log
			// history

			x_plus.stocks.get(i).stockValue = StockDataInput.getStockPrice(x_plus.stocks.get(i)); // compute stock value

			x_plus.stocks.get(i).stockSlope = x_plus.stocks.get(i).computeStockSlope(); // compute stock slope

			x_plus.stocks.get(i).stockLogSlope = x_plus.stocks.get(i).computeStockLogSlope(); // compute stock log slope
		}

		x_plus.bestSlopeValue = StockAnalyzer.computeBestSlope(x_plus.stocks, x_plus); // compute best slope value

		x_plus.bestLogSlopeValue = StockAnalyzer.computeBestLogSlope(x_plus.stocks, x_plus); // compute best log
																								// slope value

		x_plus.worstSlopeValue = StockAnalyzer.computeWorstSlope(x_plus.stocks, x_plus); // compute worst slope
																							// value

		x_plus.bestSlopeIndex = StockAnalyzer.getBestStockIndex(parameters, x_plus); // get best slope index

		x_plus.bestLogSlopeIndex = StockAnalyzer.getBestLogStockIndex(parameters, x_plus); // get best log slope
																							// index

		x_plus.worstSlopeIndex = StockAnalyzer.getWorstStockIndex(x_plus); // get worst slope index

	}
}
