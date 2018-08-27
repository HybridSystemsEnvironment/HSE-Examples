
package consensus.application;

import edu.ucsc.cross.hse.core.environment.HSEnvironment;
import edu.ucsc.cross.hse.core.figure.Figure;
import edu.ucsc.cross.hse.core.trajectory.HybridTime;
import edu.ucsc.cross.hse.core.trajectory.TrajectorySet;

/**
 * A post processor
 */
public class PostProcessor {

	/**
	 * Executes all processing tasks. This method is called by the main applications
	 */
	public static void processEnvironmentData(HSEnvironment environment) {

		Figure figure = generateAllStateFigure(environment.getTrajectories());
		figure.display();
	}

	/**
	 * Generate a figure
	 * 
	 * @param solution
	 *            trajectory set containing data to load into figure
	 * @return a figure displaying state values
	 */
	public static Figure generateAllStateFigure(TrajectorySet solution) {

		Figure figure = new Figure(650, 800, "Consensus Network Simulation");

		// Create charts and add to figure
		figure.add(0, 0, solution, HybridTime.TIME, "systemValue", "Time (sec)", "System Value", null, false);
		figure.add(1, 0, solution, HybridTime.TIME, "controllerValue", "Time (sec)", "Controller Value", null, false);
		figure.add(2, 0, solution, HybridTime.TIME, "communicationTimer", "Time (sec)", "Communication Timer", null,
				false);
		return figure;
	}
}
