
package roomtemp;

import control.Signal;
import edu.ucsc.cross.hse.core.modeling.HybridSystem;

/*
 * Hybrid system model of a data generator, consisting of a timer that increments a memory state upon each expiration.
 * This is a simple way to emulate a periodic data source such as a sensor or a data query routine.
 */
public class ThermostatSystem extends HybridSystem<ThermostatState> implements Signal<ThermostatState> {

	Signal<RoomTempState> roomTemperature;

	HeaterParameters parameters;

	// Constructor that loads data generator state and parameters
	public ThermostatSystem(ThermostatState state, HeaterParameters parameters, Signal<RoomTempState> room_state) {

		super(state); // load data generator state
		this.parameters = parameters;
		this.roomTemperature = room_state;
	}

	@Override
	public boolean C(ThermostatState x) {

		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void F(ThermostatState x, ThermostatState x_dot) {

	}

	@Override
	public boolean D(ThermostatState x) {

		if (parameters.setTemp - roomTemperature.get().temp > parameters.hysteresisRange
				&& getComponents().getState().thermostatState == 0.0) {
			return true;
		} else if (roomTemperature.get().temp - parameters.setTemp > parameters.hysteresisRange
				&& getComponents().getState().thermostatState == 1.0) {
			return true;
		} else {
			return false;
		}
	}

	@Override
	public void G(ThermostatState x, ThermostatState x_plus) {

		if (parameters.setTemp - roomTemperature.get().temp > parameters.hysteresisRange
				&& getComponents().getState().thermostatState == 0.0) {
			x_plus.thermostatState = 1.0;
		} else if (roomTemperature.get().temp - parameters.setTemp > parameters.hysteresisRange
				&& getComponents().getState().thermostatState == 1.0) {
			x_plus.thermostatState = 0.0;
		}
	}

	@Override
	public ThermostatState get() {

		return this.getComponents().getState();
	}

	/**
	 * @param roomTemperature
	 *            the roomTemperature to set
	 */
	public void connectRoomTemperature(Signal<RoomTempState> roomTemperature) {

		this.roomTemperature = roomTemperature;
	}

}
