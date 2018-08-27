
package bouncingball.application;

import bouncingball.hybridsystem.Cp;
import bouncingball.hybridsystem.Dp;
import bouncingball.hybridsystem.Fp;
import bouncingball.hybridsystem.Gp;
import bouncingball.hybridsystem.Parameters;
import bouncingball.hybridsystem.State;
import edu.ucsc.cross.hse.core.environment.HSEnvironment;
import edu.ucsc.cross.hse.core.modeling.HybridSys;

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

		// Initialize the state
		State state = new State(1.0, 1.0);
		// Initialize the parameters
		Parameters parameters = new Parameters(.97, 9.81);
		// Initialize the hybrid system
		HybridSys<State> system = createSystem(state, parameters);
		// Add system to environment
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
		Cp c = new Cp();
		// Initialize the jump set
		Dp d = new Dp();
		// Initialize the hybrid system
		HybridSys<State> system = new HybridSys<State>(state, f, g, c, d, parameters);

		return system;
	}
}
