
package old.bouncingball.combined;

import edu.ucsc.cross.hse.core.environment.HSEnvironment;
import edu.ucsc.cross.hse.core.modeling.HybridSys;

public class BouncingBallComponentLib {

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
}
