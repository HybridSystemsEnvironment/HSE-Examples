
package documentation.inverter;

import documentation.stockeval.HSEApplication;
import edu.ucsc.cross.hse.core.environment.HSEnvironment;
import edu.ucsc.cross.hse.core.figure.Figure;
import edu.ucsc.cross.hse.core.integrator.DormandPrince853IntegratorFactory;

/**
 * A hybrid inverter application that prepares and operates the environment, and
 * generates a figure.
 * 
 * @author Brendan Short
 *
 */
public class InverterApp extends HSEApplication {

	/**
	 * Main method for running application
	 * 
	 * @param args
	 *            none
	 */
	public static void main(String args[]) {

		InverterApp app = new InverterApp();
		app.executeTasks();

	}

	@Override
	public void configureSettings(HSEnvironment environment) {

		// Specify integrator parameter values (need smaller step sizes)
		double odeMinimumStepSize = 1e-12;
		double odeMaximumStepSize = 1e-7;
		double odeRelativeTolerance = 1.0e-6;
		double odeSolverAbsoluteTolerance = 1.0e-6;
		// Create and store integrator factory
		DormandPrince853IntegratorFactory integrator = new DormandPrince853IntegratorFactory(odeMaximumStepSize,
				odeMinimumStepSize, odeRelativeTolerance, odeSolverAbsoluteTolerance);
		environment.getSettings().integrator = integrator;

	}

	@Override
	public void loadContents(HSEnvironment environment) {

		// Initialize inverter parameters (f, R, L, Cap, V, e, b)
		InverterParameters parameters = new InverterParameters(60.0, 1.0, 0.1, 6.66e-5, 220.0, .05, 120.0);
		// Initialize inverter state (p0, q0, iL0, vC0)
		InverterState state = new InverterState(1.0, 1.0, 120 * 6.66e-5 * 60.0 * 2 * Math.PI, 0.0);
		InverterSystem system = new InverterSystem(state, parameters);
		// Add inverter system to environment
		environment.getSystems().add(system);

	}

	@Override
	public void processData(HSEnvironment environment) {

		// Generate a figure of the trajectories
		Figure figure = InverterPlotLib.generateFullStateFigure(environment.getTrajectories());
		// Display the figure in new window
		figure.display();

	}

	@Override
	public void executeSimulation(HSEnvironment environment) {

		environment.run(.08, 10000);
	}

}