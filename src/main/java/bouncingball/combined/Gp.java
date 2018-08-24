
package bouncingball.combined;

import edu.ucsc.cross.hse.core.modeling.JumpMap;

public class Gp implements JumpMap<BouncingBallState> {

	public BouncingBallParameters parameters;

	public Gp(BouncingBallParameters parameters) {

		this.parameters = parameters;
	}

	@Override
	public void G(BouncingBallState x, BouncingBallState x_plus) {

		x_plus.yPosition = 0.0;
		x_plus.yVelocity = -x.yVelocity * parameters.restitutionCoefficient;
	}

}
