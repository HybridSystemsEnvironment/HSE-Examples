
package old.bouncingball.combined;

import edu.ucsc.cross.hse.core.modeling.JumpSet;

public class Dp implements JumpSet<BouncingBallState> {

	@Override
	public boolean D(BouncingBallState x) {

		return x.yPosition <= 0.0 && x.yVelocity < 0.0;
	}

}
