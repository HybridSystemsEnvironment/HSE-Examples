package transmissionnode;

import edu.ucsc.cross.hse.core.modeling.HybridSystem;
import network.IdealConnection;
import network.IdealNetwork;

public class NodeSystem extends HybridSystem<NodeState> {

	public NodeParameters parameters;
	public IdealNetwork<NodeSystem> network;

	public NodeSystem(NodeState state, NodeParameters parameters, IdealNetwork<NodeSystem> network) {
		super(state);
		this.parameters = parameters;
		this.network = network;
	}

	@Override
	public boolean C(NodeState x) { // return flag indication that transmission timer hasn't expired
		return (x.transmissionTimer >= 0.0);
	}

	@Override
	public void F(NodeState x, NodeState x_dot) {
		// set derivative of transmission timer to -1.0 and counters to 0.0
		x_dot.transmissionTimer = -1.0;
		x_dot.packetsReceived = 0.0;
		x_dot.packetsTransmitted = 0.0;
	}

	@Override
	public boolean D(NodeState x) {
		// initialize flag by checking for jump
		boolean jump = (x.transmissionTimer <= 0.0);
		// iterate through incoming connections
		for (IdealConnection<NodeSystem> sys : network.getTopology().incomingEdgesOf(this)) {
			// check if incoming transmission received
			if (sys.getSource().getComponents().getState().transmissionTimer <= 0.0) {
				// flag occurance of a jump
				jump = true;
			}
		}
		return jump;
	}

	@Override
	public void G(NodeState x, NodeState x_plus) {
		// check if own transmission is to be sent
		if (x.transmissionTimer <= 0.0) {
			// increment packets transmitted counter and reset timer
			x_plus.packetsTransmitted += 1;
			x_plus.transmissionTimer = parameters.minTransmitTime
					+ Math.random() * (parameters.maxTransmitTime - parameters.minTransmitTime);
		}
		// iterate through incoming connections
		for (IdealConnection<NodeSystem> sys : network.getTopology().incomingEdgesOf(this)) {
			// check if incoming transmission received
			if (sys.getSource().getComponents().getState().transmissionTimer <= 0.0) { // increment number of packets
				// received
				x_plus.packetsReceived += 1;
			}
		}
	}
}