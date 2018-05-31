package inverter;

import edu.ucsc.cross.hse.core.modeling.DataStructure;

/**
 * Implementation of the hybrid inverter state.
 * 
 * @author Brendan Short
 *
 */
public class InverterState extends DataStructure {
	/**
	 * Supervisor state variable p=1 indicates H_fw in the loop, p=2 indicates H_g
	 * in the loop
	 */
	public double p;
	/**
	 * Logic variable indicating position of switches q in (-1,0,1)
	 */
	public double q;
	/**
	 * Current through inductor L
	 */
	public double iL;
	/**
	 * Output voltage signal of the inverter
	 */
	public double vC;

	/**
	 * Constructor for the inverter state
	 * 
	 * @param p
	 * @param q
	 * @param iL
	 * @param vC
	 */
	public InverterState(double p, double q, double iL, double vC) {
		this.p = p;
		this.q = q;
		this.iL = iL;
		this.vC = vC;

	}
}
