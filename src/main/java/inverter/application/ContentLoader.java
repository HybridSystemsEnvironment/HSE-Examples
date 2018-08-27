
package inverter.application;

import edu.ucsc.cross.hse.core.environment.HSEnvironment;
import edu.ucsc.cross.hse.core.modeling.HybridSys;
import inverter.hybridsystem.Cp;
import inverter.hybridsystem.Dp;
import inverter.hybridsystem.Fp;
import inverter.hybridsystem.Gp;
import inverter.hybridsystem.Parameters;
import inverter.hybridsystem.State;

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

		// Initialize inverter parameters (f, R, L, Cap, V, e, b)
		Parameters parameters = new Parameters(60.0, 1.0, 0.1, 6.66e-5, 220.0, .05, 120.0);
		// Initialize inverter state (p0, q0, iL0, vC0)
		State state = new State(1.0, 1.0, 120 * 6.66e-5 * 60.0 * 2 * Math.PI, 0.0);
		// Initialize inverter system
		HybridSys<State> system = createSystem(state, parameters);
		// Add inverter system to environment
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
		Fp f = new Fp(parameters);
		// Initialize the jump map
		Gp g = new Gp(parameters);
		// Initialize the flow set
		Cp c = new Cp(parameters);
		// Initialize the jump set
		Dp d = new Dp(parameters);
		// Initialize the hybrid system
		HybridSys<State> system = new HybridSys<State>(state, f, g, c, d, parameters);

		return system;
	}
}
