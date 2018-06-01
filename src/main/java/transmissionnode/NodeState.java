package transmissionnode;

import edu.ucsc.cross.hse.core.modeling.DataStructure;

public class NodeState extends DataStructure {

	public double packetsReceived; // total transmissions received
	public double packetsTransmitted; // total data transmitted
	public double transmissionTimer; // time remaining until transmission complete

	public NodeState(double packets_received, double packets_transmitted, double transmission_timer) {
		packetsReceived = packets_received;
		packetsTransmitted = packets_transmitted;
		transmissionTimer = transmission_timer;
	}
}
