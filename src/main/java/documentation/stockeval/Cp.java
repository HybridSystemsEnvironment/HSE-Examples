
package documentation.stockeval;

import edu.ucsc.cross.hse.core.modeling.FlowSet;

public class Cp implements FlowSet<StockEvaluatorState> {

	@Override
	public boolean C(StockEvaluatorState x) {

		boolean waitToQueryStockMarket = x.evaluationTimer >= 0.0;
		return waitToQueryStockMarket;
	}

}
