package network;

import java.util.ArrayList;

import org.jgrapht.graph.DirectedPseudograph;

import edu.ucsc.cross.hse.core.modeling.DataStructure;
import edu.ucsc.cross.hse.network.Network;

/**
 * This class implements a basic network that connects elements of a specific
 * type N
 * 
 * @author Brendan Short
 * 
 * @param <N>
 *            the type of objects connected by the network
 *
 */
public class IdealNetwork<N> extends Network<N, IdealConnection<N>, DirectedPseudograph<N, IdealConnection<N>>> {

	/**
	 * Constructor for a new network
	 */
	public IdealNetwork() {

		super(new IdealNetworkFactory<N>());
	}

	/**
	 * Get all of the objects connected to a specified object
	 * 
	 * @param source
	 *            source vertex
	 * @return array list containing all objects that the source is connected to
	 */
	public ArrayList<N> getConnections(N source) {
		ArrayList<N> connections = new ArrayList<N>();
		for (IdealConnection<N> connection : getTopology().edgesOf(source)) {
			connections.add(connection.getTarget());
		}
		return connections;
	}

	/**
	 * Get all of the vertices in the network
	 * 
	 * @return array list containing all objects that are vertices of the network
	 */
	public ArrayList<N> getAllVertices() {
		ArrayList<N> connections = new ArrayList<N>();
		for (N connection : getTopology().vertexSet()) {
			connections.add(connection);
		}
		return connections;
	}

	public static void main(String args[]) {
		IdealNetwork<DataStructure> network = new IdealNetwork<DataStructure>();
		DataStructure origin = null;
		DataStructure target = null;
		network.connect(origin, target);
	}
}
