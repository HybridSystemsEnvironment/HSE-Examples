
package firefly.application;

import edu.ucsc.cross.hse.core.environment.HSEnvironment;
import edu.ucsc.cross.hse.core.modeling.HybridSys;
import edu.ucsc.cross.hse.core.network.Network;
import firefly.hybridsystem.Cp;
import firefly.hybridsystem.Dp;
import firefly.hybridsystem.Fp;
import firefly.hybridsystem.Gp;
import firefly.hybridsystem.Parameters;
import firefly.hybridsystem.State;

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

		// Initialize the parameters
		Parameters parameters = new Parameters(0.1);
		// Initialize network
		Network<HybridSys<State>> network = new Network<HybridSys<State>>(true);
		// Initialize the hybrid system
		HybridSys<State> systemA = createSystem(new State(0.0), parameters, network);
		HybridSys<State> systemB = createSystem(new State(0.5), parameters, network);
		network.connect(systemA, systemB);
		network.connect(systemB, systemA);
		// Add system to environment
		environment.getSystems().add(systemA, systemB);
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
	public static HybridSys<State> createSystem(State state, Parameters parameters, Network<HybridSys<State>> network) {

		// Initialize the flow map
		Fp f = new Fp();
		// Initialize the jump map
		Gp g = new Gp(parameters);
		// Initialize the flow set
		Cp c = new Cp(network);
		// Initialize the jump set
		Dp d = new Dp(network);
		// Initialize the hybrid system
		HybridSys<State> system = new HybridSys<State>(state, f, g, c, d, parameters);

		return system;
	}
}
