
package stockevaluator.hybridsystem;

import edu.ucsc.cross.hse.core.modeling.FlowSet;

public class Cp implements FlowSet<State> {

	@Override
	public boolean C(State x) {

		boolean waitToQueryStockMarket = x.evaluationTimer >= 0.0;
		return waitToQueryStockMarket;
	}

}
