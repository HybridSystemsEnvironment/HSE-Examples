
package inverter.hybridsystem;

import edu.ucsc.cross.hse.core.modeling.FlowMap;

public class Fp implements FlowMap<State> {

	/**
	 * Consensus network parameters
	 */
	public Parameters params;

	/**
	 * Constructor for the agent system
	 * 
	 * @param state
	 *            agent state
	 * @param network
	 *            agent network (shared)
	 * @param params
	 *            consensus network parameters
	 */
	public Fp(Parameters params) {

		this.params = params;

	}

	/**
	 * Flow map
	 * 
	 * @param x
	 *            current state
	 * @param x_dot
	 *            state derivative
	 */
	@Override
	public void F(State x, State x_dot) {

		x_dot.p = 0.0;
		x_dot.q = 0.0;
		x_dot.iL = (-params.R * x.iL - x.vC + params.V * x.q) / params.L;
		x_dot.vC = (x.iL - x.vC / params.Rl) / params.Cap;
	}
}