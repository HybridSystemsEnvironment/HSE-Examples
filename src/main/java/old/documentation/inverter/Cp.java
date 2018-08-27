
package old.documentation.inverter;

import edu.ucsc.cross.hse.core.modeling.FlowSet;

public class Cp implements FlowSet<InverterState> {

	/**
	 * inverter parameters
	 */
	public InverterParameters p;

	/**
	 * Constructor for the agent system
	 * 
	 * @param params
	 *            inverter parameters
	 */
	public Cp(InverterParameters params) {

		this.p = params;
	}

	/**
	 * Flow set
	 * 
	 * @param x
	 *            current state
	 */
	@Override
	public boolean C(InverterState x) {

		Double V = Math.pow((x.iL / p.a), 2) + Math.pow((x.vC / p.b), 2);
		// System.out.println(XMLParser.serializeObject(x));

		if (x.p == 1) // when Hfw is in the loop
		{
			if (V >= p.ci && V <= p.co) {
				return true; // Hfw flow within K
			} else if (V <= p.ci && V >= p.cii && x.iL * x.q >= 0 && x.q != 0) {
				return true; // Hfw flow when q is correct (and Hg flow?)
			} else if (V >= p.co && V <= p.coi && x.iL * x.q <= 0) {
				return true;
			}

		} else if (x.p == 2.0) {
			if (x.q == 0 && V >= p.co) {
				return true; // Hg flow when outside of So, q = 0
			} else if (x.q != 0 && V <= p.ci) {
				return true; // Hg flow when inside of Si, q = 1 or -1
			}
		}
		return false;
	}

}
