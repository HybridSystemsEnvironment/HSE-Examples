
package old.samplehold;

import edu.ucsc.cross.hse.core.modeling.DataStructure;
import edu.ucsc.cross.hse.core.variable.Variable;

public class SampleHoldState<X> extends DataStructure {

	public FixedVar<X> holdValue;

	public double timerValue;

	public SampleHoldState(X hold_value) {

		holdValue = new FixedVar<X>(hold_value);
		timerValue = 0.0;
	}

	public static class FixedVar<C> implements Variable<C> {

		public C value;

		public FixedVar(C hold_value) {

			value = (hold_value);

		}

		@Override
		public C getValue() {

			// TODO Auto-generated method stub
			return value;
		}

	}
}
