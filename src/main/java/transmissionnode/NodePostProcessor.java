package transmissionnode;

import java.util.HashMap;

import com.be3short.obj.modification.XMLParser;

import edu.ucsc.cross.hse.core.trajectory.HybridTime;
import edu.ucsc.cross.hse.core.trajectory.HybridTrajectory;
import edu.ucsc.cross.hse.core.trajectory.TrajectorySet;

public class NodePostProcessor {

	public static HashMap<NodeState, Double> computeMaxTransmissionTimes(TrajectorySet trajectories) {
		// Get all node state trajectories
		HashMap<NodeState, HybridTrajectory<NodeState>> nodeTrajectoryMap = trajectories
				.getHybridTrajectoryMapByObject(NodeState.class);

		// initialize maximum transmission time mapping;
		HashMap<NodeState, Double> maxTransmissionTime = new HashMap<NodeState, Double>();

		// iterate through node states that have stored trajectories
		for (NodeState state : nodeTrajectoryMap.keySet()) {
			// get tge trajectory for the current node state
			HybridTrajectory<NodeState> stateTrajectory = nodeTrajectoryMap.get(state);
			// compute the maximum transmission time of the node
			Double maxTransmitTime = computeMaxTransmitTime(stateTrajectory);
			// store the maximum transmission time in the mapping
			maxTransmissionTime.put(state, maxTransmitTime);
		}
		System.out.println(XMLParser.serializeObject(maxTransmissionTime));
		return maxTransmissionTime;
	}

	public static Double computeMaxTransmitTime(HybridTrajectory<NodeState> nodeTrajectory) {
		// initialize max time counter
		Double maxTime = 0.0;
		// initialize transmit start time
		Double transmitStartTime = nodeTrajectory.getInitialTime().getTime();
		// initialize transmit count
		double transsionIndex = nodeTrajectory.getDataPoint(nodeTrajectory.getInitialTime()).packetsTransmitted;
		// get time domain state mapping
		HashMap<HybridTime, NodeState> trajectoryMap = nodeTrajectory.getDataPointMap();
		// iterate through time domain
		for (HybridTime domainTime : nodeTrajectory.getTimeDomain()) {
			// get state at domain time
			NodeState currentState = trajectoryMap.get(domainTime);
			// check if transmission has completed
			if (transsionIndex != currentState.packetsTransmitted) {
				// compute transmission time
				Double transmitTime = (domainTime.getTime() - transmitStartTime);
				// check if transmission time is greater than previous max
				if (transmitTime > maxTime) {
					System.out.println(transmitTime);
					// store new maximum transmission time
					maxTime = transmitTime;
				}
				// store next transmission start time
				transmitStartTime = domainTime.getTime();
				// store new transmission index
				transsionIndex = currentState.packetsTransmitted;
			}
		}
		return maxTime;
	}
}
