
package documentation.consensus;

import java.util.ArrayList;

import edu.ucsc.cross.hse.core.modeling.DataStructure;
import edu.ucsc.cross.hse.core.modeling.HybridSys;

/**
 * The consensus network agent state.
 * 
 * @author Brendan Short
 *
 */
public class AgentState extends DataStructure {

	/**
	 * System value that is the target of the consensus
	 */
	public double systemValue;

	/**
	 * Controller value that drives the system value
	 */
	public double controllerValue;

	/**
	 * Communication timer that triggers the communication events
	 */
	public double communicationTimer;

	/**
	 * Constructor for agent state
	 * 
	 * @param system_value
	 *            initial system value
	 * @param controller_value
	 *            initial controller value
	 * @param timer_value
	 *            initial timer value
	 */
	public AgentState(double system_value, double controller_value, double timer_value) {

		this.systemValue = system_value;
		this.controllerValue = controller_value;
		this.communicationTimer = timer_value;
	}

	/**
	 * Get parent hybrid system that this state belongs to
	 * 
	 * @param parents
	 *            list of potential parent systems
	 * @return system with this state if found, null if no parent found
	 */
	public HybridSys<AgentState> getParentSystem(ArrayList<HybridSys<AgentState>> parents) {

		for (HybridSys<AgentState> parent : parents) {
			if (parent.getState().equals(this)) {
				return parent;
			}
		}
		return null;
	}
}
