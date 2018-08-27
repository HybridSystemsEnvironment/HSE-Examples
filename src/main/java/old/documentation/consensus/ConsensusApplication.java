
package old.documentation.consensus;

import edu.ucsc.cross.hse.core.environment.HSEnvironment;
import edu.ucsc.cross.hse.core.figure.Figure;
import edu.ucsc.cross.hse.core.figure.GraphicFormat;
import edu.ucsc.cross.hse.core.modeling.HybridSys;
import edu.ucsc.cross.hse.core.network.Network;
import edu.ucsc.cross.hse.core.trajectory.HybridTime;
import edu.ucsc.cross.hse.core.trajectory.TrajectorySet;

/**
 * A stock evaluator application that prepares and operates the environment, and
 * generates a figure.
 */
public class ConsensusApplication {

	/**
	 * Main method for running application
	 */
	public static void main(String args[]) {

		// Initialize environment
		HSEnvironment environment = new HSEnvironment();
		// Initialize stock evaluator parameters
		ConsensusParameters params = new ConsensusParameters(.3, .2, .5, false);
		// Initialize network
		Network<HybridSys<AgentState>> network = new Network<HybridSys<AgentState>>(false);
		// Initialize three consensus agent systems
		HybridSys<AgentState> system1 = new HybridSys<AgentState>(new AgentState(-.7, .3, .3), new Fp(network, params),
				new Gp(network, params), new Cp(), new Dp(network, params), params);
		HybridSys<AgentState> system2 = new HybridSys<AgentState>(new AgentState(.2, .5, .5), new Fp(network, params),
				new Gp(network, params), new Cp(), new Dp(network, params), params);
		HybridSys<AgentState> system3 = new HybridSys<AgentState>(new AgentState(.7, .7, .7), new Fp(network, params),
				new Gp(network, params), new Cp(), new Dp(network, params), params);
		// Add systems to environment
		environment.getSystems().add(system1, system2, system3);
		// Create connection between system 1 and 2, and another between 2 and 3
		network.connect(system1, system2);
		network.connect(system2, system3);
		// Run environment (max time duration, max jumps)
		TrajectorySet solution = environment.run(20.0, 200);
		// generate figure
		// Create figure w:800 h:600
		Figure figure = new Figure(800, 600, "Consensus Network Simulation");

		// Create charts and add to figure
		figure.addChart(0, 0, solution, HybridTime.TIME, "systemValue", "Time (sec)", "System Value", null, false);
		figure.addChart(0, 1, solution, HybridTime.TIME, "controllerValue", "Time (sec)", "Controller Value", null,
				false);
		figure.addChart(0, 2, solution, HybridTime.TIME, "communicationTimer", "Time (sec)", "Communication Timer",
				null, false);
		// export figure to pdf
		figure.exportToFile(GraphicFormat.PDF);

	}

}