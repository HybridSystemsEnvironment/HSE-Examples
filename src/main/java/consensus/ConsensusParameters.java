package consensus;

/**
 * Consensus network parameter data structure
 * 
 * @author Brendan Short
 *
 */
public class ConsensusParameters
{

	/**
	 * Flow gain constant for the control law
	 */
	public Double flowGain;

	/**
	 * Jump gain constant for the control law
	 */
	public Double jumpGain;

	/**
	 * Minimum duration between communication events
	 */
	public Double minimumCommunicationInterval;

	/**
	 * Maximum duration between communication events
	 */
	public Double maximumCommunicationInterval;

	/**
	 * Flag indicating if the agents are communication synchronously (true) or
	 * asynchrnouosly (false)
	 */
	public boolean synchronous;

	/**
	 * Constructor for consensus network parameters
	 * 
	 * @param flow_gain
	 *            flow gain constant for the control law
	 * @param jump_gain
	 *            jump gain constant for the control law
	 * @param minimum_comm
	 *            minimum duration between communication events
	 * @param maximum_comm
	 *            maximum duration between communication events
	 * @param synchronous
	 */
	public ConsensusParameters(Double flow_gain, Double jump_gain, Double minimum_comm, Double maximum_comm,
	boolean synchronous)
	{
		this.flowGain = flow_gain;
		this.jumpGain = jump_gain;
		this.minimumCommunicationInterval = minimum_comm;
		this.maximumCommunicationInterval = maximum_comm;
		this.synchronous = synchronous;
	}

}
