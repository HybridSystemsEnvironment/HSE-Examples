
package bouncingball.combined;

import edu.ucsc.cross.hse.core.environment.HSEnvironment;
import edu.ucsc.cross.hse.core.figure.Figure;
import edu.ucsc.cross.hse.core.trajectory.HybridTime;
import edu.ucsc.cross.hse.core.trajectory.TrajectorySet;

public class BouncingBallPlotLibrary {

	public static Figure generateVerticalStateFigure(HSEnvironment environment) {

		TrajectorySet traj = environment.getTrajectories();
		Figure figure = new Figure(1000, 600, "Bouncing Ball Simulation");
		figure.addChart(0, 0, traj, HybridTime.TIME, "yPosition", "Time (sec)", "Y Position (m)", null, false);
		figure.addChart(0, 1, traj, HybridTime.TIME, "yVelocity", "Time (sec)", "Y Velocity (m/s)", null, false);
		return figure;
	}
}
