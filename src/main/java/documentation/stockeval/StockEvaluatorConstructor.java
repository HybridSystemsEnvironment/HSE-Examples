
package documentation.stockeval;

import edu.ucsc.cross.hse.core.environment.HSEnvironment;
import edu.ucsc.cross.hse.core.modeling.HybridSys;

public class StockEvaluatorConstructor {

	public static HybridSys<StockEvaluatorState> addDefaultStockEvaluator(HSEnvironment environment,
			StockEvaluatorParameters params) {
	
		// Initialize stock evaluator system
		HybridSys<StockEvaluatorState> system = new HybridSys<StockEvaluatorState>(new StockEvaluatorState(params),
				new Fp(), new Gp(params), new Cp(), new Dp(), params);
		environment.getSystems().add(system);
		return system;
	}
}
