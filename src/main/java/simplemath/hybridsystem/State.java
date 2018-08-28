
package simplemath.hybridsystem;

import edu.ucsc.cross.hse.core.modeling.DataStructure;

/**
 * A data structure defining the state of a bouncing ball.
 */
public class State extends DataStructure {

	/**
	 * value
	 */
	public double value;

	/**
	 * Construct the state with the given initial conditions
	 * 
	 * @param value
	 *            initial value
	 * 
	 */
	public State(double value) {

		this.value = value;
	}

}
