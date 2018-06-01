package transmissionnode;

import org.jgrapht.Graph;

import edu.ucsc.cross.hse.core.logging.Console;
import edu.ucsc.cross.hse.network.NetworkFactory;

/**
 * The base network object that defines the topology as a graph with nodes of
 * class N and connections of class C. A network factory is initially required
 * in order to construct the topology graph. This class can be used as a
 * standalone object, or extended to implement an object with additional
 * functionality.
 * 
 * @author Brendan Short
 *
 * @param <N>
 *            the node object class
 * @param <C>
 *            the connection object class
 * @param <G>
 *            the graph object class, which must be one of the graph classes
 *            provided by the jgrapht library.
 */
public class Network<N, C, G extends Graph> {

	/**
	 * The network topology graph
	 */
	private G topology;

	/**
	 * Network constructor with specified topology
	 * 
	 * @param topology
	 *            the network topology to be used
	 */
	public Network(G topology) {
		this.topology = topology;
	}

	/**
	 * Get the network topology graph
	 * 
	 * @return the network topology graph
	 */
	public G getTopology() {
		return topology;
	}

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

	/**
	 * Static network constructor
	 * 
	 * @param factory
	 *            to create the network
	 * @return network object
	 */
	public static <A, B, C extends

	Graph<A, B>> Network<A, B, C> create(NetworkFactory<A, B, C> factory) {
		return Network.create(factory);
	}
}
