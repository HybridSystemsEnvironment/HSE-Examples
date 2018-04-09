package inverter;

import java.io.File;

import org.jfree.chart.ChartPanel;

import edu.ucsc.cross.hse.core.chart.ChartUtils;
import edu.ucsc.cross.hse.core.environment.EnvironmentSettings;
import edu.ucsc.cross.hse.core.environment.ExecutionParameters;
import edu.ucsc.cross.hse.core.environment.HSEnvironment;
import edu.ucsc.cross.hse.core.figure.Figure;
import edu.ucsc.cross.hse.core.figure.GraphicFormat;
import edu.ucsc.cross.hse.core.file.DataFormat;
import edu.ucsc.cross.hse.core.logging.Console;
import edu.ucsc.cross.hse.core.logging.ConsoleSettings;
import edu.ucsc.cross.hse.core.modeling.HybridSystem;
import edu.ucsc.cross.hse.core.specification.DomainPriority;
import edu.ucsc.cross.hse.core.specification.IntegratorType;
import edu.ucsc.cross.hse.core.trajectory.HybridTime;
import edu.ucsc.cross.hse.core.trajectory.TrajectorySet;

public class InverterNew
{

	public static void main(String args[])
	{
		runInverterSimulation();
	}

	public static void runInverterSimulation()
	{
		HSEnvironment env = getConfiguredEnvironment();
		InverterSystem s = getTestInverterSystem();
		env.getSystems().add(s);
		TrajectorySet solution = env.run();
		generateQuadFigure(solution).exportToFile(new File("test_outputs/trial1_plot"), GraphicFormat.EPS);//.display();
		solution.exportToCSVFile(new File("test_outputs/trial1_csv.csv"));
		env.saveToFile(new File("test_outputs/trial1_data"), DataFormat.HSE);
	}

	public static ExecutionParameters getExecutionParameters()
	{
		ExecutionParameters params = new ExecutionParameters();
		params.maximumJumps = 4000000;
		params.maximumTime = 0.3;
		params.dataPointInterval = .005;
		return params;
	}

	public static EnvironmentSettings getEnvironmentSettings()
	{
		EnvironmentSettings settings = new EnvironmentSettings();
		settings.odeMinimumStepSize = .5E-9;
		settings.odeMaximumStepSize = .5E-5;
		settings.odeSolverAbsoluteTolerance = 1.0e-7;
		settings.odeRelativeTolerance = 1.0e-12;
		settings.eventHandlerMaximumCheckInterval = .0000000001;
		settings.eventHandlerConvergenceThreshold = .0000000001;
		settings.maxEventHandlerIterations = 22;
		settings.integratorType = IntegratorType.DORMAND_PRINCE_853;
		settings.domainPriority = DomainPriority.JUMP;
		settings.storeNonPrimativeData = false;
		return settings;
	}

	public static ConsoleSettings getConsoleSettings()
	{
		ConsoleSettings console = new ConsoleSettings();
		console.printStatusInterval = 4.0;
		console.printProgressIncrement = 10;
		console.printIntegratorExceptions = true;
		console.printInfo = true;
		console.printDebug = false;
		console.printWarning = true;
		console.printError = true;
		console.printLogToFile = true;
		console.terminateAtInput = true;
		return console;
	}

	public static InverterSystem getTestInverterSystem()
	{
		InverterParameters params = InverterParameters.idealParameters(.05);
		Double p0 = 1.0;
		Double q0 = 1.0;
		//Double c = params.ci + Math.random() * (params.co - params.ci);
		Double vIn0 = params.V;
		Double iL0 = params.a * 1.0;
		Double vC0 = 0.0;//params.b * Math.sqrt(c - Math.pow((iL0 / params.a), 2));
		InverterState state = new InverterState(p0, q0, iL0, vC0, vIn0);
		InverterSystem invSys = new InverterSystem(state, params);
		return invSys;
	}

	public static Figure generateQuadFigure(TrajectorySet solution)
	{
		Figure figure = new Figure(1200, 1200);

		ChartPanel pA = ChartUtils.createPanel(solution, "iL", "vC");
		ChartPanel sA = ChartUtils.createPanel(solution, HybridTime.TIME, "p");
		ChartPanel tA = ChartUtils.createPanel(solution, HybridTime.TIME, "q");
		ChartPanel pV = ChartUtils.createPanel(solution, HybridTime.TIME, "iL");
		ChartPanel sV = ChartUtils.createPanel(solution, HybridTime.TIME, "vC");
		ChartPanel tV = ChartUtils.createPanel(solution, HybridTime.TIME, "tau");

		figure.addComponent(1, 0, pA);
		figure.addComponent(2, 0, sA);
		figure.addComponent(3, 0, tA);
		figure.addComponent(1, 1, pV);
		figure.addComponent(2, 1, sV);
		figure.addComponent(3, 1, tV);

		ChartUtils.configureLabels(pA, "iL", "vC", null, false);
		ChartUtils.configureLabels(sA, "Time (sec)", "p", null, false);
		ChartUtils.configureLabels(tA, "Time (sec)", "q", null, false);
		ChartUtils.configureLabels(pV, "Time (sec)", "iL", null, false);
		ChartUtils.configureLabels(sV, "Time (sec)", "vC", null, false);
		ChartUtils.configureLabels(tV, "Time (sec)", "tau", null, false);

		figure.getTitle().setText("Hybrid Inverter Simulation Results");
		return figure;
	}

	public static HSEnvironment getConfiguredEnvironment(HybridSystem<?>... systems)
	{
		HSEnvironment env = new HSEnvironment();
		env.loadSettings(getEnvironmentSettings());
		env.loadParameters(getExecutionParameters());
		env.getSystems().add(systems);
		Console.setSettings(getConsoleSettings());
		HSEnvironment loadedEnv = HSEnvironment.loadFromFile(new File("savedEnv"));
		return env;
	}
}
