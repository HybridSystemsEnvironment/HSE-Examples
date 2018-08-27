
package old.bouncingball.combined;

import edu.ucsc.cross.hse.core.modeling.FlowMap;

public class Fp implements FlowMap<BouncingBallState> {

	public BouncingBallParameters parameters;

	public Fp(BouncingBallParameters parameters) {

		this.parameters = parameters;
	}

	@Override
	public void F(BouncingBallState x, BouncingBallState x_dot) {

		x_dot.yPosition = x.yVelocity;
		x_dot.yVelocity = -parameters.gravityConstant;
	}

}
