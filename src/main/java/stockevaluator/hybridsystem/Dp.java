
package stockevaluator.hybridsystem;

import edu.ucsc.cross.hse.core.modeling.JumpSet;

public class Dp implements JumpSet<State> {

	@Override
	public boolean D(State x) {

		// Check if necessary to query stock market again
		boolean queryStockMarket = x.evaluationTimer <= 0.0;
		return queryStockMarket;
	}
}
