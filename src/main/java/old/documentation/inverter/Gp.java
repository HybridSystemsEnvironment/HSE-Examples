
package old.documentation.inverter;

import edu.ucsc.cross.hse.core.modeling.JumpMap;

public class Gp implements JumpMap<InverterState> {

	/**
	 * Inverter parameters
	 */
	public InverterParameters p;

	/**
	 * Constructor for the inverter system
	 * 
	 * @param params
	 *            inverter parameters
	 */
	public Gp(InverterParameters params) {

		this.p = params;

	}

	/**
	 * Jump map
	 * 
	 * @param x
	 *            current state
	 * @param x_dot
	 *            updated state
	 */
	@Override
	public void G(InverterState x, InverterState x_plus) {

		Double pplus = x.p;
		Double qplus = x.q;
		Double iLPlus = x.iL;
		Double vCplus = x.vC;

		// useful expressions
		Double V = Math.pow((x.iL / p.a), 2) + Math.pow((x.vC / p.b), 2);

		if (x.p == 1.0) // when Hfw is in the loop
		{
			pplus = x.p; // p dosen't switch from 2 to 1

			if (V <= p.ci && V >= p.cii) // when z in Si
			{
				if (x.iL <= 0.0) // && q ~= -1
				{
					qplus = -1.0; // switch q to -1 when lhp, Si
				} else// if iL >= 0 && q ~= 1
				{
					qplus = 1.0; // switch q to 1 when rhp, Si
				}

			} else if (V >= p.co && V <= p.coi) // when z in So
			{
				if (x.iL * x.vC * p.FIc <= 0.0 || (x.iL * x.vC * p.FIc >= 0 && Math.abs(x.iL) >= p.eps)) // when z ~in M
				{
					if (x.iL <= 0.0) // && q ~= 1
					{
						qplus = 1.0; // switch q to 1 when lhp, So
					} else if (x.iL >= 0) // && q ~= -1
					{
						qplus = -1.0; // switch q to -1 when rhp, So
					} else {
						qplus = x.q;
					}
				} else// if iL * vC * FIc >= 0 && abs(iL) <= eps //&& q ~= 0 // when z in M
				{
					qplus = 0.0;
				}
			}

			else {
				qplus = x.q;
			}
		} else if (x.p == 2) // when Hg is in the loop
		{
			if (V >= p.ci && V <= p.co) // switch to Hfw when in K
			{
				pplus = 1.0;
				qplus = x.q;
			} else if (V >= p.co && x.q != 0) // switch to q = 0 when outside So
			{
				pplus = x.p;
				qplus = 0.0;
			} else if (V <= p.ci && x.q == 0) // switch to q = 1 or -1 when inside Si
			{
				pplus = x.p;
				Double signCalc = Math.random() - 0.5;
				if (signCalc > 0.0) {
					qplus = 1.0;
				} else {
					qplus = -1.0;
				}
			} else {
				pplus = x.p;
				qplus = x.q;
			}
		} else {
			pplus = x.p;
			qplus = x.q;
		}

		x_plus.q = qplus;
		x_plus.p = pplus;
		x_plus.iL = iLPlus;
		x_plus.vC = vCplus;

	}
}
