package network;

import java.util.ArrayList;

import org.jgrapht.graph.DirectedPseudograph;

import edu.ucsc.cross.hse.network.Network;
import edu.ucsc.cross.hse.network.NetworkFactory;

/**
 * This class implements a basic network that connects elements of a specific
 * type T
 * 
 * @author Brendan Short
 * 
 * @param <N>
 *            the class type of the objects connected by the network
 *
 */
public class DirectNetwork<N> extends Network<N, DirectConnection<N>, DirectedPseudograph<N, DirectConnection<N>>> {

	/**
	 * Constructor for a new network
	 */
	public DirectNetwork() {

		super(new BasicNetworkFactory<N>());
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
		for (DirectConnection<N> connection : getTopology().edgesOf(source)) {
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

	/**
	 * Basic network factory to create instances of the direct network.
	 * 
	 * @author Brendan Short
	 *
	 * @param <N>
	 *            node type
	 */
	private static class BasicNetworkFactory<N>
			implements NetworkFactory<N, DirectConnection<N>, DirectedPseudograph<N, DirectConnection<N>>> {

		/**
		 * Creates a new edge whose endpoints are the specified source and target
		 * vertices.
		 *
		 * @param source
		 *            the source vertex.
		 * @param target
		 *            the target vertex.
		 *
		 * @return a new edge whose endpoints are the specified source and target
		 *         vertices.
		 */
		@Override
		public DirectConnection<N> createEdge(N source, N target) {
			return new DirectConnection<N>(source, target);
		}

		/**
		 * Create an instance of a network topology graph.
		 * 
		 * @return the topology graph
		 */
		@Override
		public DirectedPseudograph<N, DirectConnection<N>> createTopology() {
			return new DirectedPseudograph<N, DirectConnection<N>>(new BasicNetworkFactory<N>());
		}

	}
}
