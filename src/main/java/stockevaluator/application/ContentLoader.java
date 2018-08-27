
package stockevaluator.application;

import edu.ucsc.cross.hse.core.environment.HSEnvironment;
import edu.ucsc.cross.hse.core.modeling.HybridSys;
import pl.zankowski.iextrading4j.api.stocks.ChartRange;
import stockevaluator.hybridsystem.Cp;
import stockevaluator.hybridsystem.Dp;
import stockevaluator.hybridsystem.Fp;
import stockevaluator.hybridsystem.Gp;
import stockevaluator.hybridsystem.Parameters;
import stockevaluator.hybridsystem.State;

/**
 * A content loader
 */
public class ContentLoader {

	/**
	 * Load the environment contents
	 * 
	 * @param environment
	 *            environment to be loaded
	 */
	public static void loadEnvironmentContent(HSEnvironment environment) {

		// Initialize stock index array
		String[] indices = new String[] { "GOOG", "AAPL", "FB" };
		// Initialize stock evaluator parameters
		Parameters params = new Parameters(ChartRange.ONE_YEAR, 60.0, indices);
		// Initialize the state
		State state = new State(params);
		// Initialize new stock evaluator
		HybridSys<State> system = createSystem(state, params);
		// Add stock evaluator to environment
		environment.getSystems().add(system);
	}

	/**
	 * Create the hybrid system
	 * 
	 * @param state
	 *            system state
	 * @param parameters
	 *            system parameters
	 * @return initialized hybrid system
	 */
	public static HybridSys<State> createSystem(State state, Parameters parameters) {

		// Initialize the flow map
		Fp f = new Fp();
		// Initialize the jump map
		Gp g = new Gp(parameters);
		// Initialize the flow set
		Cp c = new Cp();
		// Initialize the jump set
		Dp d = new Dp();
		// Initialize the hybrid system
		HybridSys<State> system = new HybridSys<State>(state, f, g, c, d, parameters);

		return system;
	}
}
