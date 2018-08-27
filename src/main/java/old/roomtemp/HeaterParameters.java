
package old.roomtemp;

import edu.ucsc.cross.hse.core.modeling.DataStructure;

public class HeaterParameters extends DataStructure {

	public double outsideTemp;

	public double hysteresisRange;

	public double setTemp;

	public double heaterCapacity;

	public HeaterParameters(double heater_capacity, double outside_temp, double hysteresis_range, double set_temp) {

		heaterCapacity = heater_capacity;
		outsideTemp = outside_temp;
		hysteresisRange = hysteresis_range;
		setTemp = set_temp;
	}
}