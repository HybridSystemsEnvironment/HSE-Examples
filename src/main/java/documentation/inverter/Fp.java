
package documentation.inverter;

import edu.ucsc.cross.hse.core.modeling.FlowMap;

public class Fp implements FlowMap<InverterState> {

	/**
	 * inverter parameters
	 */
	public InverterParameters params;

	/**
	 * Constructor for the agent system
	 * 
	 * 
	 * @param params
	 *            inverter parameters
	 */
	public Fp(InverterParameters params) {

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
	public void F(InverterState x, InverterState x_dot) {

		x_dot.p = 0.0;
		x_dot.q = 0.0;
		x_dot.iL = (-params.R * x.iL - x.vC + params.V * x.q) / params.L;
		x_dot.vC = (x.iL - x.vC / params.Rl) / params.Cap;
	}
}