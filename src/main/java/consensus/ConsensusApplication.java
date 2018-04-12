package consensus;

import java.util.ArrayList;

import org.jfree.chart.ChartPanel;

import edu.ucsc.cross.hse.core.chart.ChartUtils;
import edu.ucsc.cross.hse.core.environment.EnvironmentSettings;
import edu.ucsc.cross.hse.core.environment.ExecutionParameters;
import edu.ucsc.cross.hse.core.environment.HSEnvironment;
import edu.ucsc.cross.hse.core.figure.Figure;
import edu.ucsc.cross.hse.core.logging.Console;
import edu.ucsc.cross.hse.core.logging.ConsoleSettings;
import edu.ucsc.cross.hse.core.modeling.SystemSet;
import edu.ucsc.cross.hse.core.specification.DomainPriority;
import edu.ucsc.cross.hse.core.specification.IntegratorType;
import edu.ucsc.cross.hse.core.trajectory.HybridTime;
import edu.ucsc.cross.hse.core.trajectory.TrajectorySet;
import network.BasicNetwork;

public class ConsensusApplication
{

	/**
	 * Main method for running application
	 * 
	 * @param args
	 *            none
	 */
	public static void main(String args[])
	{
		loadConsensusConsoleSettings();
		HSEnvironment environment = generateEnvironment();
		environment.run();
		generateFullStateFigure(environment.getTrajectories()).display();
	}

	/**
	 * Generate the environment
	 * 
	 * @return environment
	 */
	public static HSEnvironment generateEnvironment()
	{
		HSEnvironment environment = new HSEnvironment();
		SystemSet systems = generateConsensusAgentSystems(25, 5, false, .4, .3, .3, 1.0);
		ExecutionParameters parameters = getConsensusExeParameters();
		EnvironmentSettings settings = getBouncingBallEnvSettings();

		environment = HSEnvironment.create(systems, parameters, settings);

		return environment;
	}

	/**
	 * Initializes execution parameters
	 * 
	 * @return execution parameters
	 */
	public static ExecutionParameters getConsensusExeParameters()
	{
		ExecutionParameters parameters = new ExecutionParameters();
		parameters.maximumJumps = 4000000;
		parameters.maximumTime = 10.0;
		parameters.dataPointInterval = .05;
		return parameters;
	}

	/**
	 * Initializes environment settings
	 * 
	 * @return environment settings
	 */
	public static EnvironmentSettings getBouncingBallEnvSettings()
	{
		EnvironmentSettings settings = new EnvironmentSettings();
		settings.odeMinimumStepSize = 1E-9;
		settings.odeMaximumStepSize = 1E-3;
		settings.odeSolverAbsoluteTolerance = 1E-6;
		settings.odeRelativeTolerance = 1E-6;
		settings.eventHandlerMaximumCheckInterval = 1E-3;
		settings.eventHandlerConvergenceThreshold = 1E-9;
		settings.maxEventHandlerIterations = 100;
		settings.integratorType = IntegratorType.DORMAND_PRINCE_853;
		settings.domainPriority = DomainPriority.JUMP;
		settings.storeNonPrimativeData = false;
		return settings;
	}

	/**
	 * Initializes and loads console settings
	 * 
	 * @return console settings
	 */
	public static void loadConsensusConsoleSettings()
	{
		ConsoleSettings console = new ConsoleSettings();
		console.printStatusInterval = 10.0;
		console.printProgressIncrement = 10;
		console.printIntegratorExceptions = false;
		console.printInfo = true;
		console.printDebug = false;
		console.printWarning = true;
		console.printError = true;
		console.printLogToFile = true;
		console.terminateAtInput = true;
		Console.setSettings(console);
	}

	/**
	 * Generates consensus agent systems with randomized values and connections
	 * 
	 * @param num_nodes
	 *            number of agent systems to generate
	 * @param num_connections
	 *            number of connections for each agent
	 * @param synchronous
	 *            flag indicating synchronous if true, or asynchronous if false
	 * @param flow_gain
	 *            flow gain constant for the control law
	 * @param jump_gain
	 *            jump gain constant for the control law
	 * @param minimum_comm
	 *            minimum duration between communication events
	 * @param maximum_comm
	 *            maximum duration between communication events
	 * @return set of agent systems
	 */
	public static SystemSet generateConsensusAgentSystems(int num_nodes, int num_connections, boolean synchronous,
	double jump_gain, double flow_gain, double min_communication_time, double max_communication_time)
	{

		SystemSet systems = new SystemSet();
		BasicNetwork<ConsensusAgentState> network = new BasicNetwork<ConsensusAgentState>();
		ConsensusParameters params = new ConsensusParameters(flow_gain, jump_gain, min_communication_time,
		max_communication_time, synchronous);

		for (int i = 0; i < num_nodes; i++)
		{
			ConsensusAgentState agent = new ConsensusAgentState(Math.random(), Math.random(), Math.random() + .05);
			network.topology.addVertex(agent);
			ConsensusAgentSystem system = new ConsensusAgentSystem(agent, network, params);
			systems.add(system);

		}

		connectAgentsRandomly(network, num_connections);

		return systems;

	}

	/**
	 * Connects each agent in a network to a specified number of other agents at
	 * random
	 * 
	 * @param network
	 *            network containing all agents as vertices
	 * @param num_connections
	 *            number of connections to assign to each agent
	 */
	public static void connectAgentsRandomly(BasicNetwork<ConsensusAgentState> network, int num_connections)
	{
		ArrayList<ConsensusAgentState> conns = new ArrayList<ConsensusAgentState>(network.getAllVertices());

		for (ConsensusAgentState node : network.getAllVertices())
		{
			for (int coni = 0; coni < num_connections; coni++)
			{
				ConsensusAgentState connect = conns.get(0);
				while (connect.equals(node))
				{
					connect = conns.get(Math.round(conns.size()) - 1);
				}
				network.establishConnection(node, connect);
				conns.remove(connect);
				if (conns.size() <= 1)
				{
					conns.clear();
					conns.addAll(network.getAllVertices());
				}
			}
		}
	}

	/**
	 * Generate a figure with all state elements
	 * 
	 * @param solution
	 *            trajectory set containing data to load into figure
	 * @return a figure displaying all state elements
	 */
	public static Figure generateFullStateFigure(TrajectorySet solution)
	{
		Figure figure = new Figure(800, 600);

		ChartPanel xPos = ChartUtils.createPanel(solution, HybridTime.TIME, "systemValue");
		ChartPanel yPos = ChartUtils.createPanel(solution, HybridTime.TIME, "controllerValue");
		ChartPanel xVel = ChartUtils.createPanel(solution, HybridTime.TIME, "communicationTimer");

		figure.addComponent(0, 0, xPos);
		figure.addComponent(0, 1, xVel);
		figure.addComponent(0, 2, yPos);

		ChartUtils.configureLabels(xPos, "Time (sec)", "System Value", null, false);
		ChartUtils.configureLabels(yPos, "Time (sec)", "Controller Value", null, false);
		ChartUtils.configureLabels(xVel, "Time (sec)", "Communication Timer", null, false);

		return figure;
	}
}
