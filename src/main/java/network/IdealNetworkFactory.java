package network;

import org.jgrapht.graph.DirectedPseudograph;

import edu.ucsc.cross.hse.network.NetworkFactory;

/**
 * Basic network factory to create instances of the direct network.
 * 
 * @author Brendan Short
 *
 * @param <N>
 *            node type
 */
public class IdealNetworkFactory<N>
		implements NetworkFactory<N, IdealConnection<N>, DirectedPseudograph<N, IdealConnection<N>>> {

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
	public IdealConnection<N> createEdge(N source, N target) {
		return new IdealConnection<N>(source, target);
	}

	/**
	 * Create an instance of a network topology graph.
	 * 
	 * @return the topology graph
	 */
	@Override
	public DirectedPseudograph<N, IdealConnection<N>> createTopology() {
		return new DirectedPseudograph<N, IdealConnection<N>>(new IdealNetworkFactory<N>());
	}

}