
package documentation.stockeval;

import edu.ucsc.cross.hse.core.modeling.JumpSet;

public class Dp implements JumpSet<StockEvaluatorState> {

	@Override
	public boolean D(StockEvaluatorState x) {

		// Check if necessary to query stock market again
		boolean queryStockMarket = x.evaluationTimer <= 0.0;
		return queryStockMarket;
	}
}
