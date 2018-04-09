package inverter;

import edu.ucsc.cross.hse.core.modeling.DataStructure;

public class InverterState extends DataStructure
{

	public double p;
	public double q;
	public double iL;
	public double vC;
	public double tau;

	public InverterState(double p, double q, double iL, double vC, double tau)
	{
		this.p = p;
		this.q = q;
		this.iL = iL;
		this.vC = vC;
		this.tau = tau;

	}
}
