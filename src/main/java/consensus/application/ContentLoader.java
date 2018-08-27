
package consensus.application;

import consensus.hybridsystem.Cp;
import consensus.hybridsystem.Dp;
import consensus.hybridsystem.Fp;
import consensus.hybridsystem.Gp;
import consensus.hybridsystem.Parameters;
import consensus.hybridsystem.State;
import edu.ucsc.cross.hse.core.environment.HSEnvironment;
import edu.ucsc.cross.hse.core.modeling.HybridSys;
import edu.ucsc.cross.hse.core.network.Network;

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

		Parameters params = new Parameters(.3, .2, .5, false);
		// Initialize network
		Network<HybridSys<State>> network = new Network<HybridSys<State>>(false);
		// Initialize three consensus agent systems
		HybridSys<State> system1 = createSystem(new State(-.7, .3, .3), params, network);
		HybridSys<State> system2 = createSystem(new State(.2, .5, .5), params, network);
		HybridSys<State> system3 = createSystem(new State(.7, .7, .7), params, network);
		// Add systems to environment
		environment.getSystems().add(system1, system2, system3);
		// Create connection between system 1 and 2, and another between 2 and 3
		network.connect(system1, system2);
		network.connect(system2, system3);
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
		Fp f = new Fp(network, parameters);
		// Initialize the jump map
		Gp g = new Gp(network, parameters);
		// Initialize the flow set
		Cp c = new Cp();
		// Initialize the jump set
		Dp d = new Dp(network, parameters);
		// Initialize the hybrid system
		HybridSys<State> system = new HybridSys<State>(state, f, g, c, d, parameters);

		return system;
	}
}
