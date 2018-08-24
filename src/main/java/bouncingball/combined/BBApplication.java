
package bouncingball.combined;

import edu.ucsc.cross.hse.core.environment.HSEnvironment;
import edu.ucsc.cross.hse.core.figure.Figure;
import edu.ucsc.cross.hse.core.modeling.HybridSys;

public class BBApplication {

	/**
	 * Main method for running application
	 */
	public static void main(String args[]) {

		BBApplication appBB = new BBApplication();
		appBB.exportPreparedApp();

	}

	@Override
	public void configureSettings(HSEnvironment environment) {

	}

	@Override
	public void loadContents(HSEnvironment environment) {

		BouncingBallComponentLib.generateSingleBall(environment, .95, 9.81, 1.0, 1.0);
	}

	public static void generateSingleBall(HSEnvironment environment, double elasticity, double gravity, double ypos_0,
			double yvel_0) {

		// Initialize bouncing ball parameters (restitution, gravity)
		BouncingBallParameters params = new BouncingBallParameters(elasticity, gravity);
		// Initialize three bouncing ball systems
		HybridSys<BouncingBallState> systemA = new HybridSys<BouncingBallState>(new BouncingBallState(ypos_0, yvel_0),
				new Fp(params), new Gp(params), new Cp(), new Dp(), params);
		// Add bouncing ball system to environment
		environment.getSystems().add(systemA);
	}

	@Override
	public void executeSimulation(HSEnvironment environment) {

		environment.run(20.0, 200);
	}

	@Override
	public void processData(HSEnvironment environment) {

		Figure figure = BouncingBallPlotLibrary.generateVerticalStateFigure(environment);
		figure.display();

	}
}
