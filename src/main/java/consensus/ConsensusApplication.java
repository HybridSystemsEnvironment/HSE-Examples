
package consensus;

import org.jfree.chart.ChartPanel;

import edu.ucsc.cross.hse.core.chart.ChartUtils;
import edu.ucsc.cross.hse.core.environment.HSEnvironment;
import edu.ucsc.cross.hse.core.figure.Figure;
import edu.ucsc.cross.hse.core.trajectory.HybridTime;
import edu.ucsc.cross.hse.core.trajectory.TrajectorySet;
import network.IdealNetwork;

/**
 * A consensus network application that prepares and operates the environment,
 * and generates a figure.
 * 
 * @author Brendan Short
 *
 */
public class ConsensusApplication {

	/**
	 * Main method for running application
	 * 
	 * @param args
	 *            none
	 */
	public static void main(String args[]) {

		// Initialize environment
		HSEnvironment environment = new HSEnvironment();
		// Initialize consensus parameters (gain, min comm time, max comm time,sync)
		ConsensusParameters params = new ConsensusParameters(.3, .5, 1.0, false);
		// Initialize agent states (system val, controller val, timer val)
		ConsensusAgentState state1 = new ConsensusAgentState(.3, .3, .3);
		ConsensusAgentState state2 = new ConsensusAgentState(.5, .5, .5);
		ConsensusAgentState state3 = new ConsensusAgentState(.7, .7, .7);
		// Initialize network
		IdealNetwork<ConsensusAgentState> network = new IdealNetwork<ConsensusAgentState>();
		// Connect agent states (source, target)
		network.connect(state1, state2);
		network.connect(state2, state3);
		// Initialize agent systems
		ConsensusAgentSystem system1 = new ConsensusAgentSystem(state1, network, params);
		ConsensusAgentSystem system2 = new ConsensusAgentSystem(state2, network, params);
		ConsensusAgentSystem system3 = new ConsensusAgentSystem(state3, network, params);
		// Add agent systems to environment
		environment.getSystems().add(system1, system2, system3);
		// Run environment (max time duration, max jumps)
		environment.run(20.0, 200);
		// Generate a figure of the trajectories
		Figure figure = generateFullStateFigure(environment.getTrajectories());
		// Display the figure in new window
		figure.display();

	}

	/**
	 * Generate a figure with all state elements
	 * 
	 * @param solution
	 *            trajectory set containing data to load into figure
	 * @return a figure displaying all state elements
	 */
	public static Figure generateFullStateFigure(TrajectorySet solution) {

		// Create figure w:800 h:600
		Figure figure = new Figure(800, 600);
		// Assign title to figure
		figure.getTitle().setText("Consensus Network Simulation");
		// Create charts
		ChartPanel xPos = ChartUtils.createPanel(solution, HybridTime.TIME, "systemValue");
		ChartPanel yPos = ChartUtils.createPanel(solution, HybridTime.TIME, "controllerValue");
		ChartPanel xVel = ChartUtils.createPanel(solution, HybridTime.TIME, "communicationTimer");
		// Label chart axis and configure legend visibility
		ChartUtils.configureLabels(xPos, "Time (sec)", "System Value", null, false);
		ChartUtils.configureLabels(yPos, "Time (sec)", "Controller Value", null, false);
		ChartUtils.configureLabels(xVel, "Time (sec)", "Communication Timer", null, false);
		// Add charts to figure
		figure.addComponent(0, 0, xPos);
		figure.addComponent(0, 1, xVel);
		figure.addComponent(0, 2, yPos);
		// Return generated figure
		return figure;
	}
}