
package network;

import edu.ucsc.cross.hse.core.modeling.DataStructure;
import edu.ucsc.cross.hse.core.network.Connection;

/**
 * The basic connection is a graph edge that directly connects two objects of
 * class N.
 * 
 * @author Brendan Short
 * 
 * @param <N>
 *            The type of objects that are connected
 */
public class IdealConnection<N> extends DataStructure implements Connection<N> {

	/*
	 * Source vertex
	 */
	private N source;

	/*
	 * Target vertex
	 */
	private N target;

	/**
	 * Constructor for the connection from specified source to destination
	 * 
	 * @param sourceVertex
	 *            start vertex
	 * @param targetVertex
	 *            end vertex
	 */
	public IdealConnection(N sourceVertex, N targetVertex) {

		this.source = sourceVertex;
		this.target = targetVertex;

	}

	/**
	 * Get source vertex
	 * 
	 * @return source vertex
	 */
	public N getSource() {

		return source;
	}

	/**
	 * Get target vertex
	 * 
	 * @return target vertex
	 */
	public N getTarget() {

		return target;
	}

	@Override
	public Connection<N> createConnection(N arg0, N arg1) {

		// TODO Auto-generated method stub
		return new IdealConnection<N>(arg0, arg1);
	}

	@Override
	public boolean terminateConnection() {

		// TODO Auto-generated method stub
		return false;
	}

}