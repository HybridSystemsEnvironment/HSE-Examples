
package network;

import java.util.ArrayList;

import org.jgrapht.graph.DirectedPseudograph;

import edu.ucsc.cross.hse.core.logging.Console;
import edu.ucsc.cross.hse.core.modeling.DataStructure;
import edu.ucsc.cross.hse.core.network.Connection;
import edu.ucsc.cross.hse.core.network.Network;

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
public class IdealNetwork<N> implements Network<N, Connection<N>, DirectedPseudograph<N, Connection<N>>> {

	private DirectedPseudograph<N, Connection<N>> topology;

	/**
	 * Constructor for a new network
	 */
	public IdealNetwork() {

		topology = new DirectedPseudograph<N, Connection<N>>(
				EdgeFactoryBuilder.createFactory(new IdealConnection<N>(null, null)));

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
		for (Connection<N> connection : getTopology().edgesOf(source)) {
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

	@Override
	/**
	 * Connect two nodes
	 * 
	 * @param source
	 *            node
	 * @param target
	 *            node
	 */
	public void connect(N source, N target) {

		try {
			if (source != null && target != null) {
				if (!topology.containsVertex(source)) {
					topology.addVertex(source);
				}
				if (!topology.containsVertex(target)) {
					topology.addVertex(target);
				}
				topology.addEdge(source, target);
			}
		} catch (Exception badConnection) {
			Console.error("Unable to connect " + source + " and " + target, badConnection);
		}
	}

	/**
	 * Connect two nodes
	 * 
	 * @param source
	 *            node
	 * @param target
	 *            node
	 */
	public void connectMultiple(N source, @SuppressWarnings("unchecked") N... targets) {

		for (N target : targets) {
			try {
				if (source != null && target != null) {
					if (!topology.containsVertex(source)) {
						topology.addVertex(source);
					}
					if (!topology.containsVertex(target)) {
						topology.addVertex(target);
					}
					topology.addEdge(source, target);
				}
			} catch (Exception badConnection) {
				Console.error("Unable to connect " + source + " and " + target, badConnection);
			}
		}
	}

	@Override
	public void disconnect(N arg0, N arg1) {

	}

	@Override
	public DirectedPseudograph<N, Connection<N>> getTopology() {

		return topology;
	}

}
