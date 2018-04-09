package inverter;

import edu.ucsc.cross.hse.core.modeling.HybridSystem;

public class InverterSystem extends HybridSystem<InverterState>
{

	public InverterParameters p;

	public InverterSystem(InverterState state, InverterParameters params)
	{
		super(state);
		this.p = params;
		// TODO Auto-generated constructor stub
	}

	@Override
	public boolean C(InverterState x)
	{
		// Double V = Math.pow((x.iL / p.a), 2) + Math.pow((x.vC / p.b), 2);
		// // System.out.println(XMLParser.serializeObject(x));
		//
		// if (x.p == 1) // when Hfw is in the loop
		// {
		// if (V >= p.ci && V <= p.co)
		// {
		// return true; // Hfw flow within K
		// } else if (V <= p.ci && V >= p.cii && x.iL * x.q >= 0 && x.q != 0)
		// {
		// return true; // Hfw flow when q is correct (and Hg flow?)
		// } else if (V >= p.co && V <= p.coi && x.iL * x.q <= 0)
		// {
		// return true;
		// }
		//
		// } else if (x.p == 2.0)
		// {
		// if (x.q == 0 && V >= p.co)
		// {
		// return true; // Hg flow when outside of So, q = 0
		// } else if (x.q != 0 && V <= p.ci)
		// {
		// return true; // Hg flow when inside of Si, q = 1 or -1
		// }
		// }
		// // System.out.println("Ellipse value " + Math.sqrt(Math.pow(x.iL, 2) / p.a + Math.pow(x.vC, 2) / p.b));
		// // if (Math.sqrt(Math.pow(x.iL, 2) / p.a + Math.pow(x.vC, 2) / p.b) > 1.1)
		// {
		// // System.exit(0);
		// }
		// return false;
		return true;
	}

	@Override
	public void F(InverterState x, InverterState x_dot)
	{
		x_dot.p = 0.0;
		x_dot.q = 0.0;
		x_dot.iL = (-p.R * x.iL - x.vC + p.V * x.q) / p.L;
		x_dot.vC = (x.iL - x.vC / p.Rl) / p.Cap;
		x_dot.tau = 1.0;
	}

	@Override
	public boolean D(InverterState x)
	{

		Double V = Math.pow((x.iL / p.a), 2) + Math.pow((x.vC / p.b), 2);
		if (x.p == 1.0)
		{
			if (V <= p.ci && V >= p.cii && (x.q * x.iL) <= 0.0) // switch q for z in Si
			{
				return true;
			} else if (V >= p.co && V <= p.coi && (x.q * x.iL) >= 0.0) // switch q for z in So
			{
				if (x.iL * x.vC * p.FIc <= 0.0 || (x.iL * x.vC * p.FIc >= 0.0 && Math.abs(x.iL) >= p.eps))
				{
					return true;
				} else if (x.iL * x.vC * p.FIc >= 0 && Math.abs(x.iL) <= p.eps && x.q != 0.0) // switch q for z in M
				{
					return true;
				}
			}
		} else if (x.p == 2.0)
		{
			if (V >= p.ci && x.q != 0) // Hg switch for outside Si
			{
				return true;
			} else if (x.q == 0.0 && V <= p.co) // Hg switch for inside So
			{
				return true;
			}
		}
		return false;

	}

	@Override
	public void G(InverterState x, InverterState x_plus)
	{
		Double pplus = x.p;
		Double qplus = x.q;
		Double iLPlus = x.iL;
		Double tauplus = x.tau;
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
				// else
				// qplus = q;
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
					} else
					{
						qplus = x.q;
					}
				} else// if iL * vC * FIc >= 0 && abs(iL) <= eps //&& q ~= 0 // when z in M
				{
					qplus = 0.0;
				}
			}
			// else
			// qplus = q;

			else
			{
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
				if (signCalc > 0.0)
				{
					qplus = 1.0;
				} else
				{
					qplus = -1.0;
				}
			} else
			{
				pplus = x.p;
				qplus = x.q;
			}
		} else
		{
			pplus = x.p;
			qplus = x.q;
		}

		x_plus.q = qplus;
		x_plus.p = pplus;
		x_plus.iL = iLPlus;
		x_plus.tau = tauplus;
		x_plus.vC = vCplus;

	}

}
