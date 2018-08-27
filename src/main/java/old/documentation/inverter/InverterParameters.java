package old.documentation.inverter;

public class InverterParameters {

	/**
	 * Output signal frequency
	 */
	public double f;
	/**
	 * 
	 */
	public double omega;
	/**
	 * Resistor R
	 */
	public double R;
	/**
	 * Inductor L
	 */
	public double L;
	/**
	 * Capacitor C
	 */
	public double Cap;
	/**
	 * Load resistance
	 */
	public double Rl;
	/**
	 * Constant
	 */
	public double e;
	/**
	 * Outer tracking band boundary
	 */
	public double co;
	/**
	 * Inner tracking band boundary
	 */
	public double ci;
	public double eps;
	/**
	 * Inner tracking band tolerance
	 */
	public double coi;
	/**
	 * Inner tracking band tolerance
	 */
	public double cii;
	/**
	 * Output voltage
	 */
	public double V;
	/**
	 * Sinusoid constant a
	 */
	public double a;
	/**
	 * Sinusoid constant a
	 */
	public double b;
	/**
	 * 
	 */
	public double FIc;

	/**
	 * 
	 * @param f
	 *            output signal frequency
	 * @param R
	 *            resistance
	 * @param L
	 *            inductance
	 * @param Cap
	 *            capacitance
	 * @param V
	 *            output voltage
	 * @param e
	 *            constant
	 * @param b
	 *            constant
	 */
	public InverterParameters(double f, double R, double L, double Cap, double V, double e, double b) {
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
		if (L * Cap * Math.pow(omega, 2) >= 1) {
			this.FIc = -1.0;
		} else {
			this.FIc = 1.0;
		}

		this.eps = a * Math.sqrt(co - ci);
	}

	/**
	 * Parameters that produce decent results
	 * 
	 * @param e
	 *            constant
	 * @return ideal inverter parameters
	 */
	public static InverterParameters idealParameters(Double e) {
		InverterParameters idealParams = new InverterParameters(60.0, 1.0, 0.1, 6.66 * .00001, 220.0, e, 120.0);
		return idealParams;
	}
}
