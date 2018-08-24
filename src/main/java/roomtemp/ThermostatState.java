
package roomtemp;

import edu.ucsc.cross.hse.core.modeling.DataStructure;

public class ThermostatState extends DataStructure {

	public double thermostatState;

	public ThermostatState(double heater_state) {

		thermostatState = heater_state;
	}

}
