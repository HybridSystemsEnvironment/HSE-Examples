package inverter;

public class InverterParameters
{

	public double f;
	public double omega;
	public double R;
	public double L;
	public double Cap;
	public double Rl;
	public double e;
	public double co;
	public double ci;
	public double eps;
	public double coi;
	public double cii;
	public double V;
	public double a;
	public double b;
	public double FIc;

	public InverterParameters(double f, double R, double L, double Cap, double V, double e, double b)
	{
		this.f = f;
		this.omega = f * 2 * Math.PI;
		this.R = R;
		this.L = L;
		this.Cap = Cap;
		this.Rl = Double.POSITIVE_INFINITY;
		this.V = V;
		this.b = b;
		this.e = e;
		Double delta = e * 0.1;
		co = 1 + e;
		ci = 1 - e;
		coi = 1 + e + delta;
		cii = 1 - e - delta;
		this.a = b * Cap * omega;
		if (L * Cap * Math.pow(omega, 2) >= 1)
		{
			this.FIc = -1.0;
		} else
		{
			this.FIc = 1.0;
		}

		/////// Double eps0 = a * Math.sqrt(co - ci) * Math.sqrt(Math.pow(b, 2) * (ci - co) + 4 * V * (V - b * Math.sqrt(ci)))/ (2 * V);
		////// Double r = 1.1;
		this.eps = a * Math.sqrt(co - ci);
	}

	public static InverterParameters idealParameters(Double e)
	{
		InverterParameters idealParams = new InverterParameters(60.0, 1.0, 0.1, 6.66 * .00001, 220.0, e, 120.0);
		return idealParams;
	}
}
