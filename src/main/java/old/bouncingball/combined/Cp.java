
package old.bouncingball.combined;

import edu.ucsc.cross.hse.core.modeling.FlowSet;

public class Cp implements FlowSet<BouncingBallState> {

	@Override
	public boolean C(BouncingBallState x) {

		return x.yPosition >= 0.0;
	}

}
