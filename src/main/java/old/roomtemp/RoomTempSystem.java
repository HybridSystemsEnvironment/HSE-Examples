
package old.roomtemp;

import edu.ucsc.cross.hse.core.modeling.HybridSystem;
import old.control.Signal;

public class RoomTempSystem extends HybridSystem<RoomTempState> implements Signal<RoomTempState> {

	Signal<ThermostatState> thermostatState;

	HeaterParameters parameters;

	// Constructor that loads data generator state and parameters
	public RoomTempSystem(RoomTempState state, HeaterParameters parameters, Signal<ThermostatState> thermostat_state) {

		super(state); // load data generator state
		this.thermostatState = thermostat_state;
		this.parameters = parameters; // load data generator parameters
		// heaterState = thermostat_state;
	}

	@Override
	public boolean C(RoomTempState x) {

		return true;
	}

	@Override
	public void F(RoomTempState x, RoomTempState x_dot) {

		x_dot.temp = -x.temp + parameters.heaterCapacity * thermostatState.get().thermostatState
				+ parameters.outsideTemp;
	}

	@Override
	public boolean D(RoomTempState x) {

		return false;
	}

	@Override
	public void G(RoomTempState x, RoomTempState x_plus) {

		x_plus = x;
	}

	@Override
	public RoomTempState get() {

		return getComponents().getState();
	}

	public void connectThermostatSignal(Signal<ThermostatState> therm) {

		this.thermostatState = therm;
	}

}