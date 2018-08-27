
package old.roomtemp;

import java.util.ArrayList;
import java.util.Arrays;

import org.jfree.chart.ChartPanel;

import edu.ucsc.cross.hse.core.chart.ChartUtils;
import edu.ucsc.cross.hse.core.environment.HSEnvironment;
import edu.ucsc.cross.hse.core.figure.Figure;
import edu.ucsc.cross.hse.core.network.Connection;
import edu.ucsc.cross.hse.core.network.Network;
import edu.ucsc.cross.hse.core.trajectory.HybridTime;
import edu.ucsc.cross.hse.core.trajectory.TrajectorySet;
import old.netproperties.ConnectSystems;
import old.netproperties.Latency;
import old.netproperties.TransmittedSignal;
import old.samplehold.SampleHoldState;
import old.samplehold.SampleHoldSystem;

/**
 * A consensus network application that prepares and operates the environment,
 * and generates a figure.
 * 
 * @author Brendan Short
 *
 */
public class RoomTempApplication {

	/**
	 * Main method for running application
	 * 
	 * @param args
	 *            none
	 */
	public static void main(String args[]) {

		main4();

	}

	/**
	 * Main method for running application
	 * 
	 * @param args
	 *            none
	 */
	public static void main1() {

		// Initialize environment
		HSEnvironment environment = new HSEnvironment();
		// Initialize consensus parameters (gain, min comm time, max comm time,sync)
		HeaterParameters params = new HeaterParameters(120, 30, 3.0, 60);
		// Initialize agent states (system val, controller val, timer val)
		RoomTempState room = new RoomTempState(40.0);
		ThermostatState therm = new ThermostatState(0.0);
		RoomTempSystem roomSys = new RoomTempSystem(room, params, null);
		ThermostatSystem thermSys = new ThermostatSystem(therm, params, null);
		roomSys.connectThermostatSignal(thermSys);
		thermSys.connectRoomTemperature(roomSys);

		// Add agent systems to environment
		environment.getSystems().add(roomSys, thermSys);
		// Run environment (max time duration, max jumps)
		environment.run(200.0, 200);
		// Generate a figure of the trajectories
		Figure figure = generateFullStateFigure(environment.getTrajectories());
		// Display the figure in new window
		figure.display();

	}

	/**
	 * Main method for running application
	 * 
	 * @param args
	 *            none
	 */
	public static void main2() {

		Network<Object> net = new Network<Object>(true);
		// Initialize environment
		HSEnvironment environment = new HSEnvironment();
		// Initialize consensus parameters (gain, min comm time, max comm time,sync)
		HeaterParameters params = new HeaterParameters(120, 30, 3.0, 60);
		// Initialize agent states (system val, controller val, timer val)
		RoomTempState room = new RoomTempState(40.0);
		ThermostatState therm = new ThermostatState(0.0);
		RoomTempSystem roomSys = new RoomTempSystem(room, params, null);
		ThermostatSystem thermSys = new ThermostatSystem(therm, params, null);

		TransmittedSignal<RoomTempState> roomSig = new TransmittedSignal<RoomTempState>(roomSys, net);
		TransmittedSignal<ThermostatState> thermSig = new TransmittedSignal<ThermostatState>(thermSys, net);
		roomSys.connectThermostatSignal(thermSig);
		thermSys.connectRoomTemperature(roomSig);
		Latency latency = new Latency(.1, .5);
		net.connect(roomSys, thermSys);
		Connection<Object> con = net.getTopology().getEdge(roomSys, thermSys);
		ArrayList<Object> props = new ArrayList<Object>(Arrays.asList(roomSig, thermSig, latency));
		con.setProperties(props);
		// Add agent systems to environment
		environment.getSystems().add(roomSys, thermSys);
		environment.getSystems().add(ConnectSystems.getConnectionSystems(net));
		// Run environment (max time duration, max jumps)
		environment.run(40.0, 200);
		// Generate a figure of the trajectories
		Figure figure = generateFullStateFigure(environment.getTrajectories());
		// Display the figure in new window
		figure.display();

	}

