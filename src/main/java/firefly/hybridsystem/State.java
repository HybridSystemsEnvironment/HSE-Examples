
package firefly.hybridsystem;

import java.util.ArrayList;

import edu.ucsc.cross.hse.core.modeling.DataStructure;
import edu.ucsc.cross.hse.core.modeling.HybridSys;

/**
 * Firefly state implementation
 */
public class State extends DataStructure {

	/**
	 * Timer state value
	 */
	public double tau;

	/**
	 * Constructor
	 * 
	 * @param tau_0
	 *            initial timer value
	 */
	public State(double tau_0) {

		this.tau = tau_0;
	}

	/**
	 * Get parent hybrid system that this state belongs to
	 * 
	 * @param parents
	 *            list of potential parent systems
	 * @return system with this state if found, null if no parent found
	 */
	public HybridSys<State> getParentSystem(ArrayList<HybridSys<State>> parents) {

		for (HybridSys<State> parent : parents) {

			if (parent.getState().equals(this)) {

				return parent;
			}
		}
		return null;
	}
}
