
package bouncingball.application;

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

		// Create figure
		Figure figure = new Figure(1000, 600, "Bouncing Ball Simulation");
		// Add charts
		figure.add(0, 0, solution, HybridTime.TIME, "yPosition", "Time (sec)", "Y Position (m)", null, false);
		figure.add(1, 0, solution, HybridTime.TIME, "yVelocity", "Time (sec)", "Y Velocity (m/s)", null, false);
		// Export the figure as a pdf
		return figure;
	}
}