	/**
	 * Main method for running application
	 * 
	 * @param args
	 *            none
	 */
	public static void main3() {

		// Initialize environment
		HSEnvironment environment = new HSEnvironment();
		// Initialize consensus parameters (gain, min comm time, max comm time,sync)
		HeaterParameters params = new HeaterParameters(120, 30, 3.0, 60);
		// Initialize agent states (system val, controller val, timer val)
		RoomTempState room = new RoomTempState(40.0);
		ThermostatState therm = new ThermostatState(0.0);
		RoomTempSystem roomSys = new RoomTempSystem(room, params, null);
		ThermostatSystem thermSys = new ThermostatSystem(therm, params, null);
		SampleHoldState<RoomTempState> state = new SampleHoldState<RoomTempState>(new RoomTempState(0.0));
		state.timerValue = .1;
		SampleHoldSystem<RoomTempState> shrm = new SampleHoldSystem<RoomTempState>(state, roomSys);
		SampleHoldState<ThermostatState> shthstate = new SampleHoldState<ThermostatState>(thermSys.get());
		SampleHoldSystem<ThermostatState> shth = new SampleHoldSystem<ThermostatState>(shthstate, thermSys);
		roomSys.connectThermostatSignal(shth);
		thermSys.connectRoomTemperature(shrm);

		// Add agent systems to environment
		environment.getSystems().add(roomSys, thermSys, shrm, shth);// , shth);
		// Run environment (max time duration, max jumps)
		environment.run(40.0, 200);
		// Generate a figure of the trajectories
		Figure figure = generateFullStateFigure(environment.getTrajectories());
		// Display the figure in new window
		figure.display();

	}

	/**
	 * Main method for running application
	 * 
	 * @param args
	 *            none
	 */
	public static void main4() {

		Network<Object> net = new Network<Object>(true);
		// Initialize environment
		HSEnvironment environment = new HSEnvironment();
		// Initialize consensus parameters (gain, min comm time, max comm time,sync)
		HeaterParameters params = new HeaterParameters(120, 30, 3.0, 60);
		// Initialize agent states (system val, controller val, timer val)
		RoomTempState room = new RoomTempState(40.0);
		ThermostatState therm = new ThermostatState(0.0);
		RoomTempSystem roomSys = new RoomTempSystem(room, params, null);
		ThermostatSystem thermSys = new ThermostatSystem(therm, params, null);
		TransmittedSignal<RoomTempState> roomSig = new TransmittedSignal<RoomTempState>(roomSys, net);
		TransmittedSignal<ThermostatState> thermSig = new TransmittedSignal<ThermostatState>(thermSys, net);
		SampleHoldState<RoomTempState> state = new SampleHoldState<RoomTempState>(new RoomTempState(0.0));
		SampleHoldSystem<RoomTempState> shrm = new SampleHoldSystem<RoomTempState>(state, roomSig);
		SampleHoldState<ThermostatState> shthstate = new SampleHoldState<ThermostatState>(new ThermostatState(0.0));
		SampleHoldSystem<ThermostatState> shth = new SampleHoldSystem<ThermostatState>(shthstate, thermSig);
		TransmittedSignal<RoomTempState> roomSig2 = new TransmittedSignal<RoomTempState>(shrm, net);
		TransmittedSignal<ThermostatState> thermSig2 = new TransmittedSignal<ThermostatState>(shth, net);
		Latency latency = new Latency(.4, 2.5);
		net.connect(thermSys, shth);
		Connection<Object> con = net.getTopology().getEdge(thermSys, shth);
		ArrayList<Object> props = new ArrayList<Object>(Arrays.asList(thermSig, latency));
		con.setProperties(props);
		net.connect(roomSys, shrm);
		Connection<Object> con2 = net.getTopology().getEdge(roomSys, shrm);
		ArrayList<Object> props2 = new ArrayList<Object>(Arrays.asList(roomSig, latency));
		con2.setProperties(props2);
		net.connect(shrm, thermSys);
		Connection<Object> con3 = net.getTopology().getEdge(shrm, thermSys);
		ArrayList<Object> props3 = new ArrayList<Object>(Arrays.asList(thermSig2, latency));
		con3.setProperties(props3);
		net.connect(shth, roomSys);
		Connection<Object> con4 = net.getTopology().getEdge(shth, roomSys);
		ArrayList<Object> props4 = new ArrayList<Object>(Arrays.asList(roomSig2, latency));
		con4.setProperties(props4);
		roomSys.connectThermostatSignal(thermSig2);
		thermSys.connectRoomTemperature(roomSig2);
		// Add agent systems to environment
		environment.getSystems().add(roomSys, thermSys, shrm, shth);
		environment.getSystems().add(ConnectSystems.getConnectionSystems(net));
		// Run environment (max time duration, max jumps)
		environment.run(140.0, 200);
		// Generate a figure of the trajectories
		Figure figure = generateFullStateFigure(environment.getTrajectories());
		// Display the figure in new window
		figure.display();

	}

