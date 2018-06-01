package transmissionnode;

import edu.ucsc.cross.hse.core.modeling.DataStructure;

public class NodeParameters extends DataStructure {

	public double minTransmitTime; // minimum transmission time
	public double maxTransmitTime; // maximum transmission time

	public NodeParameters(double min_transmit_time, double max_transmit_time) {

		minTransmitTime = min_transmit_time;
		maxTransmitTime = max_transmit_time;
	}
}
