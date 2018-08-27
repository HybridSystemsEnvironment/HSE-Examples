package old.consensus;

import edu.ucsc.cross.hse.core.modeling.DataStructure;

/**
 * The consensus network agent state.
 * 
 * @author Brendan Short
 *
 */
public class ConsensusAgentState extends DataStructure
{

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
	public ConsensusAgentState(double system_value, double controller_value, double timer_value)
	{
		this.systemValue = system_value;
		this.controllerValue = controller_value;
		this.communicationTimer = timer_value;
	}
}