	/**
	 * Generate a figure with all state elements
	 * 
	 * @param solution
	 *            trajectory set containing data to load into figure
	 * @return a figure displaying all state elements
	 */
	public static Figure generateFullStateFigure(TrajectorySet solution) {

		// Create figure w:800 h:600
		Figure figure = new Figure(800, 600);
		// Assign title to figure
		figure.getTitle().setText("Temp in Room");
		// Create charts
		ChartPanel xPos = ChartUtils.createPanel(solution, HybridTime.TIME, "thermostatState");
		ChartPanel yPos = ChartUtils.createPanel(solution, HybridTime.TIME, "temp");
		ChartPanel timer = ChartUtils.createPanel(solution, HybridTime.TIME, "timerValue");
		ChartPanel del = ChartUtils.createPanel(solution, HybridTime.TIME, "value");
		// Label chart axis and configure legend visibility
		ChartUtils.configureLabels(xPos, "Time (sec)", "Thermostat", null, false);
		ChartUtils.configureLabels(yPos, "Time (sec)", "Temperature", null, false);
		ChartUtils.configureLabels(timer, "Time (sec)", "Sample", null, false);
		ChartUtils.configureLabels(del, "Time (sec)", "Delay", null, false);
		// Add charts to figure
		figure.addComponent(0, 0, xPos);
		figure.addComponent(0, 1, yPos);
		figure.addComponent(0, 2, timer);
		figure.addComponent(0, 3, del);
		// Return generated figure
		return figure;
	}

	/**
	 * Generate a figure with all state elements
	 * 
	 * @param solution
	 *            trajectory set containing data to load into figure
	 * @return a figure displaying all state elements
	 */
	public static Figure generateFullStateFigurez(TrajectorySet solution) {

		// Create figure w:800 h:600
		Figure figure = new Figure(800, 600);
		// Assign title to figure
		figure.getTitle().setText("Consensus Network Simulation");
		// Create charts
		ChartPanel xPos = ChartUtils.createPanel(solution, HybridTime.TIME, "systemValue");
		ChartPanel yPos = ChartUtils.createPanel(solution, HybridTime.TIME, "controllerValue");
		ChartPanel xVel = ChartUtils.createPanel(solution, HybridTime.TIME, "communicationTimer");
		// Label chart axis and configure legend visibility
		ChartUtils.configureLabels(xPos, "Time (sec)", "System Value", null, false);
		ChartUtils.configureLabels(yPos, "Time (sec)", "Controller Value", null, false);
		ChartUtils.configureLabels(xVel, "Time (sec)", "Communication Timer", null, false);
		// Add charts to figure
		figure.addComponent(0, 0, xPos);
		figure.addComponent(0, 1, xVel);
		figure.addComponent(0, 2, yPos);
		// Return generated figure
		return figure;
	}
}