
package timer;

import edu.ucsc.cross.hse.core.modeling.DataStructure;

public class TimerState extends DataStructure {

	public double value;

	public TimerState(double initial_value) {

		value = initial_value;
	}
}
