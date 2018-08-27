
package inverter.hybridsystem;

import edu.ucsc.cross.hse.core.modeling.JumpSet;

public class Dp implements JumpSet<State> {

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
	public Dp(Parameters params) {

		this.params = params;
	}

	/**
	 * Jump set
	 * 
	 * @param x
	 *            current state
	 */
	@Override
	public boolean D(State x) {

		Double V = Math.pow((x.iL / params.a), 2) + Math.pow((x.vC / params.b), 2);
		if (x.p == 1.0) {
			if (V <= params.ci && V >= params.cii && (x.q * x.iL) <= 0.0) // switch q for z in Si
			{
				return true;
			} else if (V >= params.co && V <= params.coi && (x.q * x.iL) >= 0.0) // switch q for z in So
			{
				if (x.iL * x.vC * params.FIc <= 0.0 || (x.iL * x.vC * params.FIc >= 0.0 && Math.abs(x.iL) >= params.eps)) {
					return true;
				} else if (x.iL * x.vC * params.FIc >= 0 && Math.abs(x.iL) <= params.eps && x.q != 0.0) // switch q for z in M
				{
					return true;
				}
			}
		} else if (x.p == 2.0) {
			if (V >= params.ci && x.q != 0) // Hg switch for outside Si
			{
				return true;
			} else if (x.q == 0.0 && V <= params.co) // Hg switch for inside So
			{
				return true;
			}
		}
		return false;

	}

}
